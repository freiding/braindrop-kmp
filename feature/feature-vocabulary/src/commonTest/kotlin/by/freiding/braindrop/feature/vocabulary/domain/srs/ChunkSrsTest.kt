package by.freiding.braindrop.feature.vocabulary.domain.srs

import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkProgress
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ChunkSrsTest {
    private val today = LocalDate(2026, 9, 6)

    private fun progress(
        box: Int = 0,
        streak: Int = 0,
    ) = ChunkProgress(chunkId = "c", box = box, streak = streak)

    @Test
    fun `KNOW moves the chunk up one box and schedules the next box interval`() {
        val result = ChunkSrs.schedule(progress(box = 2), RecallGrade.KNOW, today, nowMillis = 1_000L)

        assertEquals(3, result.box)
        assertEquals(today.plusDaysForTest(ChunkSrs.INTERVALS[3]), result.dueDate)
        assertEquals(1, result.streak)
        assertEquals(1_000L, result.lastReviewedAt)
    }

    @Test
    fun `ALMOST holds the box and reschedules at the same interval`() {
        val result = ChunkSrs.schedule(progress(box = 2, streak = 4), RecallGrade.ALMOST, today, nowMillis = 0L)

        assertEquals(2, result.box)
        assertEquals(today.plusDaysForTest(ChunkSrs.INTERVALS[2]), result.dueDate)
        assertEquals(0, result.streak, "streak resets on anything weaker than KNOW")
    }

    @Test
    fun `ALMOST on a brand-new chunk still leaves the session with a minimum one-day interval`() {
        val result = ChunkSrs.schedule(progress(box = 0), RecallGrade.ALMOST, today, nowMillis = 0L)

        assertEquals(0, result.box)
        assertEquals(1, ChunkSrs.intervalDaysFor(progress(box = 0), RecallGrade.ALMOST))
        assertEquals(today.plusDaysForTest(1), result.dueDate)
    }

    @Test
    fun `DONT_KNOW drops the chunk to box 0 due today`() {
        val result = ChunkSrs.schedule(progress(box = 5, streak = 9), RecallGrade.DONT_KNOW, today, nowMillis = 0L)

        assertEquals(0, result.box)
        assertEquals(today, result.dueDate)
        assertEquals(0, result.streak)
        assertFalse(result.isLearned)
    }

    @Test
    fun `crossing LEARNED_BOX marks the chunk learned`() {
        val belowThreshold = ChunkSrs.schedule(progress(box = ChunkSrs.LEARNED_BOX - 2), RecallGrade.KNOW, today, 0L)
        assertFalse(belowThreshold.isLearned)

        val atThreshold = ChunkSrs.schedule(progress(box = ChunkSrs.LEARNED_BOX - 1), RecallGrade.KNOW, today, 0L)
        assertTrue(atThreshold.isLearned)
    }

    @Test
    fun `box never exceeds the interval table`() {
        val maxed = ChunkSrs.schedule(progress(box = ChunkSrs.INTERVALS.lastIndex), RecallGrade.KNOW, today, 0L)
        assertEquals(ChunkSrs.INTERVALS.lastIndex, maxed.box)
    }

    @Test
    fun `interval preview matches the design copy for a box-2 chunk`() {
        val p = progress(box = 2)
        assertEquals(0, ChunkSrs.intervalDaysFor(p, RecallGrade.DONT_KNOW))
        assertEquals(2, ChunkSrs.intervalDaysFor(p, RecallGrade.ALMOST))
        assertEquals(6, ChunkSrs.intervalDaysFor(p, RecallGrade.KNOW))
    }

    private fun LocalDate.plusDaysForTest(days: Int): LocalDate = plus(days, DateTimeUnit.DAY)
}
