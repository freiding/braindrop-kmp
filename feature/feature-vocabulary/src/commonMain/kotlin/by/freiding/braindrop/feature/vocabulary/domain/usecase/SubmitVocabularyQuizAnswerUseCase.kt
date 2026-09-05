package by.freiding.braindrop.feature.vocabulary.domain.usecase

import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkProgress
import by.freiding.braindrop.feature.vocabulary.domain.repository.ChunkRepository
import by.freiding.braindrop.feature.vocabulary.domain.srs.RecallGrade

/**
 * Feeds a quiz answer into the spaced-repetition schedule: a correct answer counts as
 * [RecallGrade.KNOW], a wrong one as [RecallGrade.DONT_KNOW]. The returned [ChunkProgress] carries
 * the new due date the result screen buckets into "РАСПИСАНИЕ ПОВТОРЕНИЙ".
 */
class SubmitVocabularyQuizAnswerUseCase(
    private val repository: ChunkRepository,
) {
    suspend operator fun invoke(
        chunkId: String,
        isCorrect: Boolean,
    ): Result<ChunkProgress> =
        repository.gradeChunk(chunkId, if (isCorrect) RecallGrade.KNOW else RecallGrade.DONT_KNOW)
}
