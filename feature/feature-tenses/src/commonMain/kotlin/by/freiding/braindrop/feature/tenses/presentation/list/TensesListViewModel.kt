package by.freiding.braindrop.feature.tenses.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.tenses.domain.usecase.GetTensesUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TensesListViewModel(
    private val getTenses: GetTensesUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(TensesListUiState())
    val state: StateFlow<TensesListUiState> = _state.asStateFlow()

    private val _effects = MutableSharedFlow<TensesListUiEffect>()
    val effects: SharedFlow<TensesListUiEffect> = _effects.asSharedFlow()

    fun onEvent(event: TensesListUiEvent) {
        when (event) {
            is TensesListUiEvent.TenseClicked -> viewModelScope.launch {
                _effects.emit(TensesListUiEffect.NavigateToDetail(event.tenseId))
            }
            is TensesListUiEvent.ComparisonsClicked -> viewModelScope.launch {
                _effects.emit(TensesListUiEffect.NavigateToComparisons)
            }
            is TensesListUiEvent.CheatSheetClicked -> viewModelScope.launch {
                _effects.emit(TensesListUiEffect.NavigateToCheatSheet)
            }
            is TensesListUiEvent.StartQuiz -> viewModelScope.launch {
                _effects.emit(TensesListUiEffect.NavigateToQuiz(event.mode))
            }
            is TensesListUiEvent.NavigateBack -> viewModelScope.launch {
                _effects.emit(TensesListUiEffect.NavigateBack)
            }
        }
    }

    fun reload() = loadTenses()

    private fun loadTenses() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            when (val result = getTenses()) {
                is Result.Success -> _state.update {
                    it.copy(
                        isLoading = false,
                        tenses = result.data,
                        learnedCount = result.data.count { t -> t.progress.isLearned },
                    )
                }
                is Result.Error -> _state.update {
                    it.copy(isLoading = false, error = result.exception.message)
                }
            }
        }
    }
}
