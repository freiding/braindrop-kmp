package by.freiding.braindrop.feature.vocabulary.domain.usecase

import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.vocabulary.domain.repository.ChunkRepository

class GetVocabularyStreakDaysUseCase(
    private val repository: ChunkRepository,
) {
    suspend operator fun invoke(): Result<Int> = repository.getStreakDays()
}
