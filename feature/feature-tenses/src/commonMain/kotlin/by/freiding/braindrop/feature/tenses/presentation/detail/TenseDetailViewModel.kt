package by.freiding.braindrop.feature.tenses.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.tenses.domain.usecase.GetComparisonsUseCase
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
    private val getComparisons: GetComparisonsUseCase,
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
            is TenseDetailUiEvent.SectionToggled -> _state.update {
                val expanded = it.expandedSections
                it.copy(
                    expandedSections = if (event.section in expanded) {
                        expanded - event.section
                    } else {
                        expanded + event.section
                    },
                )
            }
            is TenseDetailUiEvent.ComparisonClicked -> viewModelScope.launch {
                _effects.emit(TenseDetailUiEffect.NavigateToComparisons(event.comparisonId))
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
                is Result.Success -> {
                    val confusedWith = result.data.tense.confusedWith
                    val comparisons = (getComparisons() as? Result.Success)?.data.orEmpty()
                    val confusedComparisons = confusedWith.mapNotNull { partnerId ->
                        val comparison = comparisons.firstOrNull { comparison ->
                            (comparison.tenseIdA == tenseId && comparison.tenseIdB == partnerId) ||
                                (comparison.tenseIdB == tenseId && comparison.tenseIdA == partnerId)
                        }
                        comparison?.let { ConfusedComparison(partnerId, it.id) }
                    }
                    _state.update {
                        it.copy(
                            isLoading = false,
                            tenseWithProgress = result.data,
                            confusedComparisons = confusedComparisons,
                        )
                    }
                }
                is Result.Error -> _state.update {
                    it.copy(isLoading = false, error = result.exception.message)
                }
            }
        }
    }
}
