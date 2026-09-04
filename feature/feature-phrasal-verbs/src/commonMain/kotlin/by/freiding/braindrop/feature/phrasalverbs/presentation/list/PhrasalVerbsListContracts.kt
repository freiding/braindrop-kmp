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
    val displayedVerbs: List<PhrasalVerbWithProgress> = emptyList(),
)

sealed class PhrasalVerbsListUiEffect {
    data class NavigateToDetail(
        val verbId: String,
    ) : PhrasalVerbsListUiEffect()

    data class NavigateToQuiz(
        val mode: String,
    ) : PhrasalVerbsListUiEffect()

    data object NavigateBack : PhrasalVerbsListUiEffect()

    data class ShowError(
        val message: String,
    ) : PhrasalVerbsListUiEffect()
}

sealed class PhrasalVerbsListUiEvent {
    data class VerbClicked(
        val verbId: String,
    ) : PhrasalVerbsListUiEvent()

    data class ToggleLearned(
        val verbId: String,
    ) : PhrasalVerbsListUiEvent()

    data class StartQuiz(
        val mode: String,
    ) : PhrasalVerbsListUiEvent()

    data class SearchChanged(
        val query: String,
    ) : PhrasalVerbsListUiEvent()

    data class CategorySelected(
        val category: PhrasalVerbCategory?,
    ) : PhrasalVerbsListUiEvent()

    data object ToggleFilter : PhrasalVerbsListUiEvent()

    data object ClearFilter : PhrasalVerbsListUiEvent()

    data object NavigateBack : PhrasalVerbsListUiEvent()
}

fun PhrasalVerbQuizType.displayName(): String =
    when (this) {
        PhrasalVerbQuizType.DEFINITION_TO_VERB -> "Definition → Verb"
        PhrasalVerbQuizType.VERB_TO_TRANSLATION -> "Verb → Translation"
        PhrasalVerbQuizType.FILL_PARTICLE -> "Fill the particle"
    }

fun PhrasalVerbCategory.displayName(): String =
    when (this) {
        PhrasalVerbCategory.WORK -> "Work"
        PhrasalVerbCategory.RELATIONSHIPS -> "Relationships"
        PhrasalVerbCategory.MOVEMENT -> "Movement"
        PhrasalVerbCategory.COMMUNICATION -> "Communication"
        PhrasalVerbCategory.CHANGES -> "Changes"
        PhrasalVerbCategory.GENERAL -> "General"
    }
