package by.freiding.braindrop.feature.phrasalverbs.domain.usecase

import by.freiding.braindrop.feature.phrasalverbs.domain.model.PhrasalVerbQuizQuestion

class EvaluatePhrasalVerbAnswerUseCase {
    operator fun invoke(
        question: PhrasalVerbQuizQuestion,
        answer: String,
    ): Boolean = answer == question.correctAnswer
}
