package by.freiding.braindrop.feature.home.data.repository

import by.freiding.braindrop.core.common.AppDispatchers
import by.freiding.braindrop.core.common.AppException
import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.home.data.datasource.LocalStudyCategoryDataSource
import by.freiding.braindrop.feature.home.data.datasource.LocalStudyProgressDataSource
import by.freiding.braindrop.feature.home.domain.model.StudyCategory
import by.freiding.braindrop.feature.home.domain.repository.StudyCategoryRepository
import kotlinx.coroutines.withContext

class StudyCategoryRepositoryImpl(
    private val categoryDataSource: LocalStudyCategoryDataSource,
    private val progressDataSource: LocalStudyProgressDataSource,
    private val dispatchers: AppDispatchers,
) : StudyCategoryRepository {

    override suspend fun getCategories(): Result<List<StudyCategory>> =
        withContext(dispatchers.io) {
            try {
                val categories = categoryDataSource.getStaticCategories().map { category ->
                    category.copy(studiedCount = progressDataSource.getStudiedCount(category.id))
                }
                Result.Success(categories)
            } catch (e: Exception) {
                Result.Error(AppException.DatabaseException(e.message ?: "Failed to load categories", e))
            }
        }
}
