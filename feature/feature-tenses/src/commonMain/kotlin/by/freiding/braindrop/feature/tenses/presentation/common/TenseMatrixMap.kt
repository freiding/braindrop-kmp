package by.freiding.braindrop.feature.tenses.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.freiding.braindrop.core.ui.BrainDropTheme
import by.freiding.braindrop.feature.tenses.domain.model.TenseAspect
import by.freiding.braindrop.feature.tenses.domain.model.TenseTime
import by.freiding.braindrop.feature.tenses.domain.model.TenseWithProgress

/**
 * The list screen's header map: 4 aspect columns × 3 time rows, one cell per tense. Doubles as
 * a progress indicator (filled = learned) and navigation (tap a row to switch the time tab) —
 * the same "N segments, not a percentage" principle as [by.freiding.braindrop.core.ui.component.SegmentedProgressBar],
 * specialized for a 2D map since this module has two independent axes (time × aspect) instead of one.
 */
@Composable
fun TenseMatrixMap(
    tenses: List<TenseWithProgress>,
    selectedTime: TenseTime,
    onTimeSelected: (TenseTime) -> Unit,
    modifier: Modifier = Modifier,
    cellSize: Dp = 20.dp,
) {
    val learnedByCell = remember(tenses) {
        tenses.associate { (it.tense.time to it.tense.aspect) to it.progress.isLearned }
    }
    val gap = 3.dp

    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(7.dp)) {
        Column(
            verticalArrangement = Arrangement.spacedBy(gap),
            modifier = Modifier.padding(top = 15.dp),
        ) {
            TenseTime.entries.forEach { time ->
                Box(modifier = Modifier.height(cellSize), contentAlignment = Alignment.CenterStart) {
                    Text(text = rowLabel(time), style = matrixLabelStyle(), color = BrainDropTheme.semantics.ink400)
                }
            }
        }

        Column {
            Row(horizontalArrangement = Arrangement.spacedBy(gap), modifier = Modifier.padding(bottom = 4.dp)) {
                TenseAspect.entries.forEach { aspect ->
                    Text(
                        text = BrainDropTheme.semantics.aspectShortLabel(aspect.name),
                        style = matrixLabelStyle(),
                        color = BrainDropTheme.semantics.aspectColor(aspect.name),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.width(cellSize),
                    )
                }
            }

            TenseTime.entries.forEachIndexed { index, time ->
                val isActive = time == selectedTime
                val rowModifier = if (isActive) {
                    Modifier
                        .offset(x = (-2).dp)
                        .border(1.5.dp, MaterialTheme.colorScheme.primary, BrainDropTheme.shapes.sm)
                        .padding(2.dp)
                } else {
                    Modifier
                }
                Box(
                    modifier = rowModifier
                        .clickable(onClick = { onTimeSelected(time) })
                        .padding(bottom = if (index != TenseTime.entries.lastIndex) gap else 0.dp),
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(gap)) {
                        TenseAspect.entries.forEach { aspect ->
                            val learned = learnedByCell[time to aspect] == true
                            val color = if (learned) {
                                BrainDropTheme.semantics.aspectColor(aspect.name)
                            } else {
                                MaterialTheme.colorScheme.surfaceVariant
                            }
                            Box(
                                modifier = Modifier
                                    .size(cellSize)
                                    .background(color, RoundedCornerShape(5.dp)),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun matrixLabelStyle() = BrainDropTheme.type.label.copy(fontSize = 9.sp, letterSpacing = 0.sp)

private fun rowLabel(time: TenseTime): String =
    when (time) {
        TenseTime.PRESENT -> "PRES"
        TenseTime.PAST -> "PAST"
        TenseTime.FUTURE -> "FUT"
    }
