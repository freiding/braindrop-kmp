package by.freiding.braindrop.feature.tenses.data.datasource

import by.freiding.braindrop.core.common.AppClock
import by.freiding.braindrop.database.StudyProgressQueries

private const val CATEGORY = "tenses"

class LocalTenseProgressDataSource(
    private val queries: StudyProgressQueries,
) {
    fun getAll() = queries.getByCategory(CATEGORY).executeAsList()

    fun getByTenseId(tenseId: String) = queries.getItem(CATEGORY, tenseId).executeAsOneOrNull()

    fun upsertProgress(
        tenseId: String,
        isLearned: Boolean,
        timesCorrect: Int,
        timesIncorrect: Int,
    ) {
        queries.upsertProgress(
            category = CATEGORY,
            item_id = tenseId,
            is_learned = if (isLearned) 1L else 0L,
            times_correct = timesCorrect.toLong(),
            times_incorrect = timesIncorrect.toLong(),
            last_studied_at = AppClock.nowEpochMillis(),
        )
    }
}
