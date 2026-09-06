package by.freiding.braindrop.feature.vocabulary.domain.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.daysUntil

/**
 * Spaced-repetition state for one [Chunk]. Persisted in the `ChunkProgress` table; scheduled by
 * [by.freiding.braindrop.feature.vocabulary.domain.srs.ChunkSrs].
 *
 * @param box Leitner box / SRS level. Higher box ⇒ longer interval.
 * @param dueDate the date the chunk is next due for review, or null if it has never been graded.
 * @param streak consecutive "know" grades; reset to 0 by any weaker grade.
 * @param isLearned true once [box] reaches [by.freiding.braindrop.feature.vocabulary.domain.srs.ChunkSrs.LEARNED_BOX].
 */
data class ChunkProgress(
    val chunkId: String,
    val box: Int = 0,
    val dueDate: LocalDate? = null,
    val streak: Int = 0,
    val timesSeen: Int = 0,
    val timesCorrect: Int = 0,
    val lastReviewedAt: Long? = null,
    val isLearned: Boolean = false,
) {
    /** True when the chunk has never been reviewed. */
    val isNew: Boolean get() = dueDate == null && timesSeen == 0
}

/**
 * A [Chunk] paired with its [ChunkProgress] and the [ReviewStatus] derived from the two,
 * relative to a reference date.
 */
data class ChunkWithProgress(
    val chunk: Chunk,
    val progress: ChunkProgress,
) {
    fun status(today: LocalDate): ReviewStatus =
        when {
            progress.isLearned -> ReviewStatus.Learned
            progress.isNew -> ReviewStatus.New
            progress.dueDate == null -> ReviewStatus.New
            progress.dueDate <= today -> ReviewStatus.DueToday
            else -> ReviewStatus.DueInDays(today.daysUntil(progress.dueDate))
        }
}
