package by.freiding.braindrop.feature.phrasalverbs.domain.usecase

import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.phrasalverbs.domain.repository.PhrasalVerbRepository

class SubmitPhrasalVerbQuizAnswerUseCase(
    private val repository: PhrasalVerbRepository,
) {
    suspend operator fun invoke(
        verbId: String,
        isCorrect: Boolean,
    ): Result<Unit> = repository.recordAnswer(verbId, isCorrect)
}
