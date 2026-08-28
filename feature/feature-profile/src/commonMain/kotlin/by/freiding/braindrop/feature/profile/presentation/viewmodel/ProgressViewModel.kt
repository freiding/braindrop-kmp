package by.freiding.braindrop.feature.profile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.profile.domain.usecase.GetProgressDataUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProgressViewModel(
    private val getProgressData: GetProgressDataUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(ProgressUiState())
    val state: StateFlow<ProgressUiState> = _state.asStateFlow()

    init {
        load()
    }

    fun onEvent(event: ProgressUiEvent) {
        when (event) {
            ProgressUiEvent.Reload -> load()
        }
    }

    private fun load() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            when (val result = getProgressData()) {
                is Result.Success -> _state.update { it.copy(isLoading = false, data = result.data) }
                is Result.Error -> _state.update { it.copy(isLoading = false, error = result.exception.message) }
            }
        }
    }
}
