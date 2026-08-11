package by.freiding.braindrop.feature.tenses.presentation.comparison

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.tenses.domain.usecase.GetComparisonsUseCase
import by.freiding.braindrop.feature.tenses.domain.usecase.GetTensesUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TenseComparisonsViewModel(
    private val initialComparisonId: String?,
    private val getComparisons: GetComparisonsUseCase,
    private val getTenses: GetTensesUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(TenseComparisonsUiState())
    val state: StateFlow<TenseComparisonsUiState> = _state.asStateFlow()

    private val _effects = MutableSharedFlow<TenseComparisonsUiEffect>()
    val effects: SharedFlow<TenseComparisonsUiEffect> = _effects.asSharedFlow()

    init {
        load()
    }

    fun reload() = load()

    fun onEvent(event: TenseComparisonsUiEvent) {
        when (event) {
            is TenseComparisonsUiEvent.ComparisonClicked -> _state.update {
                it.copy(expandedId = if (it.expandedId == event.id) null else event.id)
            }
            is TenseComparisonsUiEvent.NavigateBack -> viewModelScope.launch {
                _effects.emit(TenseComparisonsUiEffect.NavigateBack)
            }
        }
    }

    private fun load() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            when (val result = getComparisons()) {
                is Result.Success -> {
                    val tensesById = (getTenses() as? Result.Success)
                        ?.data
                        ?.associate { it.tense.id to it.tense }
                        .orEmpty()
                    _state.update {
                        it.copy(
                            isLoading = false,
                            comparisons = result.data,
                            tensesById = tensesById,
                            expandedId = initialComparisonId ?: it.expandedId,
                            scrollToId = initialComparisonId,
                        )
                    }
                }
                is Result.Error -> _state.update { it.copy(isLoading = false, error = result.exception.message) }
            }
        }
    }
}
