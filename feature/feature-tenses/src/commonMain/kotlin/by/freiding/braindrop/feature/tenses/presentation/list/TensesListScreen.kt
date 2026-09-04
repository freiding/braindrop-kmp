package by.freiding.braindrop.feature.tenses.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import by.freiding.braindrop.core.navigation.Routes
import by.freiding.braindrop.core.ui.BrainDropTheme
import by.freiding.braindrop.core.ui.component.BrainDropButton
import by.freiding.braindrop.core.ui.component.BrainDropIconButton
import by.freiding.braindrop.core.ui.component.BrainDropLoadingIndicator
import by.freiding.braindrop.core.ui.component.ErrorStatusCard
import by.freiding.braindrop.core.ui.icon.BrainDropIcons
import by.freiding.braindrop.feature.tenses.Res
import by.freiding.braindrop.feature.tenses.cd_back
import by.freiding.braindrop.feature.tenses.domain.model.TenseTime
import by.freiding.braindrop.feature.tenses.domain.model.TenseWithProgress
import by.freiding.braindrop.feature.tenses.error_retry
import by.freiding.braindrop.feature.tenses.presentation.common.TenseMatrixMap
import by.freiding.braindrop.feature.tenses.presentation.common.aspectAccent
import by.freiding.braindrop.feature.tenses.tenses_list_all_learned
import by.freiding.braindrop.feature.tenses.tenses_list_cheatsheet_button
import by.freiding.braindrop.feature.tenses.tenses_list_confused_subtitle
import by.freiding.braindrop.feature.tenses.tenses_list_confused_title
import by.freiding.braindrop.feature.tenses.tenses_list_error_body
import by.freiding.braindrop.feature.tenses.tenses_list_error_title
import by.freiding.braindrop.feature.tenses.tenses_list_remaining
import by.freiding.braindrop.feature.tenses.tenses_list_time_future
import by.freiding.braindrop.feature.tenses.tenses_list_time_past
import by.freiding.braindrop.feature.tenses.tenses_list_time_present
import by.freiding.braindrop.feature.tenses.tenses_list_title
import by.freiding.braindrop.feature.tenses.tenses_list_train_button
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

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
                    navController.navigate(Routes.TenseComparisons())
                is TensesListUiEffect.NavigateToCheatSheet ->
                    navController.navigate(Routes.TenseCheatSheet)
                is TensesListUiEffect.NavigateToQuiz ->
                    navController.navigate(Routes.TensesQuiz())
                is TensesListUiEffect.NavigateBack -> navController.popBackStack()
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        when {
            state.error != null -> {
                TensesListTopBar(onBack = { viewModel.onEvent(TensesListUiEvent.NavigateBack) })
                Box(modifier = Modifier.fillMaxSize()) {
                    ErrorStatusCard(
                        title = stringResource(Res.string.tenses_list_error_title),
                        body = stringResource(Res.string.tenses_list_error_body),
                        retryText = stringResource(Res.string.error_retry),
                        onRetry = { viewModel.reload() },
                        secondaryText = stringResource(Res.string.cd_back),
                        onSecondary = { viewModel.onEvent(TensesListUiEvent.NavigateBack) },
                    )
                }
            }

            state.isLoading -> {
                TensesListTopBar(onBack = { viewModel.onEvent(TensesListUiEvent.NavigateBack) })
                Box(modifier = Modifier.fillMaxSize()) { BrainDropLoadingIndicator() }
            }

            else -> TensesListContent(
                state = state,
                onBack = { viewModel.onEvent(TensesListUiEvent.NavigateBack) },
                onTimeSelected = { time -> viewModel.onEvent(TensesListUiEvent.TimeTabSelected(time)) },
                onTenseClick = { id -> viewModel.onEvent(TensesListUiEvent.TenseClicked(id)) },
                onConfusedWithClick = { viewModel.onEvent(TensesListUiEvent.ConfusedWithClicked) },
                onCheatSheet = { viewModel.onEvent(TensesListUiEvent.CheatSheetClicked) },
                onTrain = { viewModel.onEvent(TensesListUiEvent.TrainClicked) },
            )
        }
    }
}

@Composable
private fun TensesListTopBar(onBack: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface).statusBarsPadding().padding(
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
}

