package by.freiding.braindrop.feature.irregularverbs.presentation.quiz

import by.freiding.braindrop.feature.irregularverbs.domain.model.IrregularVerb
import by.freiding.braindrop.feature.irregularverbs.domain.model.QuizQuestion

/** One wrong answer from the session — the verb plus what the user picked (for review on the result screen). */
data class QuizMistake(val verb: IrregularVerb, val userAnswerText: String)

data class QuizUiState(
    val isLoading: Boolean = true,
    val questions: List<QuizQuestion> = emptyList(),
    val currentIndex: Int = 0,
    val selectedAnswer: String? = null,
    val score: Int = 0,
    val isFinished: Boolean = false,
    val error: String? = null,
    /** One item per question: null means not answered yet, otherwise whether it was answered correctly. */
    val answerHistory: List<Boolean?> = emptyList(),
    val mistakes: List<QuizMistake> = emptyList(),
    val elapsedSeconds: Int = 0,
    val streakDays: Int = 0,
) {
    val currentQuestion: QuizQuestion? get() = questions.getOrNull(currentIndex)
    val isAnswered: Boolean get() = selectedAnswer != null
    val totalQuestions: Int get() = questions.size
    val isEmpty: Boolean get() = !isLoading && error == null && questions.isEmpty()
}

sealed class QuizUiEffect {
    data object NavigateBack : QuizUiEffect()
    data class NavigateToVerbDetail(val verbId: String) : QuizUiEffect()
}

sealed class QuizUiEvent {
    data class AnswerSelected(val answer: String) : QuizUiEvent()
    data object NextQuestion : QuizUiEvent()
    data object RestartQuiz : QuizUiEvent()
    data object RetryMistakes : QuizUiEvent()
    data object NavigateBack : QuizUiEvent()
    data class MistakeClicked(val verbId: String) : QuizUiEvent()
}
