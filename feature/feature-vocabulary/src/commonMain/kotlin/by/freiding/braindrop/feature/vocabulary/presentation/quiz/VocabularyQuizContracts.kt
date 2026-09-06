package by.freiding.braindrop.feature.vocabulary.presentation.quiz

import by.freiding.braindrop.feature.vocabulary.domain.model.Chunk
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkProgress
import by.freiding.braindrop.feature.vocabulary.domain.model.VocabularyQuizQuestion
import by.freiding.braindrop.feature.vocabulary.domain.model.VocabularyQuizType

data class VocabularyQuizMistake(
    val chunk: Chunk,
    val userAnswer: String,
    val correctAnswer: String,
)

data class VocabularyQuizUiState(
    val isLoading: Boolean = true,
    val questions: List<VocabularyQuizQuestion> = emptyList(),
    val currentIndex: Int = 0,
    val selectedOption: String? = null,
    val typedAnswer: String = "",
    val hintLevel: Int = 0,
    val checked: Boolean = false,
    val lastAnswerCorrect: Boolean = false,
    val score: Int = 0,
    val mistakes: List<VocabularyQuizMistake> = emptyList(),
    val scheduled: List<ChunkProgress> = emptyList(),
    val elapsedSeconds: Int = 0,
    val streakDays: Int = 0,
    val isFinished: Boolean = false,
    val error: String? = null,
) {
    val currentQuestion: VocabularyQuizQuestion? get() = questions.getOrNull(currentIndex)
    val total: Int get() = questions.size
    val position: Int get() = (currentIndex + 1).coerceAtMost(total)
    val isAnswered: Boolean get() = checked
    val isEmpty: Boolean get() = !isLoading && error == null && questions.isEmpty()
    val isCloze: Boolean get() = currentQuestion?.type == VocabularyQuizType.CLOZE
}

sealed class VocabularyQuizUiEffect {
    data object NavigateBack : VocabularyQuizUiEffect()

    data class NavigateToDetail(
        val chunkId: String,
    ) : VocabularyQuizUiEffect()
}

sealed class VocabularyQuizUiEvent {
    data class OptionSelected(
        val option: String,
    ) : VocabularyQuizUiEvent()

    data class TypedAnswerChanged(
        val value: String,
    ) : VocabularyQuizUiEvent()

    data object RevealHint : VocabularyQuizUiEvent()

    data object GiveUp : VocabularyQuizUiEvent()

    data object CheckTyping : VocabularyQuizUiEvent()

    data object Next : VocabularyQuizUiEvent()

    data object Restart : VocabularyQuizUiEvent()

    data object RetryMistakes : VocabularyQuizUiEvent()

    data class MistakeClicked(
        val chunkId: String,
    ) : VocabularyQuizUiEvent()

    data object NavigateBack : VocabularyQuizUiEvent()
}
