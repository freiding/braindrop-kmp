package by.freiding.braindrop.feature.home.presentation.viewmodel

import by.freiding.braindrop.feature.home.domain.model.StudyCategory

data class HomeUiState(
    val isLoading: Boolean = true,
    val categories: List<StudyCategory> = emptyList(),
    val error: String? = null,
)

sealed class HomeUiEffect {
    data class NavigateToCategory(val categoryId: String) : HomeUiEffect()
}

sealed class HomeUiEvent {
    data class CategoryClicked(val categoryId: String) : HomeUiEvent()
}
