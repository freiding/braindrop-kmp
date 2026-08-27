package by.freiding.braindrop.feature.phrasalverbs.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.phrasalverbs.domain.usecase.GetPhrasalVerbsUseCase
import by.freiding.braindrop.feature.phrasalverbs.domain.usecase.TogglePhrasalVerbLearnedUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PhrasalVerbsListViewModel(
    private val getVerbs: GetPhrasalVerbsUseCase,
    private val toggleLearned: TogglePhrasalVerbLearnedUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(PhrasalVerbsListUiState())
    val state: StateFlow<PhrasalVerbsListUiState> = _state.asStateFlow()

    private val _effects = MutableSharedFlow<PhrasalVerbsListUiEffect>()
    val effects: SharedFlow<PhrasalVerbsListUiEffect> = _effects.asSharedFlow()

    fun onEvent(event: PhrasalVerbsListUiEvent) {
        when (event) {
            is PhrasalVerbsListUiEvent.VerbClicked -> viewModelScope.launch {
                _effects.emit(PhrasalVerbsListUiEffect.NavigateToDetail(event.verbId))
            }
            is PhrasalVerbsListUiEvent.ToggleLearned -> viewModelScope.launch {
                toggleLearned(event.verbId)
                loadVerbs()
            }
            is PhrasalVerbsListUiEvent.StartQuiz -> viewModelScope.launch {
                _effects.emit(PhrasalVerbsListUiEffect.NavigateToQuiz(event.mode))
            }
            is PhrasalVerbsListUiEvent.SearchChanged -> _state.update {
                it.copy(searchQuery = event.query)
            }
            is PhrasalVerbsListUiEvent.CategorySelected -> _state.update {
                it.copy(selectedCategory = event.category)
            }
            is PhrasalVerbsListUiEvent.ToggleFilter -> _state.update {
                it.copy(unlearnedOnly = !it.unlearnedOnly)
            }
            is PhrasalVerbsListUiEvent.ClearFilter -> _state.update {
                it.copy(unlearnedOnly = false, selectedCategory = null, searchQuery = "")
            }
            is PhrasalVerbsListUiEvent.NavigateBack -> viewModelScope.launch {
                _effects.emit(PhrasalVerbsListUiEffect.NavigateBack)
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
