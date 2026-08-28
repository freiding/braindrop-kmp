package by.freiding.braindrop.feature.profile.domain.model

/** Progress stats for a single study category (learned count, quiz accuracy). */
data class CategoryProgressData(
    val id: String,
    val learnedCount: Int,
    val totalCount: Int,
    val timesCorrect: Int,
    val timesIncorrect: Int,
) {
    /** Fraction of correct quiz answers, or 0 if no attempts yet. */
    val accuracy: Float
        get() {
            val total = timesCorrect + timesIncorrect
            return if (total > 0) timesCorrect.toFloat() / total else 0f
        }

    val hasAttempts: Boolean get() = timesCorrect + timesIncorrect > 0
}

/** Learned-item count for a single calendar day. */
data class DayActivity(
    val date: String,
    val learnedCount: Int,
)

/** Aggregate progress snapshot used by the Progress tab. */
data class ProgressData(
    val categories: List<CategoryProgressData>,
    val streakDays: Int,
    val weekActivity: List<DayActivity>,
) {
    val totalLearned: Int get() = categories.sumOf { it.learnedCount }
    val totalItems: Int get() = categories.sumOf { it.totalCount }
    val overallAccuracy: Float
        get() {
            val correct = categories.sumOf { it.timesCorrect }
            val total = correct + categories.sumOf { it.timesIncorrect }
            return if (total > 0) correct.toFloat() / total else 0f
        }
    val hasAnyAttempts: Boolean get() = categories.any { it.hasAttempts }
}
