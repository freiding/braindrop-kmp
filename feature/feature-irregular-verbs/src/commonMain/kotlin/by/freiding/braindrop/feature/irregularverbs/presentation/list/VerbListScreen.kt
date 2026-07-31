package by.freiding.braindrop.feature.irregularverbs.presentation.list

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import by.freiding.braindrop.core.navigation.Routes
import by.freiding.braindrop.feature.irregularverbs.domain.model.QuizType
import by.freiding.braindrop.feature.irregularverbs.domain.model.VerbGroup
import by.freiding.braindrop.feature.irregularverbs.domain.model.VerbWithProgress
import by.freiding.braindrop.feature.irregularverbs.Res
import by.freiding.braindrop.feature.irregularverbs.error_loading
import by.freiding.braindrop.feature.irregularverbs.nav_back
import by.freiding.braindrop.feature.irregularverbs.swipe_mark_learned
import by.freiding.braindrop.feature.irregularverbs.swipe_unmark_learned
import by.freiding.braindrop.feature.irregularverbs.verb_group_aaa_hint
import by.freiding.braindrop.feature.irregularverbs.verb_group_aaa_title
import by.freiding.braindrop.feature.irregularverbs.verb_group_aba_hint
import by.freiding.braindrop.feature.irregularverbs.verb_group_aba_title
import by.freiding.braindrop.feature.irregularverbs.verb_group_abb_aid_hint
import by.freiding.braindrop.feature.irregularverbs.verb_group_abb_aid_title
import by.freiding.braindrop.feature.irregularverbs.verb_group_abb_other_hint
import by.freiding.braindrop.feature.irregularverbs.verb_group_abb_other_title
import by.freiding.braindrop.feature.irregularverbs.verb_group_abb_ought_hint
import by.freiding.braindrop.feature.irregularverbs.verb_group_abb_ought_title
import by.freiding.braindrop.feature.irregularverbs.verb_group_abb_ound_hint
import by.freiding.braindrop.feature.irregularverbs.verb_group_abb_ound_title
import by.freiding.braindrop.feature.irregularverbs.verb_group_abb_t_hint
import by.freiding.braindrop.feature.irregularverbs.verb_group_abb_t_title
import by.freiding.braindrop.feature.irregularverbs.verb_group_abb_ung_hint
import by.freiding.braindrop.feature.irregularverbs.verb_group_abb_ung_title
import by.freiding.braindrop.feature.irregularverbs.verb_group_abc_ewn_hint
import by.freiding.braindrop.feature.irregularverbs.verb_group_abc_ewn_title
import by.freiding.braindrop.feature.irregularverbs.verb_group_abc_ian_hint
import by.freiding.braindrop.feature.irregularverbs.verb_group_abc_ian_title
import by.freiding.braindrop.feature.irregularverbs.verb_group_abc_o_hint
import by.freiding.braindrop.feature.irregularverbs.verb_group_abc_o_title
import by.freiding.braindrop.feature.irregularverbs.verb_group_abc_other_hint
import by.freiding.braindrop.feature.irregularverbs.verb_group_abc_other_title
import by.freiding.braindrop.feature.irregularverbs.verb_list_filter_groups
import by.freiding.braindrop.feature.irregularverbs.verb_list_filter_unlearned
import by.freiding.braindrop.feature.irregularverbs.verb_list_quiz_button
import by.freiding.braindrop.feature.irregularverbs.verb_list_quiz_en_ru
import by.freiding.braindrop.feature.irregularverbs.verb_list_quiz_forms
import by.freiding.braindrop.feature.irregularverbs.verb_list_quiz_ru_en
import by.freiding.braindrop.feature.irregularverbs.verb_list_studied_count
import kotlin.math.abs
import kotlin.math.roundToInt
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
            }
        }
    }

    var quizMenuExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Irregular Verbs")
                        if (!state.isLoading) {
                            Text(
                                text = stringResource(Res.string.verb_list_studied_count, state.learnedCount, state.allVerbs.size),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                },
                navigationIcon = {
                    Text(
                        text = stringResource(Res.string.nav_back),
                        modifier = Modifier
                            .clickable { navController.popBackStack() }
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                },
            )
        },
    ) { innerPadding ->
        when {
            state.isLoading -> Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center,
            ) { CircularProgressIndicator() }

            state.error != null -> Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center,
            ) { Text(text = state.error ?: stringResource(Res.string.error_loading)) }

            else -> LazyColumn(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentPadding = PaddingValues(bottom = 88.dp),
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            FilterChip(
                                selected = state.showLearnedOnly,
                                onClick = { viewModel.onEvent(VerbListUiEvent.ToggleFilter) },
                                label = { Text(stringResource(Res.string.verb_list_filter_unlearned)) },
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            FilterChip(
                                selected = state.viewMode == ViewMode.GROUPED,
                                onClick = { viewModel.onEvent(VerbListUiEvent.ToggleViewMode) },
                                label = { Text(stringResource(Res.string.verb_list_filter_groups)) },
                            )
                        }
                        Box {
                            Button(onClick = { quizMenuExpanded = true }) {
                                Text(stringResource(Res.string.verb_list_quiz_button))
                            }
                            DropdownMenu(
                                expanded = quizMenuExpanded,
                                onDismissRequest = { quizMenuExpanded = false },
                            ) {
                                DropdownMenuItem(
                                    text = { Text(stringResource(Res.string.verb_list_quiz_en_ru)) },
                                    onClick = {
                                        quizMenuExpanded = false
                                        viewModel.onEvent(VerbListUiEvent.StartQuiz(QuizType.EN_TO_RU.name))
                                    },
                                )
                                DropdownMenuItem(
                                    text = { Text(stringResource(Res.string.verb_list_quiz_ru_en)) },
                                    onClick = {
                                        quizMenuExpanded = false
                                        viewModel.onEvent(VerbListUiEvent.StartQuiz(QuizType.RU_TO_EN.name))
                                    },
                                )
                                DropdownMenuItem(
                                    text = { Text(stringResource(Res.string.verb_list_quiz_forms)) },
                                    onClick = {
                                        quizMenuExpanded = false
                                        viewModel.onEvent(VerbListUiEvent.StartQuiz(QuizType.VERB_FORMS.name))
                                    },
                                )
                            }
                        }
                    }
                }

                if (state.viewMode == ViewMode.LIST) {
                    items(state.displayedVerbs, key = { it.verb.id }) { item ->
                        SwipeableVerbRow(
                            verb = item,
                            onClick = { viewModel.onEvent(VerbListUiEvent.VerbClicked(item.verb.id)) },
                            onToggleLearned = { viewModel.onEvent(VerbListUiEvent.ToggleLearned(item.verb.id)) },
                        )
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                    }
                } else {
                    state.groupedVerbs.forEach { (group, verbs) ->
                        stickyHeader(key = "header_${group.name}") {
                            GroupHeader(
                                group = group,
                                total = verbs.size,
                                learned = verbs.count { it.progress.isLearned },
                            )
                        }
                        items(verbs, key = { it.verb.id }) { item ->
                            SwipeableVerbRow(
                                verb = item,
                                onClick = { viewModel.onEvent(VerbListUiEvent.VerbClicked(item.verb.id)) },
                                onToggleLearned = { viewModel.onEvent(VerbListUiEvent.ToggleLearned(item.verb.id)) },
                            )
                            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun GroupHeader(group: VerbGroup, total: Int, learned: Int) {
    val title = when (group) {
        VerbGroup.AAA -> stringResource(Res.string.verb_group_aaa_title)
        VerbGroup.ABA -> stringResource(Res.string.verb_group_aba_title)
        VerbGroup.ABB_OUGHT -> stringResource(Res.string.verb_group_abb_ought_title)
        VerbGroup.ABB_OUND -> stringResource(Res.string.verb_group_abb_ound_title)
        VerbGroup.ABB_UNG -> stringResource(Res.string.verb_group_abb_ung_title)
        VerbGroup.ABB_T -> stringResource(Res.string.verb_group_abb_t_title)
        VerbGroup.ABB_AID -> stringResource(Res.string.verb_group_abb_aid_title)
        VerbGroup.ABB_OTHER -> stringResource(Res.string.verb_group_abb_other_title)
        VerbGroup.ABC_IAN -> stringResource(Res.string.verb_group_abc_ian_title)
        VerbGroup.ABC_EWN -> stringResource(Res.string.verb_group_abc_ewn_title)
        VerbGroup.ABC_O -> stringResource(Res.string.verb_group_abc_o_title)
        VerbGroup.ABC_OTHER -> stringResource(Res.string.verb_group_abc_other_title)
    }
    val hint = when (group) {
        VerbGroup.AAA -> stringResource(Res.string.verb_group_aaa_hint)
        VerbGroup.ABA -> stringResource(Res.string.verb_group_aba_hint)
        VerbGroup.ABB_OUGHT -> stringResource(Res.string.verb_group_abb_ought_hint)
        VerbGroup.ABB_OUND -> stringResource(Res.string.verb_group_abb_ound_hint)
        VerbGroup.ABB_UNG -> stringResource(Res.string.verb_group_abb_ung_hint)
        VerbGroup.ABB_T -> stringResource(Res.string.verb_group_abb_t_hint)
        VerbGroup.ABB_AID -> stringResource(Res.string.verb_group_abb_aid_hint)
        VerbGroup.ABB_OTHER -> stringResource(Res.string.verb_group_abb_other_hint)
        VerbGroup.ABC_IAN -> stringResource(Res.string.verb_group_abc_ian_hint)
        VerbGroup.ABC_EWN -> stringResource(Res.string.verb_group_abc_ewn_hint)
        VerbGroup.ABC_O -> stringResource(Res.string.verb_group_abc_o_hint)
        VerbGroup.ABC_OTHER -> stringResource(Res.string.verb_group_abc_other_hint)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = "$learned/$total",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Text(
            text = hint,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
        )
    }
}

@Composable
private fun SwipeableVerbRow(
    verb: VerbWithProgress,
    onClick: () -> Unit,
    onToggleLearned: () -> Unit,
) {
    var dragOffsetPx by remember { mutableFloatStateOf(0f) }
    var didExceedThreshold by remember { mutableStateOf(false) }
    val animatedOffset by animateFloatAsState(targetValue = dragOffsetPx, label = "swipe")
    val thresholdPx = with(LocalDensity.current) { 120.dp.toPx() }

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
                    onHorizontalDrag = { _, delta ->
                        dragOffsetPx = (dragOffsetPx + delta).coerceIn(-400f, 400f)
                        if (abs(dragOffsetPx) > thresholdPx) didExceedThreshold = true
                    },
                )
            },
    ) {
        val isLearned = verb.progress.isLearned
        if (abs(animatedOffset) > 1f) {
            val bgColor = if (isLearned) Color(0xFFF44336) else Color(0xFF4CAF50)
            val label = if (isLearned) stringResource(Res.string.swipe_unmark_learned) else stringResource(Res.string.swipe_mark_learned)
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(bgColor)
                    .padding(horizontal = 24.dp),
                contentAlignment = if (animatedOffset > 0f) Alignment.CenterStart else Alignment.CenterEnd,
            ) {
                Text(
                    text = label,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
        Box(modifier = Modifier.offset { IntOffset(animatedOffset.roundToInt(), 0) }) {
            VerbRow(verb = verb, onClick = onClick)
        }
    }
}

@Composable
private fun VerbRow(verb: VerbWithProgress, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = verb.verb.baseForm,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = "${verb.verb.pastSimple} / ${verb.verb.pastParticiple}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = verb.verb.translation,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.tertiary,
            )
        }
        if (verb.progress.isLearned) {
            Text(
                text = "✓",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(start = 8.dp),
            )
        }
    }
}
