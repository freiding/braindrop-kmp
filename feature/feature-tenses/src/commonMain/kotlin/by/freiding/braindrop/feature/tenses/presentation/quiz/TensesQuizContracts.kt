package by.freiding.braindrop.feature.tenses.presentation.quiz

import by.freiding.braindrop.feature.tenses.domain.model.TenseQuizQuestion

/** One wrong answer from the session — the question plus what the user picked (for review on the result screen). */
data class TenseQuizMistake(
    val question: TenseQuizQuestion,
    val userAnswerText: String,
)

data class TensesQuizUiState(
    val isLoading: Boolean = true,
    val questions: List<TenseQuizQuestion> = emptyList(),
    val currentIndex: Int = 0,
    val selectedAnswer: String? = null,
    val score: Int = 0,
    val isFinished: Boolean = false,
    val error: String? = null,
    /** One item per question: null means not answered yet, otherwise whether it was answered correctly. */
    val answerHistory: List<Boolean?> = emptyList(),
    val mistakes: List<TenseQuizMistake> = emptyList(),
    val elapsedSeconds: Int = 0,
    val streakDays: Int = 0,
) {
    val currentQuestion: TenseQuizQuestion? get() = questions.getOrNull(currentIndex)
    val isAnswered: Boolean get() = selectedAnswer != null
    val totalQuestions: Int get() = questions.size
    val isEmpty: Boolean get() = !isLoading && error == null && questions.isEmpty()
}

sealed class TensesQuizUiEffect {
    data object NavigateBack : TensesQuizUiEffect()

    data class NavigateToTenseDetail(
        val tenseId: String,
    ) : TensesQuizUiEffect()
}

sealed class TensesQuizUiEvent {
    data class AnswerSelected(
        val answer: String,
    ) : TensesQuizUiEvent()

    data object NextQuestion : TensesQuizUiEvent()

    data object RestartQuiz : TensesQuizUiEvent()

    data object RetryMistakes : TensesQuizUiEvent()

    data object NavigateBack : TensesQuizUiEvent()

    data class MistakeClicked(
        val tenseId: String,
    ) : TensesQuizUiEvent()
}
