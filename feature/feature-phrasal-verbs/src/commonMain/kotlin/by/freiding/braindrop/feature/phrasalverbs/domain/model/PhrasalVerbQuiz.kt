package by.freiding.braindrop.feature.phrasalverbs.domain.model

enum class PhrasalVerbQuizType {
    /** Show English definition → choose the correct phrasal verb. */
    DEFINITION_TO_VERB,

    /** Show the phrasal verb → choose the correct Russian translation. */
    VERB_TO_TRANSLATION,

    /** Show the base verb + context hint → choose the correct particle. */
    FILL_PARTICLE,
}

data class PhrasalVerbQuizQuestion(
    val verb: PhrasalVerb,
    val meaningIndex: Int,
    val type: PhrasalVerbQuizType,
    val questionText: String,
    val correctAnswer: String,
    val options: List<String>,
)
