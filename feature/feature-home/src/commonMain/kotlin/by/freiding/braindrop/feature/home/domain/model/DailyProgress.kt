package by.freiding.braindrop.feature.home.domain.model

/** Daily goal progress and the current streak — used by the "Daily goal" card and the streak chip on Home. */
data class DailyProgress(
    val goal: Int,
    val done: Int,
    val streakDays: Int,
)
