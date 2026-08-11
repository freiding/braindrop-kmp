package by.freiding.braindrop.feature.tenses.domain.model

data class TenseProgress(
    val tenseId: String,
    val isLearned: Boolean,
    val timesCorrect: Int,
    val timesIncorrect: Int,
)
