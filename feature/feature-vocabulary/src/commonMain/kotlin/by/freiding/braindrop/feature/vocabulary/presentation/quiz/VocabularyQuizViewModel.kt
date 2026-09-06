package by.freiding.braindrop.feature.vocabulary.presentation.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.vocabulary.domain.model.VocabularyQuizMode
import by.freiding.braindrop.feature.vocabulary.domain.model.VocabularyQuizQuestion
import by.freiding.braindrop.feature.vocabulary.domain.usecase.EvaluateVocabularyAnswerUseCase
import by.freiding.braindrop.feature.vocabulary.domain.usecase.GenerateVocabularyQuizUseCase
import by.freiding.braindrop.feature.vocabulary.domain.usecase.GetVocabularyStreakDaysUseCase
import by.freiding.braindrop.feature.vocabulary.domain.usecase.SubmitVocabularyQuizAnswerUseCase
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

class VocabularyQuizViewModel(
    private val mode: VocabularyQuizMode,
    private val generateQuiz: GenerateVocabularyQuizUseCase,
    private val evaluateAnswer: EvaluateVocabularyAnswerUseCase,
    private val submitAnswer: SubmitVocabularyQuizAnswerUseCase,
    private val getStreakDays: GetVocabularyStreakDaysUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(VocabularyQuizUiState())
    val state: StateFlow<VocabularyQuizUiState> = _state.asStateFlow()

    private val _effects = MutableSharedFlow<VocabularyQuizUiEffect>()
    val effects: SharedFlow<VocabularyQuizUiEffect> = _effects.asSharedFlow()

    private var tickerJob: Job? = null

    init {
        loadQuiz()
    }

    fun onEvent(event: VocabularyQuizUiEvent) {
        when (event) {
            is VocabularyQuizUiEvent.OptionSelected -> onOptionSelected(event.option)
            is VocabularyQuizUiEvent.TypedAnswerChanged ->
                if (!_state.value.checked) _state.update { it.copy(typedAnswer = event.value) }
            is VocabularyQuizUiEvent.RevealHint -> _state.update {
                it.copy(
                    hintLevel = (it.hintLevel + 1).coerceAtMost(2),
                )
            }
            is VocabularyQuizUiEvent.GiveUp -> onGiveUp()
            is VocabularyQuizUiEvent.CheckTyping -> onCheckTyping()
            is VocabularyQuizUiEvent.Next -> advance()
            is VocabularyQuizUiEvent.Restart -> loadQuiz()
            is VocabularyQuizUiEvent.RetryMistakes ->
                loadQuiz(restrictToChunkIds = _state.value.mistakes.map { it.chunk.id })
            is VocabularyQuizUiEvent.MistakeClicked -> viewModelScope.launch {
                _effects.emit(VocabularyQuizUiEffect.NavigateToDetail(event.chunkId))
            }
            is VocabularyQuizUiEvent.NavigateBack -> viewModelScope.launch {
                _effects.emit(VocabularyQuizUiEffect.NavigateBack)
            }
        }
    }

    private fun onOptionSelected(option: String) {
        val question = _state.value.currentQuestion ?: return
        if (_state.value.checked) return
        val correct = evaluateAnswer(question, option)
        record(question, correct, option)
    }

    private fun onCheckTyping() {
        val question = _state.value.currentQuestion ?: return
        val answer = _state.value.typedAnswer
        if (_state.value.checked || answer.isBlank()) return
        record(question, evaluateAnswer(question, answer), answer.trim())
    }

    private fun onGiveUp() {
        val question = _state.value.currentQuestion ?: return
        if (_state.value.checked) return
        record(
            question,
            isCorrect = false,
            userAnswer = _state.value.typedAnswer
                .trim()
                .ifBlank { "—" },
        )
    }

    private fun record(
        question: VocabularyQuizQuestion,
        isCorrect: Boolean,
        userAnswer: String,
    ) {
        viewModelScope.launch {
            val result = submitAnswer(question.chunk.id, isCorrect)
            if (result is Result.Success) _state.update { it.copy(scheduled = it.scheduled + result.data) }
        }
        _state.update { state ->
            state.copy(
                checked = true,
                lastAnswerCorrect = isCorrect,
                selectedOption = userAnswer.takeIf { state.isCloze },
                score = if (isCorrect) state.score + 1 else state.score,
                mistakes = if (isCorrect) {
                    state.mistakes
                } else {
                    state.mistakes + VocabularyQuizMistake(question.chunk, userAnswer, question.correctAnswer)
                },
            )
        }
    }

    private fun advance() {
        val next = _state.value.currentIndex + 1
        if (next >= _state.value.total) {
            finish()
        } else {
            _state.update {
                it.copy(
                    currentIndex = next,
                    selectedOption = null,
                    typedAnswer = "",
                    hintLevel = 0,
                    checked = false,
                    lastAnswerCorrect = false,
                )
            }
        }
    }

    private fun finish() {
        tickerJob?.cancel()
        _state.update { it.copy(isFinished = true) }
        viewModelScope.launch {
            val result = getStreakDays()
            if (result is Result.Success) _state.update { it.copy(streakDays = result.data) }
        }
    }

    private fun loadQuiz(restrictToChunkIds: List<String>? = null) {
        tickerJob?.cancel()
        viewModelScope.launch {
            _state.update { VocabularyQuizUiState(isLoading = true) }
            when (val result = generateQuiz(mode, restrictToChunkIds = restrictToChunkIds)) {
                is Result.Success -> {
                    _state.update { it.copy(isLoading = false, questions = result.data) }
                    if (result.data.isNotEmpty()) startTicker()
                }
                is Result.Error -> _state.update { it.copy(isLoading = false, error = result.exception.message) }
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
