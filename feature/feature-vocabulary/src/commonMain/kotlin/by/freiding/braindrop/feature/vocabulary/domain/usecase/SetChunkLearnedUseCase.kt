package by.freiding.braindrop.feature.vocabulary.domain.usecase

import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.vocabulary.domain.repository.ChunkRepository

class SetChunkLearnedUseCase(
    private val repository: ChunkRepository,
) {
    suspend operator fun invoke(
        chunkId: String,
        learned: Boolean,
    ): Result<Unit> = repository.setLearned(chunkId, learned)
}
