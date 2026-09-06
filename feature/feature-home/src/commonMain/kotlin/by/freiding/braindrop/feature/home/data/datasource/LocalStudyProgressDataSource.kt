package by.freiding.braindrop.feature.home.data.datasource

import by.freiding.braindrop.core.common.AppClock
import by.freiding.braindrop.database.ChunkProgressQueries
import by.freiding.braindrop.database.IrregularVerbProgressQueries
import by.freiding.braindrop.database.StudyProgressQueries

class LocalStudyProgressDataSource(
    private val studyProgressQueries: StudyProgressQueries,
    private val irregularVerbProgressQueries: IrregularVerbProgressQueries,
    private val chunkProgressQueries: ChunkProgressQueries,
) {
    fun getStudiedCount(categoryId: String): Int =
        when (categoryId) {
            "irregular_verbs" -> irregularVerbProgressQueries.countLearned().executeAsOne().toInt()
            "vocabulary" -> chunkProgressQueries.countLearned().executeAsOne().toInt()
            else -> studyProgressQueries.countLearnedByCategory(categoryId).executeAsOne().toInt()
        }

    /** Items due for review today; null when the category has no spaced-repetition schedule. */
    fun getDueCount(categoryId: String): Int? =
        when (categoryId) {
            "vocabulary" -> chunkProgressQueries.countDue(AppClock.todayIso()).executeAsOne().toInt()
            else -> null
        }
}
