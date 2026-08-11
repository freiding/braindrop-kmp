package by.freiding.braindrop.core.ui.icon

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/*
 * BrainDrop doesn't pull in a third-party icon set — instead, simple outline
 * SVG-like icons are drawn directly via Canvas/Path. Geometry is defined in a
 * 24×24 coordinate system and scaled to the actual size (scale = renderedPx / 24).
 * The stroke is rounded (round cap/join), default thickness is a design token
 * (2.2–2.4dp), and the icon size itself doesn't include inner padding.
 */
object BrainDropIcons {
    @Composable
    fun ChevronLeft(
        modifier: Modifier = Modifier,
        tint: Color = LocalContentColor.current,
        iconSize: Dp = 24.dp,
        strokeWidth: Dp = 2.4.dp,
    ) = strokeIcon(modifier, tint, iconSize, strokeWidth) { scale ->
        moveTo(15f * scale, 4f * scale)
        lineTo(9f * scale, 12f * scale)
        lineTo(15f * scale, 20f * scale)
    }

    @Composable
    fun ChevronRight(
        modifier: Modifier = Modifier,
        tint: Color = LocalContentColor.current,
        iconSize: Dp = 24.dp,
        strokeWidth: Dp = 2.4.dp,
    ) = strokeIcon(modifier, tint, iconSize, strokeWidth) { scale ->
        moveTo(9f * scale, 4f * scale)
        lineTo(15f * scale, 12f * scale)
        lineTo(9f * scale, 20f * scale)
    }

    @Composable
    fun Check(
        modifier: Modifier = Modifier,
        tint: Color = LocalContentColor.current,
        iconSize: Dp = 24.dp,
        strokeWidth: Dp = 2.6.dp,
    ) = strokeIcon(modifier, tint, iconSize, strokeWidth) { scale ->
        moveTo(4f * scale, 12.5f * scale)
        lineTo(9.5f * scale, 18f * scale)
        lineTo(20f * scale, 5.5f * scale)
    }

    @Composable
    fun Close(
        modifier: Modifier = Modifier,
        tint: Color = LocalContentColor.current,
        iconSize: Dp = 24.dp,
        strokeWidth: Dp = 2.4.dp,
    ) = Canvas(modifier = modifier.size(iconSize)) {
        val scale = size.width / 24f
        val stroke = strokeWidth.toPx()
        drawLine(tint, Offset(5f * scale, 5f * scale), Offset(19f * scale, 19f * scale), stroke, StrokeCap.Round)
        drawLine(tint, Offset(19f * scale, 5f * scale), Offset(5f * scale, 19f * scale), stroke, StrokeCap.Round)
    }

    @Composable
    fun Search(
        modifier: Modifier = Modifier,
        tint: Color = LocalContentColor.current,
        iconSize: Dp = 24.dp,
        strokeWidth: Dp = 2.2.dp,
    ) = Canvas(modifier = modifier.size(iconSize)) {
        val scale = size.width / 24f
        val stroke = strokeWidth.toPx()
        drawCircle(
            color = tint,
            radius = 6.6f * scale,
            center = Offset(10.2f * scale, 10.2f * scale),
            style = Stroke(stroke, cap = StrokeCap.Round),
        )
        drawLine(
            color = tint,
            start = Offset(15f * scale, 15f * scale),
            end = Offset(20.2f * scale, 20.2f * scale),
            strokeWidth = stroke,
            cap = StrokeCap.Round,
        )
    }

    @Composable
    fun List(
        modifier: Modifier = Modifier,
        tint: Color = LocalContentColor.current,
        iconSize: Dp = 24.dp,
        strokeWidth: Dp = 2.2.dp,
    ) = Canvas(modifier = modifier.size(iconSize)) {
        val scale = size.width / 24f
        val stroke = strokeWidth.toPx()
        val xs = 4.5f * scale
        val xe = 19.5f * scale
        listOf(6f, 12f, 18f).forEach { y ->
            drawLine(tint, Offset(xs, y * scale), Offset(xe, y * scale), stroke, StrokeCap.Round)
        }
    }

