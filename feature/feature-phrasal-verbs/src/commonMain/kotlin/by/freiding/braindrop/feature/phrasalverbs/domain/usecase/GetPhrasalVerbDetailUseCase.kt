package by.freiding.braindrop.feature.phrasalverbs.domain.usecase

import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.phrasalverbs.domain.model.PhrasalVerbWithProgress
import by.freiding.braindrop.feature.phrasalverbs.domain.repository.PhrasalVerbRepository

class GetPhrasalVerbDetailUseCase(
    private val repository: PhrasalVerbRepository,
) {
    suspend operator fun invoke(verbId: String): Result<PhrasalVerbWithProgress> = repository.getVerbDetail(verbId)
}
