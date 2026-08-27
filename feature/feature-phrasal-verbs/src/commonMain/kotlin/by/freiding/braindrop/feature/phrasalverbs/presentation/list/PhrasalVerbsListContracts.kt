package by.freiding.braindrop.feature.phrasalverbs.presentation.list

import by.freiding.braindrop.feature.phrasalverbs.domain.model.PhrasalVerbCategory
import by.freiding.braindrop.feature.phrasalverbs.domain.model.PhrasalVerbQuizType
import by.freiding.braindrop.feature.phrasalverbs.domain.model.PhrasalVerbWithProgress

data class PhrasalVerbsListUiState(
    val isLoading: Boolean = true,
    val allVerbs: List<PhrasalVerbWithProgress> = emptyList(),
    val unlearnedOnly: Boolean = false,
    val searchQuery: String = "",
    val learnedCount: Int = 0,
    val selectedCategory: PhrasalVerbCategory? = null,
    val error: String? = null,
) {
    val filteredByToggle: List<PhrasalVerbWithProgress> by lazy {
        if (unlearnedOnly) allVerbs.filter { !it.progress.isLearned } else allVerbs
    }

    val filteredByCategory: List<PhrasalVerbWithProgress> by lazy {
        if (selectedCategory == null) filteredByToggle
        else filteredByToggle.filter { it.verb.category == selectedCategory }
    }

    val displayedVerbs: List<PhrasalVerbWithProgress> by lazy {
        val query = searchQuery.trim()
        if (query.isEmpty()) {
            filteredByCategory
        } else {
            filteredByCategory.filter { item ->
                item.verb.fullForm.contains(query, ignoreCase = true) ||
                    item.verb.verb.contains(query, ignoreCase = true) ||
                    item.verb.particle.contains(query, ignoreCase = true) ||
                    item.verb.meanings.any { m ->
                        m.translation.contains(query, ignoreCase = true) ||
                            m.definition.contains(query, ignoreCase = true)
                    }
            }
        }
    }

    val groupedByCategory: List<Pair<PhrasalVerbCategory, List<PhrasalVerbWithProgress>>> by lazy {
        PhrasalVerbCategory.entries.mapNotNull { cat ->
            val verbs = displayedVerbs.filter { it.verb.category == cat }
            if (verbs.isNotEmpty()) cat to verbs else null
        }
    }
}

sealed class PhrasalVerbsListUiEffect {
    data class NavigateToDetail(val verbId: String) : PhrasalVerbsListUiEffect()
    data class NavigateToQuiz(val mode: String) : PhrasalVerbsListUiEffect()
    data object NavigateBack : PhrasalVerbsListUiEffect()
}

sealed class PhrasalVerbsListUiEvent {
    data class VerbClicked(val verbId: String) : PhrasalVerbsListUiEvent()
    data class ToggleLearned(val verbId: String) : PhrasalVerbsListUiEvent()
    data class StartQuiz(val mode: String) : PhrasalVerbsListUiEvent()
    data class SearchChanged(val query: String) : PhrasalVerbsListUiEvent()
    data class CategorySelected(val category: PhrasalVerbCategory?) : PhrasalVerbsListUiEvent()
    data object ToggleFilter : PhrasalVerbsListUiEvent()
    data object ClearFilter : PhrasalVerbsListUiEvent()
    data object NavigateBack : PhrasalVerbsListUiEvent()
}

fun PhrasalVerbQuizType.displayName(): String = when (this) {
    PhrasalVerbQuizType.DEFINITION_TO_VERB -> "Definition → Verb"
    PhrasalVerbQuizType.VERB_TO_TRANSLATION -> "Verb → Translation"
    PhrasalVerbQuizType.FILL_PARTICLE -> "Fill the particle"
}

fun PhrasalVerbCategory.displayName(): String = when (this) {
    PhrasalVerbCategory.WORK -> "Work"
    PhrasalVerbCategory.RELATIONSHIPS -> "Relationships"
    PhrasalVerbCategory.MOVEMENT -> "Movement"
    PhrasalVerbCategory.COMMUNICATION -> "Communication"
    PhrasalVerbCategory.CHANGES -> "Changes"
    PhrasalVerbCategory.GENERAL -> "General"
}
