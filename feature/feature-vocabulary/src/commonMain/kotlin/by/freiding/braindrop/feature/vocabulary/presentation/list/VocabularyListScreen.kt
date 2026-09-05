package by.freiding.braindrop.feature.vocabulary.presentation.list

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import by.freiding.braindrop.core.navigation.Routes
import by.freiding.braindrop.core.ui.BrainDropTheme
import by.freiding.braindrop.core.ui.component.ErrorStatusCard
import by.freiding.braindrop.core.ui.component.SegmentedProgressBar
import by.freiding.braindrop.core.ui.icon.BrainDropIcons
import by.freiding.braindrop.feature.vocabulary.Res
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkLevel
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkTheme
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkWithProgress
import by.freiding.braindrop.feature.vocabulary.domain.model.ReviewStatus
import by.freiding.braindrop.feature.vocabulary.domain.model.VocabularyQuizMode
import by.freiding.braindrop.feature.vocabulary.presentation.common.VocabularyHeader
import by.freiding.braindrop.feature.vocabulary.presentation.common.displayName
import by.freiding.braindrop.feature.vocabulary.presentation.common.highlightChunk
import by.freiding.braindrop.feature.vocabulary.presentation.common.metaLine
import by.freiding.braindrop.feature.vocabulary.presentation.common.stripColor
import by.freiding.braindrop.feature.vocabulary.vocab_cd_back
import by.freiding.braindrop.feature.vocabulary.vocab_error_retry
import by.freiding.braindrop.feature.vocabulary.vocab_list_empty
import by.freiding.braindrop.feature.vocabulary.vocab_list_error_body
import by.freiding.braindrop.feature.vocabulary.vocab_list_error_title
import by.freiding.braindrop.feature.vocabulary.vocab_list_filter_due
import by.freiding.braindrop.feature.vocabulary.vocab_list_filter_unlearned
import by.freiding.braindrop.feature.vocabulary.vocab_list_progress
import by.freiding.braindrop.feature.vocabulary.vocab_list_quiz_button
import by.freiding.braindrop.feature.vocabulary.vocab_list_search_hint
import by.freiding.braindrop.feature.vocabulary.vocab_list_session_bar
import by.freiding.braindrop.feature.vocabulary.vocab_list_session_bar_empty
import by.freiding.braindrop.feature.vocabulary.vocab_list_title
import by.freiding.braindrop.feature.vocabulary.vocab_quiz_mode_cloze
import by.freiding.braindrop.feature.vocabulary.vocab_quiz_mode_mixed
import by.freiding.braindrop.feature.vocabulary.vocab_quiz_mode_typing
import by.freiding.braindrop.feature.vocabulary.vocab_status_due_in_days
import by.freiding.braindrop.feature.vocabulary.vocab_status_due_today
import by.freiding.braindrop.feature.vocabulary.vocab_status_new
import kotlinx.coroutines.delay
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.math.roundToInt
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun VocabularyListScreen(
    navController: NavController,
    viewModel: VocabularyListViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.reload()
        viewModel.effects.collect { effect ->
            when (effect) {
                is VocabularyListUiEffect.NavigateToDetail ->
                    navController.navigate(Routes.VocabularyChunkDetail(effect.chunkId))
                is VocabularyListUiEffect.NavigateToCardSession ->
                    navController.navigate(Routes.VocabularyCardSession)
                is VocabularyListUiEffect.NavigateToQuiz ->
                    navController.navigate(Routes.VocabularyQuiz(effect.mode))
                is VocabularyListUiEffect.NavigateBack -> navController.popBackStack()
            }
        }
    }

    var searchInput by rememberSaveable { mutableStateOf(state.searchQuery) }
    LaunchedEffect(searchInput) {
        if (searchInput == state.searchQuery) return@LaunchedEffect
        delay(200.milliseconds)
        viewModel.onEvent(VocabularyListUiEvent.SearchChanged(searchInput))
    }

    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        ListHeader(
            state = state,
            searchInput = searchInput,
            onSearchInputChange = { searchInput = it },
            onBack = { viewModel.onEvent(VocabularyListUiEvent.NavigateBack) },
            onEvent = viewModel::onEvent,
        )

        Box(modifier = Modifier.weight(1f)) {
            when {
                state.error != null -> ErrorStatusCard(
                    title = stringResource(Res.string.vocab_list_error_title),
                    body = stringResource(Res.string.vocab_list_error_body),
                    retryText = stringResource(Res.string.vocab_error_retry),
                    onRetry = { viewModel.reload() },
                    secondaryText = stringResource(Res.string.vocab_cd_back),
                    onSecondary = { viewModel.onEvent(VocabularyListUiEvent.NavigateBack) },
                )
                state.isLoading -> ListSkeleton()
                state.displayedChunks.isEmpty() -> EmptyResult()
                else -> ChunkList(
                    chunks = state.displayedChunks,
                    today = state.today,
                    onClick = { id -> viewModel.onEvent(VocabularyListUiEvent.ChunkClicked(id)) },
                )
            }
        }

        BottomSessionBar(
            dueCount = state.dueCount,
            onStart = { viewModel.onEvent(VocabularyListUiEvent.StartCardSession) },
        )
    }
}

