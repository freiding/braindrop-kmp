package by.freiding.braindrop.feature.tenses.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import by.freiding.braindrop.core.ui.component.BrainDropButtonStyle
import by.freiding.braindrop.core.ui.component.BrainDropIconButton
import by.freiding.braindrop.core.ui.component.BrainDropLoadingIndicator
import by.freiding.braindrop.core.ui.component.ErrorStatusCard
import by.freiding.braindrop.core.ui.component.brainDropCard
import by.freiding.braindrop.core.ui.icon.BrainDropIcons
import by.freiding.braindrop.feature.tenses.Res
import by.freiding.braindrop.feature.tenses.cd_back
import by.freiding.braindrop.feature.tenses.domain.model.Tense
import by.freiding.braindrop.feature.tenses.domain.model.TenseAspect
import by.freiding.braindrop.feature.tenses.domain.model.TenseExample
import by.freiding.braindrop.feature.tenses.domain.model.TenseFormExample
import by.freiding.braindrop.feature.tenses.domain.model.TenseScenario
import by.freiding.braindrop.feature.tenses.domain.model.TenseUsageCase
import by.freiding.braindrop.feature.tenses.error_generic
import by.freiding.braindrop.feature.tenses.error_retry
import by.freiding.braindrop.feature.tenses.presentation.common.aspectAccent
import by.freiding.braindrop.feature.tenses.presentation.common.formatTenseId
import by.freiding.braindrop.feature.tenses.presentation.common.highlightedExample
import by.freiding.braindrop.feature.tenses.presentation.common.highlightedMarkers
import by.freiding.braindrop.feature.tenses.tense_detail_compare_button
import by.freiding.braindrop.feature.tenses.tense_detail_confused_title
import by.freiding.braindrop.feature.tenses.tense_detail_example_title
import by.freiding.braindrop.feature.tenses.tense_detail_label_affirmative
import by.freiding.braindrop.feature.tenses.tense_detail_label_negative
import by.freiding.braindrop.feature.tenses.tense_detail_label_question
import by.freiding.braindrop.feature.tenses.tense_detail_mark_learned
import by.freiding.braindrop.feature.tenses.tense_detail_markers_title
import by.freiding.braindrop.feature.tenses.tense_detail_mistake_title
import by.freiding.braindrop.feature.tenses.tense_detail_more_examples_title
import by.freiding.braindrop.feature.tenses.tense_detail_special_notes_title
import by.freiding.braindrop.feature.tenses.tense_detail_unmark_learned
import by.freiding.braindrop.feature.tenses.tense_detail_usage_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun TenseDetailScreen(
    tenseId: String,
    navController: NavController,
    viewModel: TenseDetailViewModel = koinViewModel(parameters = { parametersOf(tenseId) }),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is TenseDetailUiEffect.NavigateBack -> navController.popBackStack()
                is TenseDetailUiEffect.NavigateToComparisons -> navController.navigate(Routes.TenseComparisons)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Column(modifier = Modifier.fillMaxSize()) {
            DetailHeader(
                tense = state.tenseWithProgress?.tense,
                onBack = { viewModel.onEvent(TenseDetailUiEvent.NavigateBack) },
            )

            Box(modifier = Modifier.weight(1f)) {
                when {
                    state.isLoading -> BrainDropLoadingIndicator()

                    state.error != null -> ErrorStatusCard(
                        title = stringResource(Res.string.error_generic),
                        body = state.error.orEmpty(),
                        retryText = stringResource(Res.string.error_retry),
                        onRetry = { viewModel.reload() },
                    )

                    state.tenseWithProgress != null -> {
                        val tense = state.tenseWithProgress!!.tense
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .padding(horizontal = 20.dp)
                                .padding(bottom = 96.dp),
                            verticalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.md),
                        ) {
                            FormulasCard(tense = tense)
                            AspectAccentCard(aspect = tense.aspect)
                            UsageCasesCard(cases = tense.usageCases)
                            OtherExamplesCard(scenarios = tense.scenarios)
                            MarkersCard(tense = tense)
                            if (tense.specialNotes.isNotEmpty()) {
                                SpecialNotesCard(notes = tense.specialNotes)
                            }
                            MistakeCard(text = tense.commonMistake)
                            if (tense.confusedWith.isNotEmpty()) {
                                ConfusedWithRow(
                                    ids = tense.confusedWith,
                                    onClick = { viewModel.onEvent(TenseDetailUiEvent.ComparisonsClicked) },
                                )
                            }
                        }
                    }
                }
            }
        }

        if (state.tenseWithProgress != null) {
            LearnButtonBar(
                isLearned = state.tenseWithProgress!!.progress.isLearned,
                onToggle = { viewModel.onEvent(TenseDetailUiEvent.ToggleLearned) },
                modifier = Modifier.align(Alignment.BottomCenter),
            )
        }
    }
}

