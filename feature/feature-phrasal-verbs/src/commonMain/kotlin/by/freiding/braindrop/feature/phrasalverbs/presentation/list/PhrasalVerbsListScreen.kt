package by.freiding.braindrop.feature.phrasalverbs.presentation.list

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import by.freiding.braindrop.core.navigation.Routes
import by.freiding.braindrop.core.ui.BrainDropTheme
import by.freiding.braindrop.core.ui.component.BrainDropIconButton
import by.freiding.braindrop.core.ui.component.ErrorStatusCard
import by.freiding.braindrop.core.ui.component.SegmentedProgressBar
import by.freiding.braindrop.core.ui.icon.BrainDropIcons
import by.freiding.braindrop.feature.phrasalverbs.Res
import by.freiding.braindrop.feature.phrasalverbs.domain.model.PhrasalVerbCategory
import by.freiding.braindrop.feature.phrasalverbs.domain.model.PhrasalVerbQuizType
import by.freiding.braindrop.feature.phrasalverbs.domain.model.PhrasalVerbWithProgress
import by.freiding.braindrop.feature.phrasalverbs.swipe_mark_learned
import by.freiding.braindrop.feature.phrasalverbs.swipe_unmark_learned
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
fun PhrasalVerbsListScreen(
    navController: NavController,
    viewModel: PhrasalVerbsListViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        viewModel.reload()
        viewModel.effects.collect { effect ->
            when (effect) {
                is PhrasalVerbsListUiEffect.NavigateToDetail ->
                    navController.navigate(Routes.PhrasalVerbDetail(effect.verbId))
                is PhrasalVerbsListUiEffect.NavigateToQuiz ->
                    navController.navigate(Routes.PhrasalVerbsQuiz(effect.mode))
                is PhrasalVerbsListUiEffect.NavigateBack -> navController.popBackStack()
                is PhrasalVerbsListUiEffect.ShowError -> errorMessage = effect.message
            }
        }
    }

    errorMessage?.let { msg ->
        LaunchedEffect(msg) {
            delay(3000)
            errorMessage = null
        }
    }

    var searchInput by rememberSaveable { mutableStateOf(state.searchQuery) }
    LaunchedEffect(searchInput) {
        if (searchInput == state.searchQuery) return@LaunchedEffect
        delay(200)
        viewModel.onEvent(PhrasalVerbsListUiEvent.SearchChanged(searchInput))
    }

    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        PhrasalVerbsListHeader(
            state = state,
            searchInput = searchInput,
            onSearchInputChange = { searchInput = it },
            onBack = { viewModel.onEvent(PhrasalVerbsListUiEvent.NavigateBack) },
            onToggleUnlearned = { viewModel.onEvent(PhrasalVerbsListUiEvent.ToggleFilter) },
            onCategorySelected = { cat -> viewModel.onEvent(PhrasalVerbsListUiEvent.CategorySelected(cat)) },
            onStartQuiz = { mode -> viewModel.onEvent(PhrasalVerbsListUiEvent.StartQuiz(mode)) },
        )

        errorMessage?.let { msg ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.errorContainer)
                    .padding(horizontal = BrainDropTheme.spacing.md, vertical = BrainDropTheme.spacing.xs),
            ) {
                Text(text = msg, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onErrorContainer)
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            when {
                state.error != null -> ErrorStatusCard(
                    title = "Не удалось загрузить глаголы",
                    body = "Данные хранятся на устройстве — попробуйте ещё раз.",
                    retryText = "Повторить",
                    onRetry = { viewModel.reload() },
                    secondaryText = "Назад",
                    onSecondary = { viewModel.onEvent(PhrasalVerbsListUiEvent.NavigateBack) },
                )
                state.isLoading -> PhrasalVerbsListSkeleton()
                state.displayedVerbs.isEmpty() -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(32.dp)) {
                        BrainDropIcons.Check(iconSize = 32.dp, tint = BrainDropTheme.semantics.correct)
                        Spacer(Modifier.height(12.dp))
                        Text(
                            text = "Ничего не найдено",
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                }
                else -> PhrasalVerbsListContent(
                    verbs = state.displayedVerbs,
                    onVerbClick = { id -> viewModel.onEvent(PhrasalVerbsListUiEvent.VerbClicked(id)) },
                    onToggleLearned = { id -> viewModel.onEvent(PhrasalVerbsListUiEvent.ToggleLearned(id)) },
                )
            }
        }
    }
}

