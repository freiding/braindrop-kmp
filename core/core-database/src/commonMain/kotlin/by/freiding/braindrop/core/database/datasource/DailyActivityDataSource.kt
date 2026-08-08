package by.freiding.braindrop.core.database.datasource

import by.freiding.braindrop.core.common.AppClock
import by.freiding.braindrop.database.DailyActivityQueries
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus

/**
 * Daily activity ("did the user learn something today") backing the Home daily-goal card and
 * streak chip, and the quiz result's streak tile. Shared across features so the streak
 * definition and lookback window live in exactly one place.
 */
class DailyActivityDataSource(
    private val queries: DailyActivityQueries,
) {
    fun getTodayCount(): Int =
        queries
            .getByDate(AppClock.todayIso())
            .executeAsOneOrNull()
            ?.learned_count
            ?.toInt() ?: 0

    fun recordLearnedToday() {
        val today = AppClock.todayIso()
        queries.insertIfAbsent(today)
        queries.incrementToday(today)
    }

    /**
     * Number of consecutive active days. If today has no recorded activity yet, the streak
     * is counted from yesterday — otherwise it would reset every midnight until the first action.
     */
    fun getStreakDays(): Int {
        val today = LocalDate.parse(AppClock.todayIso())
        val activeDates = queries
            .getActiveDatesDesc(STREAK_LOOKBACK_DAYS.toLong())
            .executeAsList()
            .map { LocalDate.parse(it) }
            .toSet()
        return calculateStreakDays(today, activeDates)
    }

    private companion object {
        const val STREAK_LOOKBACK_DAYS = 400
    }
}

/**
 * Pure date-arithmetic core of [DailyActivityDataSource.getStreakDays], split out so the streak
 * rule can be unit tested without a SQLDelight driver.
 */
internal fun calculateStreakDays(
    today: LocalDate,
    activeDates: Set<LocalDate>,
): Int {
    var cursor = if (today in activeDates) today else today.minus(1, DateTimeUnit.DAY)
    var streak = 0
    while (cursor in activeDates) {
        streak++
        cursor = cursor.minus(1, DateTimeUnit.DAY)
    }
    return streak
}
