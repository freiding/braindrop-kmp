package by.freiding.braindrop.feature.tenses.domain.model

data class TenseQuizQuestion(
    val tenseId: String,
    val type: TenseQuizType,
    val questionText: String,
    val correctAnswer: String,
    val options: List<String>,
    /** Why the correct answer is right (and the confusable alternative isn't) — shown on a miss. */
    val explanation: String,
)
