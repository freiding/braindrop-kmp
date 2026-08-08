package by.freiding.braindrop.feature.irregularverbs.domain.repository

import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.irregularverbs.domain.model.IrregularVerb
import by.freiding.braindrop.feature.irregularverbs.domain.model.VerbWithProgress

interface IrregularVerbRepository {
    suspend fun getVerbsWithProgress(): Result<List<VerbWithProgress>>
    suspend fun getVerbDetail(verbId: String): Result<VerbWithProgress>
    suspend fun toggleLearned(verbId: String): Result<Unit>
    suspend fun recordAnswer(verbId: String, isCorrect: Boolean): Result<Unit>
    suspend fun getUnlearnedVerbs(): Result<List<IrregularVerb>>
    suspend fun getStreakDays(): Result<Int>
}