@Composable
private fun PhrasalVerbsListHeader(
    state: PhrasalVerbsListUiState,
    searchInput: String,
    onSearchInputChange: (String) -> Unit,
    onBack: () -> Unit,
    onToggleUnlearned: () -> Unit,
    onCategorySelected: (PhrasalVerbCategory?) -> Unit,
    onStartQuiz: (String) -> Unit,
) {
    var quizMenuExpanded by remember { mutableStateOf(false) }
    val total = state.allVerbs.size
    val ratio = if (total > 0) state.learnedCount.toFloat() / total else 0f
    val percent = (ratio * 100).roundToInt()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .statusBarsPadding(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = BrainDropTheme.spacing.xs, vertical = BrainDropTheme.spacing.xxs),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BrainDropIconButton(onClick = onBack, contentDescription = "Назад") {
                BrainDropIcons.ChevronLeft(iconSize = 22.dp, tint = MaterialTheme.colorScheme.onSurface)
            }
            Text(
                text = "Phrasal Verbs",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.weight(1f).padding(start = BrainDropTheme.spacing.xxs),
            )
            Box {
                Row(
                    modifier = Modifier
                        .height(34.dp)
                        .clip(BrainDropTheme.shapes.lg)
                        .background(MaterialTheme.colorScheme.primary, BrainDropTheme.shapes.lg)
                        .clickable { quizMenuExpanded = true }
                        .padding(horizontal = BrainDropTheme.spacing.md),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Text(text = "Квиз", style = MaterialTheme.typography.labelLarge, color = Color.White)
                    BrainDropIcons.ChevronRight(iconSize = 13.dp, tint = Color.White, strokeWidth = 2.2.dp)
                }
                DropdownMenu(
                    expanded = quizMenuExpanded,
                    onDismissRequest = { quizMenuExpanded = false },
                    shape = BrainDropTheme.shapes.md,
                    containerColor = MaterialTheme.colorScheme.surface,
                ) {
                    PhrasalVerbQuizType.entries.forEach { type ->
                        DropdownMenuItem(
                            text = {
                                Text(type.displayName(), style = MaterialTheme.typography.bodyLarge)
                            },
                            onClick = {
                                quizMenuExpanded = false
                                onStartQuiz(type.name)
                            },
                        )
                    }
                }
            }
        }

        Column(modifier = Modifier.padding(horizontal = BrainDropTheme.spacing.sm)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "${state.learnedCount} из $total изучено",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = "$percent%",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.ExtraBold,
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
                modifier = Modifier.padding(bottom = 12.dp),
            )
            SearchField(
                value = searchInput,
                onValueChange = onSearchInputChange,
                modifier = Modifier.padding(bottom = BrainDropTheme.spacing.sm),
            )
        }

        LazyRow(
            contentPadding = PaddingValues(horizontal = BrainDropTheme.spacing.sm, vertical = 0.dp),
            horizontalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.xs),
            modifier = Modifier.padding(bottom = BrainDropTheme.spacing.sm),
        ) {
            item {
                FilterChip(
                    label = "Неизученные",
                    selected = state.unlearnedOnly,
                    onClick = onToggleUnlearned,
                )
            }
            items(PhrasalVerbCategory.entries) { cat ->
                FilterChip(
                    label = cat.displayName(),
                    selected = state.selectedCategory == cat,
                    onClick = { onCategorySelected(if (state.selectedCategory == cat) null else cat) },
                )
            }
        }

        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant, thickness = 1.dp)
    }
}

@Composable
private fun SearchField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp)
            .background(BrainDropTheme.semantics.searchFieldSurface, BrainDropTheme.shapes.md)
            .padding(horizontal = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.xs),
    ) {
        BrainDropIcons.Search(iconSize = 18.dp, tint = BrainDropTheme.semantics.ink400)
        Box(modifier = Modifier.weight(1f)) {
            if (value.isEmpty()) {
                Text(
                    text = "Глагол или перевод",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.5.sp),
                    color = BrainDropTheme.semantics.ink400,
                )
            }
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface),
                cursorBrush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.primary),
                modifier = Modifier.fillMaxWidth(),
            )
        }
        if (value.isNotEmpty()) {
            Box(
                modifier = Modifier.size(20.dp).clickable { onValueChange("") },
                contentAlignment = Alignment.Center,
            ) {
                BrainDropIcons.Close(iconSize = 14.dp, tint = BrainDropTheme.semantics.ink400)
            }
        }
    }
}

