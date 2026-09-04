package by.freiding.braindrop.feature.profile.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import by.freiding.braindrop.core.ui.BrainDropTheme
import by.freiding.braindrop.core.ui.component.StatusCard
import by.freiding.braindrop.feature.profile.Res
import by.freiding.braindrop.feature.profile.placeholder_badge
import org.jetbrains.compose.resources.stringResource

/** Shared shell for the "Progress" and "Profile" placeholders — both are not yet designed. */
@Composable
fun PlaceholderScreen(
    icon: @Composable () -> Unit,
    title: String,
    body: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background).statusBarsPadding(),
        contentAlignment = Alignment.Center,
    ) {
        StatusCard(
            icon = icon,
            iconBackground = BrainDropTheme.semantics.soonTile,
            title = title,
            body = body,
            trailingContent = {
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(12.dp))
                        .padding(horizontal = BrainDropTheme.spacing.sm, vertical = 6.dp),
                ) {
                    Text(
                        text = stringResource(Res.string.placeholder_badge),
                        style = BrainDropTheme.type.label,
                        color = BrainDropTheme.semantics.soonInk,
                    )
                }
            },
        )
    }
}
