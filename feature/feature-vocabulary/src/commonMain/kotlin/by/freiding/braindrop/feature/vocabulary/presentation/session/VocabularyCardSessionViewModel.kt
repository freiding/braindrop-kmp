package by.freiding.braindrop.feature.vocabulary.presentation.session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkProgress
import by.freiding.braindrop.feature.vocabulary.domain.srs.RecallGrade
import by.freiding.braindrop.feature.vocabulary.domain.usecase.GetDueChunksUseCase
import by.freiding.braindrop.feature.vocabulary.domain.usecase.GradeChunkUseCase
import by.freiding.braindrop.feature.vocabulary.domain.usecase.PreviewRecallIntervalsUseCase
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

class VocabularyCardSessionViewModel(
    private val getDueChunks: GetDueChunksUseCase,
    private val gradeChunk: GradeChunkUseCase,
    private val previewIntervals: PreviewRecallIntervalsUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(VocabularyCardSessionUiState())
    val state: StateFlow<VocabularyCardSessionUiState> = _state.asStateFlow()

    private val _effects = MutableSharedFlow<VocabularyCardSessionUiEffect>()
    val effects: SharedFlow<VocabularyCardSessionUiEffect> = _effects.asSharedFlow()

    private var tickerJob: Job? = null

    init {
        loadSession()
    }

    fun onEvent(event: VocabularyCardSessionUiEvent) {
        when (event) {
            is VocabularyCardSessionUiEvent.Reveal -> _state.update { it.copy(isRevealed = true) }
            is VocabularyCardSessionUiEvent.Grade -> handleGrade(event.grade)
            is VocabularyCardSessionUiEvent.Restart -> loadSession()
            is VocabularyCardSessionUiEvent.NavigateBack -> viewModelScope.launch {
                _effects.emit(VocabularyCardSessionUiEffect.NavigateBack)
            }
        }
    }

    private fun handleGrade(grade: RecallGrade) {
        val current = _state.value.currentCard ?: return
        viewModelScope.launch { gradeChunk(current.chunk.id, grade) }

        _state.update { state ->
            val history = state.gradeHistory + grade
            // "Не знаю" ⇒ the card returns later in the same session (box 0, due today).
            val requeued = if (grade == RecallGrade.DONT_KNOW) {
                state.queue + current.copy(progress = ChunkProgress(chunkId = current.chunk.id))
            } else {
                state.queue
            }
            val nextIndex = state.currentIndex + 1
            val finished = nextIndex >= requeued.size
            state.copy(
                queue = requeued,
                gradeHistory = history,
                currentIndex = nextIndex,
                isRevealed = false,
                isFinished = finished,
                intervalLabels = requeued.getOrNull(nextIndex)?.let { previewIntervals(it.progress) } ?: emptyMap(),
            )
        }
        if (_state.value.isFinished) tickerJob?.cancel()
    }

    private fun loadSession() {
        tickerJob?.cancel()
        viewModelScope.launch {
            _state.update { VocabularyCardSessionUiState(isLoading = true) }
            when (val result = getDueChunks()) {
                is Result.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            queue = result.data,
                            intervalLabels = result.data.firstOrNull()?.let { c -> previewIntervals(c.progress) }
                                ?: emptyMap(),
                        )
                    }
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
