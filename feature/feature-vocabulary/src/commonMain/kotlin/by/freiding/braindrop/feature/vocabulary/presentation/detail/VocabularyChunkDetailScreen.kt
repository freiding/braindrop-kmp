package by.freiding.braindrop.feature.vocabulary.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import by.freiding.braindrop.core.ui.BrainDropTheme
import by.freiding.braindrop.core.ui.component.BrainDropIconButton
import by.freiding.braindrop.core.ui.component.ErrorStatusCard
import by.freiding.braindrop.core.ui.icon.BrainDropIcons
import by.freiding.braindrop.core.ui.tts.TextToSpeechPlayer
import by.freiding.braindrop.feature.vocabulary.Res
import by.freiding.braindrop.feature.vocabulary.domain.model.Chunk
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkWithProgress
import by.freiding.braindrop.feature.vocabulary.domain.model.ReviewStatus
import by.freiding.braindrop.feature.vocabulary.domain.srs.ChunkSrs
import by.freiding.braindrop.feature.vocabulary.domain.srs.RecallGrade
import by.freiding.braindrop.feature.vocabulary.presentation.common.ChunkExampleCard
import by.freiding.braindrop.feature.vocabulary.presentation.common.CommonErrorCard
import by.freiding.braindrop.feature.vocabulary.presentation.common.GradeButtonRow
import by.freiding.braindrop.feature.vocabulary.presentation.common.displayName
import by.freiding.braindrop.feature.vocabulary.presentation.common.highlightChunk
import by.freiding.braindrop.feature.vocabulary.presentation.common.metaLine
import by.freiding.braindrop.feature.vocabulary.vocab_cd_back
import by.freiding.braindrop.feature.vocabulary.vocab_detail_error_title
import by.freiding.braindrop.feature.vocabulary.vocab_detail_section_collocations
import by.freiding.braindrop.feature.vocabulary.vocab_detail_section_context
import by.freiding.braindrop.feature.vocabulary.vocab_detail_section_forms
import by.freiding.braindrop.feature.vocabulary.vocab_detail_section_nearby
import by.freiding.braindrop.feature.vocabulary.vocab_detail_srs_due_in_days
import by.freiding.braindrop.feature.vocabulary.vocab_detail_srs_due_today
import by.freiding.braindrop.feature.vocabulary.vocab_detail_srs_learned
import by.freiding.braindrop.feature.vocabulary.vocab_detail_srs_new
import by.freiding.braindrop.feature.vocabulary.vocab_detail_toggle_learn
import by.freiding.braindrop.feature.vocabulary.vocab_detail_toggle_unlearn
import by.freiding.braindrop.feature.vocabulary.vocab_error_retry
import by.freiding.braindrop.feature.vocabulary.vocab_recall_prompt
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun VocabularyChunkDetailScreen(
    chunkId: String,
    navController: NavController,
    viewModel: VocabularyChunkDetailViewModel = koinViewModel { parametersOf(chunkId) },
    tts: TextToSpeechPlayer = koinInject(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is VocabularyChunkDetailUiEffect.NavigateBack -> navController.popBackStack()
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        DetailHeader(
            item = state.item,
            onBack = { viewModel.onEvent(VocabularyChunkDetailUiEvent.NavigateBack) },
            onToggleLearned = { viewModel.onEvent(VocabularyChunkDetailUiEvent.ToggleLearned) },
        )

        when {
            state.error != null -> ErrorStatusCard(
                title = stringResource(Res.string.vocab_detail_error_title),
                body = state.error.orEmpty(),
                retryText = stringResource(Res.string.vocab_error_retry),
                onRetry = { viewModel.reload() },
                secondaryText = stringResource(Res.string.vocab_cd_back),
                onSecondary = { viewModel.onEvent(VocabularyChunkDetailUiEvent.NavigateBack) },
            )
            state.isLoading -> Box(Modifier.fillMaxSize())
            state.item != null -> DetailBody(
                item = state.item!!,
                today = state.today,
                onSpeak = { tts.speak(it) },
                onGrade = { grade -> viewModel.onEvent(VocabularyChunkDetailUiEvent.Grade(grade)) },
            )
        }
    }
}

