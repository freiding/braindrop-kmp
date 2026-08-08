package by.freiding.braindrop.core.database.datasource

import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlin.test.Test
import kotlin.test.assertEquals

class DailyActivityStreakTest {

    private val today = LocalDate(2026, 8, 8)

    @Test
    fun `empty activity means no streak`() {
        assertEquals(0, calculateStreakDays(today, emptySet()))
    }

    @Test
    fun `consecutive days including today count each day`() {
        val activeDates = setOf(
            today,
            today.minusDays(1),
            today.minusDays(2),
        )
        assertEquals(3, calculateStreakDays(today, activeDates))
    }

    @Test
    fun `no activity yet today falls back to counting from yesterday`() {
        val activeDates = setOf(
            today.minusDays(1),
            today.minusDays(2),
            today.minusDays(3),
        )
        assertEquals(3, calculateStreakDays(today, activeDates))
    }

    @Test
    fun `gap in the middle stops the streak at the gap`() {
        val activeDates = setOf(
            today,
            today.minusDays(1),
            // gap at today - 2
            today.minusDays(3),
            today.minusDays(4),
        )
        assertEquals(2, calculateStreakDays(today, activeDates))
    }

    @Test
    fun `activity only today counts as a one day streak`() {
        assertEquals(1, calculateStreakDays(today, setOf(today)))
    }

    @Test
    fun `activity that stops before yesterday counts as no streak`() {
        val activeDates = setOf(today.minusDays(2), today.minusDays(3))
        assertEquals(0, calculateStreakDays(today, activeDates))
    }

    private fun LocalDate.minusDays(days: Int): LocalDate = minus(DatePeriod(days = days))
}
