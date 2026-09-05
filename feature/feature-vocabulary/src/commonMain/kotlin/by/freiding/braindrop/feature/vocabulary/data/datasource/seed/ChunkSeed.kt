package by.freiding.braindrop.feature.vocabulary.data.datasource.seed

import by.freiding.braindrop.feature.vocabulary.domain.model.Chunk
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkError
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkExample
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkLevel
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkRegister
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkTheme
import by.freiding.braindrop.feature.vocabulary.domain.model.Collocation
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand
import by.freiding.braindrop.feature.vocabulary.domain.model.WordForm
import by.freiding.braindrop.feature.vocabulary.domain.model.WordPartOfSpeech

/**
 * Concise builders for the hand-authored chunk seed set, in the spirit of the `ex()` / `meaning()`
 * helpers in `LocalPhrasalVerbDataSource`. Kept `internal` so only the per-theme seed files use them.
 */

internal fun ex(
    en: String,
    ru: String,
): ChunkExample = ChunkExample(en, ru)

internal fun err(
    wrong: String,
    explanation: String,
): ChunkError = ChunkError(wrong, explanation)

internal fun col(
    text: String,
    translation: String,
): Collocation = Collocation(text, translation)

internal fun noun(form: String) = WordForm(WordPartOfSpeech.NOUN, form)

internal fun verb(form: String) = WordForm(WordPartOfSpeech.VERB, form)

internal fun adj(form: String) = WordForm(WordPartOfSpeech.ADJ, form)

internal fun adv(form: String) = WordForm(WordPartOfSpeech.ADV, form)

@Suppress("LongParameterList")
internal fun chunk(
    id: String,
    text: String,
    translation: String,
    headword: String,
    theme: ChunkTheme,
    level: ChunkLevel,
    example: ChunkExample,
    frequencyBand: FrequencyBand? = null,
    register: ChunkRegister = ChunkRegister.NEUTRAL,
    pattern: String? = null,
    more: List<ChunkExample> = emptyList(),
    error: ChunkError? = null,
    collocations: List<Collocation> = emptyList(),
    forms: List<WordForm> = emptyList(),
    nearby: List<String> = emptyList(),
): Chunk =
    Chunk(
        id = id,
        text = text,
        translation = translation,
        headword = headword,
        theme = theme,
        level = level,
        frequencyBand = frequencyBand,
        register = register,
        pattern = pattern,
        primaryExample = example,
        moreExamples = more,
        commonError = error,
        relatedCollocations = collocations,
        wordForms = forms,
        nearbyChunks = nearby,
    )
