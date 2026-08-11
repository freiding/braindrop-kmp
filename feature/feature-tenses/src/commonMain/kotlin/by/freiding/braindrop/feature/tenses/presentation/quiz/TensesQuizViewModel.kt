package by.freiding.braindrop.feature.tenses.presentation.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.tenses.domain.model.TenseQuizType
import by.freiding.braindrop.feature.tenses.domain.usecase.GenerateTenseQuizUseCase
import by.freiding.braindrop.feature.tenses.domain.usecase.GetStreakDaysUseCase
import by.freiding.braindrop.feature.tenses.domain.usecase.GetTensesUseCase
import by.freiding.braindrop.feature.tenses.domain.usecase.SubmitTenseQuizAnswerUseCase
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

class TensesQuizViewModel(
    private val quizType: TenseQuizType,
    private val generateQuiz: GenerateTenseQuizUseCase,
    private val submitAnswer: SubmitTenseQuizAnswerUseCase,
    private val getStreakDays: GetStreakDaysUseCase,
    private val getTenses: GetTensesUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(TensesQuizUiState())
    val state: StateFlow<TensesQuizUiState> = _state.asStateFlow()

    private val _effects = MutableSharedFlow<TensesQuizUiEffect>()
    val effects: SharedFlow<TensesQuizUiEffect> = _effects.asSharedFlow()

    private var tickerJob: Job? = null

    init {
        loadQuiz()
    }

    fun onEvent(event: TensesQuizUiEvent) {
        when (event) {
            is TensesQuizUiEvent.AnswerSelected -> handleAnswerSelected(event.answer)
            is TensesQuizUiEvent.NextQuestion -> advanceToNext()
            is TensesQuizUiEvent.RestartQuiz -> loadQuiz()
            is TensesQuizUiEvent.RetryMistakes ->
                loadQuiz(restrictToTenseIds = _state.value.mistakes.map { it.question.tenseId })
            is TensesQuizUiEvent.NavigateBack -> viewModelScope.launch {
                _effects.emit(TensesQuizUiEffect.NavigateBack)
            }
            is TensesQuizUiEvent.MistakeClicked -> viewModelScope.launch {
                _effects.emit(TensesQuizUiEffect.NavigateToTenseDetail(event.tenseId))
            }
        }
    }

    private fun handleAnswerSelected(answer: String) {
        val current = _state.value.currentQuestion ?: return
        if (_state.value.isAnswered) return

        val isCorrect = answer == current.correctAnswer
        viewModelScope.launch {
            submitAnswer(current.tenseId, isCorrect)
        }
        _state.update { state ->
            val history = state.answerHistory.toMutableList().apply { this[state.currentIndex] = isCorrect }
            state.copy(
                selectedAnswer = answer,
                score = if (isCorrect) state.score + 1 else state.score,
                answerHistory = history,
                mistakes = if (isCorrect) state.mistakes else state.mistakes + TenseQuizMistake(current, answer),
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

    private fun loadQuiz(restrictToTenseIds: List<String>? = null) {
        tickerJob?.cancel()
        viewModelScope.launch {
            _state.update { TensesQuizUiState(isLoading = true) }
            val tensesById = (getTenses() as? Result.Success)
                ?.data
                ?.associate { it.tense.id to it.tense }
                .orEmpty()
            when (val result = generateQuiz(quizType, restrictToTenseIds = restrictToTenseIds)) {
                is Result.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            questions = result.data,
                            answerHistory = List(result.data.size) { null },
                            tensesById = tensesById,
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
