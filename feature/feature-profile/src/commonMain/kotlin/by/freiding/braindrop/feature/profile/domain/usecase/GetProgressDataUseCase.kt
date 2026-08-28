package by.freiding.braindrop.feature.profile.domain.usecase

import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.profile.domain.model.ProgressData
import by.freiding.braindrop.feature.profile.domain.repository.ProgressRepository

/** Returns aggregate progress across all study categories, current streak, and weekly activity. */
class GetProgressDataUseCase(private val repository: ProgressRepository) {
    suspend operator fun invoke(): Result<ProgressData> = repository.getProgressData()
}
