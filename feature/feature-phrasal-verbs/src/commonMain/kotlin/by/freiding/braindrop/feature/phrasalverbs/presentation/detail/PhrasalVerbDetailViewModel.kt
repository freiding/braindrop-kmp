package by.freiding.braindrop.feature.phrasalverbs.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.phrasalverbs.domain.usecase.GetPhrasalVerbDetailUseCase
import by.freiding.braindrop.feature.phrasalverbs.domain.usecase.TogglePhrasalVerbLearnedUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PhrasalVerbDetailViewModel(
    private val verbId: String,
    private val getDetail: GetPhrasalVerbDetailUseCase,
    private val toggleLearned: TogglePhrasalVerbLearnedUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(PhrasalVerbDetailUiState())
    val state: StateFlow<PhrasalVerbDetailUiState> = _state.asStateFlow()

    private val _effects = MutableSharedFlow<PhrasalVerbDetailUiEffect>()
    val effects: SharedFlow<PhrasalVerbDetailUiEffect> = _effects.asSharedFlow()

    init {
        loadDetail()
    }

    fun onEvent(event: PhrasalVerbDetailUiEvent) {
        when (event) {
            is PhrasalVerbDetailUiEvent.ToggleLearned -> viewModelScope.launch {
                toggleLearned(verbId)
                loadDetail()
            }
            is PhrasalVerbDetailUiEvent.NavigateBack -> viewModelScope.launch {
                _effects.emit(PhrasalVerbDetailUiEffect.NavigateBack)
            }
        }
    }

    private fun loadDetail() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            when (val result = getDetail(verbId)) {
                is Result.Success -> _state.update { it.copy(isLoading = false, item = result.data) }
                is Result.Error -> _state.update { it.copy(isLoading = false, error = result.exception.message) }
            }
        }
    }
}
