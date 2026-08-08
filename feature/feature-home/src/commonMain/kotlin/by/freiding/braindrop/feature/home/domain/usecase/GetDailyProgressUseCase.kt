package by.freiding.braindrop.feature.home.domain.usecase

import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.home.domain.model.DailyProgress
import by.freiding.braindrop.feature.home.domain.repository.DailyProgressRepository

class GetDailyProgressUseCase(
    private val repository: DailyProgressRepository,
) {
    suspend operator fun invoke(): Result<DailyProgress> = repository.getDailyProgress()
}
