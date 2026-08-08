package by.freiding.braindrop.feature.irregularverbs.presentation.list

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import by.freiding.braindrop.core.navigation.Routes
import by.freiding.braindrop.core.ui.BrainDropTheme
import by.freiding.braindrop.core.ui.component.BrainDropButton
import by.freiding.braindrop.core.ui.component.BrainDropButtonStyle
import by.freiding.braindrop.core.ui.component.BrainDropIconButton
import by.freiding.braindrop.core.ui.component.ErrorStatusCard
import by.freiding.braindrop.core.ui.component.SegmentedBar
import by.freiding.braindrop.core.ui.component.SegmentedProgressBar
import by.freiding.braindrop.core.ui.component.StatusCard
import by.freiding.braindrop.core.ui.icon.BrainDropIcons
import by.freiding.braindrop.feature.irregularverbs.Res
import by.freiding.braindrop.feature.irregularverbs.cd_back
import by.freiding.braindrop.feature.irregularverbs.cd_search
import by.freiding.braindrop.feature.irregularverbs.error_retry
import by.freiding.braindrop.feature.irregularverbs.domain.model.QuizType
import by.freiding.braindrop.feature.irregularverbs.domain.model.VerbGroup
import by.freiding.braindrop.feature.irregularverbs.domain.model.VerbWithProgress
import by.freiding.braindrop.feature.irregularverbs.presentation.common.familyDescription
import by.freiding.braindrop.feature.irregularverbs.presentation.common.groupHint
import by.freiding.braindrop.feature.irregularverbs.presentation.common.groupTitle
import by.freiding.braindrop.feature.irregularverbs.presentation.common.verbFormsLine
import by.freiding.braindrop.feature.irregularverbs.verb_list_empty_action
import by.freiding.braindrop.feature.irregularverbs.verb_list_empty_body
import by.freiding.braindrop.feature.irregularverbs.verb_list_empty_title
import by.freiding.braindrop.feature.irregularverbs.verb_list_error_body
import by.freiding.braindrop.feature.irregularverbs.verb_list_error_title
import by.freiding.braindrop.feature.irregularverbs.verb_list_filter_groups
import by.freiding.braindrop.feature.irregularverbs.verb_list_filter_unlearned
import by.freiding.braindrop.feature.irregularverbs.verb_list_progress_count
import by.freiding.braindrop.feature.irregularverbs.verb_list_quiz_button
import by.freiding.braindrop.feature.irregularverbs.verb_list_quiz_en_ru
import by.freiding.braindrop.feature.irregularverbs.verb_list_quiz_forms
import by.freiding.braindrop.feature.irregularverbs.verb_list_quiz_ru_en
import by.freiding.braindrop.feature.irregularverbs.verb_list_search_empty_body
import by.freiding.braindrop.feature.irregularverbs.verb_list_search_empty_title
import by.freiding.braindrop.feature.irregularverbs.verb_list_search_placeholder
import by.freiding.braindrop.feature.irregularverbs.verb_list_title
import by.freiding.braindrop.feature.irregularverbs.swipe_mark_learned
import by.freiding.braindrop.feature.irregularverbs.swipe_unmark_learned
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerbListScreen(
    navController: NavController,
    viewModel: VerbListViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.reload()
        viewModel.effects.collect { effect ->
            when (effect) {
                is VerbListUiEffect.NavigateToDetail ->
                    navController.navigate(Routes.IrregularVerbDetail(effect.verbId))
                is VerbListUiEffect.NavigateToQuiz ->
                    navController.navigate(Routes.IrregularVerbsQuiz(effect.mode))
                is VerbListUiEffect.NavigateBack -> navController.popBackStack()
            }
        }
    }

    var searchInput by rememberSaveable { mutableStateOf(state.searchQuery) }
    LaunchedEffect(searchInput) {
        // Skip when it already matches the ViewModel's value — true on first composition
        // (both start at state.searchQuery), so it doesn't send a redundant SearchChanged("")
        // the moment the screen appears.
        if (searchInput == state.searchQuery) return@LaunchedEffect
        delay(200)
        viewModel.onEvent(VerbListUiEvent.SearchChanged(searchInput))
    }

    val searchFocusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        VerbListHeader(
            state = state,
            searchInput = searchInput,
            onSearchInputChange = { searchInput = it },
            searchFocusRequester = searchFocusRequester,
            onSearchIconClick = {
                searchFocusRequester.requestFocus()
                keyboardController?.show()
            },
            onBack = { viewModel.onEvent(VerbListUiEvent.NavigateBack) },
            onToggleUnlearnedFilter = { viewModel.onEvent(VerbListUiEvent.ToggleFilter) },
            onToggleViewMode = { viewModel.onEvent(VerbListUiEvent.ToggleViewMode) },
            onStartQuiz = { mode -> viewModel.onEvent(VerbListUiEvent.StartQuiz(mode)) },
        )

        Box(modifier = Modifier.fillMaxSize()) {
            when {
                state.error != null -> ErrorStatusCard(
                    title = stringResource(Res.string.verb_list_error_title),
                    body = stringResource(Res.string.verb_list_error_body),
                    retryText = stringResource(Res.string.error_retry),
                    onRetry = { viewModel.reload() },
                    secondaryText = stringResource(Res.string.cd_back),
                    onSecondary = { viewModel.onEvent(VerbListUiEvent.NavigateBack) },
                )

                state.isLoading -> VerbListSkeleton()

                state.displayedVerbs.isEmpty() -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    if (state.searchQuery.isNotBlank()) {
                        StatusCard(
                            icon = { BrainDropIcons.Search(iconSize = 26.dp, tint = BrainDropTheme.semantics.ink400) },
                            iconBackground = MaterialTheme.colorScheme.surfaceVariant,
                            title = stringResource(Res.string.verb_list_search_empty_title),
                            body = stringResource(Res.string.verb_list_search_empty_body, state.searchQuery),
                        )
                    } else {
                        StatusCard(
                            icon = { BrainDropIcons.Check(iconSize = 30.dp, tint = BrainDropTheme.semantics.correct) },
                            iconBackground = BrainDropTheme.semantics.correctTint,
                            title = stringResource(Res.string.verb_list_empty_title),
                            body = stringResource(Res.string.verb_list_empty_body),
                            primaryAction = {
                                BrainDropButton(
                                    text = stringResource(Res.string.verb_list_empty_action, state.allVerbs.size),
                                    onClick = { viewModel.onEvent(VerbListUiEvent.ClearFilter) },
                                    style = BrainDropButtonStyle.OUTLINED,
                                )
                            },
                        )
                    }
                }

                else -> VerbListContent(
                    state = state,
                    onVerbClick = { id -> viewModel.onEvent(VerbListUiEvent.VerbClicked(id)) },
                    onToggleLearned = { id -> viewModel.onEvent(VerbListUiEvent.ToggleLearned(id)) },
                )
            }
        }
    }
}

