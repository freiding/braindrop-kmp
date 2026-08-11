package by.freiding.braindrop.feature.home.data.datasource

import by.freiding.braindrop.feature.home.domain.model.StudyCategory

class LocalStudyCategoryDataSource {
    fun getStaticCategories(): List<StudyCategory> =
        listOf(
            StudyCategory(
                id = "irregular_verbs",
                icon = "📚",
                totalItems = 179,
                secondaryCount = 12,
                isAvailable = true,
            ),
            StudyCategory(
                id = "tenses",
                icon = "⏰",
                totalItems = 12,
                isAvailable = true,
            ),
            StudyCategory(
                id = "phrasal_verbs",
                icon = "💬",
                totalItems = 200,
                isAvailable = false,
            ),
        )
}
