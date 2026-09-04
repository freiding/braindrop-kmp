package by.freiding.braindrop.feature.phrasalverbs.data.repository

import by.freiding.braindrop.core.common.AppDispatchers
import by.freiding.braindrop.core.common.AppException
import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.core.database.datasource.DailyActivityDataSource
import by.freiding.braindrop.feature.phrasalverbs.data.datasource.LocalPhrasalVerbDataSource
import by.freiding.braindrop.feature.phrasalverbs.data.datasource.LocalPhrasalVerbProgressDataSource
import by.freiding.braindrop.feature.phrasalverbs.domain.model.PhrasalVerb
import by.freiding.braindrop.feature.phrasalverbs.domain.model.PhrasalVerbProgress
import by.freiding.braindrop.feature.phrasalverbs.domain.model.PhrasalVerbWithProgress
import by.freiding.braindrop.feature.phrasalverbs.domain.repository.PhrasalVerbRepository
import kotlinx.coroutines.withContext

class PhrasalVerbRepositoryImpl(
    private val verbDataSource: LocalPhrasalVerbDataSource,
    private val progressDataSource: LocalPhrasalVerbProgressDataSource,
    private val dailyActivityDataSource: DailyActivityDataSource,
    private val dispatchers: AppDispatchers,
) : PhrasalVerbRepository {

    override suspend fun getVerbsWithProgress(): Result<List<PhrasalVerbWithProgress>> =
        withContext(dispatchers.io) {
            runCatching {
                val allProgress = progressDataSource.getAll().associateBy { it.item_id }
                verbDataSource.getVerbs().map { verb ->
                    val row = allProgress[verb.id]
                    VerbWithProgress(verb, row)
                }
            }.toResult()
        }

    override suspend fun getVerbDetail(verbId: String): Result<PhrasalVerbWithProgress> =
        withContext(dispatchers.io) {
            runCatching {
                val verb = verbDataSource.getById(verbId)
                    ?: throw NoSuchElementException("Phrasal verb not found: $verbId")
                val row = progressDataSource.getByVerbId(verbId)
                VerbWithProgress(verb, row)
            }.toResult()
        }

    override suspend fun getUnlearnedVerbs(): Result<List<PhrasalVerb>> =
        withContext(dispatchers.io) {
            runCatching {
                val learnedIds = progressDataSource
                    .getAll()
                    .filter { it.is_learned == 1L }
                    .map { it.item_id }
                    .toSet()
                verbDataSource.getVerbs().filter { it.id !in learnedIds }
            }.toResult()
        }

    override suspend fun toggleLearned(verbId: String): Result<Unit> =
        withContext(dispatchers.io) {
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

    override suspend fun recordAnswer(verbId: String, isCorrect: Boolean): Result<Unit> =
        withContext(dispatchers.io) {
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

    override suspend fun getStreakDays(): Result<Int> =
        withContext(dispatchers.io) {
            runCatching { dailyActivityDataSource.getStreakDays() }.toResult()
        }

    private fun VerbWithProgress(verb: PhrasalVerb, row: by.freiding.braindrop.database.StudyProgress?): PhrasalVerbWithProgress =
        PhrasalVerbWithProgress(
            verb = verb,
            progress = PhrasalVerbProgress(
                verbId = verb.id,
                isLearned = row?.is_learned == 1L,
                timesCorrect = row?.times_correct?.toInt() ?: 0,
                timesIncorrect = row?.times_incorrect?.toInt() ?: 0,
            ),
        )

    private fun <T> kotlin.Result<T>.toResult(): Result<T> =
        fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Error(AppException.DatabaseException(it.message ?: "Database error", it)) },
        )
}