@Composable
private fun FilterChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val bg by animateColorAsState(
        if (selected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
    )
    val borderColor = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
    val contentColor = if (selected) MaterialTheme.colorScheme.onPrimaryContainer else BrainDropTheme.semantics.ink500

    Box(
        modifier = Modifier
            .height(34.dp)
            .clip(BrainDropTheme.shapes.lg)
            .background(bg, BrainDropTheme.shapes.lg)
            .border(1.5.dp, borderColor, BrainDropTheme.shapes.lg)
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
            color = contentColor,
        )
    }
}

@Composable
private fun PhrasalVerbsListContent(
    verbs: List<PhrasalVerbWithProgress>,
    onVerbClick: (String) -> Unit,
    onToggleLearned: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = WindowInsets.navigationBars.asPaddingValues(),
    ) {
        items(verbs, key = { it.verb.id }) { item ->
            SwipeablePhrasalVerbRow(
                item = item,
                onClick = { onVerbClick(item.verb.id) },
                onToggleLearned = { onToggleLearned(item.verb.id) },
            )
        }
    }
}

@Composable
private fun SwipeablePhrasalVerbRow(
    item: PhrasalVerbWithProgress,
    onClick: () -> Unit,
    onToggleLearned: () -> Unit,
) {
    var dragOffsetPx by remember { mutableFloatStateOf(0f) }
    var didExceedThreshold by remember { mutableStateOf(false) }
    val animatedOffset by animateFloatAsState(targetValue = dragOffsetPx, label = "swipe")
    val thresholdPx = with(LocalDensity.current) { 120.dp.toPx() }
    val haptics = LocalHapticFeedback.current
    val semantics = BrainDropTheme.semantics
    val isLearned = item.progress.isLearned
    val isLearnedState = rememberUpdatedState(isLearned)

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .pointerInput(item.verb.id) {
                    detectHorizontalDragGestures(
                        onDragStart = { didExceedThreshold = false },
                        onDragEnd = {
                            dragOffsetPx = 0f
                            if (didExceedThreshold) onToggleLearned()
                        },
                        onDragCancel = { dragOffsetPx = 0f },
                        onHorizontalDrag = { change, delta ->
                            val allowedDelta = if ((delta > 0 && !isLearnedState.value) ||
                                (delta < 0 && isLearnedState.value)
                            ) delta else 0f
                            dragOffsetPx = (dragOffsetPx + allowedDelta).coerceIn(-400f, 400f)
                            val exceeded = abs(dragOffsetPx) > thresholdPx
                            if (exceeded && !didExceedThreshold) {
                                haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                            }
                            didExceedThreshold = exceeded
                            if (allowedDelta != 0f) change.consume()
                        },
                    )
                },
        ) {
            if (abs(animatedOffset) > 1f) {
                val bgColor = if (isLearned) semantics.incorrect else semantics.correct
                val label = if (isLearned) {
                    stringResource(Res.string.swipe_unmark_learned)
                } else {
                    stringResource(Res.string.swipe_mark_learned)
                }
                val icon: @Composable () -> Unit = {
                    if (isLearned) {
                        BrainDropIcons.Undo(iconSize = 19.dp, tint = Color.White)
                    } else {
                        BrainDropIcons.Check(iconSize = 19.dp, tint = Color.White)
                    }
                }
                Row(
                    modifier = Modifier
                        .matchParentSize()
                        .background(bgColor)
                        .padding(horizontal = BrainDropTheme.spacing.lg, vertical = BrainDropTheme.spacing.lg),
                    horizontalArrangement = if (animatedOffset > 0f) {
                        Arrangement.spacedBy(BrainDropTheme.spacing.xs)
                    } else {
                        Arrangement.End
                    },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (animatedOffset > 0f) {
                        icon()
                        Text(text = label, color = Color.White, fontWeight = FontWeight.ExtraBold, style = MaterialTheme.typography.bodyMedium)
                    } else {
                        Text(text = label, color = Color.White, fontWeight = FontWeight.ExtraBold, style = MaterialTheme.typography.bodyMedium)
                        Spacer(Modifier.width(BrainDropTheme.spacing.xs))
                        icon()
                    }
                }
            }
            Box(
                modifier = Modifier
                    .offset { IntOffset(animatedOffset.roundToInt(), 0) }
                    .let { if (abs(animatedOffset) > 1f) it.shadow(6.dp, RectangleShape, ambientColor = Color.Black.copy(alpha = 0.1f), spotColor = Color.Black.copy(alpha = 0.1f)) else it },
            ) {
                PhrasalVerbRow(item = item, onClick = onClick, onToggleLearned = onToggleLearned)
            }
        }
        HorizontalDivider(
            color = MaterialTheme.colorScheme.outlineVariant,
            thickness = 1.dp,
            modifier = Modifier.padding(start = BrainDropTheme.spacing.md),
        )
    }
}

