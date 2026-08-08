package by.freiding.braindrop.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import by.freiding.braindrop.core.ui.BrainDropTheme
import by.freiding.braindrop.core.ui.generated.resources.Res
import by.freiding.braindrop.core.ui.generated.resources.nav_tab_learn
import by.freiding.braindrop.core.ui.generated.resources.nav_tab_profile
import by.freiding.braindrop.core.ui.generated.resources.nav_tab_progress
import by.freiding.braindrop.core.ui.icon.BrainDropIcons
import org.jetbrains.compose.resources.stringResource

/** The three top-level tabs — the only screens with the tab bar visible underneath. */
enum class BrainDropNavTab { LEARN, PROGRESS, PROFILE }

@Composable
fun BrainDropNavigationBar(
    selected: BrainDropNavTab,
    onSelect: (BrainDropNavTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface)) {
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant, thickness = 1.dp)
        Column(modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)) {
            Row(
                modifier = Modifier.fillMaxWidth().height(64.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                NavTabItem(
                    label = stringResource(Res.string.nav_tab_learn),
                    isSelected = selected == BrainDropNavTab.LEARN,
                    onClick = { onSelect(BrainDropNavTab.LEARN) },
                    icon = { tint, size -> BrainDropIcons.Cards(iconSize = size, tint = tint) },
                )
                NavTabItem(
                    label = stringResource(Res.string.nav_tab_progress),
                    isSelected = selected == BrainDropNavTab.PROGRESS,
                    onClick = { onSelect(BrainDropNavTab.PROGRESS) },
                    icon = { tint, size -> BrainDropIcons.BarChart(iconSize = size, tint = tint) },
                )
                NavTabItem(
                    label = stringResource(Res.string.nav_tab_profile),
                    isSelected = selected == BrainDropNavTab.PROFILE,
                    onClick = { onSelect(BrainDropNavTab.PROFILE) },
                    icon = { tint, size -> BrainDropIcons.Person(iconSize = size, tint = tint) },
                )
            }
        }
    }
}

@Composable
private fun RowScope.NavTabItem(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    icon: @Composable (tint: Color, size: Dp) -> Unit,
) {
    val tint = if (isSelected) MaterialTheme.colorScheme.primary else BrainDropTheme.semantics.ink400
    Column(
        modifier = Modifier
            .weight(1f)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            ).padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        icon(tint, 24.dp)
        Spacer(Modifier.height(5.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.SemiBold,
            color = tint,
        )
    }
}
