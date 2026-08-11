package by.freiding.braindrop.feature.tenses.domain.model

enum class TenseQuizType {
    /** Fill in the correct verb phrase for a tense in one of the shared scenarios. */
    FORM,

    /** Given a sentence with a signal word, pick which tense it is. */
    MARKER_MATCH,

    /** Given a context clue, choose between two commonly confused tenses' forms. */
    DISCRIMINATION,

    /** A mix of all question kinds across every tense, weighted toward unlearned ones. */
    MIXED_REVIEW,
    ;

    companion object {
        fun fromString(value: String): TenseQuizType = entries.firstOrNull { it.name == value } ?: MIXED_REVIEW
    }
}
