package by.freiding.braindrop.feature.vocabulary.domain.srs

import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkProgress
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus

/** How well the learner recalled a chunk — the three buttons on the card session and detail screen. */
enum class RecallGrade {
    DONT_KNOW,
    ALMOST,
    KNOW,
}

/**
 * Leitner-style spaced-repetition scheduler. Pure date arithmetic — no clock, no I/O — so it can
 * be unit tested with a fixed [LocalDate], the same way `calculateStreakDays` is in core-database.
 *
 * A chunk climbs one box on [RecallGrade.KNOW], holds its box on [RecallGrade.ALMOST], and drops
 * to box 0 on [RecallGrade.DONT_KNOW]. The box indexes [INTERVALS]; interval 0 means "due again
 * today" (the card comes back in the same session) — only [RecallGrade.DONT_KNOW] ever does that,
 * [RecallGrade.ALMOST] is floored at one day.
 *
 * Interval table is tuned to the design copy: a box-2 chunk yields "Почти → 2 ДНЯ",
 * "Знаю → 6 ДНЕЙ", and the detail strip reads "интервал 6 дней".
 */
object ChunkSrs {
    val INTERVALS = listOf(0, 1, 2, 6, 14, 30, 90)

    /** Box at (and above) which a chunk counts as learned. */
    const val LEARNED_BOX = 4

    /** Applies [grade] to [current], returning the updated progress due on/after [today]. */
    fun schedule(
        current: ChunkProgress,
        grade: RecallGrade,
        today: LocalDate,
        nowMillis: Long,
    ): ChunkProgress {
        val newBox = boxFor(current, grade)
        return current.copy(
            box = newBox,
            dueDate = today.plus(intervalDaysFor(current, grade), DateTimeUnit.DAY),
            streak = if (grade == RecallGrade.KNOW) current.streak + 1 else 0,
            timesSeen = current.timesSeen + 1,
            timesCorrect = current.timesCorrect + if (grade == RecallGrade.KNOW) 1 else 0,
            lastReviewedAt = nowMillis,
            isLearned = newBox >= LEARNED_BOX,
        )
    }

    /** Days until the next review if [grade] were applied to [current] now. */
    fun intervalDaysFor(
        current: ChunkProgress,
        grade: RecallGrade,
    ): Int {
        val days = INTERVALS[boxFor(current, grade)]
        return if (grade == RecallGrade.ALMOST) days.coerceAtLeast(1) else days
    }

    private fun boxFor(
        current: ChunkProgress,
        grade: RecallGrade,
    ): Int =
        when (grade) {
            RecallGrade.DONT_KNOW -> 0
            RecallGrade.ALMOST -> current.box.coerceIn(0, INTERVALS.lastIndex)
            RecallGrade.KNOW -> (current.box + 1).coerceAtMost(INTERVALS.lastIndex)
        }

    /** The current review interval in days for a chunk already in the schedule. */
    fun currentIntervalDays(current: ChunkProgress): Int = INTERVALS[current.box.coerceIn(0, INTERVALS.lastIndex)]
}
