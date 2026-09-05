package by.freiding.braindrop.feature.vocabulary.data.datasource

import by.freiding.braindrop.database.ChunkProgressQueries
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkProgress
import kotlinx.datetime.LocalDate

/**
 * Thin SQLDelight wrapper for the `ChunkProgress` table. Maps rows to the domain [ChunkProgress]
 * via typed mapper lambdas so nothing above the data layer sees the generated row types (the
 * `getDue` query even generates its own result class because of the `due_date` nullability change).
 */
class LocalChunkProgressDataSource(
    private val queries: ChunkProgressQueries,
) {
    fun getAll(): List<ChunkProgress> = queries.getAll(::mapRow).executeAsList()

    fun getById(chunkId: String): ChunkProgress? = queries.getById(chunkId, ::mapRow).executeAsOneOrNull()

    fun getDue(todayIso: String): List<ChunkProgress> = queries.getDue(todayIso, ::mapDueRow).executeAsList()

    fun countLearned(): Int = queries.countLearned().executeAsOne().toInt()

    fun countDue(todayIso: String): Int = queries.countDue(todayIso).executeAsOne().toInt()

    fun upsert(progress: ChunkProgress) {
        queries.upsertProgress(
            chunk_id = progress.chunkId,
            box = progress.box.toLong(),
            due_date = progress.dueDate?.toString(),
            streak = progress.streak.toLong(),
            times_seen = progress.timesSeen.toLong(),
            times_correct = progress.timesCorrect.toLong(),
            last_reviewed_at = progress.lastReviewedAt,
            is_learned = if (progress.isLearned) 1L else 0L,
        )
    }

    @Suppress("LongParameterList")
    private fun mapRow(
        chunkId: String,
        box: Long,
        dueDate: String?,
        streak: Long,
        timesSeen: Long,
        timesCorrect: Long,
        lastReviewedAt: Long?,
        isLearned: Long,
    ): ChunkProgress =
        ChunkProgress(
            chunkId = chunkId,
            box = box.toInt(),
            dueDate = dueDate?.let(LocalDate::parse),
            streak = streak.toInt(),
            timesSeen = timesSeen.toInt(),
            timesCorrect = timesCorrect.toInt(),
            lastReviewedAt = lastReviewedAt,
            isLearned = isLearned == 1L,
        )

    @Suppress("LongParameterList")
    private fun mapDueRow(
        chunkId: String,
        box: Long,
        dueDate: String,
        streak: Long,
        timesSeen: Long,
        timesCorrect: Long,
        lastReviewedAt: Long?,
        isLearned: Long,
    ): ChunkProgress = mapRow(chunkId, box, dueDate, streak, timesSeen, timesCorrect, lastReviewedAt, isLearned)
}
