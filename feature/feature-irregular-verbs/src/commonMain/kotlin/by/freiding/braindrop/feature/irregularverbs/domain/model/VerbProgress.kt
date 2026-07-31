package by.freiding.braindrop.feature.irregularverbs.domain.model

data class VerbProgress(
    val verbId: String,
    val isLearned: Boolean = false,
    val timesCorrect: Int = 0,
    val timesIncorrect: Int = 0,
)
