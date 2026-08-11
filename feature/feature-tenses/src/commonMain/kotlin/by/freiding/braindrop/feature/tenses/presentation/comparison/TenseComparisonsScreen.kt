package by.freiding.braindrop.feature.tenses.presentation.comparison

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import by.freiding.braindrop.core.ui.BrainDropTheme
import by.freiding.braindrop.core.ui.component.BrainDropIconButton
import by.freiding.braindrop.core.ui.component.BrainDropLoadingIndicator
import by.freiding.braindrop.core.ui.component.ErrorStatusCard
import by.freiding.braindrop.core.ui.component.brainDropCard
import by.freiding.braindrop.core.ui.icon.BrainDropIcons
import by.freiding.braindrop.feature.tenses.Res
import by.freiding.braindrop.feature.tenses.cd_back
import by.freiding.braindrop.feature.tenses.domain.model.TenseComparison
import by.freiding.braindrop.feature.tenses.domain.model.TenseExample
import by.freiding.braindrop.feature.tenses.error_retry
import by.freiding.braindrop.feature.tenses.presentation.common.formatTenseId
import by.freiding.braindrop.feature.tenses.tense_comparisons_error_body
import by.freiding.braindrop.feature.tenses.tense_comparisons_error_title
import by.freiding.braindrop.feature.tenses.tense_comparisons_title
import by.freiding.braindrop.feature.tenses.tense_comparisons_vs
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TenseComparisonsScreen(
    navController: NavController,
    viewModel: TenseComparisonsViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is TenseComparisonsUiEffect.NavigateBack -> navController.popBackStack()
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Row(
            modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface).padding(
                horizontal = BrainDropTheme.spacing.xs,
                vertical = BrainDropTheme.spacing.xxs,
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BrainDropIconButton(
                onClick = { viewModel.onEvent(TenseComparisonsUiEvent.NavigateBack) },
                contentDescription = stringResource(Res.string.cd_back),
            ) {
                BrainDropIcons.ChevronLeft(iconSize = 22.dp, tint = MaterialTheme.colorScheme.onSurface)
            }
            Text(
                text = stringResource(Res.string.tense_comparisons_title),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f).padding(start = BrainDropTheme.spacing.xxs),
            )
        }

        Box(modifier = Modifier.fillMaxSize()) {
            when {
                state.error != null -> ErrorStatusCard(
                    title = stringResource(Res.string.tense_comparisons_error_title),
                    body = stringResource(Res.string.tense_comparisons_error_body),
                    retryText = stringResource(Res.string.error_retry),
                    onRetry = { viewModel.reload() },
                )

                state.isLoading -> BrainDropLoadingIndicator()

                else -> LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = BrainDropTheme.spacing.sm),
                    verticalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.sm),
                ) {
                    items(state.comparisons, key = { it.id }) { comparison ->
                        ComparisonCard(
                            comparison = comparison,
                            expanded = state.expandedId == comparison.id,
                            onClick = { viewModel.onEvent(TenseComparisonsUiEvent.ComparisonClicked(comparison.id)) },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ComparisonCard(
    comparison: TenseComparison,
    expanded: Boolean,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .brainDropCard(BrainDropTheme.shapes.xl)
            .clickable(onClick = onClick)
            .animateContentSize()
            .padding(horizontal = 18.dp, vertical = 16.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = formatTenseId(comparison.tenseIdA),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f),
            )
            Text(
                text = stringResource(Res.string.tense_comparisons_vs),
                style = BrainDropTheme.type.label,
                color = BrainDropTheme.semantics.ink400,
                modifier = Modifier.padding(horizontal = BrainDropTheme.spacing.xs),
            )
            Text(
                text = formatTenseId(comparison.tenseIdB),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f),
            )
            if (expanded) {
                BrainDropIcons.ChevronLeft(
                    iconSize = 16.dp,
                    tint = BrainDropTheme.semantics.ink400,
                    modifier = Modifier.padding(start = BrainDropTheme.spacing.xs),
                )
            } else {
                BrainDropIcons.ChevronRight(
                    iconSize = 16.dp,
                    tint = BrainDropTheme.semantics.ink400,
                    modifier = Modifier.padding(start = BrainDropTheme.spacing.xs),
                )
            }
        }
        Spacer(Modifier.height(BrainDropTheme.spacing.xs))
        Text(
            text = comparison.tip,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        if (expanded) {
            Spacer(Modifier.height(BrainDropTheme.spacing.md))
            ComparisonSide(
                title = formatTenseId(comparison.tenseIdA),
                points = comparison.pointsA,
                example = comparison.exampleA,
            )
            Spacer(Modifier.height(BrainDropTheme.spacing.sm))
            ComparisonSide(
                title = formatTenseId(comparison.tenseIdB),
                points = comparison.pointsB,
                example = comparison.exampleB,
            )
        }
    }
}

@Composable
private fun ComparisonSide(
    title: String,
    points: List<String>,
    example: TenseExample,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant, BrainDropTheme.shapes.md)
            .padding(BrainDropTheme.spacing.sm),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
        )
        Spacer(Modifier.height(BrainDropTheme.spacing.xxs))
        points.forEach { point ->
            Text(
                text = "· $point",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Spacer(Modifier.height(BrainDropTheme.spacing.xs))
        Text(
            text = example.english,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            text = example.russian,
            style = BrainDropTheme.type.translation,
            color = BrainDropTheme.semantics.ink500,
        )
    }
}
