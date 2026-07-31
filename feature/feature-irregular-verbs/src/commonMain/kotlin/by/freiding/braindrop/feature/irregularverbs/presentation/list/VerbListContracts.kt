package by.freiding.braindrop.feature.irregularverbs.presentation.list

import by.freiding.braindrop.feature.irregularverbs.domain.model.VerbGroup
import by.freiding.braindrop.feature.irregularverbs.domain.model.VerbWithProgress

enum class ViewMode { LIST, GROUPED }

data class VerbListUiState(
    val isLoading: Boolean = true,
    val allVerbs: List<VerbWithProgress> = emptyList(),
    val showLearnedOnly: Boolean = false,
    val learnedCount: Int = 0,
    val viewMode: ViewMode = ViewMode.LIST,
    val error: String? = null,
) {
    val displayedVerbs: List<VerbWithProgress>
        get() = if (showLearnedOnly) allVerbs.filter { !it.progress.isLearned } else allVerbs

    val groupedVerbs: List<Pair<VerbGroup, List<VerbWithProgress>>>
        get() = VerbGroup.entries.mapNotNull { group ->
            val verbs = displayedVerbs.filter { it.verb.group == group }
            if (verbs.isNotEmpty()) group to verbs else null
        }
}

sealed class VerbListUiEffect {
    data class NavigateToDetail(val verbId: String) : VerbListUiEffect()
    data class NavigateToQuiz(val mode: String) : VerbListUiEffect()
}

sealed class VerbListUiEvent {
    data class VerbClicked(val verbId: String) : VerbListUiEvent()
    data class ToggleLearned(val verbId: String) : VerbListUiEvent()
    data object ToggleFilter : VerbListUiEvent()
    data object ToggleViewMode : VerbListUiEvent()
    data class StartQuiz(val mode: String) : VerbListUiEvent()
}
