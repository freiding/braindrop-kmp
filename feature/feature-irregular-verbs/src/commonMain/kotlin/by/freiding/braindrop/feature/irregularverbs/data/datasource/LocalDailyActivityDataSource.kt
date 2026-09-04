package by.freiding.braindrop.feature.irregularverbs.data.datasource

import by.freiding.braindrop.core.common.AppClock
import by.freiding.braindrop.database.DailyActivityQueries
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus

/** Marks today as active and reads the current streak — for the daily goal on Home and the streak tile in the quiz result. */
class LocalDailyActivityDataSource(private val queries: DailyActivityQueries) {

    fun recordLearnedToday() {
        val today = AppClock.todayIso()
        val existing = queries.getByDate(today).executeAsOneOrNull()
        if (existing == null) {
            queries.insertActivity(today, 1)
        } else {
            queries.updateActivity(existing.learned_count + 1, today)
        }
    }

    /**
     * Number of consecutive active days. If today has no recorded activity yet, the streak
     * is counted from yesterday — otherwise it would reset every midnight until the first action.
     */
    fun getStreakDays(): Int {
        val today = LocalDate.parse(AppClock.todayIso())
        val activeDates = queries.getRecentDesc(STREAK_LOOKBACK_DAYS.toLong())
            .executeAsList()
            .filter { it.learned_count > 0 }
            .map { LocalDate.parse(it.date) }
            .toSet()

        var cursor = if (today in activeDates) today else today.minus(1, DateTimeUnit.DAY)
        var streak = 0
        while (cursor in activeDates) {
            streak++
            cursor = cursor.minus(1, DateTimeUnit.DAY)
        }
        return streak
    }

    private companion object {
        const val STREAK_LOOKBACK_DAYS = 400
    }
}