@Composable
private fun ColumnScope.TensesListContent(
    state: TensesListUiState,
    onBack: () -> Unit,
    onTimeSelected: (TenseTime) -> Unit,
    onTenseClick: (String) -> Unit,
    onConfusedWithClick: () -> Unit,
    onCheatSheet: () -> Unit,
    onTrain: () -> Unit,
) {
    Column(modifier = Modifier.weight(1f)) {
        TensesListHeader(state = state, onBack = onBack, onTimeSelected = onTimeSelected)
        TimeTabBar(selectedTime = state.selectedTime, onTimeSelected = onTimeSelected)

        Box(modifier = Modifier.weight(1f)) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(state.tensesForSelectedTime, key = { it.tense.id }) { item ->
                    TenseRow(item = item, onClick = { onTenseClick(item.tense.id) })
                }
                item {
                    ConfusedWithRow(count = state.comparisonsCount, onClick = onConfusedWithClick)
                }
                item { Spacer(Modifier.height(96.dp)) }
            }
        }
    }
    BottomActionPanel(onCheatSheet = onCheatSheet, onTrain = onTrain)
}

@Composable
private fun TensesListHeader(
    state: TensesListUiState,
    onBack: () -> Unit,
    onTimeSelected: (TenseTime) -> Unit,
) {
    val total = state.tenses.size
    val remaining = total - state.learnedCount

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .statusBarsPadding()
            .padding(
                top = BrainDropTheme.spacing.xxs,
                start = BrainDropTheme.spacing.xs,
                end = BrainDropTheme.spacing.xs,
            ).padding(bottom = BrainDropTheme.spacing.sm),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = BrainDropTheme.spacing.xs),
        ) {
            BrainDropIconButton(onClick = onBack, contentDescription = stringResource(Res.string.cd_back)) {
                BrainDropIcons.ChevronLeft(iconSize = 22.dp, tint = MaterialTheme.colorScheme.onSurface)
            }
            Text(
                text = stringResource(Res.string.tenses_list_title),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f),
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 14.dp, end = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.Top,
        ) {
            TenseMatrixMap(
                tenses = state.tenses,
                selectedTime = state.selectedTime,
                onTimeSelected = onTimeSelected,
            )
            Column(modifier = Modifier.padding(top = 2.dp)) {
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "${state.learnedCount}",
                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 22.sp, lineHeight = 22.sp),
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Spacer(Modifier.width(7.dp))
                    Text(
                        text = "/ $total",
                        style = BrainDropTheme.type.counter,
                        color = BrainDropTheme.semantics.ink400,
                    )
                }
                Spacer(Modifier.height(6.dp))
                Text(
                    text = if (remaining > 0) {
                        pluralStringResource(Res.plurals.tenses_list_remaining, remaining, remaining)
                    } else {
                        stringResource(Res.string.tenses_list_all_learned)
                    },
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.5.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
private fun TimeTabBar(
    selectedTime: TenseTime,
    onTimeSelected: (TenseTime) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 14.dp, bottom = 12.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(14.dp))
            .padding(3.dp),
        horizontalArrangement = Arrangement.spacedBy(3.dp),
    ) {
        TenseTime.entries.forEach { time ->
            val isActive = time == selectedTime
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(38.dp)
                    .then(
                        if (isActive) {
                            Modifier
                                .shadow(
                                    elevation = 1.dp,
                                    shape = RoundedCornerShape(11.dp),
                                    ambientColor = Color.Black.copy(alpha = 0.12f),
                                    spotColor = Color.Black.copy(alpha = 0.12f),
                                ).background(MaterialTheme.colorScheme.surface, RoundedCornerShape(11.dp))
                        } else {
                            Modifier
                        },
                    ).clickable(onClick = { onTimeSelected(time) }),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = timeLabel(time),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 13.5.sp,
                        fontWeight = if (isActive) FontWeight.ExtraBold else FontWeight.SemiBold,
                    ),
                    color = if (isActive) {
                        MaterialTheme.colorScheme.onSurface
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    },
                )
            }
        }
    }
}