@Composable
private fun VerbListHeader(
    state: VerbListUiState,
    searchInput: String,
    onSearchInputChange: (String) -> Unit,
    searchFocusRequester: FocusRequester,
    onSearchIconClick: () -> Unit,
    onBack: () -> Unit,
    onToggleUnlearnedFilter: () -> Unit,
    onToggleViewMode: () -> Unit,
    onStartQuiz: (String) -> Unit,
) {
    var quizMenuExpanded by remember { mutableStateOf(false) }
    val total = state.allVerbs.size
    val ratio = if (total > 0) state.learnedCount.toFloat() / total else 0f
    val percent = (ratio * 100).roundToInt()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = BrainDropTheme.spacing.xs, vertical = BrainDropTheme.spacing.xxs),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BrainDropIconButton(onClick = onBack, contentDescription = stringResource(Res.string.cd_back)) {
                BrainDropIcons.ChevronLeft(iconSize = 22.dp, tint = MaterialTheme.colorScheme.onSurface)
            }
            Text(
                text = stringResource(Res.string.verb_list_title),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f).padding(start = BrainDropTheme.spacing.xxs),
            )
            BrainDropIconButton(onClick = onSearchIconClick, contentDescription = stringResource(Res.string.cd_search)) {
                BrainDropIcons.Search(iconSize = 20.dp, tint = MaterialTheme.colorScheme.onSurface)
            }
        }

        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = BrainDropTheme.spacing.sm)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = stringResource(Res.string.verb_list_progress_count, state.learnedCount, total),
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
                modifier = Modifier.padding(bottom = 14.dp),
            )
            SearchField(
                value = searchInput,
                onValueChange = onSearchInputChange,
                focusRequester = searchFocusRequester,
                modifier = Modifier.padding(bottom = BrainDropTheme.spacing.sm),
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = BrainDropTheme.spacing.sm),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.xs),
            ) {
                FilterPill(
                    label = stringResource(Res.string.verb_list_filter_unlearned),
                    selected = state.unlearnedOnly,
                    onClick = onToggleUnlearnedFilter,
                )
                FilterPill(
                    label = stringResource(Res.string.verb_list_filter_groups),
                    selected = state.viewMode == ViewMode.GROUPED,
                    onClick = onToggleViewMode,
                    icon = { tint -> BrainDropIcons.List(iconSize = 15.dp, tint = tint) },
                )
                Spacer(Modifier.weight(1f))
                Box {
                    Row(
                        modifier = Modifier
                            .height(36.dp)
                            .clip(BrainDropTheme.shapes.lg)
                            .background(MaterialTheme.colorScheme.primary, BrainDropTheme.shapes.lg)
                            .clickable { quizMenuExpanded = true }
                            .padding(horizontal = BrainDropTheme.spacing.md),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.xxs),
                    ) {
                        Text(
                            text = stringResource(Res.string.verb_list_quiz_button),
                            style = MaterialTheme.typography.labelLarge,
                            color = Color.White,
                        )
                        BrainDropIcons.ChevronRight(iconSize = 13.dp, tint = Color.White, strokeWidth = 2.2.dp)
                    }
                    DropdownMenu(
                        expanded = quizMenuExpanded,
                        onDismissRequest = { quizMenuExpanded = false },
                        shape = BrainDropTheme.shapes.md,
                        containerColor = MaterialTheme.colorScheme.surface,
                    ) {
                        DropdownMenuItem(
                            text = { Text(stringResource(Res.string.verb_list_quiz_en_ru), style = MaterialTheme.typography.bodyLarge) },
                            onClick = { quizMenuExpanded = false; onStartQuiz(QuizType.EN_TO_RU.name) },
                        )
                        DropdownMenuItem(
                            text = { Text(stringResource(Res.string.verb_list_quiz_ru_en), style = MaterialTheme.typography.bodyLarge) },
                            onClick = { quizMenuExpanded = false; onStartQuiz(QuizType.RU_TO_EN.name) },
                        )
                        DropdownMenuItem(
                            text = { Text(stringResource(Res.string.verb_list_quiz_forms), style = MaterialTheme.typography.bodyLarge) },
                            onClick = { quizMenuExpanded = false; onStartQuiz(QuizType.VERB_FORMS.name) },
                        )
                    }
                }
            }
        }
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant, thickness = 1.dp)
    }
}

