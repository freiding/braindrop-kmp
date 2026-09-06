package by.freiding.braindrop.feature.vocabulary.presentation.session

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import by.freiding.braindrop.core.ui.BrainDropTheme
import by.freiding.braindrop.core.ui.component.BrainDropButton
import by.freiding.braindrop.core.ui.component.BrainDropButtonStyle
import by.freiding.braindrop.core.ui.component.SegmentedBar
import by.freiding.braindrop.core.ui.component.brainDropCard
import by.freiding.braindrop.core.ui.icon.BrainDropIcons
import by.freiding.braindrop.core.ui.tts.TextToSpeechPlayer
import by.freiding.braindrop.feature.vocabulary.Res
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkWithProgress
import by.freiding.braindrop.feature.vocabulary.domain.srs.RecallGrade
import by.freiding.braindrop.feature.vocabulary.presentation.common.ChunkExampleCard
import by.freiding.braindrop.feature.vocabulary.presentation.common.GradeButtonRow
import by.freiding.braindrop.feature.vocabulary.presentation.common.VocabularyHeader
import by.freiding.braindrop.feature.vocabulary.presentation.common.highlightChunk
import by.freiding.braindrop.feature.vocabulary.presentation.common.metaLine
import by.freiding.braindrop.feature.vocabulary.vocab_cd_back
import by.freiding.braindrop.feature.vocabulary.vocab_loading
import by.freiding.braindrop.feature.vocabulary.vocab_retry_again
import by.freiding.braindrop.feature.vocabulary.vocab_session_empty_body
import by.freiding.braindrop.feature.vocabulary.vocab_session_empty_title
import by.freiding.braindrop.feature.vocabulary.vocab_session_finished_count
import by.freiding.braindrop.feature.vocabulary.vocab_session_finished_title
import by.freiding.braindrop.feature.vocabulary.vocab_session_front_hint
import by.freiding.braindrop.feature.vocabulary.vocab_session_front_label
import by.freiding.braindrop.feature.vocabulary.vocab_session_meta_seen
import by.freiding.braindrop.feature.vocabulary.vocab_session_more_examples
import by.freiding.braindrop.feature.vocabulary.vocab_session_reveal
import by.freiding.braindrop.feature.vocabulary.vocab_session_title
import by.freiding.braindrop.feature.vocabulary.vocab_to_list
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun VocabularyCardSessionScreen(
    navController: NavController,
    viewModel: VocabularyCardSessionViewModel = koinViewModel(),
    tts: TextToSpeechPlayer = koinInject(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is VocabularyCardSessionUiEffect.NavigateBack -> navController.popBackStack()
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        SessionHeader(
            state = state,
            onBack = { viewModel.onEvent(VocabularyCardSessionUiEvent.NavigateBack) },
        )

        when {
            state.error != null -> Centered {
                Text(state.error.orEmpty(), style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
                Spacer(Modifier.height(BrainDropTheme.spacing.lg))
                BrainDropButton(
                    text = stringResource(Res.string.vocab_cd_back),
                    onClick = { viewModel.onEvent(VocabularyCardSessionUiEvent.NavigateBack) },
                    style = BrainDropButtonStyle.OUTLINED,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            state.isLoading -> Centered {
                Text(stringResource(Res.string.vocab_loading), style = MaterialTheme.typography.bodyLarge)
            }
            state.isEmpty -> SessionEmpty(onBack = { viewModel.onEvent(VocabularyCardSessionUiEvent.NavigateBack) })
            state.isFinished -> SessionFinished(
                reviewed = state.reviewedCount,
                onRestart = { viewModel.onEvent(VocabularyCardSessionUiEvent.Restart) },
                onBack = { viewModel.onEvent(VocabularyCardSessionUiEvent.NavigateBack) },
            )
            state.currentCard != null -> SessionCard(
                item = state.currentCard!!,
                revealed = state.isRevealed,
                intervalLabels = state.intervalLabels,
                onReveal = { viewModel.onEvent(VocabularyCardSessionUiEvent.Reveal) },
                onSpeak = { tts.speak(it) },
                onGrade = { grade -> viewModel.onEvent(VocabularyCardSessionUiEvent.Grade(grade)) },
            )
        }
    }
}

@Composable
private fun SessionHeader(
    state: VocabularyCardSessionUiState,
    onBack: () -> Unit,
) {
    val semantics = BrainDropTheme.semantics
    Column(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface)) {
        VocabularyHeader(
            title = stringResource(Res.string.vocab_session_title),
            onBack = onBack,
            divider = false,
            trailing = {
                if (state.total > 0 && !state.isFinished) {
                    Column(
                        horizontalAlignment = Alignment.End,
                        modifier = Modifier.padding(end = BrainDropTheme.spacing.sm),
                    ) {
                        Text(
                            text = "${state.position}/${state.total}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary,
                        )
                        Text(
                            text = timeText(state.elapsedSeconds),
                            style = BrainDropTheme.type.counter,
                            color = semantics.ink400,
                        )
                    }
                }
            },
        )
        if (state.total > 0 && !state.isFinished) {
            val currentColor = MaterialTheme.colorScheme.primary
            val emptyColor = MaterialTheme.colorScheme.surfaceVariant
            SegmentedBar(
                segmentCount = state.total.coerceAtLeast(1),
                height = 6.dp,
                gap = 3.dp,
                cornerRadius = 3.dp,
                modifier = Modifier.padding(
                    horizontal = BrainDropTheme.spacing.sm,
                    vertical = BrainDropTheme.spacing.sm,
                ),
            ) { index ->
                when (state.gradeHistory.getOrNull(index)) {
                    RecallGrade.KNOW -> semantics.correct
                    RecallGrade.ALMOST -> semantics.streak
                    RecallGrade.DONT_KNOW -> semantics.incorrect
                    null -> if (index == state.currentIndex) currentColor else emptyColor
                }
            }
        }
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant, thickness = 1.dp)
    }
}

@Composable
private fun SessionCard(
    item: ChunkWithProgress,
    revealed: Boolean,
    intervalLabels: Map<RecallGrade, String>,
    onReveal: () -> Unit,
    onSpeak: (String) -> Unit,
    onGrade: (RecallGrade) -> Unit,
) {
    val chunk = item.chunk
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(BrainDropTheme.spacing.md)
                .brainDropCard(BrainDropTheme.shapes.xxl)
                .padding(BrainDropTheme.spacing.lg)
                .verticalScroll(rememberScrollState()),
        ) {
            CardLabel(stringResource(Res.string.vocab_session_front_label))
            Spacer(Modifier.height(14.dp))
            Text(
                text = highlightChunk(
                    sentence = chunk.primaryExample.english,
                    chunk = chunk.text,
                    headword = chunk.headword,
                    highlightColor = MaterialTheme.colorScheme.primary,
                ),
                style = MaterialTheme.typography.bodyLarge.copy(
                    lineHeight = MaterialTheme.typography.titleLarge.lineHeight,
                ),
                color = MaterialTheme.colorScheme.onSurface,
            )

            if (!revealed) {
                Spacer(Modifier.height(BrainDropTheme.spacing.lg))
                BrainDropButton(
                    text = stringResource(Res.string.vocab_session_reveal),
                    onClick = onReveal,
                    style = BrainDropButtonStyle.OUTLINED,
                    modifier = Modifier.fillMaxWidth(),
                )
            } else {
                Spacer(Modifier.height(BrainDropTheme.spacing.md))
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                Spacer(Modifier.height(BrainDropTheme.spacing.md))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = chunk.text,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        Text(
                            text = chunk.translation,
                            style = MaterialTheme.typography.bodyLarge,
                            color = BrainDropTheme.semantics.ink500,
                            modifier = Modifier.padding(top = 4.dp),
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(BrainDropTheme.shapes.chip)
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .clickable { onSpeak(chunk.text) },
                        contentAlignment = Alignment.Center,
                    ) {
                        BrainDropIcons.Speaker(iconSize = 17.dp, tint = MaterialTheme.colorScheme.primary)
                    }
                }
                if (chunk.moreExamples.isNotEmpty()) {
                    Spacer(Modifier.height(BrainDropTheme.spacing.md))
                    CardLabel(stringResource(Res.string.vocab_session_more_examples))
                    Spacer(Modifier.height(10.dp))
                    chunk.moreExamples.take(2).forEach { example ->
                        ChunkExampleCard(example = example, chunk = chunk, modifier = Modifier.padding(bottom = 8.dp))
                    }
                }
                Spacer(Modifier.height(BrainDropTheme.spacing.sm))
                Text(
                    text = stringResource(
                        Res.string.vocab_session_meta_seen,
                        chunk.metaLine(),
                        item.progress.timesSeen,
                    ),
                    style = BrainDropTheme.type.label,
                    color = BrainDropTheme.semantics.ink400,
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .navigationBarsPadding()
                .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 16.dp),
        ) {
            if (revealed) {
                GradeButtonRow(onGrade = onGrade, intervalLabels = intervalLabels)
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .clip(BrainDropTheme.shapes.md)
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = stringResource(Res.string.vocab_session_front_hint),
                        style = MaterialTheme.typography.bodyMedium,
                        color = BrainDropTheme.semantics.ink400,
                    )
                }
            }
        }
    }
}