    @Composable
    fun Cards(
        modifier: Modifier = Modifier,
        tint: Color = LocalContentColor.current,
        iconSize: Dp = 24.dp,
        strokeWidth: Dp = 2.2.dp,
    ) = Canvas(modifier = modifier.size(iconSize)) {
        val scale = size.width / 24f
        val stroke = Stroke(width = strokeWidth.toPx(), join = StrokeJoin.Round)
        drawRoundRect(
            color = tint.copy(alpha = 0.55f),
            topLeft = Offset(6.5f * scale, 3f * scale),
            size = androidx.compose.ui.geometry
                .Size(14f * scale, 14f * scale),
            cornerRadius = androidx.compose.ui.geometry
                .CornerRadius(3f * scale),
            style = stroke,
        )
        drawRoundRect(
            color = tint,
            topLeft = Offset(3.5f * scale, 7f * scale),
            size = androidx.compose.ui.geometry
                .Size(14f * scale, 14f * scale),
            cornerRadius = androidx.compose.ui.geometry
                .CornerRadius(3f * scale),
            style = stroke,
        )
    }

    @Composable
    fun BarChart(
        modifier: Modifier = Modifier,
        tint: Color = LocalContentColor.current,
        iconSize: Dp = 24.dp,
        strokeWidth: Dp = 2.4.dp,
    ) = Canvas(modifier = modifier.size(iconSize)) {
        val scale = size.width / 24f
        val stroke = strokeWidth.toPx()
        drawLine(tint, Offset(5f * scale, 19f * scale), Offset(5f * scale, 12f * scale), stroke, StrokeCap.Round)
        drawLine(tint, Offset(12f * scale, 19f * scale), Offset(12f * scale, 5f * scale), stroke, StrokeCap.Round)
        drawLine(tint, Offset(19f * scale, 19f * scale), Offset(19f * scale, 9f * scale), stroke, StrokeCap.Round)
    }

    @Composable
    fun Person(
        modifier: Modifier = Modifier,
        tint: Color = LocalContentColor.current,
        iconSize: Dp = 24.dp,
        strokeWidth: Dp = 2.3.dp,
    ) = Canvas(modifier = modifier.size(iconSize)) {
        val scale = size.width / 24f
        val stroke = strokeWidth.toPx()
        drawCircle(tint, radius = 3.6f * scale, center = Offset(12f * scale, 7.6f * scale), style = Stroke(stroke))
        val shoulders = Path().apply {
            moveTo(4.5f * scale, 20f * scale)
            cubicTo(4.5f * scale, 15f * scale, 8f * scale, 13f * scale, 12f * scale, 13f * scale)
            cubicTo(16f * scale, 13f * scale, 19.5f * scale, 15f * scale, 19.5f * scale, 20f * scale)
        }
        drawPath(shoulders, tint, style = Stroke(width = stroke, cap = StrokeCap.Round))
    }

    @Composable
    fun Flame(
        modifier: Modifier = Modifier,
        tint: Color = LocalContentColor.current,
        iconSize: Dp = 24.dp,
    ) = Canvas(modifier = modifier.size(iconSize)) {
        val scale = size.width / 24f
        val path = Path().apply {
            moveTo(12f * scale, 2.5f * scale)
            cubicTo(13.5f * scale, 6f * scale, 17.5f * scale, 8.3f * scale, 17.5f * scale, 13f * scale)
            cubicTo(17.5f * scale, 17.6f * scale, 14.5f * scale, 21f * scale, 11.3f * scale, 21f * scale)
            cubicTo(7.6f * scale, 21f * scale, 5.2f * scale, 18.2f * scale, 5.6f * scale, 14.4f * scale)
            cubicTo(5.9f * scale, 11.6f * scale, 8f * scale, 11f * scale, 8.2f * scale, 13.6f * scale)
            cubicTo(8.3f * scale, 14.8f * scale, 9f * scale, 15.1f * scale, 9.5f * scale, 14.2f * scale)
            cubicTo(10.4f * scale, 12.5f * scale, 8.7f * scale, 10.6f * scale, 9.6f * scale, 6.8f * scale)
            cubicTo(10.1f * scale, 4.7f * scale, 11.2f * scale, 3.3f * scale, 12f * scale, 2.5f * scale)
            close()
        }
        drawPath(path, tint)
    }

