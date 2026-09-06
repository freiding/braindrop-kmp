package by.freiding.braindrop.feature.vocabulary.domain.usecase

import by.freiding.braindrop.core.common.AppException
import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.vocabulary.domain.model.Chunk
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkExample
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkLevel
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkProgress
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkRegister
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkTheme
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkWithProgress
import by.freiding.braindrop.feature.vocabulary.domain.repository.ChunkRepository
import by.freiding.braindrop.feature.vocabulary.domain.srs.ChunkSrs
import by.freiding.braindrop.feature.vocabulary.domain.srs.RecallGrade
import kotlinx.datetime.LocalDate

/**
 * Hand-written fake — same approach as `FakeTenseRepository`. Configure each method's result
 * independently and inspect recorded calls where a test needs to assert on them.
 */
class FakeChunkRepository(
    chunks: List<ChunkWithProgress> = emptyList(),
) : ChunkRepository {
    private val store = chunks.associateBy { it.chunk.id }.toMutableMap()
    var chunksResult: Result<List<ChunkWithProgress>> = Result.Success(chunks)
    var sessionChunks: Result<List<ChunkWithProgress>> = Result.Success(chunks)
    var dueChunks: Result<List<ChunkWithProgress>> = Result.Success(chunks)
    var streakDays: Result<Int> = Result.Success(0)

    val gradedCalls = mutableListOf<Pair<String, RecallGrade>>()
    val setLearnedCalls = mutableListOf<Pair<String, Boolean>>()

    private val today = LocalDate(2026, 9, 6)

    override suspend fun getChunksWithProgress(): Result<List<ChunkWithProgress>> = chunksResult

    override suspend fun getChunkDetail(chunkId: String): Result<ChunkWithProgress> =
        store[chunkId]?.let { Result.Success(it) }
            ?: Result.Error(AppException.DatabaseException("Chunk not found: $chunkId"))

    override suspend fun getDueChunks(limit: Int): Result<List<ChunkWithProgress>> = dueChunks.map { it.take(limit) }

    override suspend fun getSessionChunks(limit: Int): Result<List<ChunkWithProgress>> =
        sessionChunks.map { it.take(limit) }

    override suspend fun gradeChunk(
        chunkId: String,
        grade: RecallGrade,
    ): Result<ChunkProgress> {
        gradedCalls += chunkId to grade
        val current = store[chunkId]?.progress ?: ChunkProgress(chunkId = chunkId)
        val updated = ChunkSrs.schedule(current, grade, today, nowMillis = 0L)
        store[chunkId]?.let { store[chunkId] = it.copy(progress = updated) }
        return Result.Success(updated)
    }

    override suspend fun setLearned(
        chunkId: String,
        learned: Boolean,
    ): Result<Unit> {
        setLearnedCalls += chunkId to learned
        return Result.Success(Unit)
    }

    override suspend fun getStreakDays(): Result<Int> = streakDays

    private fun <T, R> Result<T>.map(transform: (T) -> R): Result<R> =
        when (this) {
            is Result.Success -> Result.Success(transform(data))
            is Result.Error -> this
        }
}

fun chunkFixture(
    id: String,
    text: String = "make a $id",
    headword: String = id,
    theme: ChunkTheme = ChunkTheme.WORK,
    level: ChunkLevel = ChunkLevel.B1,
): Chunk =
    Chunk(
        id = id,
        text = text,
        translation = "перевод $id",
        headword = headword,
        theme = theme,
        level = level,
        frequencyBand = null,
        register = ChunkRegister.NEUTRAL,
        pattern = "V + N",
        primaryExample = ChunkExample("We must $text today.", "Мы должны $text сегодня."),
        moreExamples = listOf(ChunkExample("She will $text tomorrow.", "Она $text завтра.")),
        commonError = null,
        relatedCollocations = emptyList(),
        wordForms = emptyList(),
        nearbyChunks = emptyList(),
    )

fun chunkWithProgressFixture(
    chunk: Chunk,
    progress: ChunkProgress = ChunkProgress(chunkId = chunk.id),
): ChunkWithProgress = ChunkWithProgress(chunk = chunk, progress = progress)
