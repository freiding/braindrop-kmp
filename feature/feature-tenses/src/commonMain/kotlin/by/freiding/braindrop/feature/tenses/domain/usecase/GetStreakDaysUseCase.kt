package by.freiding.braindrop.feature.tenses.domain.usecase

import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.tenses.domain.repository.TenseRepository

class GetStreakDaysUseCase(
    private val repository: TenseRepository,
) {
    suspend operator fun invoke(): Result<Int> = repository.getStreakDays()
}
