package by.freiding.braindrop.feature.tenses.presentation.cheatsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import by.freiding.braindrop.core.ui.BrainDropTheme
import by.freiding.braindrop.core.ui.component.BrainDropIconButton
import by.freiding.braindrop.core.ui.component.BrainDropLoadingIndicator
import by.freiding.braindrop.core.ui.component.ErrorStatusCard
import by.freiding.braindrop.core.ui.icon.BrainDropIcons
import by.freiding.braindrop.feature.tenses.Res
import by.freiding.braindrop.feature.tenses.cd_back
import by.freiding.braindrop.feature.tenses.cd_share
import by.freiding.braindrop.feature.tenses.domain.model.Tense
import by.freiding.braindrop.feature.tenses.domain.model.TenseTime
import by.freiding.braindrop.feature.tenses.error_retry
import by.freiding.braindrop.feature.tenses.tense_cheatsheet_col_formula
import by.freiding.braindrop.feature.tenses.tense_cheatsheet_col_tense
import by.freiding.braindrop.feature.tenses.tense_cheatsheet_error_body
import by.freiding.braindrop.feature.tenses.tense_cheatsheet_error_title
import by.freiding.braindrop.feature.tenses.tense_cheatsheet_title
import by.freiding.braindrop.feature.tenses.tenses_list_time_future
import by.freiding.braindrop.feature.tenses.tenses_list_time_past
import by.freiding.braindrop.feature.tenses.tenses_list_time_present
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

private val FORMULA_COLUMN_WIDTH = 186.dp

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
            // Export-to-image/PDF isn't implemented yet — it needs a platform-specific capture +
            // share flow (expect/actual per target), which is a separate feature from this
            // layout redesign. The button is wired up visually so the affordance is in place.
            BrainDropIconButton(onClick = {}, contentDescription = stringResource(Res.string.cd_share)) {
                BrainDropIcons.Share(iconSize = 20.dp, tint = MaterialTheme.colorScheme.primary)
            }
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

                else -> CheatSheetTable(tenses = state.tenses)
            }
        }
    }
}

@OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
private fun CheatSheetTable(tenses: List<Tense>) {
    val grouped = TenseTime.entries.map { time -> time to tenses.filter { it.time == time } }
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        stickyHeader { TableHeaderRow() }
        grouped.forEach { (time, group) ->
            if (group.isEmpty()) return@forEach
            item(key = "group_${time.name}") { GroupHeaderRow(time) }
            items(group, key = { it.id }) { tense -> CheatSheetRow(tense) }
        }
    }
}

@Composable
private fun TableHeaderRow() {
    Column(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.background)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 9.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(Res.string.tense_cheatsheet_col_tense),
                style = BrainDropTheme.type.label.copy(fontSize = 9.5.sp, letterSpacing = 0.95.sp),
                color = BrainDropTheme.semantics.ink400,
                modifier = Modifier.weight(1f),
            )
            Text(
                text = stringResource(Res.string.tense_cheatsheet_col_formula),
                style = BrainDropTheme.type.label.copy(fontSize = 9.5.sp, letterSpacing = 0.95.sp),
                color = BrainDropTheme.semantics.ink400,
                modifier = Modifier.width(FORMULA_COLUMN_WIDTH),
            )
        }
        HorizontalDivider(color = MaterialTheme.colorScheme.outline)
    }
}

@Composable
private fun GroupHeaderRow(time: TenseTime) {
    val semantics = BrainDropTheme.semantics
    Text(
        text = timeLabel(time).uppercase(),
        style = MaterialTheme.typography.titleSmall.copy(fontSize = 12.sp, fontWeight = FontWeight.ExtraBold),
        color = semantics.tenseTimeColor(time.name),
        modifier = Modifier
            .fillMaxWidth()
            .background(semantics.tenseTimeSurface(time.name))
            .padding(start = 16.dp, end = 16.dp, top = 11.dp, bottom = 5.dp),
    )
}

@Composable
private fun CheatSheetRow(tense: Tense) {
    val aspectColor = BrainDropTheme.semantics.aspectColor(tense.aspect.name)
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Box(modifier = Modifier.size(8.dp).background(aspectColor, CircleShape))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = cheatSheetTitle(tense),
                    style = MaterialTheme.typography.titleSmall.copy(fontSize = 13.sp, fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = tense.markers.take(3).joinToString(" · "),
                    style = BrainDropTheme.type.verbForms.copy(fontSize = 10.5.sp, fontWeight = FontWeight.Medium),
                    color = BrainDropTheme.semantics.ink400,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 3.dp),
                )
            }
            Text(
                text = tense.formulas.affirmative,
                style = BrainDropTheme.type.verbForms.copy(fontSize = 11.5.sp, fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.width(FORMULA_COLUMN_WIDTH),
            )
        }
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant, thickness = 1.dp)
    }
}

/** Shortens "Perfect Continuous" to "Perf. Cont." so the row fits on one line — cheat sheet only. */
private fun cheatSheetTitle(tense: Tense): String = tense.titleEn.replace("Perfect Continuous", "Perf. Cont.")

@Composable
private fun timeLabel(time: TenseTime): String =
    when (time) {
        TenseTime.PRESENT -> stringResource(Res.string.tenses_list_time_present)
        TenseTime.PAST -> stringResource(Res.string.tenses_list_time_past)
        TenseTime.FUTURE -> stringResource(Res.string.tenses_list_time_future)
    }
