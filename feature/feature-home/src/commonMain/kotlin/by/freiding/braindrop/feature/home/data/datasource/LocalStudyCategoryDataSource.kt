package by.freiding.braindrop.feature.home.data.datasource

import by.freiding.braindrop.feature.home.domain.model.StudyCategory

class LocalStudyCategoryDataSource {
    fun getStaticCategories(): List<StudyCategory> = listOf(
        StudyCategory(
            id = "irregular_verbs",
            title = "Irregular Verbs",
            description = "Master past tense and past participle forms",
            icon = "📚",
            totalItems = 179,
        ),
        StudyCategory(
            id = "tenses",
            title = "English Tenses",
            description = "Learn all 12 tenses with examples",
            icon = "⏰",
            totalItems = 12,
        ),
        StudyCategory(
            id = "phrasal_verbs",
            title = "Phrasal Verbs",
            description = "Essential two-word verbs for daily use",
            icon = "💬",
            totalItems = 200,
        ),
    )
}
