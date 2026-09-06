package by.freiding.braindrop.feature.vocabulary.presentation.list

import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkLevel
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkTheme
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkWithProgress
import kotlinx.datetime.LocalDate

data class VocabularyListUiState(
    val isLoading: Boolean = true,
    val allChunks: List<ChunkWithProgress> = emptyList(),
    val displayedChunks: List<ChunkWithProgress> = emptyList(),
    val searchQuery: String = "",
    val selectedTheme: ChunkTheme? = null,
    val selectedLevel: ChunkLevel? = null,
    val dueOnly: Boolean = false,
    val unlearnedOnly: Boolean = false,
    val learnedCount: Int = 0,
    val dueCount: Int = 0,
    val today: LocalDate,
    val error: String? = null,
) {
    val totalCount: Int get() = allChunks.size
}

sealed class VocabularyListUiEffect {
    data class NavigateToDetail(
        val chunkId: String,
    ) : VocabularyListUiEffect()

    data object NavigateToCardSession : VocabularyListUiEffect()

    data class NavigateToQuiz(
        val mode: String,
    ) : VocabularyListUiEffect()

    data object NavigateBack : VocabularyListUiEffect()
}

sealed class VocabularyListUiEvent {
    data class ChunkClicked(
        val chunkId: String,
    ) : VocabularyListUiEvent()

    data class SearchChanged(
        val query: String,
    ) : VocabularyListUiEvent()

    data class ThemeSelected(
        val theme: ChunkTheme?,
    ) : VocabularyListUiEvent()

    data class LevelSelected(
        val level: ChunkLevel?,
    ) : VocabularyListUiEvent()

    data object ToggleDueOnly : VocabularyListUiEvent()

    data object ToggleUnlearnedOnly : VocabularyListUiEvent()

    data object StartCardSession : VocabularyListUiEvent()

    data class StartQuiz(
        val mode: String,
    ) : VocabularyListUiEvent()

    data object NavigateBack : VocabularyListUiEvent()
}
