package by.freiding.braindrop.feature.vocabulary.domain.usecase

import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkProgress
import by.freiding.braindrop.feature.vocabulary.domain.repository.ChunkRepository
import by.freiding.braindrop.feature.vocabulary.domain.srs.RecallGrade

/**
 * Applies a recall [RecallGrade] to a chunk (card session and detail "Как хорошо помнишь?"),
 * advancing its spaced-repetition schedule and returning the new [ChunkProgress].
 */
class GradeChunkUseCase(
    private val repository: ChunkRepository,
) {
    suspend operator fun invoke(
        chunkId: String,
        grade: RecallGrade,
    ): Result<ChunkProgress> = repository.gradeChunk(chunkId, grade)
}