    @Composable
    fun Alert(
        modifier: Modifier = Modifier,
        tint: Color = LocalContentColor.current,
        iconSize: Dp = 24.dp,
    ) = Canvas(modifier = modifier.size(iconSize)) {
        val scale = size.width / 24f
        drawLine(
            color = tint,
            start = Offset(12f * scale, 5.5f * scale),
            end = Offset(12f * scale, 14.5f * scale),
            strokeWidth = 2.6.dp.toPx(),
            cap = StrokeCap.Round,
        )
        drawCircle(tint, radius = 1.4f * scale, center = Offset(12f * scale, 19f * scale))
    }

    /** BrainDrop droplet logo: used in the Home header next to the app name (22dp). */
    @Composable
    fun Droplet(
        modifier: Modifier = Modifier,
        tint: Color = LocalContentColor.current,
        iconSize: Dp = 24.dp,
    ) = Canvas(modifier = modifier.size(iconSize)) {
        val scale = size.width / 24f
        val path = Path().apply {
            moveTo(12f * scale, 2.2f * scale)
            cubicTo(15.6f * scale, 7.4f * scale, 19f * scale, 11.5f * scale, 19f * scale, 15f * scale)
            cubicTo(19f * scale, 19.6f * scale, 15.9f * scale, 22.2f * scale, 12f * scale, 22.2f * scale)
            cubicTo(8.1f * scale, 22.2f * scale, 5f * scale, 19.6f * scale, 5f * scale, 15f * scale)
            cubicTo(5f * scale, 11.5f * scale, 8.4f * scale, 7.4f * scale, 12f * scale, 2.2f * scale)
            close()
        }
        drawPath(path, tint)
    }

    /** Clock face: used for the "tenses" study category on Home. */
    @Composable
    fun Clock(
        modifier: Modifier = Modifier,
        tint: Color = LocalContentColor.current,
        iconSize: Dp = 24.dp,
        strokeWidth: Dp = 2.2.dp,
    ) = Canvas(modifier = modifier.size(iconSize)) {
        val scale = size.width / 24f
        val stroke = strokeWidth.toPx()
        drawCircle(
            color = tint,
            radius = 8.5f * scale,
            center = Offset(12f * scale, 12f * scale),
            style = Stroke(stroke),
        )
        drawLine(tint, Offset(12f * scale, 12f * scale), Offset(12f * scale, 7f * scale), stroke, StrokeCap.Round)
        drawLine(tint, Offset(12f * scale, 12f * scale), Offset(15.5f * scale, 13.5f * scale), stroke, StrokeCap.Round)
    }

    @Composable
    fun Undo(
        modifier: Modifier = Modifier,
        tint: Color = LocalContentColor.current,
        iconSize: Dp = 24.dp,
        strokeWidth: Dp = 2.3.dp,
    ) = Canvas(modifier = modifier.size(iconSize)) {
        val scale = size.width / 24f
        val stroke = strokeWidth.toPx()
        val arc = Path().apply {
            moveTo(19f * scale, 17f * scale)
            cubicTo(19f * scale, 12.5f * scale, 15.5f * scale, 9.5f * scale, 11f * scale, 9.5f * scale)
            cubicTo(9f * scale, 9.5f * scale, 7f * scale, 10.2f * scale, 5.5f * scale, 11.5f * scale)
        }
        drawPath(arc, tint, style = Stroke(width = stroke, cap = StrokeCap.Round))
        val arrow = Path().apply {
            moveTo(9f * scale, 7f * scale)
            lineTo(5f * scale, 11.3f * scale)
            lineTo(9.6f * scale, 14.3f * scale)
        }
        drawPath(arrow, tint, style = Stroke(width = stroke, cap = StrokeCap.Round, join = StrokeJoin.Round))
    }
}

@Composable
private fun strokeIcon(
    modifier: Modifier,
    tint: Color,
    iconSize: Dp,
    strokeWidth: Dp,
    buildPath: Path.(scale: Float) -> Unit,
) = Canvas(modifier = modifier.size(iconSize)) {
    val scale = size.width / 24f
    val path = Path().apply { buildPath(scale) }
    drawPath(
        path = path,
        color = tint,
        style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round),
    )
}
