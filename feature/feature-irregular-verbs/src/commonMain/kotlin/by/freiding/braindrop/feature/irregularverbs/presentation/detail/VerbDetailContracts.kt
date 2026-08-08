package by.freiding.braindrop.feature.irregularverbs.presentation.detail

import by.freiding.braindrop.feature.irregularverbs.domain.model.VerbWithProgress

data class VerbDetailUiState(
    val isLoading: Boolean = true,
    val verbWithProgress: VerbWithProgress? = null,
    val error: String? = null,
)

sealed class VerbDetailUiEffect {
    data object NavigateBack : VerbDetailUiEffect()
}

sealed class VerbDetailUiEvent {
    data object ToggleLearned : VerbDetailUiEvent()

    data object NavigateBack : VerbDetailUiEvent()
}
