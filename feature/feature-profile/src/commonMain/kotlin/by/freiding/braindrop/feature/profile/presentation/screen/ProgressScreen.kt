package by.freiding.braindrop.feature.profile.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import by.freiding.braindrop.core.ui.BrainDropTheme
import by.freiding.braindrop.core.ui.component.BrainDropLoadingIndicator
import by.freiding.braindrop.core.ui.component.ErrorStatusCard
import by.freiding.braindrop.core.ui.component.SegmentedProgressBar
import by.freiding.braindrop.core.ui.component.brainDropCard
import by.freiding.braindrop.core.ui.icon.BrainDropIcons
import by.freiding.braindrop.feature.profile.Res
import by.freiding.braindrop.feature.profile.domain.model.CategoryProgressData
import by.freiding.braindrop.feature.profile.domain.model.DayActivity
import by.freiding.braindrop.feature.profile.domain.model.ProgressData
import by.freiding.braindrop.feature.profile.presentation.viewmodel.ProgressUiEvent
import by.freiding.braindrop.feature.profile.presentation.viewmodel.ProgressViewModel
import by.freiding.braindrop.feature.profile.progress_accuracy_label
import by.freiding.braindrop.feature.profile.progress_category_irregular_verbs
import by.freiding.braindrop.feature.profile.progress_category_phrasal_verbs
import by.freiding.braindrop.feature.profile.progress_category_tenses
import by.freiding.braindrop.feature.profile.progress_error_body
import by.freiding.braindrop.feature.profile.progress_error_retry
import by.freiding.braindrop.feature.profile.progress_error_title
import by.freiding.braindrop.feature.profile.progress_learned_of
import by.freiding.braindrop.feature.profile.progress_no_data
import by.freiding.braindrop.feature.profile.progress_section_activity
import by.freiding.braindrop.feature.profile.progress_section_categories
import by.freiding.braindrop.feature.profile.progress_stat_accuracy
import by.freiding.braindrop.feature.profile.progress_stat_accuracy_pct
import by.freiding.braindrop.feature.profile.progress_stat_learned
import by.freiding.braindrop.feature.profile.progress_stat_streak
import by.freiding.braindrop.feature.profile.progress_stat_streak_days
import by.freiding.braindrop.feature.profile.progress_title
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.math.roundToInt

private val BAR_COLUMN_HEIGHT = 96.dp
private val MAX_BAR_HEIGHT = 60.dp

@Composable
fun ProgressScreen(viewModel: ProgressViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onEvent(ProgressUiEvent.Reload)
    }

    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        ProgressHeader(streakDays = state.data?.streakDays ?: 0)

        when {
            state.isLoading -> BrainDropLoadingIndicator()
            state.error != null -> ErrorStatusCard(
                title = stringResource(Res.string.progress_error_title),
                body = stringResource(Res.string.progress_error_body),
                retryText = stringResource(Res.string.progress_error_retry),
                onRetry = { viewModel.onEvent(ProgressUiEvent.Reload) },
            )
            state.data != null -> ProgressContent(data = state.data!!)
        }
    }
}

@Composable
private fun ProgressHeader(streakDays: Int) {
    val semantics = BrainDropTheme.semantics
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .statusBarsPadding()
            .padding(start = 20.dp, end = 12.dp, top = BrainDropTheme.spacing.sm, bottom = BrainDropTheme.spacing.sm),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(Res.string.progress_title),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f),
        )
        if (streakDays > 0) {
            Row(
                modifier = Modifier
                    .clip(BrainDropTheme.shapes.chip)
                    .background(semantics.streakTint)
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                BrainDropIcons.Flame(iconSize = 14.dp, tint = semantics.streak)
                Text(
                    text = stringResource(Res.string.progress_stat_streak_days, streakDays),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = semantics.streakInk,
                )
            }
        }
    }
}

@Composable
private fun ProgressContent(data: ProgressData) {
    val navBarPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = BrainDropTheme.spacing.md,
            bottom = BrainDropTheme.spacing.md + navBarPadding,
        ),
        verticalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.md),
    ) {
        item { SummaryRow(data) }
        item { WeeklyActivityCard(data.weekActivity) }
        item {
            Text(
                text = stringResource(Res.string.progress_section_categories).uppercase(),
                style = BrainDropTheme.type.label,
                color = BrainDropTheme.semantics.ink400,
                modifier = Modifier.padding(top = BrainDropTheme.spacing.xs),
            )
        }
        items(data.categories, key = { it.id }) { category ->
            CategoryProgressCard(category)
        }
    }
}

@Composable
private fun SummaryRow(data: ProgressData) {
    val primary = MaterialTheme.colorScheme.primary
    val semantics = BrainDropTheme.semantics

    val accuracyText = if (data.hasAnyAttempts) {
        stringResource(Res.string.progress_stat_accuracy_pct, (data.overallAccuracy * 100).roundToInt())
    } else {
        stringResource(Res.string.progress_no_data)
    }
    val accuracyColor = when {
        !data.hasAnyAttempts -> semantics.ink400
        data.overallAccuracy >= 0.80f -> semantics.correct
        data.overallAccuracy >= 0.60f -> primary
        else -> semantics.incorrect
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.sm),
    ) {
        SummaryTile(
            value = "${data.totalLearned}",
            label = stringResource(Res.string.progress_stat_learned),
            valueColor = primary,
            modifier = Modifier.weight(1f),
        )
        SummaryTile(
            value = "${data.streakDays}",
            label = stringResource(Res.string.progress_stat_streak),
            valueColor = if (data.streakDays > 0) semantics.streak else semantics.ink400,
            modifier = Modifier.weight(1f),
        )
        SummaryTile(
            value = accuracyText,
            label = stringResource(Res.string.progress_stat_accuracy),
            valueColor = accuracyColor,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun SummaryTile(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    valueColor: Color = Color.Unspecified,
) {
    Column(
        modifier = modifier
            .brainDropCard(BrainDropTheme.shapes.xl)
            .padding(vertical = BrainDropTheme.spacing.sm, horizontal = BrainDropTheme.spacing.xs),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.xxs),
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.ExtraBold,
            color = valueColor,
        )
        Text(
            text = label,
            style = BrainDropTheme.type.label,
            color = BrainDropTheme.semantics.ink400,
        )
    }
}

