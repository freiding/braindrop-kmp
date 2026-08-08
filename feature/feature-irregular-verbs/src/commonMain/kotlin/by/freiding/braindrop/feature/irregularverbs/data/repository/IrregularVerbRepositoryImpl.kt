package by.freiding.braindrop.feature.irregularverbs.data.repository

import by.freiding.braindrop.core.common.AppDispatchers
import by.freiding.braindrop.core.common.AppException
import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.core.database.datasource.DailyActivityDataSource
import by.freiding.braindrop.feature.irregularverbs.data.datasource.LocalIrregularVerbDataSource
import by.freiding.braindrop.feature.irregularverbs.data.datasource.LocalVerbProgressDataSource
import by.freiding.braindrop.feature.irregularverbs.domain.model.IrregularVerb
import by.freiding.braindrop.feature.irregularverbs.domain.model.VerbProgress
import by.freiding.braindrop.feature.irregularverbs.domain.model.VerbWithProgress
import by.freiding.braindrop.feature.irregularverbs.domain.repository.IrregularVerbRepository
import kotlinx.coroutines.withContext

class IrregularVerbRepositoryImpl(
    private val verbDataSource: LocalIrregularVerbDataSource,
    private val progressDataSource: LocalVerbProgressDataSource,
    private val dailyActivityDataSource: DailyActivityDataSource,
    private val dispatchers: AppDispatchers,
) : IrregularVerbRepository {

    override suspend fun getVerbsWithProgress(): Result<List<VerbWithProgress>> = withContext(dispatchers.io) {
        runCatching {
            val allProgress = progressDataSource.getAll().associateBy { it.verb_id }
            verbDataSource.getVerbs().map { verb ->
                val row = allProgress[verb.id]
                VerbWithProgress(
                    verb = verb,
                    progress = VerbProgress(
                        verbId = verb.id,
                        isLearned = row?.is_learned == 1L,
                        timesCorrect = row?.times_correct?.toInt() ?: 0,
                        timesIncorrect = row?.times_incorrect?.toInt() ?: 0,
                    ),
                )
            }
        }.toResult()
    }

    override suspend fun getVerbDetail(verbId: String): Result<VerbWithProgress> = withContext(dispatchers.io) {
        runCatching {
            val verb = verbDataSource.getById(verbId)
                ?: throw NoSuchElementException("Verb not found: $verbId")
            val row = progressDataSource.getByVerbId(verbId)
            VerbWithProgress(
                verb = verb,
                progress = VerbProgress(
                    verbId = verbId,
                    isLearned = row?.is_learned == 1L,
                    timesCorrect = row?.times_correct?.toInt() ?: 0,
                    timesIncorrect = row?.times_incorrect?.toInt() ?: 0,
                ),
            )
        }.toResult()
    }

    override suspend fun toggleLearned(verbId: String): Result<Unit> = withContext(dispatchers.io) {
        runCatching {
            val row = progressDataSource.getByVerbId(verbId)
            val currentlyLearned = row?.is_learned == 1L
            progressDataSource.upsertProgress(
                verbId = verbId,
                isLearned = !currentlyLearned,
                timesCorrect = row?.times_correct?.toInt() ?: 0,
                timesIncorrect = row?.times_incorrect?.toInt() ?: 0,
            )
            if (!currentlyLearned) dailyActivityDataSource.recordLearnedToday()
        }.toResult()
    }

    override suspend fun recordAnswer(verbId: String, isCorrect: Boolean): Result<Unit> = withContext(dispatchers.io) {
        runCatching {
            val row = progressDataSource.getByVerbId(verbId)
            progressDataSource.upsertProgress(
                verbId = verbId,
                isLearned = row?.is_learned == 1L,
                timesCorrect = (row?.times_correct?.toInt() ?: 0) + if (isCorrect) 1 else 0,
                timesIncorrect = (row?.times_incorrect?.toInt() ?: 0) + if (isCorrect) 0 else 1,
            )
        }.toResult()
    }

    override suspend fun getUnlearnedVerbs(): Result<List<IrregularVerb>> = withContext(dispatchers.io) {
        runCatching {
            val learnedIds = progressDataSource.getAll()
                .filter { it.is_learned == 1L }
                .map { it.verb_id }
                .toSet()
            verbDataSource.getVerbs().filter { it.id !in learnedIds }
        }.toResult()
    }

    override suspend fun getStreakDays(): Result<Int> = withContext(dispatchers.io) {
        runCatching {
            dailyActivityDataSource.getStreakDays()
        }.toResult()
    }

    private fun <T> kotlin.Result<T>.toResult(): Result<T> = fold(
        onSuccess = { Result.Success(it) },
        onFailure = { Result.Error(AppException.DatabaseException(it.message ?: "Database error", it)) },
    )
}