@Composable
private fun SearchField(
    value: String,
    onValueChange: (String) -> Unit,
    focusRequester: FocusRequester,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(46.dp)
            .background(BrainDropTheme.semantics.searchFieldSurface, BrainDropTheme.shapes.md)
            .padding(horizontal = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.xs),
    ) {
        BrainDropIcons.Search(iconSize = 18.dp, tint = BrainDropTheme.semantics.ink400)
        Box(modifier = Modifier.weight(1f)) {
            if (value.isEmpty()) {
                Text(
                    text = stringResource(Res.string.verb_list_search_placeholder),
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
                modifier = Modifier.fillMaxWidth().focusRequester(focusRequester),
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
private fun FilterPill(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    icon: (@Composable (Color) -> Unit)? = null,
) {
    val bg by animateColorAsState(
        if (selected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
    )
    val borderColor = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
    val content = if (selected) MaterialTheme.colorScheme.onPrimaryContainer else BrainDropTheme.semantics.ink500

    Row(
        modifier = Modifier
            .height(36.dp)
            .clip(BrainDropTheme.shapes.lg)
            .background(bg, BrainDropTheme.shapes.lg)
            .border(1.5.dp, borderColor, BrainDropTheme.shapes.lg)
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Text(text = label, style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold), color = content)
        icon?.invoke(content)
    }
}

@OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
private fun VerbListContent(
    state: VerbListUiState,
    onVerbClick: (String) -> Unit,
    onToggleLearned: (String) -> Unit,
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        if (state.viewMode == ViewMode.LIST) {
            items(state.displayedVerbs, key = { it.verb.id }) { item ->
                SwipeableVerbRow(
                    verb = item,
                    translationAccent = MaterialTheme.colorScheme.primaryContainer,
                    onClick = { onVerbClick(item.verb.id) },
                    onToggleLearned = { onToggleLearned(item.verb.id) },
                )
            }
        } else {
            state.groupedVerbs.forEach { (group, verbs) ->
                stickyHeader(key = "header_${group.name}") {
                    GroupHeader(group = group, total = verbs.size, learned = verbs.count { it.progress.isLearned })
                }
                items(verbs, key = { it.verb.id }) { item ->
                    SwipeableVerbRow(
                        verb = item,
                        translationAccent = BrainDropTheme.semantics.groupSurface(group.name),
                        onClick = { onVerbClick(item.verb.id) },
                        onToggleLearned = { onToggleLearned(item.verb.id) },
                    )
                }
            }
        }
    }
}

@Composable
private fun GroupHeader(group: VerbGroup, total: Int, learned: Int) {
    val semantics = BrainDropTheme.semantics
    val title = groupTitle(group)
    val hint = groupHint(group)
    val familyDescription = familyDescription(group)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(semantics.groupSurface(group.name))
            .padding(start = BrainDropTheme.spacing.md, end = BrainDropTheme.spacing.md, top = 11.dp, bottom = 11.dp),
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.xs)) {
                Text(
                    text = semantics.familyLabel(group.name),
                    style = BrainDropTheme.type.family,
                    color = semantics.familyColor(group.name),
                    modifier = Modifier.semantics { contentDescription = familyDescription },
                )
                Text(text = title, style = MaterialTheme.typography.titleSmall, color = semantics.groupInk(group.name))
            }
            Text(text = "$learned/$total", style = BrainDropTheme.type.counter, color = semantics.familyColor(group.name))
        }
        Spacer(Modifier.height(2.dp))
        Text(text = hint, style = MaterialTheme.typography.bodySmall, color = semantics.groupHintInk(group.name))
    }
}

