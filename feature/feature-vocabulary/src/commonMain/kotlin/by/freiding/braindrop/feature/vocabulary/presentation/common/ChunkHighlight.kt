package by.freiding.braindrop.feature.vocabulary.presentation.common

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

/**
 * Highlights the studied chunk inside an example sentence. Tries the whole chunk first
 * ("make a decision"), then each significant word of it separately (so an inflected sentence like
 * "She made a decision" still lights up "decision"), then falls back to the bare headword.
 *
 * Adapted from `feature-tenses` `HighlightedExample` — kept local because `CLAUDE.md` forbids
 * importing one feature module from another.
 */
internal fun highlightChunk(
    sentence: String,
    chunk: String,
    headword: String,
    highlightColor: Color,
): AnnotatedString {
    val whole = indexOfWord(sentence, chunk)
    if (whole != -1) {
        return buildAnnotatedString {
            append(sentence.substring(0, whole))
            withStyle(highlightSpan(highlightColor)) { append(sentence.substring(whole, whole + chunk.length)) }
            append(sentence.substring(whole + chunk.length))
        }
    }

    val targets = (chunk.split(WHITESPACE).filter { it.length > 2 } + headword)
        .map { word -> word.trim { ch -> !ch.isLetterOrDigit() } }
        .filter { it.isNotBlank() }
        .distinct()

    return buildAnnotatedString {
        var cursor = 0
        while (cursor < sentence.length) {
            val next = targets
                .mapNotNull { word ->
                    val i = sentence.indexOf(word, cursor, ignoreCase = true)
                    if (i == -1) null else i to word
                }.minByOrNull { it.first }
            if (next == null) {
                append(sentence.substring(cursor))
                break
            }
            val (start, word) = next
            append(sentence.substring(cursor, start))
            withStyle(highlightSpan(highlightColor)) { append(sentence.substring(start, start + word.length)) }
            cursor = start + word.length
        }
    }
}

/** Renders [sentence] with the CLOZE gap token styled as a coloured, bold blank. */
internal fun highlightGap(
    sentence: String,
    gapToken: String,
    highlightColor: Color,
): AnnotatedString {
    val start = sentence.indexOf(gapToken)
    if (start == -1) return AnnotatedString(sentence)
    return buildAnnotatedString {
        append(sentence.substring(0, start))
        withStyle(highlightSpan(highlightColor)) { append(gapToken) }
        append(sentence.substring(start + gapToken.length))
    }
}

private fun highlightSpan(color: Color) = SpanStyle(color = color, fontWeight = FontWeight.ExtraBold)

/** Index of [needle] in [haystack] only when it sits on word boundaries; -1 otherwise. */
private fun indexOfWord(
    haystack: String,
    needle: String,
): Int {
    if (needle.isBlank()) return -1
    var from = 0
    var result = -1
    while (result == -1) {
        val i = haystack.indexOf(needle, from, ignoreCase = true)
        if (i == -1) break
        val before = i == 0 || !haystack[i - 1].isLetterOrDigit()
        val afterIdx = i + needle.length
        val after = afterIdx == haystack.length || !haystack[afterIdx].isLetterOrDigit()
        if (before && after) result = i else from = i + 1
    }
    return result
}

private val WHITESPACE = Regex("\\s+")