@Composable
private fun DetailHeader(
    item: ChunkWithProgress?,
    onBack: () -> Unit,
    onToggleLearned: () -> Unit,
) {
    val semantics = BrainDropTheme.semantics
    val learned = item?.progress?.isLearned == true

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
            BrainDropIconButton(onClick = onBack, contentDescription = stringResource(Res.string.vocab_cd_back)) {
                BrainDropIcons.ChevronLeft(iconSize = 22.dp, tint = MaterialTheme.colorScheme.onSurface)
            }
            Column(modifier = Modifier.weight(1f).padding(start = BrainDropTheme.spacing.xxs)) {
                if (item != null) {
                    Text(
                        text = highlightChunk(
                            sentence = item.chunk.text,
                            chunk = item.chunk.headword,
                            headword = item.chunk.headword,
                            highlightColor = MaterialTheme.colorScheme.primary,
                        ),
                        style = MaterialTheme.typography.headlineSmall,
                    )
                    Text(
                        text = item.chunk.metaLine(),
                        style = BrainDropTheme.type.label,
                        color = semantics.ink400,
                        modifier = Modifier.padding(top = 3.dp),
                    )
                }
            }
            BrainDropIconButton(
                onClick = onToggleLearned,
                contentDescription = stringResource(
                    if (learned) Res.string.vocab_detail_toggle_unlearn else Res.string.vocab_detail_toggle_learn,
                ),
            ) {
                if (learned) {
                    Box(
                        modifier = Modifier.size(28.dp).background(semantics.correct, BrainDropTheme.shapes.chip),
                        contentAlignment = Alignment.Center,
                    ) {
                        BrainDropIcons.Check(iconSize = 15.dp, tint = Color.White, strokeWidth = 2.4.dp)
                    }
                } else {
                    Box(modifier = Modifier.size(28.dp).border(2.dp, semantics.ink400, BrainDropTheme.shapes.chip))
                }
            }
        }
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant, thickness = 1.dp)
    }
}

@Composable
private fun DetailBody(
    item: ChunkWithProgress,
    today: LocalDate,
    onSpeak: (String) -> Unit,
    onGrade: (RecallGrade) -> Unit,
) {
    val chunk = item.chunk

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = WindowInsets.navigationBars.asPaddingValues(),
        ) {
            item { HeroCard(chunk = chunk, onSpeak = onSpeak) }
            item { SrsStrip(item = item, today = today) }
            item { SectionLabel(stringResource(Res.string.vocab_detail_section_context)) }
            item {
                ChunkExampleCard(
                    example = chunk.primaryExample,
                    chunk = chunk,
                    modifier = Modifier.padding(horizontal = BrainDropTheme.spacing.md, vertical = 4.dp),
                )
            }
            items(chunk.moreExamples) { example ->
                ChunkExampleCard(
                    example = example,
                    chunk = chunk,
                    modifier = Modifier.padding(horizontal = BrainDropTheme.spacing.md, vertical = 4.dp),
                )
            }
            chunk.commonError?.let { error ->
                item {
                    CommonErrorCard(
                        error = error,
                        modifier = Modifier.padding(horizontal = BrainDropTheme.spacing.md, vertical = 4.dp),
                    )
                }
            }

            if (chunk.relatedCollocations.isNotEmpty()) {
                item { SectionLabel(stringResource(Res.string.vocab_detail_section_collocations)) }
                item { CollocationsCard(chunk) }
            }
            if (chunk.wordForms.isNotEmpty()) {
                item { SectionLabel(stringResource(Res.string.vocab_detail_section_forms)) }
                item { WordFormsCard(chunk) }
            }
            if (chunk.nearbyChunks.isNotEmpty()) {
                item { SectionLabel(stringResource(Res.string.vocab_detail_section_nearby)) }
                item { NearbyChunksRow(chunk) }
            }
            item { Spacer(Modifier.height(BrainDropTheme.spacing.xl)) }
        }

        DetailFooter(onGrade = onGrade)
    }
}

@Composable
private fun HeroCard(
    chunk: Chunk,
    onSpeak: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = BrainDropTheme.spacing.md, vertical = BrainDropTheme.spacing.sm)
            .fillMaxWidth()
            .clip(BrainDropTheme.shapes.card)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(BrainDropTheme.spacing.md),
    ) {
        Row(verticalAlignment = Alignment.Top) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = chunk.text,
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = 26.sp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = chunk.translation,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(BrainDropTheme.shapes.chip)
                    .background(MaterialTheme.colorScheme.surface)
                    .clickable { onSpeak(chunk.text) },
                contentAlignment = Alignment.Center,
            ) {
                BrainDropIcons.Speaker(iconSize = 20.dp, tint = MaterialTheme.colorScheme.primary)
            }
        }
        Row(
            modifier = Modifier.padding(top = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            HeroBadge(chunk.register.displayName())
            chunk.pattern?.let { HeroBadge(it) }
            HeroBadge(chunk.level.displayName())
        }
    }
}

