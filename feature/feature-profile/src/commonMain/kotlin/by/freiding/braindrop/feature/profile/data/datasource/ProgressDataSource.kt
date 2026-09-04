package by.freiding.braindrop.feature.profile.data.datasource

import by.freiding.braindrop.core.common.AppClock
import by.freiding.braindrop.core.database.datasource.DailyActivityDataSource
import by.freiding.braindrop.database.IrregularVerbProgressQueries
import by.freiding.braindrop.database.StudyProgressQueries
import by.freiding.braindrop.feature.profile.domain.model.CategoryProgressData
import by.freiding.braindrop.feature.profile.domain.model.DayActivity
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus

class ProgressDataSource(
    private val studyProgressQueries: StudyProgressQueries,
    private val irregularVerbProgressQueries: IrregularVerbProgressQueries,
    private val dailyActivityDataSource: DailyActivityDataSource,
) {
    fun getCategoryProgress(
        categoryId: String,
        totalCount: Int,
    ): CategoryProgressData =
        if (categoryId == IRREGULAR_VERBS_ID) {
            CategoryProgressData(
                id = categoryId,
                learnedCount = irregularVerbProgressQueries.countLearned().executeAsOne().toInt(),
                totalCount = totalCount,
                timesCorrect = irregularVerbProgressQueries.sumCorrect().executeAsOne().toInt(),
                timesIncorrect = irregularVerbProgressQueries.sumIncorrect().executeAsOne().toInt(),
            )
        } else {
            CategoryProgressData(
                id = categoryId,
                learnedCount = studyProgressQueries.countLearnedByCategory(categoryId).executeAsOne().toInt(),
                totalCount = totalCount,
                timesCorrect = studyProgressQueries.sumCorrectByCategory(categoryId).executeAsOne().toInt(),
                timesIncorrect = studyProgressQueries.sumIncorrectByCategory(categoryId).executeAsOne().toInt(),
            )
        }

    fun getStreakDays(): Int = dailyActivityDataSource.getStreakDays()

    fun getWeekActivity(): List<DayActivity> {
        val today = LocalDate.parse(AppClock.todayIso())
        val activityMap = dailyActivityDataSource.getLastNDaysActivity(WEEK_DAYS)
        return (0 until WEEK_DAYS).map { offset ->
            val date = today.plus(offset - (WEEK_DAYS - 1), DateTimeUnit.DAY)
            DayActivity(date = date.toString(), learnedCount = activityMap[date.toString()] ?: 0)
        }
    }

    private companion object {
        const val IRREGULAR_VERBS_ID = "irregular_verbs"
        const val WEEK_DAYS = 7
    }
}
