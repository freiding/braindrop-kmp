package by.freiding.braindrop.feature.irregularverbs.presentation.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.irregularverbs.domain.model.QuizType
import by.freiding.braindrop.feature.irregularverbs.domain.usecase.GenerateQuizUseCase
import by.freiding.braindrop.feature.irregularverbs.domain.usecase.SubmitQuizAnswerUseCase
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
) : ViewModel() {

    private val _state = MutableStateFlow(QuizUiState())
    val state: StateFlow<QuizUiState> = _state.asStateFlow()

    private val _effects = MutableSharedFlow<QuizUiEffect>()
    val effects: SharedFlow<QuizUiEffect> = _effects.asSharedFlow()

    init {
        loadQuiz()
    }

    fun onEvent(event: QuizUiEvent) {
        when (event) {
            is QuizUiEvent.AnswerSelected -> handleAnswerSelected(event.answer)
            is QuizUiEvent.NextQuestion -> advanceToNext()
            is QuizUiEvent.RestartQuiz -> loadQuiz()
            is QuizUiEvent.NavigateBack -> viewModelScope.launch {
                _effects.emit(QuizUiEffect.NavigateBack)
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
            state.copy(
                selectedAnswer = answer,
                score = if (isCorrect) state.score + 1 else state.score,
            )
        }
    }

    private fun advanceToNext() {
        val state = _state.value
        val nextIndex = state.currentIndex + 1
        if (nextIndex >= state.totalQuestions) {
            _state.update { it.copy(isFinished = true) }
        } else {
            _state.update { it.copy(currentIndex = nextIndex, selectedAnswer = null) }
        }
    }

    private fun loadQuiz() {
        viewModelScope.launch {
            _state.update { QuizUiState(isLoading = true) }
            when (val result = generateQuiz(quizType)) {
                is Result.Success -> _state.update {
                    it.copy(isLoading = false, questions = result.data)
                }
                is Result.Error -> _state.update {
                    it.copy(isLoading = false, error = result.exception.message)
                }
            }
        }
    }
}
