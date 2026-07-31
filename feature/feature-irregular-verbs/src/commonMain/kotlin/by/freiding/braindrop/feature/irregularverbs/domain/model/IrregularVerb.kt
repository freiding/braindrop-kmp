package by.freiding.braindrop.feature.irregularverbs.domain.model

/**
 * An irregular English verb with all three principal forms, a Russian translation, and usage examples.
 */
data class IrregularVerb(
    val id: String,
    val baseForm: String,
    val pastSimple: String,
    val pastParticiple: String,
    val translation: String,
    val examples: List<VerbExample>,
    val group: VerbGroup = VerbGroup.ABC_OTHER,
)

data class VerbExample(
    val english: String,
    val russian: String,
)
