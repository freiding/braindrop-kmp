package by.freiding.braindrop.feature.tenses.domain.usecase

import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.tenses.domain.repository.TenseRepository

class ToggleTenseLearnedUseCase(
    private val repository: TenseRepository,
) {
    suspend operator fun invoke(tenseId: String): Result<Unit> = repository.toggleLearned(tenseId)
}
