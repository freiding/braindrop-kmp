package by.freiding.braindrop.feature.tenses.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.tenses.domain.usecase.GetTenseDetailUseCase
import by.freiding.braindrop.feature.tenses.domain.usecase.ToggleTenseLearnedUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TenseDetailViewModel(
    private val tenseId: String,
    private val getTenseDetail: GetTenseDetailUseCase,
    private val toggleTenseLearned: ToggleTenseLearnedUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(TenseDetailUiState())
    val state: StateFlow<TenseDetailUiState> = _state.asStateFlow()

    private val _effects = MutableSharedFlow<TenseDetailUiEffect>()
    val effects: SharedFlow<TenseDetailUiEffect> = _effects.asSharedFlow()

    init {
        loadDetail()
    }

    fun reload() = loadDetail()

    fun onEvent(event: TenseDetailUiEvent) {
        when (event) {
            is TenseDetailUiEvent.ToggleLearned -> viewModelScope.launch {
                toggleTenseLearned(tenseId)
                loadDetail()
            }
            is TenseDetailUiEvent.ComparisonsClicked -> viewModelScope.launch {
                _effects.emit(TenseDetailUiEffect.NavigateToComparisons)
            }
            is TenseDetailUiEvent.NavigateBack -> viewModelScope.launch {
                _effects.emit(TenseDetailUiEffect.NavigateBack)
            }
        }
    }

    private fun loadDetail() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            when (val result = getTenseDetail(tenseId)) {
                is Result.Success -> _state.update {
                    it.copy(isLoading = false, tenseWithProgress = result.data)
                }
                is Result.Error -> _state.update {
                    it.copy(isLoading = false, error = result.exception.message)
                }
            }
        }
    }
}
