package by.freiding.braindrop.feature.tenses.presentation.comparison

import by.freiding.braindrop.feature.tenses.domain.model.Tense
import by.freiding.braindrop.feature.tenses.domain.model.TenseComparison

data class TenseComparisonsUiState(
    val isLoading: Boolean = true,
    val comparisons: List<TenseComparison> = emptyList(),
    val tensesById: Map<String, Tense> = emptyMap(),
    val expandedId: String? = null,
    /** Set once from the initial route argument so the screen scrolls to a deep-linked pair on load. */
    val scrollToId: String? = null,
    val error: String? = null,
)

sealed class TenseComparisonsUiEffect {
    data object NavigateBack : TenseComparisonsUiEffect()
}

sealed class TenseComparisonsUiEvent {
    data class ComparisonClicked(
        val id: String,
    ) : TenseComparisonsUiEvent()

    data object NavigateBack : TenseComparisonsUiEvent()
}
