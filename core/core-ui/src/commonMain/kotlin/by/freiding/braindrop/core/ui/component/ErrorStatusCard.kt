package by.freiding.braindrop.core.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import by.freiding.braindrop.core.ui.icon.BrainDropIcons

/**
 * Full-screen error state: a centered [StatusCard] with an alert icon, a retry action, and an
 * optional secondary (usually "go back") action — the same shell every screen falls back to
 * when its data load fails.
 */
@Composable
fun ErrorStatusCard(
    title: String,
    body: String,
    retryText: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    secondaryText: String? = null,
    onSecondary: (() -> Unit)? = null,
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        StatusCard(
            icon = { BrainDropIcons.Alert(iconSize = 30.dp, tint = MaterialTheme.colorScheme.error) },
            iconBackground = MaterialTheme.colorScheme.errorContainer,
            title = title,
            body = body,
            primaryAction = {
                BrainDropButton(text = retryText, onClick = onRetry)
            },
            secondaryAction = if (secondaryText != null && onSecondary != null) {
                {
                    BrainDropButton(text = secondaryText, onClick = onSecondary, style = BrainDropButtonStyle.TEXT)
                }
            } else {
                null
            },
        )
    }
}