@Composable
private fun ListHeader(
    state: VocabularyListUiState,
    searchInput: String,
    onSearchInputChange: (String) -> Unit,
    onBack: () -> Unit,
    onEvent: (VocabularyListUiEvent) -> Unit,
) {
    val total = state.totalCount
    val ratio = if (total > 0) state.learnedCount.toFloat() / total else 0f
    val percent = (ratio * 100).roundToInt()
    var quizMenuOpen by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface)) {
        VocabularyHeader(
            title = stringResource(Res.string.vocab_list_title),
            onBack = onBack,
            divider = false,
            trailing = {
                Box {
                    Row(
                        modifier = Modifier
                            .height(34.dp)
                            .clip(BrainDropTheme.shapes.lg)
                            .background(MaterialTheme.colorScheme.primary, BrainDropTheme.shapes.lg)
                            .clickable { quizMenuOpen = true }
                            .padding(horizontal = BrainDropTheme.spacing.md),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Text(
                            text = stringResource(Res.string.vocab_list_quiz_button),
                            style = MaterialTheme.typography.labelLarge,
                            color = Color.White,
                        )
                        BrainDropIcons.ChevronRight(iconSize = 13.dp, tint = Color.White, strokeWidth = 2.2.dp)
                    }
                    DropdownMenu(
                        expanded = quizMenuOpen,
                        onDismissRequest = { quizMenuOpen = false },
                        shape = BrainDropTheme.shapes.md,
                        containerColor = MaterialTheme.colorScheme.surface,
                    ) {
                        VocabularyQuizMode.entries.forEach { mode ->
                            DropdownMenuItem(
                                text = { Text(mode.label(), style = MaterialTheme.typography.bodyLarge) },
                                onClick = {
                                    quizMenuOpen = false
                                    onEvent(VocabularyListUiEvent.StartQuiz(mode.name))
                                },
                            )
                        }
                    }
                }
            },
        )

        Column(modifier = Modifier.padding(horizontal = BrainDropTheme.spacing.sm)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = stringResource(Res.string.vocab_list_progress, state.learnedCount, total),
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
            contentPadding = PaddingValues(horizontal = BrainDropTheme.spacing.sm),
            horizontalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.xs),
            modifier = Modifier.padding(bottom = BrainDropTheme.spacing.sm),
        ) {
            item {
                FilterChip(
                    label = stringResource(Res.string.vocab_list_filter_due, state.dueCount),
                    selected = state.dueOnly,
                    accent = true,
                    onClick = { onEvent(VocabularyListUiEvent.ToggleDueOnly) },
                )
            }
            item {
                FilterChip(
                    label = stringResource(Res.string.vocab_list_filter_unlearned),
                    selected = state.unlearnedOnly,
                    onClick = { onEvent(VocabularyListUiEvent.ToggleUnlearnedOnly) },
                )
            }
            items(ChunkTheme.entries) { theme ->
                FilterChip(
                    label = theme.displayName(),
                    selected = state.selectedTheme == theme,
                    onClick = {
                        val next = if (state.selectedTheme == theme) null else theme
                        onEvent(VocabularyListUiEvent.ThemeSelected(next))
                    },
                )
            }
            items(ChunkLevel.entries) { level ->
                FilterChip(
                    label = level.displayName(),
                    selected = state.selectedLevel == level,
                    onClick = {
                        val next = if (state.selectedLevel == level) null else level
                        onEvent(VocabularyListUiEvent.LevelSelected(next))
                    },
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
                    text = stringResource(Res.string.vocab_list_search_hint),
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.5.sp),
                    color = BrainDropTheme.semantics.ink400,
                )
            }
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
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
    accent: Boolean = false,
) {
    val semantics = BrainDropTheme.semantics
    val selectedBg = if (accent) semantics.streakTint else MaterialTheme.colorScheme.primaryContainer
    val selectedBorder = if (accent) semantics.streak else MaterialTheme.colorScheme.primary
    val selectedInk = if (accent) semantics.streakInk else MaterialTheme.colorScheme.onPrimaryContainer
    val bg by animateColorAsState(if (selected) selectedBg else MaterialTheme.colorScheme.surface)
    val borderColor = if (selected) selectedBorder else MaterialTheme.colorScheme.outline
    val contentColor = if (selected) selectedInk else semantics.ink500

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
private fun ChunkList(
    chunks: List<ChunkWithProgress>,
    today: LocalDate,
    onClick: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = WindowInsets.navigationBars.asPaddingValues(),
    ) {
        items(chunks, key = { it.chunk.id }) { item ->
            ChunkRow(item = item, today = today, onClick = { onClick(item.chunk.id) })
            HorizontalDivider(
                color = MaterialTheme.colorScheme.outlineVariant,
                thickness = 1.dp,
                modifier = Modifier.padding(start = BrainDropTheme.spacing.md),
            )
        }
    }
}

