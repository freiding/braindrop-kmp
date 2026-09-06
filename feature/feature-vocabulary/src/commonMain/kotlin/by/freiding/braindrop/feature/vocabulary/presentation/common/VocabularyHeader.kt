package by.freiding.braindrop.feature.vocabulary.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import by.freiding.braindrop.core.ui.BrainDropTheme
import by.freiding.braindrop.core.ui.component.BrainDropIconButton
import by.freiding.braindrop.core.ui.icon.BrainDropIcons
import by.freiding.braindrop.feature.vocabulary.Res
import by.freiding.braindrop.feature.vocabulary.vocab_cd_back
import org.jetbrains.compose.resources.stringResource

/** Standard screen header for the Vocabulary section: back button, title, optional trailing slot. */
@Composable
internal fun VocabularyHeader(
    title: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    divider: Boolean = true,
    trailing: @Composable (RowScope.() -> Unit)? = null,
) {
    Column(modifier = modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface).statusBarsPadding()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = BrainDropTheme.spacing.xs, vertical = BrainDropTheme.spacing.xxs),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BrainDropIconButton(onClick = onBack, contentDescription = stringResource(Res.string.vocab_cd_back)) {
                BrainDropIcons.ChevronLeft(iconSize = 22.dp, tint = MaterialTheme.colorScheme.onSurface)
            }
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.weight(1f).padding(start = BrainDropTheme.spacing.xxs),
            )
            trailing?.invoke(this)
        }
        if (divider) HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant, thickness = 1.dp)
    }
}
