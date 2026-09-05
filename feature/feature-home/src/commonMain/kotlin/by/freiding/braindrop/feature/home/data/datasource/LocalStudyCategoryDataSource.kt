package by.freiding.braindrop.feature.home.data.datasource

import by.freiding.braindrop.feature.home.domain.model.StudyCategory

class LocalStudyCategoryDataSource {
    fun getStaticCategories(): List<StudyCategory> =
        listOf(
            StudyCategory(
                id = "vocabulary",
                icon = "🧩",
                totalItems = VOCABULARY_CHUNK_COUNT,
                secondaryCount = VOCABULARY_THEME_COUNT,
                isAvailable = true,
            ),
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
                totalItems = 73,
                isAvailable = true,
            ),
        )

    private companion object {
        // Mirrors the feature-vocabulary seed set; kept as constants because feature modules
        // must not depend on one another.
        const val VOCABULARY_CHUNK_COUNT = 78
        const val VOCABULARY_THEME_COUNT = 6
    }
}
