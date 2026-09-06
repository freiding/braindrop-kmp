package by.freiding.braindrop.feature.vocabulary.domain.repository

import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkProgress
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkWithProgress
import by.freiding.braindrop.feature.vocabulary.domain.srs.RecallGrade

/**
 * Contract for the Vocabulary data layer. All suspend functions are safe to call from any
 * coroutine context (the impl confines work to a background dispatcher).
 */
interface ChunkRepository {
    suspend fun getChunksWithProgress(): Result<List<ChunkWithProgress>>

    suspend fun getChunkDetail(chunkId: String): Result<ChunkWithProgress>

    /** Card-session queue: chunks due on/before today first, then new chunks, capped at [limit]. */
    suspend fun getDueChunks(limit: Int): Result<List<ChunkWithProgress>>

    /** Quiz pool: due chunks first, then new, then the rest, shuffled within each tier, capped at [limit]. */
    suspend fun getSessionChunks(limit: Int): Result<List<ChunkWithProgress>>

    /** Applies an SRS [grade], persists the new schedule, and returns it. */
    suspend fun gradeChunk(
        chunkId: String,
        grade: RecallGrade,
    ): Result<ChunkProgress>

    /** Force a chunk learned / unlearned from the detail header, bypassing the schedule. */
    suspend fun setLearned(
        chunkId: String,
        learned: Boolean,
    ): Result<Unit>

    suspend fun getStreakDays(): Result<Int>
}
