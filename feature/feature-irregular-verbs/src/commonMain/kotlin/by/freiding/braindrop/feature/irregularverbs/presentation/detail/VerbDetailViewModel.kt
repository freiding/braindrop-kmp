package by.freiding.braindrop.feature.irregularverbs.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.irregularverbs.domain.usecase.GetVerbDetailUseCase
import by.freiding.braindrop.feature.irregularverbs.domain.usecase.ToggleVerbLearnedUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VerbDetailViewModel(
    private val verbId: String,
    private val getVerbDetail: GetVerbDetailUseCase,
    private val toggleVerbLearned: ToggleVerbLearnedUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(VerbDetailUiState())
    val state: StateFlow<VerbDetailUiState> = _state.asStateFlow()

    private val _effects = MutableSharedFlow<VerbDetailUiEffect>()
    val effects: SharedFlow<VerbDetailUiEffect> = _effects.asSharedFlow()

    init {
        loadDetail()
    }

    fun reload() = loadDetail()

    fun onEvent(event: VerbDetailUiEvent) {
        when (event) {
            is VerbDetailUiEvent.ToggleLearned -> viewModelScope.launch {
                toggleVerbLearned(verbId)
                loadDetail()
            }
            is VerbDetailUiEvent.NavigateBack -> viewModelScope.launch {
                _effects.emit(VerbDetailUiEffect.NavigateBack)
            }
        }
    }

    private fun loadDetail() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            when (val result = getVerbDetail(verbId)) {
                is Result.Success -> _state.update {
                    it.copy(isLoading = false, verbWithProgress = result.data)
                }
                is Result.Error -> _state.update {
                    it.copy(isLoading = false, error = result.exception.message)
                }
            }
        }
    }
}
