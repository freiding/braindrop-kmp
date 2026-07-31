package by.freiding.braindrop.feature.irregularverbs.domain.usecase

import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.irregularverbs.domain.model.VerbWithProgress
import by.freiding.braindrop.feature.irregularverbs.domain.repository.IrregularVerbRepository

class GetVerbsUseCase(private val repository: IrregularVerbRepository) {
    suspend operator fun invoke(): Result<List<VerbWithProgress>> =
        repository.getVerbsWithProgress()
}
