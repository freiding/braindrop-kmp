package by.freiding.braindrop.feature.tenses.data.repository

import by.freiding.braindrop.core.common.AppDispatchers
import by.freiding.braindrop.core.common.AppException
import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.core.database.datasource.DailyActivityDataSource
import by.freiding.braindrop.feature.tenses.data.datasource.LocalTenseDataSource
import by.freiding.braindrop.feature.tenses.data.datasource.LocalTenseProgressDataSource
import by.freiding.braindrop.feature.tenses.domain.model.Tense
import by.freiding.braindrop.feature.tenses.domain.model.TenseComparison
import by.freiding.braindrop.feature.tenses.domain.model.TenseProgress
import by.freiding.braindrop.feature.tenses.domain.model.TenseWithProgress
import by.freiding.braindrop.feature.tenses.domain.repository.TenseRepository
import kotlinx.coroutines.withContext

class TenseRepositoryImpl(
    private val tenseDataSource: LocalTenseDataSource,
    private val progressDataSource: LocalTenseProgressDataSource,
    private val dailyActivityDataSource: DailyActivityDataSource,
    private val dispatchers: AppDispatchers,
) : TenseRepository {
    override suspend fun getTensesWithProgress(): Result<List<TenseWithProgress>> =
        withContext(dispatchers.io) {
            runCatching {
                val allProgress = progressDataSource.getAll().associateBy { it.item_id }
                tenseDataSource.getTenses().map { tense ->
                    val row = allProgress[tense.id]
                    TenseWithProgress(
                        tense = tense,
                        progress = TenseProgress(
                            tenseId = tense.id,
                            isLearned = row?.is_learned == 1L,
                            timesCorrect = row?.times_correct?.toInt() ?: 0,
                            timesIncorrect = row?.times_incorrect?.toInt() ?: 0,
                        ),
                    )
                }
            }.toResult()
        }

    override suspend fun getTenseDetail(tenseId: String): Result<TenseWithProgress> =
        withContext(dispatchers.io) {
            runCatching {
                val tense = tenseDataSource.getById(tenseId)
                    ?: throw NoSuchElementException("Tense not found: $tenseId")
                val row = progressDataSource.getByTenseId(tenseId)
                TenseWithProgress(
                    tense = tense,
                    progress = TenseProgress(
                        tenseId = tenseId,
                        isLearned = row?.is_learned == 1L,
                        timesCorrect = row?.times_correct?.toInt() ?: 0,
                        timesIncorrect = row?.times_incorrect?.toInt() ?: 0,
                    ),
                )
            }.toResult()
        }

    override suspend fun getComparisons(): Result<List<TenseComparison>> =
        withContext(dispatchers.io) {
            runCatching { tenseDataSource.getComparisons() }.toResult()
        }

    override suspend fun getComparison(comparisonId: String): Result<TenseComparison> =
        withContext(dispatchers.io) {
            runCatching {
                tenseDataSource.getComparison(comparisonId)
                    ?: throw NoSuchElementException("Comparison not found: $comparisonId")
            }.toResult()
        }

    override suspend fun toggleLearned(tenseId: String): Result<Unit> =
        withContext(dispatchers.io) {
            runCatching {
                val row = progressDataSource.getByTenseId(tenseId)
                val currentlyLearned = row?.is_learned == 1L
                progressDataSource.upsertProgress(
                    tenseId = tenseId,
                    isLearned = !currentlyLearned,
                    timesCorrect = row?.times_correct?.toInt() ?: 0,
                    timesIncorrect = row?.times_incorrect?.toInt() ?: 0,
                )
                if (!currentlyLearned) dailyActivityDataSource.recordLearnedToday()
            }.toResult()
        }

    override suspend fun recordAnswer(
        tenseId: String,
        isCorrect: Boolean,
    ): Result<Unit> =
        withContext(dispatchers.io) {
            runCatching {
                val row = progressDataSource.getByTenseId(tenseId)
                progressDataSource.upsertProgress(
                    tenseId = tenseId,
                    isLearned = row?.is_learned == 1L,
                    timesCorrect = (row?.times_correct?.toInt() ?: 0) + if (isCorrect) 1 else 0,
                    timesIncorrect = (row?.times_incorrect?.toInt() ?: 0) + if (isCorrect) 0 else 1,
                )
            }.toResult()
        }

    override suspend fun getUnlearnedTenses(): Result<List<Tense>> =
        withContext(dispatchers.io) {
            runCatching {
                val learnedIds = progressDataSource
                    .getAll()
                    .filter { it.is_learned == 1L }
                    .map { it.item_id }
                    .toSet()
                tenseDataSource.getTenses().filter { it.id !in learnedIds }
            }.toResult()
        }

    override suspend fun getStreakDays(): Result<Int> =
        withContext(dispatchers.io) {
            runCatching { dailyActivityDataSource.getStreakDays() }.toResult()
        }

    private fun <T> kotlin.Result<T>.toResult(): Result<T> =
        fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Error(AppException.DatabaseException(it.message ?: "Database error", it)) },
        )
}
