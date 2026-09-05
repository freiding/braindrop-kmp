package by.freiding.braindrop.feature.vocabulary.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import by.freiding.braindrop.core.ui.BrainDropTheme
import by.freiding.braindrop.feature.vocabulary.domain.srs.RecallGrade

/**
 * The "КАК ХОРОШО ПОМНИШЬ?" row — three graded buttons (Не знаю / Почти / Знаю). When
 * [intervalLabels] is given, each button shows its next-review interval underneath ("СНОВА" /
 * "2 ДНЯ" / "6 ДНЕЙ"), as on the card session.
 */
@Composable
internal fun GradeButtonRow(
    onGrade: (RecallGrade) -> Unit,
    modifier: Modifier = Modifier,
    intervalLabels: Map<RecallGrade, String>? = null,
) {
    val semantics = BrainDropTheme.semantics
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.xs),
    ) {
        GradeButton(
            title = "Не знаю",
            hint = intervalLabels?.get(RecallGrade.DONT_KNOW),
            container = semantics.incorrectTint,
            border = semantics.incorrect,
            content = semantics.incorrectInk,
            onClick = { onGrade(RecallGrade.DONT_KNOW) },
        )
        GradeButton(
            title = "Почти",
            hint = intervalLabels?.get(RecallGrade.ALMOST),
            container = semantics.streakTint,
            border = semantics.streak,
            content = semantics.streakInk,
            onClick = { onGrade(RecallGrade.ALMOST) },
        )
        GradeButton(
            title = "Знаю",
            hint = intervalLabels?.get(RecallGrade.KNOW),
            container = semantics.correct,
            border = semantics.correct,
            content = Color.White,
            onClick = { onGrade(RecallGrade.KNOW) },
        )
    }
}

@Composable
private fun RowScope.GradeButton(
    title: String,
    hint: String?,
    container: Color,
    border: Color,
    content: Color,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .weight(1f)
            .height(if (hint == null) 46.dp else 52.dp)
            .clip(BrainDropTheme.shapes.button)
            .background(container, BrainDropTheme.shapes.button)
            .border(1.5.dp, border, BrainDropTheme.shapes.button)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = content,
            textAlign = TextAlign.Center,
        )
        if (hint != null) {
            Text(text = hint, style = BrainDropTheme.type.label, color = content.copy(alpha = 0.85f))
        }
    }
}
