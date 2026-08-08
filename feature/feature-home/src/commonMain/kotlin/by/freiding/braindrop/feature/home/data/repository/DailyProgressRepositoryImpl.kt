package by.freiding.braindrop.feature.home.data.repository

import by.freiding.braindrop.core.common.AppDispatchers
import by.freiding.braindrop.core.common.AppException
import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.core.database.datasource.DailyActivityDataSource
import by.freiding.braindrop.feature.home.domain.model.DailyProgress
import by.freiding.braindrop.feature.home.domain.repository.DailyProgressRepository
import kotlinx.coroutines.withContext

class DailyProgressRepositoryImpl(
    private val dataSource: DailyActivityDataSource,
    private val dispatchers: AppDispatchers,
) : DailyProgressRepository {
    override suspend fun getDailyProgress(): Result<DailyProgress> =
        withContext(dispatchers.io) {
            try {
                Result.Success(
                    DailyProgress(
                        goal = DAILY_GOAL,
                        done = dataSource.getTodayCount().coerceAtMost(DAILY_GOAL),
                        streakDays = dataSource.getStreakDays(),
                    ),
                )
            } catch (e: Exception) {
                Result.Error(AppException.DatabaseException(e.message ?: "Failed to load daily progress", e))
            }
        }

    private companion object {
        const val DAILY_GOAL = 8
    }
}
