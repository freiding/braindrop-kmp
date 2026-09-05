package by.freiding.braindrop.feature.vocabulary.data.datasource

import by.freiding.braindrop.database.ChunkProgressQueries
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkProgress
import kotlinx.datetime.LocalDate

/**
 * Thin SQLDelight wrapper for the `ChunkProgress` table. Maps rows to the domain [ChunkProgress]
 * via a typed mapper lambda so nothing above the data layer sees the generated row type.
 *
 * The table's other queries (`getDue`, `countDue`, `countLearned`) are consumed directly by
 * feature-home, not through this wrapper.
 */
class LocalChunkProgressDataSource(
    private val queries: ChunkProgressQueries,
) {
    fun getAll(): List<ChunkProgress> = queries.getAll(::mapRow).executeAsList()

    fun getById(chunkId: String): ChunkProgress? = queries.getById(chunkId, ::mapRow).executeAsOneOrNull()

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
}