@Composable
private fun DetailHeader(
    tense: Tense?,
    onBack: () -> Unit,
) {
    val semantics = BrainDropTheme.semantics
    Row(
        modifier = Modifier.fillMaxWidth().padding(
            start = BrainDropTheme.spacing.xxs,
            end = BrainDropTheme.spacing.sm,
            top = BrainDropTheme.spacing.xxs,
            bottom = BrainDropTheme.spacing.xs,
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BrainDropIconButton(onClick = onBack, contentDescription = stringResource(Res.string.cd_back)) {
            BrainDropIcons.ChevronLeft(iconSize = 22.dp, tint = MaterialTheme.colorScheme.onSurface)
        }
        Spacer(Modifier.weight(1f))
        if (tense != null) {
            Row(
                modifier = Modifier
                    .height(30.dp)
                    .background(semantics.tenseTimeSurface(tense.time.name), RoundedCornerShape(15.dp))
                    .padding(horizontal = BrainDropTheme.spacing.sm),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Text(
                    text = tense.titleEn,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp, fontWeight = FontWeight.Bold),
                    color = semantics.tenseTimeColor(tense.time.name),
                )
            }
        }
    }
}

@Composable
private fun FormulasCard(tense: Tense) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer, BrainDropTheme.shapes.xl)
            .padding(top = 22.dp, start = 20.dp, end = 20.dp, bottom = BrainDropTheme.spacing.md),
    ) {
        Text(
            text = tense.titleEn,
            style = BrainDropTheme.type.display.copy(fontSize = 28.sp, lineHeight = 30.sp),
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(Modifier.height(BrainDropTheme.spacing.xs))
        Text(
            text = tense.titleRu,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 15.5.sp),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
        Spacer(Modifier.height(18.dp))
        HorizontalDivider(color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.16f))

        FormulaRow(stringResource(Res.string.tense_detail_label_affirmative), tense.formulas.affirmative)
        HorizontalDivider(color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.12f))
        FormulaRow(stringResource(Res.string.tense_detail_label_negative), tense.formulas.negative)
        HorizontalDivider(color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.12f))
        FormulaRow(stringResource(Res.string.tense_detail_label_question), tense.formulas.question)
        HorizontalDivider(color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.16f))

        Spacer(Modifier.height(BrainDropTheme.spacing.md))
        Text(
            text = stringResource(Res.string.tense_detail_example_title),
            style = BrainDropTheme.type.label,
            color = BrainDropTheme.semantics.formsLabelInk,
        )
        Spacer(Modifier.height(BrainDropTheme.spacing.xxs))
        Text(
            text = highlightedExample(
                tense.formExample.english,
                tense.formExample.highlight,
                MaterialTheme.colorScheme.primary,
            ),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            text = tense.formExample.russian,
            style = BrainDropTheme.type.translation,
            color = BrainDropTheme.semantics.ink500,
            modifier = Modifier.padding(top = 4.dp),
        )
    }
}

@Composable
private fun FormulaRow(
    label: String,
    formula: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = BrainDropTheme.spacing.sm),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = label, style = BrainDropTheme.type.label, color = BrainDropTheme.semantics.formsLabelInk)
        Text(text = formula, style = BrainDropTheme.type.verbForms, color = MaterialTheme.colorScheme.onSurface)
    }
}

