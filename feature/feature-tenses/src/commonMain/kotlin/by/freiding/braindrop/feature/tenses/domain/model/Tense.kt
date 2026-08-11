package by.freiding.braindrop.feature.tenses.domain.model

enum class TenseTime {
    PRESENT,
    PAST,
    FUTURE,
}

enum class TenseAspect {
    SIMPLE,
    CONTINUOUS,
    PERFECT,
    PERFECT_CONTINUOUS,
}

/**
 * The same three subject+verb scenarios are conjugated by every tense so that quiz distractors
 * are grammatically apples-to-apples (a different tense's form of the *same* sentence), instead
 * of unrelated sentences that would make the wrong options obviously wrong for the wrong reason.
 */
enum class TenseScenario {
    READ_BOOK,
    CALL_FRIEND,
    FINISH_PROJECT,
    TRAVEL,
    STUDY,
}

data class TenseExample(
    val english: String,
    val russian: String,
)

/** An example sentence with the verb phrase that carries the tense marked for highlighting. */
data class TenseFormExample(
    val english: String,
    val russian: String,
    val highlight: String,
)

data class TenseFormulas(
    val affirmative: String,
    val negative: String,
    val question: String,
)

data class TenseUsageCase(
    val description: String,
    val example: TenseExample,
)

data class Tense(
    val id: String,
    val time: TenseTime,
    val aspect: TenseAspect,
    val titleEn: String,
    val titleRu: String,
    val formulas: TenseFormulas,
    val formExample: TenseFormExample,
    val scenarios: Map<TenseScenario, TenseFormExample>,
    val usageCases: List<TenseUsageCase>,
    val markers: List<String>,
    val markerExamples: List<TenseExample>,
    val commonMistake: String,
    val confusedWith: List<String>,
    /** Exceptions and lesser-known usage quirks — the "yes, but" notes beyond the core rules. */
    val specialNotes: List<TenseUsageCase>,
)
