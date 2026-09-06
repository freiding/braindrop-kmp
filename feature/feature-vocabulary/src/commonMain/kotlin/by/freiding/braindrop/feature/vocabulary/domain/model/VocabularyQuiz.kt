package by.freiding.braindrop.feature.vocabulary.domain.model

/** The two question formats in the Vocabulary quiz. */
enum class VocabularyQuizType {
    /** Show the sentence with one word blanked → choose the missing word from four options. */
    CLOZE,

    /** Show the translation + a masked skeleton → type the whole chunk in English. */
    TYPING,
}

/** How a quiz session picks its question formats. Parsed from the quiz route's `mode` string. */
enum class VocabularyQuizMode {
    CLOZE,
    TYPING,

    /** Alternate CLOZE and TYPING question by question. */
    MIXED,
    ;

    companion object {
        fun fromRoute(mode: String): VocabularyQuizMode = entries.firstOrNull { it.name == mode } ?: MIXED
    }
}

/**
 * One quiz question.
 *
 * @param sentence for CLOZE, the example sentence with [gapWord] replaced by "____"; for TYPING,
 *   the plain example sentence used by the "context if stuck" card with the chunk masked.
 * @param gapWord CLOZE only — the word removed from the sentence and the correct option.
 * @param maskedPattern TYPING only — e.g. "m___ a d______".
 * @param correctAnswer CLOZE: [gapWord]; TYPING: the full [Chunk.text].
 * @param options CLOZE only — four shuffled choices including [correctAnswer].
 * @param explanation shown after answering — from [ChunkError] or a generated collocation hint.
 */
data class VocabularyQuizQuestion(
    val chunk: Chunk,
    val type: VocabularyQuizType,
    val sentence: String,
    val sentenceTranslation: String,
    val gapWord: String?,
    val maskedPattern: String?,
    val correctAnswer: String,
    val options: List<String>,
    val explanation: String?,
)
