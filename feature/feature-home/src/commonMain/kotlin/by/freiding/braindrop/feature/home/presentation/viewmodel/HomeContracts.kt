package by.freiding.braindrop.feature.home.presentation.viewmodel

import by.freiding.braindrop.feature.home.domain.model.StudyCategory

data class HomeUiState(
    val isLoading: Boolean = true,
    val categories: List<StudyCategory> = emptyList(),
    val dailyGoal: Int = 8,
    val dailyDone: Int = 0,
    val streakDays: Int = 0,
    val error: String? = null,
)

sealed class HomeUiEffect {
    data class NavigateToCategory(
        val categoryId: String,
    ) : HomeUiEffect()

    data object ContinueQuiz : HomeUiEffect()
}

sealed class HomeUiEvent {
    data class CategoryClicked(
        val categoryId: String,
    ) : HomeUiEvent()

    data object ContinueQuizClicked : HomeUiEvent()
}
