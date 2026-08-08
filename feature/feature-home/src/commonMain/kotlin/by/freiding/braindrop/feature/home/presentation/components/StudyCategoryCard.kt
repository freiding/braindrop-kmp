package by.freiding.braindrop.feature.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.freiding.braindrop.core.ui.BrainDropTheme
import by.freiding.braindrop.core.ui.component.SegmentedProgressBar
import by.freiding.braindrop.feature.home.Res
import by.freiding.braindrop.feature.home.category_groups_count
import by.freiding.braindrop.feature.home.category_irregular_verbs_title
import by.freiding.braindrop.feature.home.category_phrasal_verbs_description
import by.freiding.braindrop.feature.home.category_phrasal_verbs_title
import by.freiding.braindrop.feature.home.category_soon_badge
import by.freiding.braindrop.feature.home.category_tenses_description
import by.freiding.braindrop.feature.home.category_tenses_title
import by.freiding.braindrop.feature.home.category_verbs_count
import by.freiding.braindrop.feature.home.domain.model.StudyCategory
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun StudyCategoryCard(
    category: StudyCategory,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (category.isAvailable) {
        ActiveCategoryCard(category, onClick, modifier)
    } else {
        SoonCategoryCard(category, modifier)
    }
}

@Composable
private fun ActiveCategoryCard(
    category: StudyCategory,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val title = categoryTitle(category.id)
    val description = categoryDescription(category)
    val ratio = if (category.totalItems > 0) category.studiedCount.toFloat() / category.totalItems else 0f

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(BrainDropTheme.shapes.card)
            .background(MaterialTheme.colorScheme.surface, BrainDropTheme.shapes.card)
            .clickable(onClick = onClick)
            .padding(BrainDropTheme.spacing.md),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(13.dp)) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer, BrainDropTheme.shapes.md),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = category.icon, fontSize = 22.sp)
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
                Text(text = description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = category.studiedCount.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = "/${category.totalItems}",
                    style = BrainDropTheme.type.counter,
                    color = BrainDropTheme.semantics.ink400,
                )
            }
        }
        Spacer(Modifier.height(14.dp))
        SegmentedProgressBar(
            segmentCount = 12,
            ratio = ratio,
            height = 9.dp,
            gap = 3.dp,
            cornerRadius = 3.dp,
            filledColor = MaterialTheme.colorScheme.primary,
            emptyColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}

@Composable
private fun SoonCategoryCard(category: StudyCategory, modifier: Modifier = Modifier) {
    val title = categoryTitle(category.id)
    val description = categoryDescription(category)
    val semantics = BrainDropTheme.semantics

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(semantics.soonSurface, BrainDropTheme.shapes.card)
            .dashedBorder(color = semantics.soonBorder, width = 1.5.dp, cornerRadius = 20.dp)
            .padding(horizontal = BrainDropTheme.spacing.md, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(13.dp),
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .background(semantics.soonTile, RoundedCornerShape(12.dp))
                .grayscaleAndDim(alpha = 0.65f),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = category.icon, fontSize = 20.sp)
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold, color = semantics.soonInk)
            Text(text = description, style = MaterialTheme.typography.bodySmall, color = semantics.ink400)
        }
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
                .padding(horizontal = 10.dp, vertical = 5.dp),
        ) {
            Text(
                text = stringResource(Res.string.category_soon_badge),
                style = BrainDropTheme.type.label,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun categoryTitle(id: String): String = when (id) {
    "irregular_verbs" -> stringResource(Res.string.category_irregular_verbs_title)
    "tenses" -> stringResource(Res.string.category_tenses_title)
    "phrasal_verbs" -> stringResource(Res.string.category_phrasal_verbs_title)
    else -> id
}

@Composable
private fun categoryDescription(category: StudyCategory): String = when (category.id) {
    "irregular_verbs" -> {
        val verbs = pluralStringResource(Res.plurals.category_verbs_count, category.totalItems, category.totalItems)
        val groups = category.secondaryCount?.let { pluralStringResource(Res.plurals.category_groups_count, it, it) }
        if (groups != null) "$verbs · $groups" else verbs
    }
    "tenses" -> stringResource(Res.string.category_tenses_description)
    "phrasal_verbs" -> stringResource(Res.string.category_phrasal_verbs_description)
    else -> ""
}

private fun Modifier.dashedBorder(color: Color, width: Dp, cornerRadius: Dp): Modifier =
    drawBehind {
        val strokeWidthPx = width.toPx()
        val stroke = Stroke(
            width = strokeWidthPx,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(7f, 5f), 0f),
        )
        drawRoundRect(
            color = color,
            topLeft = Offset(strokeWidthPx / 2, strokeWidthPx / 2),
            size = Size(size.width - strokeWidthPx, size.height - strokeWidthPx),
            cornerRadius = CornerRadius(cornerRadius.toPx() - strokeWidthPx / 2),
            style = stroke,
        )
    }

/**
 * ColorMatrix/Paint are built once via `remember` (through `composed`) rather than inside
 * `drawWithContent`, whose lambda runs on every draw pass — allocating there would mean a new
 * matrix and paint every frame for a tile that's static outside of theme/alpha changes.
 */
private fun Modifier.grayscaleAndDim(alpha: Float): Modifier = composed {
    val paint = remember(alpha) {
        Paint().apply {
            colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })
            this.alpha = alpha
        }
    }
    drawWithContent {
        drawIntoCanvas { canvas ->
            canvas.saveLayer(Rect(Offset.Zero, size), paint)
            drawContent()
            canvas.restore()
        }
    }
}
