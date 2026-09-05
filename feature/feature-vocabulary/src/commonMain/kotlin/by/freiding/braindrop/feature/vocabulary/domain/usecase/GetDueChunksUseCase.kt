package by.freiding.braindrop.feature.vocabulary.domain.usecase

import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkWithProgress
import by.freiding.braindrop.feature.vocabulary.domain.repository.ChunkRepository

class GetDueChunksUseCase(
    private val repository: ChunkRepository,
) {
    suspend operator fun invoke(limit: Int = DEFAULT_SESSION_SIZE): Result<List<ChunkWithProgress>> =
        repository.getDueChunks(limit)

    private companion object {
        const val DEFAULT_SESSION_SIZE = 20
    }
}
