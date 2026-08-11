package by.freiding.braindrop.feature.tenses.presentation.cheatsheet

import by.freiding.braindrop.feature.tenses.domain.model.Tense

data class TenseCheatSheetUiState(
    val isLoading: Boolean = true,
    val tenses: List<Tense> = emptyList(),
    val error: String? = null,
)

sealed class TenseCheatSheetUiEffect {
    data object NavigateBack : TenseCheatSheetUiEffect()
}

sealed class TenseCheatSheetUiEvent {
    data object NavigateBack : TenseCheatSheetUiEvent()
}
