package by.freiding.braindrop.feature.phrasalverbs.data.datasource

import by.freiding.braindrop.core.common.AppClock
import by.freiding.braindrop.database.StudyProgressQueries

private const val CATEGORY = "phrasal_verbs"

class LocalPhrasalVerbProgressDataSource(
    private val queries: StudyProgressQueries,
) {
    fun getAll() = queries.getByCategory(CATEGORY).executeAsList()

    fun getByVerbId(verbId: String) = queries.getItem(CATEGORY, verbId).executeAsOneOrNull()

    fun upsertProgress(
        verbId: String,
        isLearned: Boolean,
        timesCorrect: Int,
        timesIncorrect: Int,
    ) {
        queries.upsertProgress(
            category = CATEGORY,
            item_id = verbId,
            is_learned = if (isLearned) 1L else 0L,
            times_correct = timesCorrect.toLong(),
            times_incorrect = timesIncorrect.toLong(),
            last_studied_at = AppClock.nowEpochMillis(),
        )
    }
}
