package by.freiding.braindrop.feature.vocabulary.domain.usecase

import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkWithProgress
import by.freiding.braindrop.feature.vocabulary.domain.repository.ChunkRepository

class GetChunkDetailUseCase(
    private val repository: ChunkRepository,
) {
    suspend operator fun invoke(chunkId: String): Result<ChunkWithProgress> = repository.getChunkDetail(chunkId)
}
