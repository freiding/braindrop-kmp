package by.freiding.braindrop.feature.home.data.datasource

import by.freiding.braindrop.core.common.AppClock
import by.freiding.braindrop.database.DailyActivityQueries
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus

/** Reads daily activity for the "Daily goal" card and the streak chip on Home. */
class LocalDailyActivityDataSource(private val queries: DailyActivityQueries) {

    fun getTodayCount(): Int =
        queries.getByDate(AppClock.todayIso()).executeAsOneOrNull()?.learned_count?.toInt() ?: 0

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
