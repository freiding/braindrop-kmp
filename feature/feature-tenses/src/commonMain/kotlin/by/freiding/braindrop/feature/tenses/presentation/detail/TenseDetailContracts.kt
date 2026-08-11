package by.freiding.braindrop.feature.tenses.presentation.detail

import by.freiding.braindrop.feature.tenses.domain.model.TenseWithProgress

data class TenseDetailUiState(
    val isLoading: Boolean = true,
    val tenseWithProgress: TenseWithProgress? = null,
    val error: String? = null,
)

sealed class TenseDetailUiEffect {
    data object NavigateBack : TenseDetailUiEffect()

    data object NavigateToComparisons : TenseDetailUiEffect()
}

sealed class TenseDetailUiEvent {
    data object ToggleLearned : TenseDetailUiEvent()

    data object ComparisonsClicked : TenseDetailUiEvent()

    data object NavigateBack : TenseDetailUiEvent()
}
