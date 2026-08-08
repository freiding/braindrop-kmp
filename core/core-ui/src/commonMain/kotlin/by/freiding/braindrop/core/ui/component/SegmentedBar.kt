package by.freiding.braindrop.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

/**
 * Segmented progress bar: N equal capsules separated by a fixed gap. Used
 * on Home (daily goal, category card), in the Verb List header, in quiz
 * progress, and in the loading skeleton — each with different segment
 * coloring, so the color is supplied from outside via [colorForSegment].
 */
@Composable
fun SegmentedBar(
    segmentCount: Int,
    height: Dp,
    gap: Dp,
    cornerRadius: Dp,
    modifier: Modifier = Modifier,
    colorForSegment: (index: Int) -> Color,
) {
    Row(
        modifier = modifier.fillMaxWidth().height(height),
        horizontalArrangement = Arrangement.spacedBy(gap),
    ) {
        repeat(segmentCount) { index ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(colorForSegment(index), RoundedCornerShape(cornerRadius)),
            )
        }
    }
}

/**
 * A convenience wrapper over [SegmentedBar] for continuous progress (0f..1f) rather than
 * per-segment state: segments up to the integer part of `ratio*segmentCount` are
 * filled with [filledColor], the next one partially with [partialColor], the rest with [emptyColor].
 */
@Composable
fun SegmentedProgressBar(
    segmentCount: Int,
    ratio: Float,
    height: Dp,
    gap: Dp,
    cornerRadius: Dp,
    filledColor: Color,
    emptyColor: Color,
    modifier: Modifier = Modifier,
    partialColor: Color = filledColor.copy(alpha = 0.45f),
) {
    val exact = ratio.coerceIn(0f, 1f) * segmentCount
    val filled = exact.toInt()
    val hasPartial = exact - filled > 0.05f
    SegmentedBar(
        segmentCount = segmentCount,
        height = height,
        gap = gap,
        cornerRadius = cornerRadius,
        modifier = modifier,
    ) { index ->
        when {
            index < filled -> filledColor
            index == filled && hasPartial -> partialColor
            else -> emptyColor
        }
    }
}
