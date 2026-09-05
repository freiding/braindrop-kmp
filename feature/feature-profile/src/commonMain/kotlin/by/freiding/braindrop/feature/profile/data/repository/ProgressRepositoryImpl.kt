package by.freiding.braindrop.feature.profile.data.repository

import by.freiding.braindrop.core.common.AppDispatchers
import by.freiding.braindrop.core.common.AppException
import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.profile.data.datasource.ProgressDataSource
import by.freiding.braindrop.feature.profile.domain.model.ProgressData
import by.freiding.braindrop.feature.profile.domain.repository.ProgressRepository
import kotlinx.coroutines.withContext

class ProgressRepositoryImpl(
    private val dataSource: ProgressDataSource,
    private val dispatchers: AppDispatchers,
) : ProgressRepository {
    override suspend fun getProgressData(): Result<ProgressData> =
        withContext(dispatchers.io) {
            try {
                Result.Success(
                    ProgressData(
                        categories = CATEGORIES.map { (id, total) ->
                            dataSource.getCategoryProgress(id, total)
                        },
                        streakDays = dataSource.getStreakDays(),
                        weekActivity = dataSource.getWeekActivity(),
                    ),
                )
            } catch (e: Exception) {
                Result.Error(AppException.DatabaseException(e.message ?: "Failed to load progress", e))
            }
        }

    private companion object {
        val CATEGORIES = listOf(
            "irregular_verbs" to 179,
            "tenses" to 12,
            "phrasal_verbs" to 73,
        )
    }
}