@Composable
private fun ChunkRow(
    item: ChunkWithProgress,
    today: LocalDate,
    onClick: () -> Unit,
) {
    val semantics = BrainDropTheme.semantics
    val chunk = item.chunk
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
                .size(width = 4.dp, height = 52.dp)
                .background(chunk.theme.stripColor(semantics), RoundedCornerShape(2.dp)),
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = highlightChunk(
                    sentence = chunk.text,
                    chunk = chunk.headword,
                    headword = chunk.headword,
                    highlightColor = MaterialTheme.colorScheme.primary,
                ),
                style = BrainDropTheme.type.verbBase,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Row(modifier = Modifier.padding(top = 3.dp)) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(18.dp)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                )
                Text(
                    text = chunk.translation,
                    style = BrainDropTheme.type.translation,
                    color = semantics.ink500,
                    modifier = Modifier.padding(start = 8.dp),
                )
            }
            Text(
                text = chunk.metaLine(),
                style = BrainDropTheme.type.label,
                color = semantics.ink400,
                modifier = Modifier.padding(top = 3.dp),
            )
        }
        ReviewStatusBadge(status = item.status(today))
    }
}

@Composable
private fun ReviewStatusBadge(status: ReviewStatus) {
    val semantics = BrainDropTheme.semantics
    when (status) {
        ReviewStatus.Learned -> Box(
            modifier = Modifier.size(26.dp).background(semantics.correct, BrainDropTheme.shapes.chip),
            contentAlignment = Alignment.Center,
        ) {
            BrainDropIcons.Check(iconSize = 14.dp, tint = Color.White, strokeWidth = 2.4.dp)
        }
        ReviewStatus.DueToday -> StatusChip(
            stringResource(Res.string.vocab_status_due_today),
            semantics.streakTint,
            semantics.streakInk,
        )
        is ReviewStatus.DueInDays -> StatusChip(
            stringResource(Res.string.vocab_status_due_in_days, status.days),
            MaterialTheme.colorScheme.surfaceVariant,
            semantics.ink500,
        )
        ReviewStatus.New -> StatusChip(
            stringResource(Res.string.vocab_status_new),
            MaterialTheme.colorScheme.surfaceVariant,
            semantics.ink500,
        )
    }
}

@Composable
private fun StatusChip(
    text: String,
    container: Color,
    content: Color,
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(container)
            .padding(horizontal = 8.dp, vertical = 5.dp),
    ) {
        Text(text = text, style = BrainDropTheme.type.label, color = content)
    }
}

@Composable
private fun BottomSessionBar(
    dueCount: Int,
    onStart: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 16.dp),
    ) {
        HorizontalDivider(
            color = MaterialTheme.colorScheme.outlineVariant,
            thickness = 1.dp,
            modifier = Modifier.padding(bottom = 12.dp),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp)
                .clip(BrainDropTheme.shapes.button)
                .background(MaterialTheme.colorScheme.primary, BrainDropTheme.shapes.button)
                .clickable(onClick = onStart),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = if (dueCount > 0) {
                    stringResource(Res.string.vocab_list_session_bar, dueCount)
                } else {
                    stringResource(Res.string.vocab_list_session_bar_empty)
                },
                style = BrainDropTheme.type.button,
                color = Color.White,
            )
        }
    }
}

@Composable
private fun EmptyResult() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(32.dp)) {
            BrainDropIcons.Search(iconSize = 32.dp, tint = BrainDropTheme.semantics.ink400)
            Spacer(Modifier.height(12.dp))
            Text(text = stringResource(Res.string.vocab_list_empty), style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
private fun ListSkeleton() {
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
                        .size(width = 4.dp, height = 52.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(2.dp)),
                )
                Column(verticalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.xs)) {
                    Box(
                        modifier = Modifier
                            .size(width = 150.dp, height = 16.dp)
                            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(4.dp)),
                    )
                    Box(
                        modifier = Modifier
                            .size(width = 190.dp, height = 12.dp)
                            .background(MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(4.dp)),
                    )
                }
            }
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant, thickness = 1.dp)
        }
    }
}

@Composable
private fun VocabularyQuizMode.label(): String =
    stringResource(
        when (this) {
            VocabularyQuizMode.CLOZE -> Res.string.vocab_quiz_mode_cloze
            VocabularyQuizMode.TYPING -> Res.string.vocab_quiz_mode_typing
            VocabularyQuizMode.MIXED -> Res.string.vocab_quiz_mode_mixed
        },
    )
