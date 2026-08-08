package by.freiding.braindrop.feature.home.domain.repository

import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.home.domain.model.DailyProgress

interface DailyProgressRepository {
    suspend fun getDailyProgress(): Result<DailyProgress>
}
