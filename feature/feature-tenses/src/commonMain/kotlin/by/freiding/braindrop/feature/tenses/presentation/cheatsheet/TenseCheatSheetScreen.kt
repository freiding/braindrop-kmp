package by.freiding.braindrop.feature.tenses.presentation.cheatsheet

import androidx.compose.foundation.background
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
import by.freiding.braindrop.feature.tenses.domain.model.Tense
import by.freiding.braindrop.feature.tenses.error_retry
import by.freiding.braindrop.feature.tenses.presentation.common.aspectAccent
import by.freiding.braindrop.feature.tenses.tense_cheatsheet_error_body
import by.freiding.braindrop.feature.tenses.tense_cheatsheet_error_title
import by.freiding.braindrop.feature.tenses.tense_cheatsheet_title
import by.freiding.braindrop.feature.tenses.tense_detail_label_affirmative
import by.freiding.braindrop.feature.tenses.tense_detail_label_negative
import by.freiding.braindrop.feature.tenses.tense_detail_label_question
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TenseCheatSheetScreen(
    navController: NavController,
    viewModel: TenseCheatSheetViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is TenseCheatSheetUiEffect.NavigateBack -> navController.popBackStack()
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
                onClick = { viewModel.onEvent(TenseCheatSheetUiEvent.NavigateBack) },
                contentDescription = stringResource(Res.string.cd_back),
            ) {
                BrainDropIcons.ChevronLeft(iconSize = 22.dp, tint = MaterialTheme.colorScheme.onSurface)
            }
            Text(
                text = stringResource(Res.string.tense_cheatsheet_title),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f).padding(start = BrainDropTheme.spacing.xxs),
            )
        }

        Box(modifier = Modifier.fillMaxSize()) {
            when {
                state.error != null -> ErrorStatusCard(
                    title = stringResource(Res.string.tense_cheatsheet_error_title),
                    body = stringResource(Res.string.tense_cheatsheet_error_body),
                    retryText = stringResource(Res.string.error_retry),
                    onRetry = { viewModel.reload() },
                )

                state.isLoading -> BrainDropLoadingIndicator()

                else -> LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = BrainDropTheme.spacing.sm),
                    verticalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.sm),
                ) {
                    items(state.tenses, key = { it.id }) { tense -> CheatSheetRow(tense) }
                }
            }
        }
    }
}

@Composable
private fun CheatSheetRow(tense: Tense) {
    val color = BrainDropTheme.semantics.tenseTimeColor(tense.time.name)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .brainDropCard(BrainDropTheme.shapes.md)
            .padding(horizontal = 16.dp, vertical = 14.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = tense.titleEn,
                style = MaterialTheme.typography.titleSmall,
                color = color,
                modifier = Modifier.weight(1f),
            )
            Text(
                text = tense.titleRu,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Spacer(Modifier.height(BrainDropTheme.spacing.xxs))
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer, BrainDropTheme.shapes.chip)
                .padding(horizontal = BrainDropTheme.spacing.sm, vertical = 3.dp),
        ) {
            Text(
                text = aspectAccent(tense.aspect).label,
                style = BrainDropTheme.type.label,
                color = MaterialTheme.colorScheme.primary,
            )
        }
        Spacer(Modifier.height(BrainDropTheme.spacing.xs))
        CheatSheetFormulaLine(stringResource(Res.string.tense_detail_label_affirmative), tense.formulas.affirmative)
        CheatSheetFormulaLine(stringResource(Res.string.tense_detail_label_negative), tense.formulas.negative)
        CheatSheetFormulaLine(stringResource(Res.string.tense_detail_label_question), tense.formulas.question)
        Spacer(Modifier.height(BrainDropTheme.spacing.xs))
        Text(
            text = tense.markers.joinToString(" · "),
            style = BrainDropTheme.type.verbForms,
            color = BrainDropTheme.semantics.ink500,
        )
    }
}

@Composable
private fun CheatSheetFormulaLine(
    label: String,
    formula: String,
) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 1.dp)) {
        Text(
            text = label,
            style = BrainDropTheme.type.label,
            color = BrainDropTheme.semantics.formsLabelInk,
            modifier = Modifier.padding(end = BrainDropTheme.spacing.xs),
        )
        Text(
            text = formula,
            style = BrainDropTheme.type.verbForms,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}