@Composable
private fun WeeklyActivityCard(days: List<DayActivity>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .brainDropCard(BrainDropTheme.shapes.xl)
            .padding(horizontal = BrainDropTheme.spacing.md, vertical = BrainDropTheme.spacing.md),
        verticalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.sm),
    ) {
        Text(
            text = stringResource(Res.string.progress_section_activity).uppercase(),
            style = BrainDropTheme.type.label,
            color = BrainDropTheme.semantics.ink400,
        )
        WeeklyBarChart(days)
    }
}

@Composable
private fun WeeklyBarChart(days: List<DayActivity>) {
    val maxCount = days.maxOfOrNull { it.learnedCount }?.coerceAtLeast(1) ?: 1
    val primary = MaterialTheme.colorScheme.primary
    val surfaceVariant = MaterialTheme.colorScheme.surfaceVariant
    val outline = MaterialTheme.colorScheme.outline
    val semantics = BrainDropTheme.semantics

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        days.forEachIndexed { index, day ->
            val isToday = index == days.lastIndex
            val barRatio = day.learnedCount.toFloat() / maxCount
            val barColor = when {
                day.learnedCount > 0 && isToday -> primary
                day.learnedCount > 0 -> primary.copy(alpha = 0.38f)
                isToday -> outline
                else -> surfaceVariant
            }
            val minBarHeight = if (isToday || day.learnedCount > 0) 3.dp else 0.dp
            val barHeight = (MAX_BAR_HEIGHT.value * barRatio).dp.coerceAtLeast(minBarHeight)
            val labelColor = if (isToday) primary else semantics.ink400

            Column(
                modifier = Modifier.height(BAR_COLUMN_HEIGHT).weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(modifier = Modifier.height(16.dp), contentAlignment = Alignment.BottomCenter) {
                    if (day.learnedCount > 0) {
                        Text(
                            text = "${day.learnedCount}",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = labelColor,
                        )
                    }
                }
                Spacer(Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .width(22.dp)
                        .height(barHeight)
                        .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                        .background(barColor),
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = dayOfWeekLabel(day.date),
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = if (isToday) FontWeight.ExtraBold else FontWeight.Normal,
                    color = labelColor,
                )
            }
        }
    }
}

@Composable
private fun CategoryProgressCard(category: CategoryProgressData) {
    val semantics = BrainDropTheme.semantics
    val primary = MaterialTheme.colorScheme.primary
    val learnedRatio = if (category.totalCount > 0) category.learnedCount.toFloat() / category.totalCount else 0f

    val accuracyColor = when {
        !category.hasAttempts -> semantics.ink400
        category.accuracy >= 0.80f -> semantics.correct
        category.accuracy >= 0.60f -> primary
        else -> semantics.incorrect
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .brainDropCard(BrainDropTheme.shapes.xl)
            .padding(horizontal = BrainDropTheme.spacing.md, vertical = BrainDropTheme.spacing.sm),
        verticalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.xs),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = categoryName(category.id),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f),
            )
            Text(
                text = stringResource(Res.string.progress_learned_of, category.learnedCount, category.totalCount),
                style = BrainDropTheme.type.counter,
                color = semantics.ink400,
            )
        }

        SegmentedProgressBar(
            segmentCount = PROGRESS_BAR_SEGMENTS,
            ratio = learnedRatio,
            height = 7.dp,
            gap = 3.dp,
            cornerRadius = 3.dp,
            filledColor = primary,
            emptyColor = MaterialTheme.colorScheme.surfaceVariant,
        )

        Text(
            text = if (category.hasAttempts) {
                stringResource(Res.string.progress_accuracy_label, (category.accuracy * 100).roundToInt())
            } else {
                stringResource(Res.string.progress_no_data)
            },
            style = MaterialTheme.typography.bodySmall,
            fontWeight = if (category.hasAttempts) FontWeight.SemiBold else FontWeight.Normal,
            color = accuracyColor,
        )
    }
}

@Composable
private fun categoryName(id: String): String =
    when (id) {
        "irregular_verbs" -> stringResource(Res.string.progress_category_irregular_verbs)
        "tenses" -> stringResource(Res.string.progress_category_tenses)
        "phrasal_verbs" -> stringResource(Res.string.progress_category_phrasal_verbs)
        else -> id
    }

private fun dayOfWeekLabel(dateIso: String): String =
    when (LocalDate.parse(dateIso).dayOfWeek) {
        DayOfWeek.MONDAY -> "ПН"
        DayOfWeek.TUESDAY -> "ВТ"
        DayOfWeek.WEDNESDAY -> "СР"
        DayOfWeek.THURSDAY -> "ЧТ"
        DayOfWeek.FRIDAY -> "ПТ"
        DayOfWeek.SATURDAY -> "СБ"
        DayOfWeek.SUNDAY -> "ВС"
        else -> "?"
    }

private const val PROGRESS_BAR_SEGMENTS = 12
