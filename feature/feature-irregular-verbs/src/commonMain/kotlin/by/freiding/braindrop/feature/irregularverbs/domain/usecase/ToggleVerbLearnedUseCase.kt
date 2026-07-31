package by.freiding.braindrop.feature.irregularverbs.domain.usecase

import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.irregularverbs.domain.repository.IrregularVerbRepository

class ToggleVerbLearnedUseCase(private val repository: IrregularVerbRepository) {
    suspend operator fun invoke(verbId: String): Result<Unit> =
        repository.toggleLearned(verbId)
}
