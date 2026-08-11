package by.freiding.braindrop.feature.tenses.presentation.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.freiding.braindrop.core.ui.BrainDropTheme
import by.freiding.braindrop.core.ui.component.brainDropCard
import by.freiding.braindrop.feature.tenses.Res
import by.freiding.braindrop.feature.tenses.domain.model.Tense
import by.freiding.braindrop.feature.tenses.domain.model.TenseQuizQuestion
import by.freiding.braindrop.feature.tenses.domain.model.TenseQuizType
import by.freiding.braindrop.feature.tenses.presentation.common.formatTenseId
import by.freiding.braindrop.feature.tenses.presentation.common.highlightedMarkers
import by.freiding.braindrop.feature.tenses.tenses_quiz_chip_marker
import by.freiding.braindrop.feature.tenses.tenses_quiz_prompt_discrimination
import by.freiding.braindrop.feature.tenses.tenses_quiz_prompt_form
import by.freiding.braindrop.feature.tenses.tenses_quiz_prompt_marker
import by.freiding.braindrop.feature.tenses.tenses_quiz_prompt_mixed
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun QuestionCard(
    question: TenseQuizQuestion,
    tense: Tense?,
) {
    val semantics = BrainDropTheme.semantics
    val sentenceStyle = MaterialTheme.typography.bodyLarge.copy(
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 29.7.sp,
        letterSpacing = (-0.22).sp,
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .brainDropCard(BrainDropTheme.shapes.xl)
            .padding(20.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(promptResource(question.type)),
                style = BrainDropTheme.type.label,
                color = semantics.ink400,
                modifier = Modifier.weight(1f),
            )
            QuestionTypeChip(question = question, tense = tense)
        }
        Spacer(Modifier.height(16.dp))
        QuestionSentence(question = question, tense = tense, style = sentenceStyle)

        val translation = question.russianTranslation(tense)
        if (translation != null) {
            Row(modifier = Modifier.padding(top = 12.dp).height(IntrinsicSize.Min)) {
                Box(modifier = Modifier.fillMaxHeight().width(2.dp).background(MaterialTheme.colorScheme.outline))
                Text(
                    text = translation,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp),
                    color = BrainDropTheme.semantics.ink500,
                    modifier = Modifier.padding(start = 11.dp),
                )
            }
        }
    }
}

private fun promptResource(type: TenseQuizType) =
    when (type) {
        TenseQuizType.FORM -> Res.string.tenses_quiz_prompt_form
        TenseQuizType.MARKER_MATCH -> Res.string.tenses_quiz_prompt_marker
        TenseQuizType.DISCRIMINATION -> Res.string.tenses_quiz_prompt_discrimination
        TenseQuizType.MIXED_REVIEW -> Res.string.tenses_quiz_prompt_mixed
    }

@Composable
private fun QuestionTypeChip(
    question: TenseQuizQuestion,
    tense: Tense?,
) {
    val semantics = BrainDropTheme.semantics
    val (background, content, text) = if (question.type == TenseQuizType.MARKER_MATCH) {
        Triple(
            MaterialTheme.colorScheme.surfaceVariant,
            MaterialTheme.colorScheme.onSurfaceVariant,
            stringResource(Res.string.tenses_quiz_chip_marker),
        )
    } else {
        val aspectName = tense?.aspect?.name
        Triple(
            aspectName?.let { semantics.aspectSurface(it) } ?: MaterialTheme.colorScheme.surfaceVariant,
            aspectName?.let { semantics.aspectInk(it) } ?: MaterialTheme.colorScheme.onSurfaceVariant,
            formatTenseId(question.tenseId).uppercase(),
        )
    }
    Box(
        modifier = Modifier
            .height(24.dp)
            .background(background, BrainDropTheme.shapes.sm)
            .padding(horizontal = BrainDropTheme.spacing.xs),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = BrainDropTheme.type.label.copy(fontSize = 10.5.sp, letterSpacing = 0.53.sp),
            color = content,
        )
    }
}

