package by.freiding.braindrop.feature.irregularverbs.domain.usecase

import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.irregularverbs.domain.repository.IrregularVerbRepository

class SubmitQuizAnswerUseCase(
    private val repository: IrregularVerbRepository,
) {
    suspend operator fun invoke(
        verbId: String,
        isCorrect: Boolean,
    ): Result<Unit> = repository.recordAnswer(verbId, isCorrect)
}
