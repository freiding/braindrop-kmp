package by.freiding.braindrop.feature.phrasalverbs.domain.model

/**
 * A phrasal verb — a base verb combined with one or more particles that together carry a new meaning.
 *
 * @param isSeparable true when an object can be placed between the verb and the particle
 *   (e.g. "give something up"); false for inseparable and intransitive phrasal verbs.
 */
data class PhrasalVerb(
    val id: String,
    val verb: String,
    val particle: String,
    val meanings: List<PhrasalVerbMeaning>,
    val isSeparable: Boolean,
    val category: PhrasalVerbCategory,
) {
    val fullForm: String get() = "$verb $particle"
}

data class PhrasalVerbMeaning(
    val definition: String,
    val translation: String,
    val examples: List<PhrasalVerbExample>,
    val register: PhrasalVerbRegister = PhrasalVerbRegister.NEUTRAL,
)

data class PhrasalVerbExample(
    val english: String,
    val russian: String,
)

enum class PhrasalVerbCategory {
    WORK,
    RELATIONSHIPS,
    MOVEMENT,
    COMMUNICATION,
    CHANGES,
    GENERAL,
}

enum class PhrasalVerbRegister {
    FORMAL,
    INFORMAL,
    NEUTRAL,
}
