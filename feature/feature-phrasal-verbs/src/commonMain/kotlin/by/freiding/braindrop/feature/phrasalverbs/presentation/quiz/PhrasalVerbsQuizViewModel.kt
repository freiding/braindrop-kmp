package by.freiding.braindrop.feature.phrasalverbs.presentation.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.phrasalverbs.domain.model.PhrasalVerbQuizType
import by.freiding.braindrop.feature.phrasalverbs.domain.usecase.EvaluatePhrasalVerbAnswerUseCase
import by.freiding.braindrop.feature.phrasalverbs.domain.usecase.GeneratePhrasalVerbQuizUseCase
import by.freiding.braindrop.feature.phrasalverbs.domain.usecase.GetPhrasalVerbStreakDaysUseCase
import by.freiding.braindrop.feature.phrasalverbs.domain.usecase.SubmitPhrasalVerbQuizAnswerUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PhrasalVerbsQuizViewModel(
    private val quizType: PhrasalVerbQuizType,
    private val generateQuiz: GeneratePhrasalVerbQuizUseCase,
    private val submitAnswer: SubmitPhrasalVerbQuizAnswerUseCase,
    private val getStreakDays: GetPhrasalVerbStreakDaysUseCase,
    private val evaluateAnswer: EvaluatePhrasalVerbAnswerUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(PhrasalVerbsQuizUiState())
    val state: StateFlow<PhrasalVerbsQuizUiState> = _state.asStateFlow()

    private val _effects = MutableSharedFlow<PhrasalVerbsQuizUiEffect>()
    val effects: SharedFlow<PhrasalVerbsQuizUiEffect> = _effects.asSharedFlow()

    private var tickerJob: Job? = null

    init {
        loadQuiz()
    }

    fun onEvent(event: PhrasalVerbsQuizUiEvent) {
        when (event) {
            is PhrasalVerbsQuizUiEvent.AnswerSelected -> handleAnswerSelected(event.answer)
            is PhrasalVerbsQuizUiEvent.NextQuestion -> advanceToNext()
            is PhrasalVerbsQuizUiEvent.RestartQuiz -> loadQuiz()
            is PhrasalVerbsQuizUiEvent.RetryMistakes ->
                loadQuiz(restrictToVerbIds = _state.value.mistakes.map { it.verb.id })
            is PhrasalVerbsQuizUiEvent.NavigateBack -> viewModelScope.launch {
                _effects.emit(PhrasalVerbsQuizUiEffect.NavigateBack)
            }
            is PhrasalVerbsQuizUiEvent.MistakeClicked -> viewModelScope.launch {
                _effects.emit(PhrasalVerbsQuizUiEffect.NavigateToDetail(event.verbId))
            }
        }
    }

    private fun handleAnswerSelected(answer: String) {
        val current = _state.value.currentQuestion ?: return
        if (_state.value.isAnswered) return

        val isCorrect = evaluateAnswer(current, answer)
        viewModelScope.launch { submitAnswer(current.verb.id, isCorrect) }

        _state.update { state ->
            val history = state.answerHistory.mapIndexed { i, v -> if (i == state.currentIndex) isCorrect else v }
            state.copy(
                selectedAnswer = answer,
                score = if (isCorrect) state.score + 1 else state.score,
                answerHistory = history,
                mistakes = if (isCorrect) {
                    state.mistakes
                } else {
                    state.mistakes + PhrasalVerbQuizMistake(current.verb, answer)
                },
            )
        }
    }

    private fun advanceToNext() {
        val nextIndex = _state.value.currentIndex + 1
        if (nextIndex >= _state.value.totalQuestions) {
            finishQuiz()
        } else {
            _state.update { it.copy(currentIndex = nextIndex, selectedAnswer = null) }
        }
    }

    private fun finishQuiz() {
        tickerJob?.cancel()
        _state.update { it.copy(isFinished = true) }
        viewModelScope.launch {
            val result = getStreakDays()
            if (result is Result.Success) {
                _state.update { it.copy(streakDays = result.data) }
            }
        }
    }

    private fun loadQuiz(restrictToVerbIds: List<String>? = null) {
        tickerJob?.cancel()
        viewModelScope.launch {
            _state.update { PhrasalVerbsQuizUiState(isLoading = true) }
            when (val result = generateQuiz(quizType, restrictToVerbIds = restrictToVerbIds)) {
                is Result.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            questions = result.data,
                            answerHistory = List(result.data.size) { null },
                        )
                    }
                    if (result.data.isNotEmpty()) startTicker()
                }
                is Result.Error -> _state.update {
                    it.copy(isLoading = false, error = result.exception.message)
                }
            }
        }
    }

    private fun startTicker() {
        tickerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                _state.update { it.copy(elapsedSeconds = it.elapsedSeconds + 1) }
            }
        }
    }

    override fun onCleared() {
        tickerJob?.cancel()
        super.onCleared()
    }
}
