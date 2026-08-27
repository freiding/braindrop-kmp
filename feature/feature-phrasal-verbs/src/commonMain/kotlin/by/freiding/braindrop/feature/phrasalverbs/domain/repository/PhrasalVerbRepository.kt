package by.freiding.braindrop.feature.phrasalverbs.domain.repository

import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.phrasalverbs.domain.model.PhrasalVerb
import by.freiding.braindrop.feature.phrasalverbs.domain.model.PhrasalVerbWithProgress

/**
 * Contract for the phrasal verbs data layer.
 * All suspend functions are safe to call from any coroutine context.
 */
interface PhrasalVerbRepository {
    suspend fun getVerbsWithProgress(): Result<List<PhrasalVerbWithProgress>>
    suspend fun getVerbDetail(verbId: String): Result<PhrasalVerbWithProgress>
    suspend fun getUnlearnedVerbs(): Result<List<PhrasalVerb>>
    suspend fun toggleLearned(verbId: String): Result<Unit>
    suspend fun recordAnswer(verbId: String, isCorrect: Boolean): Result<Unit>
    suspend fun getStreakDays(): Result<Int>
}
