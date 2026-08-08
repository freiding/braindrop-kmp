package by.freiding.braindrop.feature.home.data.datasource

import by.freiding.braindrop.database.IrregularVerbProgressQueries
import by.freiding.braindrop.database.StudyProgressQueries

class LocalStudyProgressDataSource(
    private val studyProgressQueries: StudyProgressQueries,
    private val irregularVerbProgressQueries: IrregularVerbProgressQueries,
) {
    fun getStudiedCount(categoryId: String): Int =
        when (categoryId) {
            "irregular_verbs" -> irregularVerbProgressQueries.countLearned().executeAsOne().toInt()
            else -> studyProgressQueries.countByCategory(categoryId).executeAsOne().toInt()
        }
}