@Composable
private fun SwipeableVerbRow(
    verb: VerbWithProgress,
    translationAccent: Color,
    onClick: () -> Unit,
    onToggleLearned: () -> Unit,
) {
    var dragOffsetPx by remember { mutableFloatStateOf(0f) }
    var didExceedThreshold by remember { mutableStateOf(false) }
    val animatedOffset by animateFloatAsState(targetValue = dragOffsetPx, label = "swipe")
    val thresholdPx = with(LocalDensity.current) { 120.dp.toPx() }
    val haptics = LocalHapticFeedback.current
    val semantics = BrainDropTheme.semantics
    val isLearned = verb.progress.isLearned
    val isLearnedState = rememberUpdatedState(isLearned)

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .pointerInput(verb.verb.id) {
                    detectHorizontalDragGestures(
                        onDragStart = { didExceedThreshold = false },
                        onDragEnd = {
                            dragOffsetPx = 0f
                            if (didExceedThreshold) onToggleLearned()
                        },
                        onDragCancel = { dragOffsetPx = 0f },
                        onHorizontalDrag = { change, delta ->
                            // Right = mark as learned, left = unmark — each direction only applies in the
                            // matching state, otherwise the row doesn't move. isLearnedState is read via
                            // rememberUpdatedState since this gesture block isn't restarted when learned
                            // status changes (only verb.verb.id is a pointerInput key).
                            val allowedDelta = if ((delta > 0 && !isLearnedState.value) || (delta < 0 && isLearnedState.value)) delta else 0f
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
                val label = if (isLearned) stringResource(Res.string.swipe_unmark_learned) else stringResource(Res.string.swipe_mark_learned)
                val icon: @Composable () -> Unit = {
                    if (isLearned) BrainDropIcons.Undo(iconSize = 19.dp, tint = Color.White) else BrainDropIcons.Check(iconSize = 19.dp, tint = Color.White)
                }
                Row(
                    modifier = Modifier.fillMaxSize().background(bgColor).padding(horizontal = BrainDropTheme.spacing.lg, vertical = BrainDropTheme.spacing.lg),
                    horizontalArrangement = if (animatedOffset > 0f) Arrangement.spacedBy(BrainDropTheme.spacing.xs) else Arrangement.End,
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
                    .let { if (abs(animatedOffset) > 1f) it.shadowSwipe() else it },
            ) {
                VerbRow(verb = verb, translationAccent = translationAccent, onClick = onClick)
            }
        }
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant, thickness = 1.dp, modifier = Modifier.padding(start = BrainDropTheme.spacing.sm))
    }
}

