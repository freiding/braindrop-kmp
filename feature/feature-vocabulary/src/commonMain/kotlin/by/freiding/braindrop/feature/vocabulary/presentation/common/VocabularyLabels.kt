package by.freiding.braindrop.feature.vocabulary.presentation.common

import androidx.compose.ui.graphics.Color
import by.freiding.braindrop.core.ui.BrainDropSemantics
import by.freiding.braindrop.feature.vocabulary.domain.model.Chunk
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkLevel
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkRegister
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkTheme
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand
import by.freiding.braindrop.feature.vocabulary.domain.model.WordPartOfSpeech

internal fun ChunkTheme.displayName(): String =
    when (this) {
        ChunkTheme.WORK -> "Work"
        ChunkTheme.TRAVEL -> "Travel"
        ChunkTheme.MONEY -> "Money"
        ChunkTheme.FEELINGS -> "Feelings"
        ChunkTheme.DAILY_LIFE -> "Daily life"
        ChunkTheme.HEALTH -> "Health"
        ChunkTheme.EDUCATION -> "Education"
        ChunkTheme.SOCIAL -> "Social life"
    }

internal fun ChunkLevel.displayName(): String = name

internal fun ChunkRegister.displayName(): String =
    when (this) {
        ChunkRegister.NEUTRAL -> "NEUTRAL"
        ChunkRegister.FORMAL -> "FORMAL"
        ChunkRegister.INFORMAL -> "INFORMAL"
    }

internal fun FrequencyBand.displayName(): String =
    when (this) {
        FrequencyBand.TOP_1000 -> "ТОП-1000"
        FrequencyBand.TOP_2000 -> "ТОП-2000"
        FrequencyBand.TOP_5000 -> "ТОП-5000"
    }

internal fun WordPartOfSpeech.displayName(): String = name

/** The mono meta line under a chunk: "WORK · B1 · ТОП-1000 · NEUTRAL". */
internal fun Chunk.metaLine(): String =
    buildList {
        add(theme.displayName().uppercase())
        add(level.displayName())
        frequencyBand?.let { add(it.displayName()) }
        if (register != ChunkRegister.NEUTRAL) add(register.displayName())
    }.joinToString(" · ")

/**
 * Colour strip for the list row / detail hero, keyed by theme. Reuses the existing tenses/aspect
 * semantic colours (same trick as `categoryColor` in `PhrasalVerbsListScreen`) so `core-ui` needs
 * no new tokens.
 */
internal fun ChunkTheme.stripColor(semantics: BrainDropSemantics): Color =
    when (this) {
        ChunkTheme.WORK -> semantics.tenseTimeColor("PRESENT")
        ChunkTheme.TRAVEL -> semantics.tenseTimeColor("FUTURE")
        ChunkTheme.MONEY -> semantics.aspectColor("PERFECT")
        ChunkTheme.FEELINGS -> semantics.aspectColor("PERFECT_CONTINUOUS")
        ChunkTheme.DAILY_LIFE -> semantics.tenseTimeColor("PAST")
        ChunkTheme.HEALTH -> semantics.aspectColor("CONTINUOUS")
        ChunkTheme.EDUCATION -> semantics.aspectColor("SIMPLE")
        ChunkTheme.SOCIAL -> semantics.tenseTimeColor("PRESENT")
    }
