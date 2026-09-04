package by.freiding.braindrop.feature.phrasalverbs.domain.usecase

import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.phrasalverbs.domain.repository.PhrasalVerbRepository

class GetPhrasalVerbStreakDaysUseCase(
    private val repository: PhrasalVerbRepository,
) {
    suspend operator fun invoke(): Result<Int> = repository.getStreakDays()
}