private fun Modifier.shadowSwipe(): Modifier = this.shadow(
    elevation = 6.dp,
    shape = RectangleShape,
    ambientColor = Color.Black.copy(alpha = 0.1f),
    spotColor = Color.Black.copy(alpha = 0.1f),
)

@Composable
private fun VerbRow(verb: VerbWithProgress, translationAccent: Color, onClick: () -> Unit) {
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
                .size(width = 4.dp, height = 40.dp)
                .background(semantics.groupColor(verb.verb.group.name), RoundedCornerShape(2.dp)),
        )
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(9.dp)) {
                Text(text = verb.verb.baseForm, style = BrainDropTheme.type.verbBase, color = MaterialTheme.colorScheme.onSurface)
                Text(
                    text = verbFormsLine(verb.verb),
                    style = BrainDropTheme.type.verbForms,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            Row(modifier = Modifier.padding(top = 2.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(16.dp)
                        .background(translationAccent),
                )
                Text(
                    text = verb.verb.translation,
                    style = BrainDropTheme.type.translation,
                    color = semantics.ink500,
                    modifier = Modifier.padding(start = 9.dp),
                )
            }
        }
        if (verb.progress.isLearned) {
            Box(
                modifier = Modifier.size(26.dp).background(semantics.correct, BrainDropTheme.shapes.chip),
                contentAlignment = Alignment.Center,
            ) {
                BrainDropIcons.Check(iconSize = 14.dp, tint = Color.White, strokeWidth = 2.4.dp)
            }
        }
    }
}

@Composable
private fun VerbListSkeleton() {
    val transition = rememberInfiniteTransition(label = "skeleton")
    val alpha by transition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(900),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "skeletonAlpha",
    )
    val skeletonColor = MaterialTheme.colorScheme.surfaceVariant
    Column(modifier = Modifier.fillMaxSize().alpha(alpha).padding(top = BrainDropTheme.spacing.sm)) {
        SegmentedBar(
            segmentCount = 12,
            height = 7.dp,
            gap = 3.dp,
            cornerRadius = 3.dp,
            modifier = Modifier.padding(horizontal = BrainDropTheme.spacing.sm, vertical = BrainDropTheme.spacing.xs),
        ) { skeletonColor }
        repeat(5) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = BrainDropTheme.spacing.md, vertical = 13.dp),
                horizontalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.sm),
            ) {
                Box(modifier = Modifier.size(width = 4.dp, height = 40.dp).background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(2.dp)))
                Column(verticalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.xs)) {
                    Box(modifier = Modifier.size(width = 120.dp, height = 15.dp).background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(4.dp)))
                    Box(modifier = Modifier.size(width = 180.dp, height = 11.dp).background(MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(4.dp)))
                }
            }
        }
    }
}
