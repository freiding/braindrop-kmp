package by.freiding.braindrop.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.freiding.braindrop.core.ui.BrainDropTheme

/**
 * Shared shell for "empty" / "error" screens (see design section 8): a round icon
 * on a colored tile, a title, an explanation, up to two buttons. Used on Home,
 * Verb List, and Quiz — the same visual language everywhere instead of bare text/a spinner.
 */
@Composable
fun StatusCard(
    icon: @Composable () -> Unit,
    iconBackground: Color,
    title: String,
    body: String,
    modifier: Modifier = Modifier,
    primaryAction: (@Composable () -> Unit)? = null,
    secondaryAction: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(horizontal = BrainDropTheme.spacing.lg),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier.size(64.dp).background(iconBackground, BrainDropTheme.shapes.xl),
            contentAlignment = Alignment.Center,
        ) {
            icon()
        }
        Spacer(Modifier.size(BrainDropTheme.spacing.md))
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.size(BrainDropTheme.spacing.xs))
        Text(
            text = body,
            style = BrainDropTheme.type.translation.copy(fontSize = 13.5.sp, lineHeight = 19.sp),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
        if (trailingContent != null) {
            Spacer(Modifier.size(BrainDropTheme.spacing.md))
            trailingContent()
        }
        if (primaryAction != null || secondaryAction != null) {
            Spacer(Modifier.size(BrainDropTheme.spacing.md))
            primaryAction?.invoke()
            if (secondaryAction != null) {
                Spacer(Modifier.size(BrainDropTheme.spacing.xs))
                secondaryAction.invoke()
            }
        }
    }
}
