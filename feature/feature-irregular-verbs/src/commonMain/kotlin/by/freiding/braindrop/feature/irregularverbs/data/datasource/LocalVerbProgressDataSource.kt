package by.freiding.braindrop.feature.irregularverbs.data.datasource

import by.freiding.braindrop.database.IrregularVerbProgressQueries

class LocalVerbProgressDataSource(private val queries: IrregularVerbProgressQueries) {

    fun getAll() = queries.getAll().executeAsList()

    fun getByVerbId(verbId: String) = queries.getByVerbId(verbId).executeAsOneOrNull()

    fun countLearned(): Int = queries.countLearned().executeAsOne().toInt()

    fun upsertProgress(verbId: String, isLearned: Boolean, timesCorrect: Int, timesIncorrect: Int) {
        queries.upsertProgress(
            verb_id = verbId,
            is_learned = if (isLearned) 1L else 0L,
            times_correct = timesCorrect.toLong(),
            times_incorrect = timesIncorrect.toLong(),
            last_studied_at = null,
        )
    }
}
