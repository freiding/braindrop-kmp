package by.freiding.braindrop.feature.vocabulary.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.freiding.braindrop.core.common.AppClock
import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.vocabulary.domain.usecase.GetChunkDetailUseCase
import by.freiding.braindrop.feature.vocabulary.domain.usecase.GradeChunkUseCase
import by.freiding.braindrop.feature.vocabulary.domain.usecase.SetChunkLearnedUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

class VocabularyChunkDetailViewModel(
    private val chunkId: String,
    private val getDetail: GetChunkDetailUseCase,
    private val gradeChunk: GradeChunkUseCase,
    private val setLearned: SetChunkLearnedUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(
        VocabularyChunkDetailUiState(today = LocalDate.parse(AppClock.todayIso())),
    )
    val state: StateFlow<VocabularyChunkDetailUiState> = _state.asStateFlow()

    private val _effects = MutableSharedFlow<VocabularyChunkDetailUiEffect>()
    val effects: SharedFlow<VocabularyChunkDetailUiEffect> = _effects.asSharedFlow()

    init {
        loadDetail()
    }

    fun reload() = loadDetail()

    fun onEvent(event: VocabularyChunkDetailUiEvent) {
        when (event) {
            is VocabularyChunkDetailUiEvent.Grade -> viewModelScope.launch {
                gradeChunk(chunkId, event.grade)
                _effects.emit(VocabularyChunkDetailUiEffect.NavigateBack)
            }
            is VocabularyChunkDetailUiEvent.ToggleLearned -> viewModelScope.launch {
                val learned = _state.value.item
                    ?.progress
                    ?.isLearned == true
                setLearned(chunkId, !learned)
                loadDetail()
            }
            is VocabularyChunkDetailUiEvent.NavigateBack -> viewModelScope.launch {
                _effects.emit(VocabularyChunkDetailUiEffect.NavigateBack)
            }
        }
    }

    private fun loadDetail() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            when (val result = getDetail(chunkId)) {
                is Result.Success -> _state.update {
                    it.copy(
                        isLoading = false,
                        item = result.data,
                        today = LocalDate.parse(AppClock.todayIso()),
                    )
                }
                is Result.Error -> _state.update { it.copy(isLoading = false, error = result.exception.message) }
            }
        }
    }
}
