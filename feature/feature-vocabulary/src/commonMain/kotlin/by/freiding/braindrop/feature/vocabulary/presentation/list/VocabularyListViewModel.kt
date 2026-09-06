package by.freiding.braindrop.feature.vocabulary.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.freiding.braindrop.core.common.AppClock
import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkWithProgress
import by.freiding.braindrop.feature.vocabulary.domain.model.ReviewStatus
import by.freiding.braindrop.feature.vocabulary.domain.usecase.GetChunksUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

class VocabularyListViewModel(
    private val getChunks: GetChunksUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(VocabularyListUiState(today = LocalDate.parse(AppClock.todayIso())))
    val state: StateFlow<VocabularyListUiState> = _state.asStateFlow()

    private val _effects = MutableSharedFlow<VocabularyListUiEffect>()
    val effects: SharedFlow<VocabularyListUiEffect> = _effects.asSharedFlow()

    fun reload() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            when (val result = getChunks()) {
                is Result.Success -> _state.update { current ->
                    val today = LocalDate.parse(AppClock.todayIso())
                    refilter(
                        current.copy(
                            isLoading = false,
                            today = today,
                            allChunks = result.data,
                            learnedCount = result.data.count { it.progress.isLearned },
                            dueCount = result.data.count { it.status(today) == ReviewStatus.DueToday },
                        ),
                    )
                }
                is Result.Error -> _state.update { it.copy(isLoading = false, error = result.exception.message) }
            }
        }
    }

    fun onEvent(event: VocabularyListUiEvent) {
        when (event) {
            is VocabularyListUiEvent.ChunkClicked -> emit(VocabularyListUiEffect.NavigateToDetail(event.chunkId))
            is VocabularyListUiEvent.StartCardSession -> emit(VocabularyListUiEffect.NavigateToCardSession)
            is VocabularyListUiEvent.StartQuiz -> emit(VocabularyListUiEffect.NavigateToQuiz(event.mode))
            is VocabularyListUiEvent.NavigateBack -> emit(VocabularyListUiEffect.NavigateBack)
            is VocabularyListUiEvent.SearchChanged -> _state.update { refilter(it.copy(searchQuery = event.query)) }
            is VocabularyListUiEvent.ThemeSelected -> _state.update { refilter(it.copy(selectedTheme = event.theme)) }
            is VocabularyListUiEvent.LevelSelected -> _state.update { refilter(it.copy(selectedLevel = event.level)) }
            is VocabularyListUiEvent.ToggleDueOnly -> _state.update { refilter(it.copy(dueOnly = !it.dueOnly)) }
            is VocabularyListUiEvent.ToggleUnlearnedOnly ->
                _state.update { refilter(it.copy(unlearnedOnly = !it.unlearnedOnly)) }
        }
    }

    private fun emit(effect: VocabularyListUiEffect) {
        viewModelScope.launch { _effects.emit(effect) }
    }

    private fun refilter(state: VocabularyListUiState): VocabularyListUiState {
        val query = state.searchQuery.trim()
        val displayed = state.allChunks.filter { item ->
            matchesDue(item, state) &&
                matchesUnlearned(item, state) &&
                matchesTheme(item, state) &&
                matchesLevel(item, state) &&
                matchesQuery(item, query)
        }
        return state.copy(displayedChunks = displayed)
    }

    private fun matchesDue(
        item: ChunkWithProgress,
        state: VocabularyListUiState,
    ): Boolean = !state.dueOnly || item.status(state.today) == ReviewStatus.DueToday

    private fun matchesUnlearned(
        item: ChunkWithProgress,
        state: VocabularyListUiState,
    ): Boolean = !state.unlearnedOnly || !item.progress.isLearned

    private fun matchesTheme(
        item: ChunkWithProgress,
        state: VocabularyListUiState,
    ): Boolean = state.selectedTheme == null || item.chunk.theme == state.selectedTheme

    private fun matchesLevel(
        item: ChunkWithProgress,
        state: VocabularyListUiState,
    ): Boolean = state.selectedLevel == null || item.chunk.level == state.selectedLevel

    private fun matchesQuery(
        item: ChunkWithProgress,
        query: String,
    ): Boolean {
        if (query.isEmpty()) return true
        val chunk = item.chunk
        return chunk.text.contains(query, ignoreCase = true) ||
            chunk.headword.contains(query, ignoreCase = true) ||
            chunk.translation.contains(query, ignoreCase = true)
    }
}
