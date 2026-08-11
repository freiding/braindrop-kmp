package by.freiding.braindrop.feature.tenses.presentation.cheatsheet

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

class TenseCheatSheetViewModel(
    private val getTenses: GetTensesUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(TenseCheatSheetUiState())
    val state: StateFlow<TenseCheatSheetUiState> = _state.asStateFlow()

    private val _effects = MutableSharedFlow<TenseCheatSheetUiEffect>()
    val effects: SharedFlow<TenseCheatSheetUiEffect> = _effects.asSharedFlow()

    init {
        load()
    }

    fun reload() = load()

    fun onEvent(event: TenseCheatSheetUiEvent) {
        when (event) {
            is TenseCheatSheetUiEvent.NavigateBack -> viewModelScope.launch {
                _effects.emit(TenseCheatSheetUiEffect.NavigateBack)
            }
        }
    }

    private fun load() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            when (val result = getTenses()) {
                is Result.Success -> _state.update {
                    it.copy(isLoading = false, tenses = result.data.map { item -> item.tense })
                }
                is Result.Error -> _state.update {
                    it.copy(isLoading = false, error = result.exception.message)
                }
            }
        }
    }
}