@Composable
private fun AspectAccentCard(aspect: TenseAspect) {
    val accent = aspectAccent(aspect)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .brainDropCard(BrainDropTheme.shapes.xl)
            .padding(horizontal = 20.dp, vertical = 18.dp),
    ) {
        Text(
            text = accent.label,
            style = BrainDropTheme.type.label,
            color = MaterialTheme.colorScheme.primary,
        )
        Spacer(Modifier.height(BrainDropTheme.spacing.xs))
        Text(
            text = accent.description,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
private fun UsageCasesCard(cases: List<TenseUsageCase>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .brainDropCard(BrainDropTheme.shapes.xl)
            .padding(horizontal = 20.dp, vertical = 18.dp),
    ) {
        Text(
            text = stringResource(Res.string.tense_detail_usage_title),
            style = BrainDropTheme.type.label,
            color = BrainDropTheme.semantics.ink400,
        )
        Spacer(Modifier.height(BrainDropTheme.spacing.md))
        cases.forEachIndexed { index, case ->
            Text(
                text = case.description,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Row(modifier = Modifier.padding(top = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.width(2.dp).height(28.dp).background(MaterialTheme.colorScheme.outline))
                Column(modifier = Modifier.padding(start = 11.dp)) {
                    Text(
                        text = case.example.english,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Text(
                        text = case.example.russian,
                        style = BrainDropTheme.type.translation,
                        color = BrainDropTheme.semantics.ink500,
                    )
                }
            }
            if (index != cases.lastIndex) Spacer(Modifier.height(BrainDropTheme.spacing.sm))
        }
    }
}

@Composable
private fun OtherExamplesCard(scenarios: Map<TenseScenario, TenseFormExample>) {
    val extra = scenarios.filterKeys { it != TenseScenario.READ_BOOK }.values.toList()
    if (extra.isEmpty()) return

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .brainDropCard(BrainDropTheme.shapes.xl)
            .padding(horizontal = 20.dp, vertical = 18.dp),
    ) {
        Text(
            text = stringResource(Res.string.tense_detail_more_examples_title),
            style = BrainDropTheme.type.label,
            color = BrainDropTheme.semantics.ink400,
        )
        Spacer(Modifier.height(BrainDropTheme.spacing.md))
        extra.forEachIndexed { index, example ->
            Text(
                text = highlightedExample(example.english, example.highlight, MaterialTheme.colorScheme.primary),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = example.russian,
                style = BrainDropTheme.type.translation,
                color = BrainDropTheme.semantics.ink500,
            )
            if (index != extra.lastIndex) Spacer(Modifier.height(BrainDropTheme.spacing.sm))
        }
    }
}

@Composable
private fun SpecialNotesCard(notes: List<TenseUsageCase>) {
    val semantics = BrainDropTheme.semantics
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(semantics.streakTint, BrainDropTheme.shapes.xl)
            .padding(horizontal = 20.dp, vertical = 18.dp),
    ) {
        Text(
            text = stringResource(Res.string.tense_detail_special_notes_title),
            style = BrainDropTheme.type.label,
            color = semantics.streakInk,
        )
        Spacer(Modifier.height(BrainDropTheme.spacing.md))
        notes.forEachIndexed { index, note ->
            Text(
                text = note.description,
                style = MaterialTheme.typography.bodyMedium,
                color = semantics.streakInk,
            )
            Row(modifier = Modifier.padding(top = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.width(2.dp).height(20.dp).background(semantics.streakInk.copy(alpha = 0.35f)))
                Column(modifier = Modifier.padding(start = 11.dp)) {
                    Text(
                        text = note.example.english,
                        style = MaterialTheme.typography.bodyMedium,
                        color = semantics.streakInk,
                    )
                    Text(
                        text = note.example.russian,
                        style = BrainDropTheme.type.translation,
                        color = semantics.streakInk.copy(alpha = 0.75f),
                    )
                }
            }
            if (index != notes.lastIndex) Spacer(Modifier.height(BrainDropTheme.spacing.sm))
        }
    }
}

@Composable
private fun MarkersCard(tense: Tense) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .brainDropCard(BrainDropTheme.shapes.xl)
            .padding(horizontal = 20.dp, vertical = 18.dp),
    ) {
        Text(
            text = stringResource(Res.string.tense_detail_markers_title),
            style = BrainDropTheme.type.label,
            color = BrainDropTheme.semantics.ink400,
        )
        Spacer(Modifier.height(BrainDropTheme.spacing.sm))
        MarkerChipsRow(tense.markers)
        Spacer(Modifier.height(BrainDropTheme.spacing.md))
        tense.markerExamples.forEachIndexed { index, example ->
            MarkerExampleRow(example, tense.markers)
            if (index != tense.markerExamples.lastIndex) Spacer(Modifier.height(BrainDropTheme.spacing.sm))
        }
    }
}