@Composable
private fun CardLabel(text: String) {
    Text(text = text, style = BrainDropTheme.type.label, color = BrainDropTheme.semantics.ink400)
}

@Composable
private fun SessionFinished(
    reviewed: Int,
    onRestart: () -> Unit,
    onBack: () -> Unit,
) {
    Centered {
        BrainDropIcons.Check(iconSize = 40.dp, tint = BrainDropTheme.semantics.correct)
        Spacer(Modifier.height(BrainDropTheme.spacing.md))
        Text(
            text = stringResource(Res.string.vocab_session_finished_title),
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(Modifier.height(BrainDropTheme.spacing.xs))
        Text(
            text = stringResource(Res.string.vocab_session_finished_count, reviewed),
            style = MaterialTheme.typography.bodyMedium,
            color = BrainDropTheme.semantics.ink500,
        )
        Spacer(Modifier.height(BrainDropTheme.spacing.lg))
        BrainDropButton(
            text = stringResource(Res.string.vocab_retry_again),
            onClick = onRestart,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(Modifier.height(BrainDropTheme.spacing.xs))
        BrainDropButton(
            text = stringResource(Res.string.vocab_to_list),
            onClick = onBack,
            style = BrainDropButtonStyle.OUTLINED,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun SessionEmpty(onBack: () -> Unit) {
    Centered {
        BrainDropIcons.Check(iconSize = 40.dp, tint = BrainDropTheme.semantics.correct)
        Spacer(Modifier.height(BrainDropTheme.spacing.md))
        Text(
            text = stringResource(Res.string.vocab_session_empty_title),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(BrainDropTheme.spacing.xs))
        Text(
            text = stringResource(Res.string.vocab_session_empty_body),
            style = MaterialTheme.typography.bodyMedium,
            color = BrainDropTheme.semantics.ink500,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(BrainDropTheme.spacing.lg))
        BrainDropButton(
            text = stringResource(Res.string.vocab_to_list),
            onClick = onBack,
            style = BrainDropButtonStyle.OUTLINED,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun Centered(content: @Composable androidx.compose.foundation.layout.ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(BrainDropTheme.spacing.lg),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        content = content,
    )
}

private fun timeText(totalSeconds: Int): String {
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "$minutes:${seconds.toString().padStart(2, '0')}"
}
