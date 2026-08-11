package by.freiding.braindrop.feature.tenses.presentation.list

import by.freiding.braindrop.feature.tenses.domain.model.TenseTime
import by.freiding.braindrop.feature.tenses.domain.model.TenseWithProgress

data class TensesListUiState(
    val isLoading: Boolean = true,
    val tenses: List<TenseWithProgress> = emptyList(),
    val learnedCount: Int = 0,
    val comparisonsCount: Int = 0,
    val selectedTime: TenseTime = TenseTime.PRESENT,
    val error: String? = null,
) {
    val tensesForSelectedTime: List<TenseWithProgress> by lazy {
        tenses.filter { it.tense.time == selectedTime }
    }
}

sealed class TensesListUiEffect {
    data class NavigateToDetail(
        val tenseId: String,
    ) : TensesListUiEffect()

    data object NavigateToComparisons : TensesListUiEffect()

    data object NavigateToCheatSheet : TensesListUiEffect()

    data object NavigateToQuiz : TensesListUiEffect()

    data object NavigateBack : TensesListUiEffect()
}

sealed class TensesListUiEvent {
    data class TenseClicked(
        val tenseId: String,
    ) : TensesListUiEvent()

    data class TimeTabSelected(
        val time: TenseTime,
    ) : TensesListUiEvent()

    data object ConfusedWithClicked : TensesListUiEvent()

    data object CheatSheetClicked : TensesListUiEvent()

    data object TrainClicked : TensesListUiEvent()

    data object NavigateBack : TensesListUiEvent()
}
