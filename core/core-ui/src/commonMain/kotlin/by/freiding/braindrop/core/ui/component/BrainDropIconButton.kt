package by.freiding.braindrop.core.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import by.freiding.braindrop.core.ui.BrainDropTheme

/**
 * A tappable icon at the minimum touch target size (44dp) — used for header back/close/search
 * buttons across screens so the hit area is consistent and never smaller than the icon glyph.
 */
@Composable
fun BrainDropIconButton(
    onClick: () -> Unit,
    contentDescription: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .size(BrainDropTheme.spacing.touchTarget)
            .clickable(onClickLabel = contentDescription, role = Role.Button, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}
