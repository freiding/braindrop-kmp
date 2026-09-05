package by.freiding.braindrop.feature.vocabulary.domain.usecase

import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkProgress
import by.freiding.braindrop.feature.vocabulary.domain.repository.ChunkRepository
import by.freiding.braindrop.feature.vocabulary.domain.srs.RecallGrade

class GradeChunkUseCase(
    private val repository: ChunkRepository,
) {
    suspend operator fun invoke(
        chunkId: String,
        grade: RecallGrade,
    ): Result<ChunkProgress> = repository.gradeChunk(chunkId, grade)
}
