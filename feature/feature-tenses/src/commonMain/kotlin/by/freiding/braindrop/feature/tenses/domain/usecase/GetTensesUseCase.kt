package by.freiding.braindrop.feature.tenses.domain.usecase

import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.tenses.domain.model.TenseWithProgress
import by.freiding.braindrop.feature.tenses.domain.repository.TenseRepository

class GetTensesUseCase(
    private val repository: TenseRepository,
) {
    suspend operator fun invoke(): Result<List<TenseWithProgress>> = repository.getTensesWithProgress()
}