@Composable
private fun QuestionSentence(
    question: TenseQuizQuestion,
    tense: Tense?,
    style: TextStyle,
) {
    val color = MaterialTheme.colorScheme.onSurface
    when (question.type) {
        TenseQuizType.MARKER_MATCH -> {
            val markers = tense?.markers.orEmpty()
            if (markers.isEmpty()) {
                Text(text = question.questionText, style = style, color = color)
            } else {
                Text(
                    text = highlightedMarkers(question.questionText, markers, MaterialTheme.colorScheme.primary),
                    style = style,
                    color = color,
                )
            }
        }
        else -> SentenceWithBlank(text = question.questionText, style = style, color = color)
    }
}

/**
 * Renders a "___" blank as an inline field (min-width 78dp, bottom border) instead of literal
 * underscores, using Text's inlineContent so the field wraps with the sentence like any other
 * word instead of a fixed-position overlay.
 */
@Composable
private fun SentenceWithBlank(
    text: String,
    style: TextStyle,
    color: Color,
) {
    val blankTag = "blank"
    val markerText = "___"
    val idx = text.indexOf(markerText)
    if (idx == -1) {
        Text(text = text, style = style, color = color)
        return
    }
    val density = LocalDensity.current
    val blankWidth = with(density) { 78.dp.toSp() }
    val blankHeight = with(density) { 26.dp.toSp() }
    val primary = MaterialTheme.colorScheme.primary
    val annotated = buildAnnotatedString {
        append(text.substring(0, idx))
        appendInlineContent(blankTag, markerText)
        append(text.substring(idx + markerText.length))
    }
    val inlineContent = remember(primary, blankWidth, blankHeight) {
        mapOf(
            blankTag to InlineTextContent(
                Placeholder(blankWidth, blankHeight, PlaceholderVerticalAlign.TextCenter),
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .height(2.5.dp)
                            .background(primary),
                    )
                }
            },
        )
    }
    Text(text = annotated, style = style, color = color, inlineContent = inlineContent)
}

/** Recovers the marker/blank sentence's Russian translation by matching it back to the tense's own scenarios and marker examples — the domain layer's [TenseQuizQuestion] doesn't carry a translation field. */
private fun TenseQuizQuestion.russianTranslation(tense: Tense?): String? {
    tense ?: return null
    return when (type) {
        TenseQuizType.MARKER_MATCH -> tense.markerExamples.firstOrNull { it.english == questionText }?.russian
        TenseQuizType.FORM, TenseQuizType.DISCRIMINATION ->
            tense.scenarios.values
                .firstOrNull { form ->
                    form.highlight == correctAnswer && form.english.replaceFirst(form.highlight, "___") == questionText
                }?.russian
        TenseQuizType.MIXED_REVIEW -> null
    }
}

@Composable
internal fun MistakeBreakdownCard(
    question: TenseQuizQuestion,
    tense: Tense?,
) {
    val semantics = BrainDropTheme.semantics
    val formula = tense?.formulas?.affirmative?.removePrefix("S + ")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(semantics.mistakeCardSurface, BrainDropTheme.shapes.lg)
            .padding(horizontal = 18.dp, vertical = BrainDropTheme.spacing.md),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                text = formatTenseId(question.tenseId),
                style = MaterialTheme.typography.titleSmall.copy(fontSize = 16.sp),
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
            )
            if (formula != null) {
                Text(
                    text = formula,
                    style = BrainDropTheme.type.verbForms.copy(fontSize = 13.sp, fontWeight = FontWeight.SemiBold),
                    color = semantics.mistakeCardAccent,
                )
            }
        }
        Spacer(Modifier.height(BrainDropTheme.spacing.xs))
        Text(
            text = question.explanation,
            style = MaterialTheme.typography.bodyMedium,
            color = semantics.mistakeCardMuted,
        )
    }
}
