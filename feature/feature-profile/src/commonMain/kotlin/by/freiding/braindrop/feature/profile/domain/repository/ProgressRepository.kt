package by.freiding.braindrop.feature.profile.domain.repository

import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.profile.domain.model.ProgressData

/** Provides aggregate progress data across all study categories. */
interface ProgressRepository {
    suspend fun getProgressData(): Result<ProgressData>
}
