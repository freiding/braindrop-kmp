package by.freiding.braindrop.feature.tenses.presentation.detail

import by.freiding.braindrop.feature.tenses.domain.model.TenseWithProgress

enum class TenseDetailSection { USAGE, MARKERS, SPECIAL_NOTES, MISTAKE }

/** A confusedWith partner resolved to the comparison pair that actually covers it, for deep-linking. */
data class ConfusedComparison(
    val partnerTenseId: String,
    val comparisonId: String,
)

data class TenseDetailUiState(
    val isLoading: Boolean = true,
    val tenseWithProgress: TenseWithProgress? = null,
    val confusedComparisons: List<ConfusedComparison> = emptyList(),
    val expandedSections: Set<TenseDetailSection> = emptySet(),
    val error: String? = null,
)

sealed class TenseDetailUiEffect {
    data object NavigateBack : TenseDetailUiEffect()

    data class NavigateToComparisons(
        val comparisonId: String,
    ) : TenseDetailUiEffect()
}

sealed class TenseDetailUiEvent {
    data object ToggleLearned : TenseDetailUiEvent()

    data class SectionToggled(
        val section: TenseDetailSection,
    ) : TenseDetailUiEvent()

    data class ComparisonClicked(
        val comparisonId: String,
    ) : TenseDetailUiEvent()

    data object NavigateBack : TenseDetailUiEvent()
}
