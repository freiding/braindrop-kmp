package by.freiding.braindrop.feature.tenses.presentation.common

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

/** An example sentence with a single known substring bolded — the tense's verb phrase. */
internal fun highlightedExample(
    text: String,
    highlight: String,
    highlightColor: Color,
): AnnotatedString =
    buildAnnotatedString {
        val start = if (highlight.isBlank()) -1 else text.indexOf(highlight, ignoreCase = true)
        if (start == -1) {
            append(text)
            return@buildAnnotatedString
        }
        append(text.substring(0, start))
        withStyle(SpanStyle(color = highlightColor, fontWeight = FontWeight.Bold)) {
            append(text.substring(start, start + highlight.length))
        }
        append(text.substring(start + highlight.length))
    }

/** An example sentence with any matching signal words bolded — used for markerExamples. */
internal fun highlightedMarkers(
    text: String,
    markers: List<String>,
    highlightColor: Color,
): AnnotatedString =
    buildAnnotatedString {
        var cursor = 0
        while (cursor < text.length) {
            val next = markers
                .filter { it.isNotBlank() }
                .mapNotNull { marker ->
                    val i = text.indexOf(marker, cursor, ignoreCase = true)
                    if (i == -1) null else i to marker
                }.minByOrNull { it.first }

            if (next == null) {
                append(text.substring(cursor))
                break
            }
            val (start, marker) = next
            append(text.substring(cursor, start))
            withStyle(SpanStyle(color = highlightColor, fontWeight = FontWeight.Bold)) {
                append(text.substring(start, start + marker.length))
            }
            cursor = start + marker.length
        }
    }