@Composable
private fun TenseRow(
    item: TenseWithProgress,
    onClick: () -> Unit,
) {
    val aspectName = item.tense.aspect.name
    val stripeColor = BrainDropTheme.semantics.aspectColor(aspectName)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, BrainDropTheme.shapes.lg)
            .clickable(onClick = onClick),
    ) {
        Box(modifier = Modifier.width(4.dp).fillMaxHeight().background(stripeColor))
        Column(modifier = Modifier.padding(top = 13.dp, start = 12.dp, end = 14.dp, bottom = 13.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(9.dp)) {
                Text(
                    text = item.tense.titleEn,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontSize = 17.sp,
                        fontWeight = FontWeight.ExtraBold,
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f),
                )
                Box(
                    modifier = Modifier
                        .height(20.dp)
                        .background(BrainDropTheme.semantics.aspectSurface(aspectName), RoundedCornerShape(10.dp))
                        .padding(horizontal = BrainDropTheme.spacing.xs),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = aspectAccent(item.tense.aspect).label,
                        style = BrainDropTheme.type.label.copy(fontSize = 9.5.sp, letterSpacing = 0.7.sp),
                        color = BrainDropTheme.semantics.aspectInk(aspectName),
                    )
                }
                if (item.progress.isLearned) {
                    Box(
                        modifier = Modifier.size(24.dp).background(BrainDropTheme.semantics.correct, CircleShape),
                        contentAlignment = Alignment.Center,
                    ) {
                        BrainDropIcons.Check(iconSize = 13.dp, tint = Color.White, strokeWidth = 3.dp)
                    }
                }
            }
            Text(
                text = item.tense.formulas.affirmative,
                style = BrainDropTheme.type.verbForms.copy(fontSize = 14.sp, fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 8.dp),
            )
            Row(modifier = Modifier.padding(top = 7.dp).height(IntrinsicSize.Min)) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(2.dp)
                        .background(BrainDropTheme.semantics.aspectSurface(aspectName)),
                )
                Text(
                    text = item.tense.titleRu,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(start = 9.dp),
                )
            }
        }
    }
}

@Composable
private fun ConfusedWithRow(
    count: Int,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant, BrainDropTheme.shapes.lg)
            .clickable(onClick = onClick)
            .padding(horizontal = BrainDropTheme.spacing.md, vertical = BrainDropTheme.spacing.sm + 2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.sm),
    ) {
        BrainDropIcons.Compare(iconSize = 20.dp, tint = MaterialTheme.colorScheme.onSurfaceVariant)
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = stringResource(Res.string.tenses_list_confused_title),
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 14.5.sp, fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = pluralStringResource(Res.plurals.tenses_list_confused_subtitle, count, count),
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                color = BrainDropTheme.semantics.ink400,
                modifier = Modifier.padding(top = 2.dp),
            )
        }
        BrainDropIcons.ChevronRight(iconSize = 17.dp, tint = BrainDropTheme.semantics.answerIdleOutline)
    }
}

@Composable
private fun BottomActionPanel(
    onCheatSheet: () -> Unit,
    onTrain: () -> Unit,
) {
    val background = MaterialTheme.colorScheme.background
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Brush.verticalGradient(listOf(Color.Transparent, background)))
            .padding(top = 14.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(background)
                .navigationBarsPadding()
                .padding(start = 20.dp, end = 20.dp, bottom = 26.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .border(1.5.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(15.dp))
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(15.dp))
                    .clickable(
                        onClickLabel = stringResource(Res.string.tenses_list_cheatsheet_button),
                        onClick = onCheatSheet,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                BrainDropIcons.List(iconSize = 20.dp, tint = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            BrainDropButton(
                text = stringResource(Res.string.tenses_list_train_button),
                onClick = onTrain,
                height = 48.dp,
                cornerRadius = 15.dp,
                fillWidth = true,
                modifier = Modifier.weight(1f),
                trailingIcon = {
                    BrainDropIcons.ChevronRight(iconSize = 15.dp, tint = Color.White, strokeWidth = 2.4.dp)
                },
            )
        }
    }
}

@Composable
private fun timeLabel(time: TenseTime): String =
    when (time) {
        TenseTime.PRESENT -> stringResource(Res.string.tenses_list_time_present)
        TenseTime.PAST -> stringResource(Res.string.tenses_list_time_past)
        TenseTime.FUTURE -> stringResource(Res.string.tenses_list_time_future)
    }
