package by.freiding.braindrop.feature.home.domain.usecase

import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.home.domain.model.StudyCategory
import by.freiding.braindrop.feature.home.domain.repository.StudyCategoryRepository

class GetStudyCategoriesUseCase(
    private val repository: StudyCategoryRepository,
) {
    suspend operator fun invoke(): Result<List<StudyCategory>> = repository.getCategories()
}
