package by.freiding.braindrop.feature.phrasalverbs.domain.model

data class PhrasalVerbProgress(
    val verbId: String,
    val isLearned: Boolean = false,
    val timesCorrect: Int = 0,
    val timesIncorrect: Int = 0,
)

data class PhrasalVerbWithProgress(
    val verb: PhrasalVerb,
    val progress: PhrasalVerbProgress,
)