@Composable
private fun MarkerChipsRow(markers: List<String>) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.xxs),
        verticalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.xxs),
    ) {
        markers.forEach { marker ->
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surfaceVariant, BrainDropTheme.shapes.chip)
                    .padding(horizontal = BrainDropTheme.spacing.sm, vertical = 5.dp),
            ) {
                Text(
                    text = marker,
                    style = BrainDropTheme.type.verbForms,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
private fun MarkerExampleRow(
    example: TenseExample,
    markers: List<String>,
) {
    Column {
        Text(
            text = highlightedMarkers(example.english, markers, MaterialTheme.colorScheme.primary),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            text = example.russian,
            style = BrainDropTheme.type.translation,
            color = BrainDropTheme.semantics.ink500,
        )
    }
}

@Composable
private fun MistakeCard(text: String) {
    val semantics = BrainDropTheme.semantics
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(semantics.incorrectTint, BrainDropTheme.shapes.xl)
            .padding(horizontal = 20.dp, vertical = 18.dp),
    ) {
        Text(
            text = stringResource(Res.string.tense_detail_mistake_title),
            style = BrainDropTheme.type.label,
            color = semantics.incorrectInk,
        )
        Spacer(Modifier.height(BrainDropTheme.spacing.xs))
        Text(text = text, style = MaterialTheme.typography.bodyMedium, color = semantics.incorrectInk)
    }
}

@Composable
private fun ConfusedWithRow(
    ids: List<String>,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .brainDropCard(BrainDropTheme.shapes.xl)
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 16.dp),
    ) {
        Text(
            text = stringResource(Res.string.tense_detail_confused_title),
            style = BrainDropTheme.type.label,
            color = BrainDropTheme.semantics.ink400,
        )
        Spacer(Modifier.height(BrainDropTheme.spacing.xs))
        Row(horizontalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.xxs)) {
            ids.forEach { id ->
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primaryContainer, BrainDropTheme.shapes.chip)
                        .padding(horizontal = BrainDropTheme.spacing.sm, vertical = 5.dp),
                ) {
                    Text(
                        text = formatTenseId(id),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                }
            }
        }
        Spacer(Modifier.height(BrainDropTheme.spacing.xs))
        Text(
            text = stringResource(Res.string.tense_detail_compare_button),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Composable
private fun LearnButtonBar(
    isLearned: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val background = MaterialTheme.colorScheme.background
    Column(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(14.dp)
                .background(Brush.verticalGradient(listOf(Color.Transparent, background))),
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(background)
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp, top = 2.dp),
        ) {
            if (isLearned) {
                BrainDropButton(
                    text = stringResource(Res.string.tense_detail_unmark_learned),
                    onClick = onToggle,
                    style = BrainDropButtonStyle.OUTLINED,
                    height = 54.dp,
                    cornerRadius = 16.dp,
                    fillWidth = true,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            } else {
                BrainDropButton(
                    text = stringResource(Res.string.tense_detail_mark_learned),
                    onClick = onToggle,
                    style = BrainDropButtonStyle.FILLED,
                    height = 54.dp,
                    cornerRadius = 16.dp,
                    fillWidth = true,
                    containerColor = BrainDropTheme.semantics.correct,
                    textStyle = BrainDropTheme.type.button,
                    leadingIcon = { BrainDropIcons.Check(iconSize = 19.dp, tint = Color.White) },
                )
            }
        }
    }
}
