package by.freiding.braindrop.feature.irregularverbs.domain.usecase

import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.irregularverbs.domain.repository.IrregularVerbRepository

class GetStreakDaysUseCase(private val repository: IrregularVerbRepository) {
    suspend operator fun invoke(): Result<Int> = repository.getStreakDays()
}