@Composable
private fun HeroBadge(text: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.7f))
            .padding(horizontal = 8.dp, vertical = 4.dp),
    ) {
        Text(text = text, style = BrainDropTheme.type.label, color = MaterialTheme.colorScheme.onPrimaryContainer)
    }
}

@Composable
private fun SrsStrip(
    item: ChunkWithProgress,
    today: LocalDate,
) {
    val semantics = BrainDropTheme.semantics
    val interval = ChunkSrs.currentIntervalDays(item.progress)
    val streak = item.progress.streak
    val text = when (val status = item.status(today)) {
        ReviewStatus.New -> stringResource(Res.string.vocab_detail_srs_new)
        ReviewStatus.DueToday -> stringResource(Res.string.vocab_detail_srs_due_today, interval, streak)
        is ReviewStatus.DueInDays -> stringResource(Res.string.vocab_detail_srs_due_in_days, status.days, streak)
        ReviewStatus.Learned -> stringResource(Res.string.vocab_detail_srs_learned, interval)
    }
    Row(
        modifier = Modifier
            .padding(horizontal = BrainDropTheme.spacing.md)
            .fillMaxWidth()
            .clip(BrainDropTheme.shapes.md)
            .background(semantics.streakTint)
            .padding(horizontal = 14.dp, vertical = 11.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        BrainDropIcons.Flame(iconSize = 16.dp, tint = semantics.streak)
        Text(text = text, style = MaterialTheme.typography.bodySmall, color = semantics.streakInk)
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text,
        style = BrainDropTheme.type.label,
        color = BrainDropTheme.semantics.ink400,
        modifier = Modifier.padding(
            start = BrainDropTheme.spacing.md,
            end = BrainDropTheme.spacing.md,
            top = BrainDropTheme.spacing.md,
            bottom = BrainDropTheme.spacing.xs,
        ),
    )
}

@Composable
private fun CollocationsCard(chunk: Chunk) {
    Column(
        modifier = Modifier
            .padding(horizontal = BrainDropTheme.spacing.md)
            .fillMaxWidth()
            .clip(BrainDropTheme.shapes.md)
            .background(MaterialTheme.colorScheme.surface),
    ) {
        chunk.relatedCollocations.forEachIndexed { index, collocation ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 11.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = collocation.text,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Text(
                        text = collocation.translation,
                        style = BrainDropTheme.type.translation,
                        color = BrainDropTheme.semantics.ink500,
                    )
                }
            }
            if (index < chunk.relatedCollocations.lastIndex) {
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.outlineVariant,
                    modifier = Modifier.padding(start = 14.dp),
                )
            }
        }
    }
}

@Composable
private fun WordFormsCard(chunk: Chunk) {
    Row(
        modifier = Modifier
            .padding(horizontal = BrainDropTheme.spacing.md)
            .fillMaxWidth()
            .clip(BrainDropTheme.shapes.md)
            .background(MaterialTheme.colorScheme.surface)
            .padding(14.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        chunk.wordForms.forEach { form ->
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = form.partOfSpeech.displayName(),
                    style = BrainDropTheme.type.label,
                    color = BrainDropTheme.semantics.formsLabelInk,
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = form.form,
                    style = BrainDropTheme.type.verbForm,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    }
}

@Composable
private fun NearbyChunksRow(chunk: Chunk) {
    Row(
        modifier = Modifier
            .padding(horizontal = BrainDropTheme.spacing.md)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        chunk.nearbyChunks.forEach { label ->
            Box(
                modifier = Modifier
                    .clip(BrainDropTheme.shapes.lg)
                    .border(1.5.dp, MaterialTheme.colorScheme.outline, BrainDropTheme.shapes.lg)
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(horizontal = 12.dp, vertical = 8.dp),
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
private fun DetailFooter(onGrade: (RecallGrade) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .navigationBarsPadding()
            .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 16.dp),
    ) {
        Text(
            text = stringResource(Res.string.vocab_recall_prompt),
            style = BrainDropTheme.type.label,
            color = BrainDropTheme.semantics.ink400,
            modifier = Modifier.padding(bottom = 10.dp),
        )
        GradeButtonRow(onGrade = onGrade)
    }
}
