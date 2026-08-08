package by.freiding.braindrop.feature.profile.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import by.freiding.braindrop.core.ui.BrainDropTheme
import by.freiding.braindrop.core.ui.icon.BrainDropIcons
import by.freiding.braindrop.feature.profile.Res
import by.freiding.braindrop.feature.profile.presentation.component.PlaceholderScreen
import by.freiding.braindrop.feature.profile.profile_placeholder
import by.freiding.braindrop.feature.profile.profile_title
import org.jetbrains.compose.resources.stringResource

/**
 * Placeholder: the "Profile" section isn't designed yet (see design_handoff_braindrop_redesign).
 * The tab bar is wired up ahead of time so future features won't require reworking navigation.
 */
@Composable
fun ProfileScreen() {
    PlaceholderScreen(
        icon = { BrainDropIcons.Person(iconSize = 28.dp, tint = BrainDropTheme.semantics.soonInk) },
        title = stringResource(Res.string.profile_title),
        body = stringResource(Res.string.profile_placeholder),
    )
}
