package by.freiding.braindrop.feature.irregularverbs.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.irregularverbs.domain.usecase.GetVerbsUseCase
import by.freiding.braindrop.feature.irregularverbs.domain.usecase.ToggleVerbLearnedUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VerbListViewModel(
    private val getVerbs: GetVerbsUseCase,
    private val toggleLearned: ToggleVerbLearnedUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(VerbListUiState())
    val state: StateFlow<VerbListUiState> = _state.asStateFlow()

    private val _effects = MutableSharedFlow<VerbListUiEffect>()
    val effects: SharedFlow<VerbListUiEffect> = _effects.asSharedFlow()

    // The initial load is triggered by the screen (LaunchedEffect(Unit) { reload() }) rather
    // than here, since the screen also needs it on every re-entry — a single load path avoids
    // a duplicate query on first composition.

    fun onEvent(event: VerbListUiEvent) {
        when (event) {
            is VerbListUiEvent.VerbClicked -> viewModelScope.launch {
                _effects.emit(VerbListUiEffect.NavigateToDetail(event.verbId))
            }
            is VerbListUiEvent.ToggleLearned -> viewModelScope.launch {
                toggleLearned(event.verbId)
                loadVerbs()
            }
            is VerbListUiEvent.ToggleFilter -> _state.update {
                it.copy(unlearnedOnly = !it.unlearnedOnly)
            }
            is VerbListUiEvent.ClearFilter -> _state.update {
                it.copy(unlearnedOnly = false)
            }
            is VerbListUiEvent.ToggleViewMode -> _state.update {
                it.copy(viewMode = if (it.viewMode == ViewMode.LIST) ViewMode.GROUPED else ViewMode.LIST)
            }
            is VerbListUiEvent.StartQuiz -> viewModelScope.launch {
                _effects.emit(VerbListUiEffect.NavigateToQuiz(event.mode))
            }
            is VerbListUiEvent.SearchChanged -> _state.update {
                it.copy(searchQuery = event.query)
            }
            is VerbListUiEvent.NavigateBack -> viewModelScope.launch {
                _effects.emit(VerbListUiEffect.NavigateBack)
            }
        }
    }

    fun reload() = loadVerbs()

    private fun loadVerbs() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            when (val result = getVerbs()) {
                is Result.Success -> _state.update {
                    it.copy(
                        isLoading = false,
                        allVerbs = result.data,
                        learnedCount = result.data.count { v -> v.progress.isLearned },
                    )
                }
                is Result.Error -> _state.update {
                    it.copy(isLoading = false, error = result.exception.message)
                }
            }
        }
    }
}
