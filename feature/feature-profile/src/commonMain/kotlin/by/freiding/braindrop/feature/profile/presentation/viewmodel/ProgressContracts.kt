package by.freiding.braindrop.feature.profile.presentation.viewmodel

import by.freiding.braindrop.feature.profile.domain.model.ProgressData

data class ProgressUiState(
    val isLoading: Boolean = true,
    val data: ProgressData? = null,
    val error: String? = null,
)

sealed class ProgressUiEvent {
    data object Reload : ProgressUiEvent()
}
