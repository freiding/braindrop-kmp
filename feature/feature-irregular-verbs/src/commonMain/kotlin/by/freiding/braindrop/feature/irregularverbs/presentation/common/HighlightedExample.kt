package by.freiding.braindrop.feature.irregularverbs.presentation.common

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

/** An example sentence with its verb forms bolded — used in the verb detail card and the quiz mistake breakdown. */
internal fun highlightedExample(
    text: String,
    forms: List<String>,
    highlightColor: Color,
): AnnotatedString =
    buildAnnotatedString {
        var cursor = 0
        while (cursor < text.length) {
            val next = forms
                .filter { it.isNotBlank() }
                .mapNotNull { form ->
                    val i = text.indexOf(form, cursor, ignoreCase = true)
                    if (i == -1) null else i to form
                }.minByOrNull { it.first }

            if (next == null) {
                append(text.substring(cursor))
                break
            }
            val (start, form) = next
            append(text.substring(cursor, start))
            withStyle(SpanStyle(color = highlightColor, fontWeight = FontWeight.Bold)) {
                append(text.substring(start, start + form.length))
            }
            cursor = start + form.length
        }
    }
