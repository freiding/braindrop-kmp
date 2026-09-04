package by.freiding.braindrop.feature.phrasalverbs.presentation.quiz

import by.freiding.braindrop.feature.phrasalverbs.domain.model.PhrasalVerb
import by.freiding.braindrop.feature.phrasalverbs.domain.model.PhrasalVerbQuizQuestion

data class PhrasalVerbQuizMistake(
    val verb: PhrasalVerb,
    val userAnswerText: String,
)

data class PhrasalVerbsQuizUiState(
    val isLoading: Boolean = true,
    val questions: List<PhrasalVerbQuizQuestion> = emptyList(),
    val currentIndex: Int = 0,
    val selectedAnswer: String? = null,
    val score: Int = 0,
    val isFinished: Boolean = false,
    val error: String? = null,
    val answerHistory: List<Boolean?> = emptyList(),
    val mistakes: List<PhrasalVerbQuizMistake> = emptyList(),
    val elapsedSeconds: Int = 0,
    val streakDays: Int = 0,
) {
    val currentQuestion: PhrasalVerbQuizQuestion? get() = questions.getOrNull(currentIndex)
    val isAnswered: Boolean get() = selectedAnswer != null
    val totalQuestions: Int get() = questions.size
    val isEmpty: Boolean get() = !isLoading && error == null && questions.isEmpty()
}

sealed class PhrasalVerbsQuizUiEffect {
    data object NavigateBack : PhrasalVerbsQuizUiEffect()
    data class NavigateToDetail(val verbId: String) : PhrasalVerbsQuizUiEffect()
}

sealed class PhrasalVerbsQuizUiEvent {
    data class AnswerSelected(val answer: String) : PhrasalVerbsQuizUiEvent()
    data object NextQuestion : PhrasalVerbsQuizUiEvent()
    data object RestartQuiz : PhrasalVerbsQuizUiEvent()
    data object RetryMistakes : PhrasalVerbsQuizUiEvent()
    data object NavigateBack : PhrasalVerbsQuizUiEvent()
    data class MistakeClicked(val verbId: String) : PhrasalVerbsQuizUiEvent()
}
