package by.freiding.braindrop.feature.irregularverbs.presentation.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.irregularverbs.domain.model.QuizType
import by.freiding.braindrop.feature.irregularverbs.domain.usecase.GenerateQuizUseCase
import by.freiding.braindrop.feature.irregularverbs.domain.usecase.GetStreakDaysUseCase
import by.freiding.braindrop.feature.irregularverbs.domain.usecase.SubmitQuizAnswerUseCase
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

class QuizViewModel(
    private val quizType: QuizType,
    private val generateQuiz: GenerateQuizUseCase,
    private val submitAnswer: SubmitQuizAnswerUseCase,
    private val getStreakDays: GetStreakDaysUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(QuizUiState())
    val state: StateFlow<QuizUiState> = _state.asStateFlow()

    private val _effects = MutableSharedFlow<QuizUiEffect>()
    val effects: SharedFlow<QuizUiEffect> = _effects.asSharedFlow()

    private var tickerJob: Job? = null

    init {
        loadQuiz()
    }

    fun onEvent(event: QuizUiEvent) {
        when (event) {
            is QuizUiEvent.AnswerSelected -> handleAnswerSelected(event.answer)
            is QuizUiEvent.NextQuestion -> advanceToNext()
            is QuizUiEvent.RestartQuiz -> loadQuiz()
            is QuizUiEvent.RetryMistakes -> loadQuiz(restrictToVerbIds = _state.value.mistakes.map { it.verb.id })
            is QuizUiEvent.NavigateBack -> viewModelScope.launch {
                _effects.emit(QuizUiEffect.NavigateBack)
            }
            is QuizUiEvent.MistakeClicked -> viewModelScope.launch {
                _effects.emit(QuizUiEffect.NavigateToVerbDetail(event.verbId))
            }
        }
    }

    private fun handleAnswerSelected(answer: String) {
        val current = _state.value.currentQuestion ?: return
        if (_state.value.isAnswered) return

        val isCorrect = answer == current.correctAnswer
        viewModelScope.launch {
            submitAnswer(current.verb.id, isCorrect)
        }
        _state.update { state ->
            val history = state.answerHistory.toMutableList().apply { this[state.currentIndex] = isCorrect }
            state.copy(
                selectedAnswer = answer,
                score = if (isCorrect) state.score + 1 else state.score,
                answerHistory = history,
                mistakes = if (isCorrect) state.mistakes else state.mistakes + QuizMistake(current.verb, answer),
            )
        }
    }

    private fun advanceToNext() {
        val state = _state.value
        val nextIndex = state.currentIndex + 1
        if (nextIndex >= state.totalQuestions) {
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
            _state.update { QuizUiState(isLoading = true) }
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
