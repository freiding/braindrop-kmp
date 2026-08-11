package by.freiding.braindrop.feature.tenses.domain.usecase

import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.tenses.domain.repository.TenseRepository

class SubmitTenseQuizAnswerUseCase(
    private val repository: TenseRepository,
) {
    suspend operator fun invoke(
        tenseId: String,
        isCorrect: Boolean,
    ): Result<Unit> = repository.recordAnswer(tenseId, isCorrect)
}
