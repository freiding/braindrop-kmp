package by.freiding.braindrop.feature.vocabulary.data.repository

import by.freiding.braindrop.core.common.AppClock
import by.freiding.braindrop.core.common.AppDispatchers
import by.freiding.braindrop.core.common.AppException
import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.core.database.datasource.DailyActivityDataSource
import by.freiding.braindrop.feature.vocabulary.data.datasource.LocalChunkDataSource
import by.freiding.braindrop.feature.vocabulary.data.datasource.LocalChunkProgressDataSource
import by.freiding.braindrop.feature.vocabulary.domain.model.Chunk
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkProgress
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkWithProgress
import by.freiding.braindrop.feature.vocabulary.domain.repository.ChunkRepository
import by.freiding.braindrop.feature.vocabulary.domain.srs.ChunkSrs
import by.freiding.braindrop.feature.vocabulary.domain.srs.RecallGrade
import kotlinx.coroutines.withContext
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus

class ChunkRepositoryImpl(
    private val chunkDataSource: LocalChunkDataSource,
    private val progressDataSource: LocalChunkProgressDataSource,
    private val dailyActivityDataSource: DailyActivityDataSource,
    private val dispatchers: AppDispatchers,
) : ChunkRepository {
    override suspend fun getChunksWithProgress(): Result<List<ChunkWithProgress>> =
        withContext(dispatchers.io) {
            runCatching {
                val byId = progressDataSource.getAll().associateBy { it.chunkId }
                chunkDataSource.getChunks().map { chunk -> chunk.withProgress(byId[chunk.id]) }
            }.toResult()
        }

    override suspend fun getChunkDetail(chunkId: String): Result<ChunkWithProgress> =
        withContext(dispatchers.io) {
            runCatching {
                val chunk = chunkDataSource.getById(chunkId)
                    ?: throw NoSuchElementException("Chunk not found: $chunkId")
                chunk.withProgress(progressDataSource.getById(chunkId))
            }.toResult()
        }

    override suspend fun getDueChunks(limit: Int): Result<List<ChunkWithProgress>> =
        withContext(dispatchers.io) {
            runCatching { orderedSession(chunkDataSource.getChunks()).take(limit) }.toResult()
        }

    override suspend fun getSessionChunks(limit: Int): Result<List<ChunkWithProgress>> =
        withContext(dispatchers.io) {
            runCatching {
                val ordered = orderedSession(chunkDataSource.getChunks())
                val takenIds = ordered.map { it.chunk.id }.toSet()
                val remaining = allWithProgress(chunkDataSource.getChunks())
                    .filter { it.chunk.id !in takenIds }
                    .shuffled()
                (ordered + remaining).take(limit)
            }.toResult()
        }

    override suspend fun getChunksByIds(chunkIds: List<String>): Result<List<ChunkWithProgress>> =
        withContext(dispatchers.io) {
            runCatching {
                val byId = progressDataSource.getAll().associateBy { it.chunkId }
                chunkIds.mapNotNull { id -> chunkDataSource.getById(id)?.withProgress(byId[id]) }
            }.toResult()
        }

    override suspend fun gradeChunk(
        chunkId: String,
        grade: RecallGrade,
    ): Result<ChunkProgress> =
        withContext(dispatchers.io) {
            runCatching {
                val current = progressDataSource.getById(chunkId) ?: ChunkProgress(chunkId = chunkId)
                val updated = ChunkSrs.schedule(
                    current = current,
                    grade = grade,
                    today = LocalDate.parse(AppClock.todayIso()),
                    nowMillis = AppClock.nowEpochMillis(),
                )
                progressDataSource.upsert(updated)
                if (updated.isLearned && !current.isLearned) dailyActivityDataSource.recordLearnedToday()
                updated
            }.toResult()
        }

    override suspend fun setLearned(
        chunkId: String,
        learned: Boolean,
    ): Result<Unit> =
        withContext(dispatchers.io) {
            runCatching {
                val current = progressDataSource.getById(chunkId) ?: ChunkProgress(chunkId = chunkId)
                val box = if (learned) ChunkSrs.LEARNED_BOX else 0
                val updated = current.copy(
                    box = box,
                    dueDate = LocalDate
                        .parse(AppClock.todayIso())
                        .plus(ChunkSrs.INTERVALS[box], DateTimeUnit.DAY),
                    lastReviewedAt = AppClock.nowEpochMillis(),
                    isLearned = learned,
                )
                progressDataSource.upsert(updated)
                if (learned && !current.isLearned) dailyActivityDataSource.recordLearnedToday()
            }.toResult()
        }

    override suspend fun getStreakDays(): Result<Int> =
        withContext(dispatchers.io) {
            runCatching { dailyActivityDataSource.getStreakDays() }.toResult()
        }

    override suspend fun getDueCount(): Result<Int> =
        withContext(dispatchers.io) {
            runCatching { progressDataSource.countDue(AppClock.todayIso()) }.toResult()
        }

    private fun Chunk.withProgress(progress: ChunkProgress?): ChunkWithProgress =
        ChunkWithProgress(chunk = this, progress = progress ?: ChunkProgress(chunkId = id))

    private fun allWithProgress(chunks: List<Chunk>): List<ChunkWithProgress> {
        val byId = progressDataSource.getAll().associateBy { it.chunkId }
        return chunks.map { it.withProgress(byId[it.id]) }
    }

    /** Due chunks (soonest first, most overdue first) followed by never-seen chunks. */
    private fun orderedSession(chunks: List<Chunk>): List<ChunkWithProgress> {
        val today = LocalDate.parse(AppClock.todayIso())
        val all = allWithProgress(chunks)
        val due = all
            .filter { !it.progress.isLearned && it.progress.dueDate != null && it.progress.dueDate <= today }
            .sortedBy { it.progress.dueDate }
        val fresh = all.filter { it.progress.isNew }
        return due + fresh
    }

    private fun <T> kotlin.Result<T>.toResult(): Result<T> =
        fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Error(AppException.DatabaseException(it.message ?: "Database error", it)) },
        )
}
