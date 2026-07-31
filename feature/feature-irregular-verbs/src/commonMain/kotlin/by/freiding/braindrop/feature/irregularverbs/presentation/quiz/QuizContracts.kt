package by.freiding.braindrop.feature.irregularverbs.presentation.quiz

import by.freiding.braindrop.feature.irregularverbs.domain.model.QuizQuestion

data class QuizUiState(
    val isLoading: Boolean = true,
    val questions: List<QuizQuestion> = emptyList(),
    val currentIndex: Int = 0,
    val selectedAnswer: String? = null,
    val score: Int = 0,
    val isFinished: Boolean = false,
    val error: String? = null,
) {
    val currentQuestion: QuizQuestion? get() = questions.getOrNull(currentIndex)
    val isAnswered: Boolean get() = selectedAnswer != null
    val totalQuestions: Int get() = questions.size
}

sealed class QuizUiEffect {
    data object NavigateBack : QuizUiEffect()
}

sealed class QuizUiEvent {
    data class AnswerSelected(val answer: String) : QuizUiEvent()
    data object NextQuestion : QuizUiEvent()
    data object RestartQuiz : QuizUiEvent()
    data object NavigateBack : QuizUiEvent()
}
