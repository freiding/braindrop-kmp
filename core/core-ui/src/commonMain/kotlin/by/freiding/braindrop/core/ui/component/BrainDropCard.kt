package by.freiding.braindrop.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * The soft-shadow surface card used for the quiz question, quiz result, and verb detail
 * examples cards: a 3dp shadow at 6% black plus a surface-colored background, both clipped
 * to [shape].
 */
fun Modifier.brainDropCard(
    shape: Shape,
    elevation: Dp = 3.dp,
): Modifier =
    composed {
        this
            .shadow(
                elevation = elevation,
                shape = shape,
                ambientColor = Color.Black.copy(alpha = 0.06f),
                spotColor = Color.Black.copy(alpha = 0.06f),
            ).background(MaterialTheme.colorScheme.surface, shape)
    }
