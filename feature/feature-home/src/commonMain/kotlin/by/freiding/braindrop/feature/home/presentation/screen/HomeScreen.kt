package by.freiding.braindrop.feature.home.presentation.screen

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import by.freiding.braindrop.core.navigation.Routes
import by.freiding.braindrop.core.ui.BrainDropTheme
import by.freiding.braindrop.core.ui.component.ErrorStatusCard
import by.freiding.braindrop.core.ui.component.SegmentedProgressBar
import by.freiding.braindrop.core.ui.icon.BrainDropIcons
import by.freiding.braindrop.feature.home.Res
import by.freiding.braindrop.feature.home.app_name
import by.freiding.braindrop.feature.home.braindrop_lockup
import by.freiding.braindrop.feature.home.home_categories_label
import by.freiding.braindrop.feature.home.home_continue_quiz
import by.freiding.braindrop.feature.home.home_daily_goal_label
import by.freiding.braindrop.feature.home.home_error_generic
import by.freiding.braindrop.feature.home.home_error_retry
import by.freiding.braindrop.feature.home.home_headline_done
import by.freiding.braindrop.feature.home.home_headline_progress
import by.freiding.braindrop.feature.home.home_streak_days
import by.freiding.braindrop.feature.home.home_subtitle
import by.freiding.braindrop.feature.home.presentation.components.StudyCategoryCard
import by.freiding.braindrop.feature.home.presentation.viewmodel.HomeUiEffect
import by.freiding.braindrop.feature.home.presentation.viewmodel.HomeUiEvent
import by.freiding.braindrop.feature.home.presentation.viewmodel.HomeUiState
import by.freiding.braindrop.feature.home.presentation.viewmodel.HomeViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LifecycleResumeEffect(Unit) {
        viewModel.reload()
        onPauseOrDispose { }
    }

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is HomeUiEffect.NavigateToCategory -> when (effect.categoryId) {
                    "irregular_verbs" -> navController.navigate(Routes.IrregularVerbsList)
                    "tenses" -> navController.navigate(Routes.TensesList)
                    "phrasal_verbs" -> navController.navigate(Routes.PhrasalVerbsList)
                    else -> Unit
                }
                is HomeUiEffect.ContinueQuiz -> navController.navigate(Routes.IrregularVerbsQuiz())
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        when {
            state.error != null -> ErrorStatusCard(
                title = stringResource(Res.string.home_error_generic),
                body = state.error.orEmpty(),
                retryText = stringResource(Res.string.home_error_retry),
                onRetry = { viewModel.reload() },
            )

            else -> LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = BrainDropTheme.spacing.lg),
            ) {
                item { HomeHeader(streakDays = state.streakDays) }
                item { HomeHeadline(state) }
                item { DailyGoalCard(state, onContinueQuiz = { viewModel.onEvent(HomeUiEvent.ContinueQuizClicked) }) }
                item {
                    Text(
                        text = stringResource(Res.string.home_categories_label),
                        style = BrainDropTheme.type.label,
                        color = BrainDropTheme.semantics.ink400,
                        modifier = Modifier.padding(horizontal = 20.dp).padding(bottom = BrainDropTheme.spacing.sm),
                    )
                }
                items(state.categories, key = { it.id }) { category ->
                    StudyCategoryCard(
                        category = category,
                        onClick = { viewModel.onEvent(HomeUiEvent.CategoryClicked(category.id)) },
                        modifier = Modifier.padding(horizontal = 20.dp).padding(bottom = BrainDropTheme.spacing.sm),
                    )
                }
            }
        }
    }
}

@Composable
private fun HomeHeader(streakDays: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(top = BrainDropTheme.spacing.xs, start = 20.dp, end = 20.dp, bottom = BrainDropTheme.spacing.xxs),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(Res.drawable.braindrop_lockup),
            contentDescription = stringResource(Res.string.app_name),
            modifier = Modifier.height(48.dp).width(160.dp),
        )
        if (streakDays > 0) {
            Row(
                modifier = Modifier
                    .height(32.dp)
                    .background(BrainDropTheme.semantics.streakTint, BrainDropTheme.shapes.button)
                    .padding(horizontal = BrainDropTheme.spacing.sm),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                BrainDropIcons.Flame(iconSize = 15.dp, tint = BrainDropTheme.semantics.streak)
                Text(
                    text = pluralStringResource(Res.plurals.home_streak_days, streakDays, streakDays),
                    style = MaterialTheme.typography.labelLarge,
                    color = BrainDropTheme.semantics.streakInk,
                )
            }
        }
    }
}

@Composable
private fun HomeHeadline(state: HomeUiState) {
    val remaining = (state.dailyGoal - state.dailyDone).coerceAtLeast(0)
    Column(
        modifier = Modifier.fillMaxWidth().padding(
            top = 18.dp,
            start = 20.dp,
            end = 20.dp,
            bottom = BrainDropTheme.spacing.md,
        ),
    ) {
        Text(
            text = if (remaining > 0) {
                pluralStringResource(Res.plurals.home_headline_progress, remaining, remaining)
            } else {
                stringResource(Res.string.home_headline_done)
            },
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = stringResource(Res.string.home_subtitle, state.dailyDone, state.dailyGoal),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun DailyGoalCard(
    state: HomeUiState,
    onContinueQuiz: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 18.dp)
            .background(MaterialTheme.colorScheme.primary, BrainDropTheme.shapes.card)
            .padding(top = 18.dp, start = 18.dp, end = 18.dp, bottom = BrainDropTheme.spacing.md),
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = stringResource(Res.string.home_daily_goal_label),
                style = BrainDropTheme.type.label,
                color = Color.White.copy(alpha = 0.72f),
            )
            Text(
                text = "${state.dailyDone} / ${state.dailyGoal}",
                style = MaterialTheme.typography.labelLarge,
                color = Color.White,
            )
        }
        Spacer(Modifier.height(BrainDropTheme.spacing.sm))
        SegmentedProgressBar(
            segmentCount = state.dailyGoal.coerceAtLeast(1),
            ratio = if (state.dailyGoal > 0) state.dailyDone.toFloat() / state.dailyGoal else 0f,
            height = 8.dp,
            gap = 4.dp,
            cornerRadius = 4.dp,
            filledColor = Color.White,
            emptyColor = Color.White.copy(alpha = 0.28f),
            partialColor = Color.White,
        )
        Spacer(Modifier.height(14.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp)
                .clip(BrainDropTheme.shapes.md)
                .background(Color.White, BrainDropTheme.shapes.md)
                .clickable(onClick = onContinueQuiz),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(Res.string.home_continue_quiz),
                style = BrainDropTheme.type.button.copy(fontSize = 15.sp),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
            Spacer(Modifier.width(6.dp))
            BrainDropIcons.ChevronRight(iconSize = 16.dp, tint = MaterialTheme.colorScheme.onPrimaryContainer)
        }
    }
}
