package by.freiding.braindrop.feature.vocabulary.domain.model

/**
 * A lexical chunk — a word taught inside its natural collocation and context rather than in
 * isolation. The unit of study for the Vocabulary section: `make a decision` / `принимать
 * решение`, shown in a sentence with [headword] highlighted.
 *
 * @param text the chunk itself, e.g. "make a decision".
 * @param translation Russian translation of the whole chunk.
 * @param headword the single word the chunk is built around and that gets highlighted, e.g.
 *   "decision". Always a substring of [text].
 * @param pattern optional grammatical pattern label shown as a badge, e.g. "V + N".
 * @param primaryExample the sentence shown on the list row, card session front, and quiz.
 * @param moreExamples further usage sentences with translations.
 * @param commonError optional "native speakers don't say this" note.
 * @param relatedCollocations other collocations built around the same [headword].
 * @param wordForms the headword across parts of speech (noun / verb / adjective …).
 * @param nearbyChunks chip labels for chunks close in meaning.
 */
data class Chunk(
    val id: String,
    val text: String,
    val translation: String,
    val headword: String,
    val theme: ChunkTheme,
    val level: ChunkLevel,
    val frequencyBand: FrequencyBand?,
    val register: ChunkRegister,
    val pattern: String?,
    val primaryExample: ChunkExample,
    val moreExamples: List<ChunkExample>,
    val commonError: ChunkError?,
    val relatedCollocations: List<Collocation>,
    val wordForms: List<WordForm>,
    val nearbyChunks: List<String>,
) {
    /** The chunk's first word — the collocationally bound part blanked out in the cloze quiz. */
    val firstWord: String get() = text.trim().substringBefore(' ')

    /** Number of whitespace-separated words in [text]. */
    val wordCount: Int get() = text.trim().split(WHITESPACE).size

    private companion object {
        val WHITESPACE = Regex("\\s+")
    }
}

/** An example sentence and its Russian translation. */
data class ChunkExample(
    val english: String,
    val russian: String,
)

/**
 * A collocation error learners commonly make with this chunk.
 *
 * @param wrong the incorrect phrasing, e.g. "do a decision".
 * @param explanation why it's wrong / what to say instead.
 */
data class ChunkError(
    val wrong: String,
    val explanation: String,
)

/** Another collocation built around the same headword, shown in the detail screen list. */
data class Collocation(
    val text: String,
    val translation: String,
)

/** The headword realised as a particular part of speech, e.g. NOUN "decision", VERB "decide". */
data class WordForm(
    val partOfSpeech: WordPartOfSpeech,
    val form: String,
)

/** Topic a chunk belongs to — drives the list filter chips and the row colour strip. */
enum class ChunkTheme {
    WORK,
    TRAVEL,
    MONEY,
    FEELINGS,
    DAILY_LIFE,
    HEALTH,
}

/** CEFR level of the chunk. */
enum class ChunkLevel {
    A2,
    B1,
    B2,
}

/** How common the headword is in general English — shown in the row meta line. */
enum class FrequencyBand {
    TOP_1000,
    TOP_2000,
    TOP_5000,
}

/** Stylistic register of the chunk. */
enum class ChunkRegister {
    NEUTRAL,
    FORMAL,
    INFORMAL,
}

/** Part of speech for a [WordForm]. */
enum class WordPartOfSpeech {
    NOUN,
    VERB,
    ADJ,
    ADV,
}
