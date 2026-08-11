package by.freiding.braindrop.feature.tenses.domain.usecase

import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.tenses.domain.model.TenseComparison
import by.freiding.braindrop.feature.tenses.domain.repository.TenseRepository

class GetComparisonsUseCase(
    private val repository: TenseRepository,
) {
    suspend operator fun invoke(): Result<List<TenseComparison>> = repository.getComparisons()
}
