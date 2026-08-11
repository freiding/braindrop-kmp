package by.freiding.braindrop.feature.tenses.presentation.list

import by.freiding.braindrop.feature.tenses.domain.model.TenseTime
import by.freiding.braindrop.feature.tenses.domain.model.TenseWithProgress

data class TensesListUiState(
    val isLoading: Boolean = true,
    val tenses: List<TenseWithProgress> = emptyList(),
    val learnedCount: Int = 0,
    val error: String? = null,
) {
    val groupedByTime: List<Pair<TenseTime, List<TenseWithProgress>>> by lazy {
        TenseTime.entries.mapNotNull { time ->
            val items = tenses.filter { it.tense.time == time }
            if (items.isNotEmpty()) time to items else null
        }
    }
}

sealed class TensesListUiEffect {
    data class NavigateToDetail(
        val tenseId: String,
    ) : TensesListUiEffect()

    data object NavigateToComparisons : TensesListUiEffect()

    data object NavigateToCheatSheet : TensesListUiEffect()

    data class NavigateToQuiz(
        val mode: String,
    ) : TensesListUiEffect()

    data object NavigateBack : TensesListUiEffect()
}

sealed class TensesListUiEvent {
    data class TenseClicked(
        val tenseId: String,
    ) : TensesListUiEvent()

    data object ComparisonsClicked : TensesListUiEvent()

    data object CheatSheetClicked : TensesListUiEvent()

    data class StartQuiz(
        val mode: String,
    ) : TensesListUiEvent()

    data object NavigateBack : TensesListUiEvent()
}
