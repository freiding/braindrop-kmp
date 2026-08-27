package by.freiding.braindrop.feature.phrasalverbs.presentation.detail

import by.freiding.braindrop.feature.phrasalverbs.domain.model.PhrasalVerbWithProgress

data class PhrasalVerbDetailUiState(
    val isLoading: Boolean = true,
    val item: PhrasalVerbWithProgress? = null,
    val error: String? = null,
)

sealed class PhrasalVerbDetailUiEffect {
    data object NavigateBack : PhrasalVerbDetailUiEffect()
}

sealed class PhrasalVerbDetailUiEvent {
    data object ToggleLearned : PhrasalVerbDetailUiEvent()
    data object NavigateBack : PhrasalVerbDetailUiEvent()
}
