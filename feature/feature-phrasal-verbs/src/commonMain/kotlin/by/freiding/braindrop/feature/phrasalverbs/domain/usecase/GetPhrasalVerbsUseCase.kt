package by.freiding.braindrop.feature.phrasalverbs.domain.usecase

import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.phrasalverbs.domain.model.PhrasalVerbWithProgress
import by.freiding.braindrop.feature.phrasalverbs.domain.repository.PhrasalVerbRepository

class GetPhrasalVerbsUseCase(
    private val repository: PhrasalVerbRepository,
) {
    suspend operator fun invoke(): Result<List<PhrasalVerbWithProgress>> =
        repository.getVerbsWithProgress()
}
