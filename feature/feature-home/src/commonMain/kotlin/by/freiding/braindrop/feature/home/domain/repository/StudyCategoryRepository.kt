package by.freiding.braindrop.feature.home.domain.repository

import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.home.domain.model.StudyCategory

interface StudyCategoryRepository {
    suspend fun getCategories(): Result<List<StudyCategory>>
}
