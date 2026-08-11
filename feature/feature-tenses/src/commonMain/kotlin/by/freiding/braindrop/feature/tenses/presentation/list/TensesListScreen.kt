package by.freiding.braindrop.feature.tenses.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import by.freiding.braindrop.core.navigation.Routes
import by.freiding.braindrop.core.ui.BrainDropTheme
import by.freiding.braindrop.core.ui.component.BrainDropButton
import by.freiding.braindrop.core.ui.component.BrainDropButtonStyle
import by.freiding.braindrop.core.ui.component.BrainDropIconButton
import by.freiding.braindrop.core.ui.component.BrainDropLoadingIndicator
import by.freiding.braindrop.core.ui.component.ErrorStatusCard
import by.freiding.braindrop.core.ui.component.SegmentedProgressBar
import by.freiding.braindrop.core.ui.icon.BrainDropIcons
import by.freiding.braindrop.feature.tenses.Res
import by.freiding.braindrop.feature.tenses.cd_back
import by.freiding.braindrop.feature.tenses.domain.model.TenseQuizType
import by.freiding.braindrop.feature.tenses.domain.model.TenseTime
import by.freiding.braindrop.feature.tenses.domain.model.TenseWithProgress
import by.freiding.braindrop.feature.tenses.error_retry
import by.freiding.braindrop.feature.tenses.tenses_list_cheatsheet_button
import by.freiding.braindrop.feature.tenses.tenses_list_comparisons_button
import by.freiding.braindrop.feature.tenses.tenses_list_error_body
import by.freiding.braindrop.feature.tenses.tenses_list_error_title
import by.freiding.braindrop.feature.tenses.tenses_list_progress_count
import by.freiding.braindrop.feature.tenses.tenses_list_quiz_button
import by.freiding.braindrop.feature.tenses.tenses_list_quiz_discrimination
import by.freiding.braindrop.feature.tenses.tenses_list_quiz_form
import by.freiding.braindrop.feature.tenses.tenses_list_quiz_marker
import by.freiding.braindrop.feature.tenses.tenses_list_quiz_mixed
import by.freiding.braindrop.feature.tenses.tenses_list_time_future
import by.freiding.braindrop.feature.tenses.tenses_list_time_past
import by.freiding.braindrop.feature.tenses.tenses_list_time_present
import by.freiding.braindrop.feature.tenses.tenses_list_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.math.roundToInt

