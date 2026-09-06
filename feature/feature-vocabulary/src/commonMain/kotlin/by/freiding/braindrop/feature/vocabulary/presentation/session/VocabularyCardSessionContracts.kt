package by.freiding.braindrop.feature.vocabulary.presentation.session

import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkWithProgress
import by.freiding.braindrop.feature.vocabulary.domain.srs.RecallGrade

data class VocabularyCardSessionUiState(
    val isLoading: Boolean = true,
    val queue: List<ChunkWithProgress> = emptyList(),
    val currentIndex: Int = 0,
    val isRevealed: Boolean = false,
    val gradeHistory: List<RecallGrade> = emptyList(),
    val intervalLabels: Map<RecallGrade, String> = emptyMap(),
    val elapsedSeconds: Int = 0,
    val isFinished: Boolean = false,
    val error: String? = null,
) {
    val currentCard: ChunkWithProgress? get() = queue.getOrNull(currentIndex)
    val total: Int get() = queue.size
    val position: Int get() = (currentIndex + 1).coerceAtMost(total)
    val reviewedCount: Int get() = gradeHistory.size
    val isEmpty: Boolean get() = !isLoading && error == null && queue.isEmpty()
}

sealed class VocabularyCardSessionUiEffect {
    data object NavigateBack : VocabularyCardSessionUiEffect()
}

sealed class VocabularyCardSessionUiEvent {
    data object Reveal : VocabularyCardSessionUiEvent()

    data class Grade(
        val grade: RecallGrade,
    ) : VocabularyCardSessionUiEvent()

    data object Restart : VocabularyCardSessionUiEvent()

    data object NavigateBack : VocabularyCardSessionUiEvent()
}
