package by.freiding.braindrop.feature.tenses.domain.repository

import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.tenses.domain.model.Tense
import by.freiding.braindrop.feature.tenses.domain.model.TenseComparison
import by.freiding.braindrop.feature.tenses.domain.model.TenseWithProgress

interface TenseRepository {
    suspend fun getTensesWithProgress(): Result<List<TenseWithProgress>>

    suspend fun getTenseDetail(tenseId: String): Result<TenseWithProgress>

    suspend fun getComparisons(): Result<List<TenseComparison>>

    suspend fun getComparison(comparisonId: String): Result<TenseComparison>

    suspend fun toggleLearned(tenseId: String): Result<Unit>

    suspend fun recordAnswer(
        tenseId: String,
        isCorrect: Boolean,
    ): Result<Unit>

    suspend fun getUnlearnedTenses(): Result<List<Tense>>

    suspend fun getStreakDays(): Result<Int>
}