@Composable
private fun PhrasalVerbRow(
    item: PhrasalVerbWithProgress,
    onClick: () -> Unit,
    onToggleLearned: () -> Unit,
) {
    val semantics = BrainDropTheme.semantics
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .clickable(onClick = onClick)
            .padding(start = BrainDropTheme.spacing.md, end = BrainDropTheme.spacing.sm, top = 13.dp, bottom = 13.dp),
        horizontalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.sm),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(width = 4.dp, height = 44.dp)
                .background(categoryColor(item.verb.category, semantics), RoundedCornerShape(2.dp)),
        )
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = item.verb.verb,
                    style = BrainDropTheme.type.verbBase,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = item.verb.particle,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
                    color = MaterialTheme.colorScheme.primary,
                )
                if (!item.verb.isSeparable) {
                    Text(
                        text = "insep.",
                        style = BrainDropTheme.type.label,
                        color = semantics.ink400,
                        modifier = Modifier.padding(bottom = 1.dp),
                    )
                }
            }
            Row(modifier = Modifier.padding(top = 3.dp)) {
                Box(modifier = Modifier.width(2.dp).height(16.dp).background(MaterialTheme.colorScheme.primaryContainer))
                Text(
                    text = item.verb.meanings.first().translation,
                    style = BrainDropTheme.type.translation,
                    color = semantics.ink500,
                    modifier = Modifier.padding(start = 8.dp),
                )
            }
            Text(
                text = item.verb.category.displayName(),
                style = BrainDropTheme.type.label,
                color = semantics.ink400,
                modifier = Modifier.padding(top = 2.dp),
            )
        }
        if (item.progress.isLearned) {
            Box(
                modifier = Modifier
                    .size(26.dp)
                    .background(semantics.correct, BrainDropTheme.shapes.chip)
                    .clickable(onClick = onToggleLearned),
                contentAlignment = Alignment.Center,
            ) {
                BrainDropIcons.Check(iconSize = 14.dp, tint = Color.White, strokeWidth = 2.4.dp)
            }
        }
    }
}

@Composable
private fun PhrasalVerbsListSkeleton() {
    Column(modifier = Modifier.fillMaxSize().padding(top = BrainDropTheme.spacing.sm)) {
        repeat(7) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = BrainDropTheme.spacing.md, vertical = 13.dp),
                horizontalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.sm),
            ) {
                Box(
                    modifier = Modifier
                        .size(width = 4.dp, height = 44.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(2.dp)),
                )
                Column(verticalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.xs)) {
                    Box(
                        modifier = Modifier
                            .size(width = 130.dp, height = 16.dp)
                            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(4.dp)),
                    )
                    Box(
                        modifier = Modifier
                            .size(width = 180.dp, height = 12.dp)
                            .background(MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(4.dp)),
                    )
                }
            }
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant, thickness = 1.dp)
        }
    }
}

private fun categoryColor(category: PhrasalVerbCategory, semantics: by.freiding.braindrop.core.ui.BrainDropSemantics): Color =
    when (category) {
        PhrasalVerbCategory.WORK -> semantics.tenseTimeColor("PRESENT")
        PhrasalVerbCategory.RELATIONSHIPS -> semantics.tenseTimeColor("PAST")
        PhrasalVerbCategory.MOVEMENT -> semantics.tenseTimeColor("FUTURE")
        PhrasalVerbCategory.COMMUNICATION -> semantics.aspectColor("CONTINUOUS")
        PhrasalVerbCategory.CHANGES -> semantics.aspectColor("PERFECT")
        PhrasalVerbCategory.GENERAL -> semantics.ink400
    }
