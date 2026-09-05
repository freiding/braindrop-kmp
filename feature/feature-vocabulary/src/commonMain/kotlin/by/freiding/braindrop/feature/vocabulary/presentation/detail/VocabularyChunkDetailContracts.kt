package by.freiding.braindrop.feature.vocabulary.presentation.detail

import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkWithProgress
import by.freiding.braindrop.feature.vocabulary.domain.srs.RecallGrade
import kotlinx.datetime.LocalDate

data class VocabularyChunkDetailUiState(
    val isLoading: Boolean = true,
    val item: ChunkWithProgress? = null,
    val today: LocalDate,
    val error: String? = null,
)

sealed class VocabularyChunkDetailUiEffect {
    data object NavigateBack : VocabularyChunkDetailUiEffect()
}

sealed class VocabularyChunkDetailUiEvent {
    data class Grade(
        val grade: RecallGrade,
    ) : VocabularyChunkDetailUiEvent()

    data object ToggleLearned : VocabularyChunkDetailUiEvent()

    data object NavigateBack : VocabularyChunkDetailUiEvent()
}