@Composable
fun TensesListScreen(
    navController: NavController,
    viewModel: TensesListViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.reload()
        viewModel.effects.collect { effect ->
            when (effect) {
                is TensesListUiEffect.NavigateToDetail ->
                    navController.navigate(Routes.TenseDetail(effect.tenseId))
                is TensesListUiEffect.NavigateToComparisons ->
                    navController.navigate(Routes.TenseComparisons)
                is TensesListUiEffect.NavigateToCheatSheet ->
                    navController.navigate(Routes.TenseCheatSheet)
                is TensesListUiEffect.NavigateToQuiz ->
                    navController.navigate(Routes.TensesQuiz(effect.mode))
                is TensesListUiEffect.NavigateBack -> navController.popBackStack()
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        TensesListHeader(
            state = state,
            onBack = { viewModel.onEvent(TensesListUiEvent.NavigateBack) },
            onCheatSheet = { viewModel.onEvent(TensesListUiEvent.CheatSheetClicked) },
            onComparisons = { viewModel.onEvent(TensesListUiEvent.ComparisonsClicked) },
            onStartQuiz = { mode -> viewModel.onEvent(TensesListUiEvent.StartQuiz(mode)) },
        )

        Box(modifier = Modifier.fillMaxSize()) {
            when {
                state.error != null -> ErrorStatusCard(
                    title = stringResource(Res.string.tenses_list_error_title),
                    body = stringResource(Res.string.tenses_list_error_body),
                    retryText = stringResource(Res.string.error_retry),
                    onRetry = { viewModel.reload() },
                    secondaryText = stringResource(Res.string.cd_back),
                    onSecondary = { viewModel.onEvent(TensesListUiEvent.NavigateBack) },
                )

                state.isLoading -> BrainDropLoadingIndicator()

                else -> TensesListContent(
                    state = state,
                    onTenseClick = { id -> viewModel.onEvent(TensesListUiEvent.TenseClicked(id)) },
                )
            }
        }
    }
}

@Composable
private fun TensesListHeader(
    state: TensesListUiState,
    onBack: () -> Unit,
    onCheatSheet: () -> Unit,
    onComparisons: () -> Unit,
    onStartQuiz: (String) -> Unit,
) {
    var quizMenuExpanded by remember { mutableStateOf(false) }
    val total = state.tenses.size
    val ratio = if (total > 0) state.learnedCount.toFloat() / total else 0f
    val percent = (ratio * 100).roundToInt()

    Column(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(
                horizontal = BrainDropTheme.spacing.xs,
                vertical = BrainDropTheme.spacing.xxs,
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BrainDropIconButton(onClick = onBack, contentDescription = stringResource(Res.string.cd_back)) {
                BrainDropIcons.ChevronLeft(iconSize = 22.dp, tint = MaterialTheme.colorScheme.onSurface)
            }
            Text(
                text = stringResource(Res.string.tenses_list_title),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f).padding(start = BrainDropTheme.spacing.xxs),
            )
        }

        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = BrainDropTheme.spacing.md)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = stringResource(Res.string.tenses_list_progress_count, state.learnedCount, total),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = "$percent%",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
            Spacer(Modifier.height(BrainDropTheme.spacing.xs))
            SegmentedProgressBar(
                segmentCount = 12,
                ratio = ratio,
                height = 7.dp,
                gap = 3.dp,
                cornerRadius = 3.dp,
                filledColor = MaterialTheme.colorScheme.primary,
                emptyColor = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.padding(bottom = BrainDropTheme.spacing.sm),
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = BrainDropTheme.spacing.sm),
                horizontalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.xs),
            ) {
                BrainDropButton(
                    text = stringResource(Res.string.tenses_list_cheatsheet_button),
                    onClick = onCheatSheet,
                    style = BrainDropButtonStyle.OUTLINED,
                    height = 36.dp,
                )
                BrainDropButton(
                    text = stringResource(Res.string.tenses_list_comparisons_button),
                    onClick = onComparisons,
                    style = BrainDropButtonStyle.OUTLINED,
                    height = 36.dp,
                )
                Spacer(Modifier.weight(1f))
                Box {
                    BrainDropButton(
                        text = stringResource(Res.string.tenses_list_quiz_button),
                        onClick = { quizMenuExpanded = true },
                        height = 36.dp,
                        trailingIcon = {
                            BrainDropIcons.ChevronRight(iconSize = 13.dp, tint = Color.White, strokeWidth = 2.2.dp)
                        },
                    )
                    DropdownMenu(
                        expanded = quizMenuExpanded,
                        onDismissRequest = { quizMenuExpanded = false },
                        shape = BrainDropTheme.shapes.md,
                        containerColor = MaterialTheme.colorScheme.surface,
                    ) {
                        val modes = listOf(
                            TenseQuizType.FORM to Res.string.tenses_list_quiz_form,
                            TenseQuizType.MARKER_MATCH to Res.string.tenses_list_quiz_marker,
                            TenseQuizType.DISCRIMINATION to Res.string.tenses_list_quiz_discrimination,
                            TenseQuizType.MIXED_REVIEW to Res.string.tenses_list_quiz_mixed,
                        )
                        modes.forEach { (type, labelRes) ->
                            DropdownMenuItem(
                                text = { Text(stringResource(labelRes), style = MaterialTheme.typography.bodyLarge) },
                                onClick = {
                                    quizMenuExpanded = false
                                    onStartQuiz(type.name)
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TensesListContent(
    state: TensesListUiState,
    onTenseClick: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = BrainDropTheme.spacing.md, vertical = BrainDropTheme.spacing.sm),
        verticalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.lg),
    ) {
        items(state.groupedByTime, key = { it.first.name }) { (time, tenses) ->
            TenseTimeSection(time = time, tenses = tenses, onTenseClick = onTenseClick)
        }
    }
}

@Composable
private fun TenseTimeSection(
    time: TenseTime,
    tenses: List<TenseWithProgress>,
    onTenseClick: (String) -> Unit,
) {
    val color = BrainDropTheme.semantics.tenseTimeColor(time.name)
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = BrainDropTheme.spacing.xs),
        ) {
            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(color))
            Spacer(Modifier.width(BrainDropTheme.spacing.xs))
            Text(
                text = timeLabel(time),
                style = BrainDropTheme.type.label,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        tenses.chunked(2).forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = BrainDropTheme.spacing.xs),
                horizontalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.xs),
            ) {
                row.forEach { item ->
                    TenseCell(
                        item = item,
                        color = color,
                        onClick = { onTenseClick(item.tense.id) },
                        modifier = Modifier.weight(1f),
                    )
                }
                if (row.size == 1) Spacer(Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun TenseCell(
    item: TenseWithProgress,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val surface = BrainDropTheme.semantics.tenseTimeSurface(item.tense.time.name)
    Column(
        modifier = modifier
            .aspectRatio(1.6f)
            .clip(BrainDropTheme.shapes.md)
            .background(surface, BrainDropTheme.shapes.md)
            .clickable(onClick = onClick)
            .padding(BrainDropTheme.spacing.sm),
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(color))
            if (item.progress.isLearned) {
                BrainDropIcons.Check(iconSize = 14.dp, tint = BrainDropTheme.semantics.correct)
            }
        }
        Spacer(Modifier.weight(1f))
        Text(
            text = item.tense.titleEn,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            text = item.tense.titleRu,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun timeLabel(time: TenseTime): String =
    when (time) {
        TenseTime.PRESENT -> stringResource(Res.string.tenses_list_time_present)
        TenseTime.PAST -> stringResource(Res.string.tenses_list_time_past)
        TenseTime.FUTURE -> stringResource(Res.string.tenses_list_time_future)
    }
