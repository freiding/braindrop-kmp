package by.freiding.braindrop.feature.phrasalverbs.domain.usecase

import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.phrasalverbs.domain.repository.PhrasalVerbRepository

class TogglePhrasalVerbLearnedUseCase(
    private val repository: PhrasalVerbRepository,
) {
    suspend operator fun invoke(verbId: String): Result<Unit> =
        repository.toggleLearned(verbId)
}
