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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import org.koin.core.parameter.parametersOf

@Composable
fun TenseComparisonsScreen(
    navController: NavController,
    initialComparisonId: String? = null,
    viewModel: TenseComparisonsViewModel = koinViewModel(parameters = { parametersOf(initialComparisonId) }),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is TenseComparisonsUiEffect.NavigateBack -> navController.popBackStack()
            }
        }
    }

    LaunchedEffect(state.scrollToId, state.comparisons) {
        val targetId = state.scrollToId ?: return@LaunchedEffect
        val index = state.comparisons.indexOfFirst { it.id == targetId }
        if (index >= 0) listState.animateScrollToItem(index)
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
                    state = listState,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = BrainDropTheme.spacing.sm),
                    verticalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.sm),
                ) {
                    items(state.comparisons, key = { it.id }) { comparison ->
                        ComparisonCard(
                            comparison = comparison,
                            tenseA = state.tensesById[comparison.tenseIdA],
                            tenseB = state.tensesById[comparison.tenseIdB],
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
    tenseA: Tense?,
    tenseB: Tense?,
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
        Row(verticalAlignment = Alignment.Top) {
            ComparisonTitle(
                titleA = formatTenseId(comparison.tenseIdA),
                titleB = formatTenseId(comparison.tenseIdB),
                modifier = Modifier.weight(1f),
            )
            if (expanded) {
                BrainDropIcons.ChevronUp(
                    iconSize = 16.dp,
                    tint = BrainDropTheme.semantics.ink400,
                    modifier = Modifier.padding(start = BrainDropTheme.spacing.xs, top = 3.dp),
                )
            } else {
                BrainDropIcons.ChevronDown(
                    iconSize = 16.dp,
                    tint = BrainDropTheme.semantics.ink400,
                    modifier = Modifier.padding(start = BrainDropTheme.spacing.xs, top = 3.dp),
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
            Spacer(Modifier.height(14.dp))
            ComparisonSide(
                title = formatTenseId(comparison.tenseIdA),
                points = comparison.pointsA,
                example = comparison.exampleA,
                aspectName = tenseA?.aspect?.name,
            )
            Spacer(Modifier.height(BrainDropTheme.spacing.xs))
            ComparisonSide(
                title = formatTenseId(comparison.tenseIdB),
                points = comparison.pointsB,
                example = comparison.exampleB,
                aspectName = tenseB?.aspect?.name,
            )
        }
    }
}

@Composable
private fun ComparisonTitle(
    titleA: String,
    titleB: String,
    modifier: Modifier = Modifier,
) {
    val ink900 = MaterialTheme.colorScheme.onSurface
    val ink400 = BrainDropTheme.semantics.ink400
    val monoFamily = BrainDropTheme.type.label.fontFamily
    val vs = stringResource(Res.string.tense_comparisons_vs)
    val annotated = buildAnnotatedString {
        withStyle(SpanStyle(color = ink900, fontWeight = FontWeight.ExtraBold)) { append(titleA) }
        withStyle(
            SpanStyle(
                color = ink400,
                fontWeight = FontWeight.SemiBold,
                fontFamily = monoFamily,
                fontSize = 11.sp,
                letterSpacing = 0.88.sp,
            ),
        ) { append("  $vs  ") }
        withStyle(SpanStyle(color = ink900, fontWeight = FontWeight.ExtraBold)) { append(titleB) }
    }
    Text(
        text = annotated,
        style = MaterialTheme.typography.titleSmall.copy(fontSize = 15.5.sp, lineHeight = 20.9.sp),
        modifier = modifier,
    )
}

@Composable
private fun ComparisonSide(
    title: String,
    points: List<String>,
    example: TenseExample,
    aspectName: String?,
) {
    val semantics = BrainDropTheme.semantics
    val background = aspectName?.let { semantics.aspectSurface(it) } ?: MaterialTheme.colorScheme.surfaceVariant
    val accent = aspectName?.let { semantics.aspectColor(it) } ?: BrainDropTheme.semantics.ink400
    val ink = aspectName?.let { semantics.aspectInk(it) } ?: MaterialTheme.colorScheme.onSurfaceVariant
    val mutedInk = aspectName?.let { semantics.aspectMutedInk(it) } ?: BrainDropTheme.semantics.ink500
    val shape = RoundedCornerShape(14.dp)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(background, shape)
            .drawBehind { drawRect(color = accent, size = size.copy(width = 3.dp.toPx())) }
            .padding(start = 14.dp, top = 12.dp, end = 14.dp, bottom = 12.dp),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall.copy(fontSize = 13.sp, fontWeight = FontWeight.ExtraBold),
            color = ink,
        )
        Spacer(Modifier.height(8.dp))
        points.forEach { point ->
            BulletPoint(text = point, dotColor = accent, textColor = mutedInk)
        }
        Spacer(Modifier.height(9.dp))
        Text(
            text = example.english,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.5.sp, fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            text = example.russian,
            style = BrainDropTheme.type.translation.copy(fontSize = 12.5.sp),
            color = mutedInk,
        )
    }
}

@Composable
private fun BulletPoint(
    text: String,
    dotColor: Color,
    textColor: Color,
) {
    Row(modifier = Modifier.padding(top = 4.dp)) {
        Box(
            modifier = Modifier
                .padding(top = 6.dp)
                .size(4.dp)
                .background(dotColor, CircleShape),
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.5.sp, fontWeight = FontWeight.Medium),
            color = textColor,
            modifier = Modifier.weight(1f),
        )
    }
}
