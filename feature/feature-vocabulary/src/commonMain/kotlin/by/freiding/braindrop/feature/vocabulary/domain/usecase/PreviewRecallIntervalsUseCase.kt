package by.freiding.braindrop.feature.vocabulary.domain.usecase

import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkProgress
import by.freiding.braindrop.feature.vocabulary.domain.srs.ChunkSrs
import by.freiding.braindrop.feature.vocabulary.domain.srs.RecallGrade

/**
 * The short interval hint shown under each grade button on the card session
 * ("СНОВА" / "2 ДНЯ" / "6 ДНЕЙ") for the chunk currently on screen.
 */
class PreviewRecallIntervalsUseCase {
    operator fun invoke(progress: ChunkProgress): Map<RecallGrade, String> =
        RecallGrade.entries.associateWith { grade -> label(ChunkSrs.intervalDaysFor(progress, grade)) }

    private fun label(days: Int): String =
        when {
            days <= 0 -> "СНОВА"
            days == 1 -> "1 ДЕНЬ"
            days in 2..4 -> "$days ДНЯ"
            else -> "$days ДНЕЙ"
        }
}
