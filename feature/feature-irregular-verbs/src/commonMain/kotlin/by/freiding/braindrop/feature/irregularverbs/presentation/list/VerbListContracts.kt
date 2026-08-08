package by.freiding.braindrop.feature.irregularverbs.presentation.list

import by.freiding.braindrop.feature.irregularverbs.domain.model.VerbGroup
import by.freiding.braindrop.feature.irregularverbs.domain.model.VerbWithProgress

enum class ViewMode { LIST, GROUPED }

data class VerbListUiState(
    val isLoading: Boolean = true,
    val allVerbs: List<VerbWithProgress> = emptyList(),
    val unlearnedOnly: Boolean = false,
    val searchQuery: String = "",
    val learnedCount: Int = 0,
    val viewMode: ViewMode = ViewMode.LIST,
    val error: String? = null,
) {
    // by lazy is safe on a data class: copy() always goes through the primary constructor, so
    // every distinct state gets its own fresh, uninitialized delegate — these just avoid
    // recomputing the same filter/search/group-by on every read of an unchanged state (the
    // screen reads displayedVerbs more than once, and groupedVerbs re-filters it per group).
    val filteredByToggle: List<VerbWithProgress> by lazy {
        if (unlearnedOnly) allVerbs.filter { !it.progress.isLearned } else allVerbs
    }

    val displayedVerbs: List<VerbWithProgress> by lazy {
        val query = searchQuery.trim()
        if (query.isEmpty()) {
            filteredByToggle
        } else {
            filteredByToggle.filter { item ->
                item.verb.baseForm.contains(query, ignoreCase = true) ||
                    item.verb.pastSimple.contains(query, ignoreCase = true) ||
                    item.verb.pastParticiple.contains(query, ignoreCase = true) ||
                    item.verb.translation.contains(query, ignoreCase = true)
            }
        }
    }

    val groupedVerbs: List<Pair<VerbGroup, List<VerbWithProgress>>> by lazy {
        VerbGroup.entries.mapNotNull { group ->
            val verbs = displayedVerbs.filter { it.verb.group == group }
            if (verbs.isNotEmpty()) group to verbs else null
        }
    }
}

sealed class VerbListUiEffect {
    data class NavigateToDetail(val verbId: String) : VerbListUiEffect()
    data class NavigateToQuiz(val mode: String) : VerbListUiEffect()
    data object NavigateBack : VerbListUiEffect()
}

sealed class VerbListUiEvent {
    data class VerbClicked(val verbId: String) : VerbListUiEvent()
    data class ToggleLearned(val verbId: String) : VerbListUiEvent()
    data object ToggleFilter : VerbListUiEvent()
    data object ToggleViewMode : VerbListUiEvent()
    data class StartQuiz(val mode: String) : VerbListUiEvent()
    data class SearchChanged(val query: String) : VerbListUiEvent()
    data object ClearFilter : VerbListUiEvent()
    data object NavigateBack : VerbListUiEvent()
}
