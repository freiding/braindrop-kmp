package by.freiding.braindrop.feature.tenses.domain.usecase

import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.tenses.domain.model.TenseWithProgress
import by.freiding.braindrop.feature.tenses.domain.repository.TenseRepository

class GetTenseDetailUseCase(
    private val repository: TenseRepository,
) {
    suspend operator fun invoke(tenseId: String): Result<TenseWithProgress> = repository.getTenseDetail(tenseId)
}
