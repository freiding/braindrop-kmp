package by.freiding.braindrop.feature.tenses.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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
import by.freiding.braindrop.feature.tenses.domain.model.TenseUsageCase
import by.freiding.braindrop.feature.tenses.error_generic
import by.freiding.braindrop.feature.tenses.error_retry
import by.freiding.braindrop.feature.tenses.presentation.common.aspectAccent
import by.freiding.braindrop.feature.tenses.presentation.common.formatTenseId
import by.freiding.braindrop.feature.tenses.presentation.common.highlightedExample
import by.freiding.braindrop.feature.tenses.presentation.common.highlightedMarkers
import by.freiding.braindrop.feature.tenses.tense_detail_confused_with_format
import by.freiding.braindrop.feature.tenses.tense_detail_example_title
import by.freiding.braindrop.feature.tenses.tense_detail_label_affirmative
import by.freiding.braindrop.feature.tenses.tense_detail_label_negative
import by.freiding.braindrop.feature.tenses.tense_detail_label_question
import by.freiding.braindrop.feature.tenses.tense_detail_mark_learned
import by.freiding.braindrop.feature.tenses.tense_detail_markers_title
import by.freiding.braindrop.feature.tenses.tense_detail_mistake_title
import by.freiding.braindrop.feature.tenses.tense_detail_open_comparison
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
                is TenseDetailUiEffect.NavigateToComparisons ->
                    navController.navigate(Routes.TenseComparisons(effect.comparisonId))
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
                            verticalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.sm),
                        ) {
                            FormulasCard(tense = tense)
                            AspectAccentCard(aspect = tense.aspect)
                            ExampleCard(tense = tense)

                            UsageSectionRow(
                                cases = tense.usageCases,
                                expanded = TenseDetailSection.USAGE in state.expandedSections,
                                onToggle = {
                                    viewModel.onEvent(TenseDetailUiEvent.SectionToggled(TenseDetailSection.USAGE))
                                },
                            )
                            MarkersSectionRow(
                                tense = tense,
                                expanded = TenseDetailSection.MARKERS in state.expandedSections,
                                onToggle = {
                                    viewModel.onEvent(TenseDetailUiEvent.SectionToggled(TenseDetailSection.MARKERS))
                                },
                            )
                            if (tense.specialNotes.isNotEmpty()) {
                                SpecialNotesSectionRow(
                                    notes = tense.specialNotes,
                                    expanded = TenseDetailSection.SPECIAL_NOTES in state.expandedSections,
                                    onToggle = {
                                        viewModel.onEvent(
                                            TenseDetailUiEvent.SectionToggled(TenseDetailSection.SPECIAL_NOTES),
                                        )
                                    },
                                )
                            }
                            MistakeSectionRow(
                                text = tense.commonMistake,
                                expanded = TenseDetailSection.MISTAKE in state.expandedSections,
                                onToggle = {
                                    viewModel.onEvent(TenseDetailUiEvent.SectionToggled(TenseDetailSection.MISTAKE))
                                },
                            )
                            state.confusedComparisons.forEach { confused ->
                                ConfusedWithRow(
                                    partnerTitle = formatTenseId(confused.partnerTenseId),
                                    onClick = {
                                        viewModel.onEvent(TenseDetailUiEvent.ComparisonClicked(confused.comparisonId))
                                    },
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
            val aspectName = tense.aspect.name
            Row(
                modifier = Modifier
                    .height(30.dp)
                    .background(semantics.aspectSurface(aspectName), BrainDropTheme.shapes.md)
                    .padding(horizontal = BrainDropTheme.spacing.sm),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(7.dp),
            ) {
                Box(
                    modifier = Modifier
                        .width(7.dp)
                        .height(7.dp)
                        .background(semantics.aspectColor(aspectName), BrainDropTheme.shapes.chip),
                )
                Text(
                    text = "${aspectName.replace('_', ' ')} · ${aspectAccent(tense.aspect).label}",
                    style = BrainDropTheme.type.label.copy(fontSize = 11.5.sp, letterSpacing = 0.6.sp),
                    color = semantics.aspectInk(aspectName),
                )
            }
        }
    }
}

@Composable
private fun FormulasCard(tense: Tense) {
    val semantics = BrainDropTheme.semantics
    val aspectName = tense.aspect.name
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(semantics.aspectSurface(aspectName), BrainDropTheme.shapes.xl)
            .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 16.dp),
    ) {
        Text(
            text = tense.titleEn,
            style = BrainDropTheme.type.display.copy(
                fontSize = 27.sp,
                lineHeight = 29.7.sp,
                letterSpacing = (-0.675).sp,
            ),
            color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            text = tense.titleRu,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 15.sp),
            color = semantics.aspectInk(aspectName),
            modifier = Modifier.padding(top = 7.dp),
        )
        HorizontalDivider(
            color = semantics.aspectInk(aspectName).copy(alpha = 0.16f),
            modifier = Modifier.padding(top = 16.dp, bottom = 2.dp),
        )

        FormulaBlock(
            stringResource(Res.string.tense_detail_label_affirmative),
            tense.formulas.affirmative,
            aspectName,
        )
        HorizontalDivider(color = semantics.aspectInk(aspectName).copy(alpha = 0.13f))
        FormulaBlock(stringResource(Res.string.tense_detail_label_negative), tense.formulas.negative, aspectName)
        HorizontalDivider(color = semantics.aspectInk(aspectName).copy(alpha = 0.13f))
        FormulaBlock(stringResource(Res.string.tense_detail_label_question), tense.formulas.question, aspectName)
    }
}

@Composable
private fun FormulaBlock(
    label: String,
    formula: String,
    aspectName: String,
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 11.dp)) {
        Text(
            text = label,
            style = BrainDropTheme.type.label.copy(fontSize = 10.sp, letterSpacing = 0.85.sp),
            color = BrainDropTheme.semantics.aspectMutedInk(aspectName),
        )
        Text(
            text = formula,
            style = BrainDropTheme.type.verbForms.copy(fontSize = 16.sp, fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(top = 6.dp),
        )
    }
}

@Composable
private fun AspectAccentCard(aspect: TenseAspect) {
    val accent = aspectAccent(aspect)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .brainDropCard(BrainDropTheme.shapes.xl)
            .padding(horizontal = 20.dp, vertical = 16.dp),
    ) {
        Text(
            text = accent.label,
            style = BrainDropTheme.type.label,
            color = BrainDropTheme.semantics.aspectColor(aspect.name),
        )
        Spacer(Modifier.height(9.dp))
        Text(
            text = accent.description,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 14.5.sp),
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
private fun ExampleCard(tense: Tense) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .brainDropCard(BrainDropTheme.shapes.xl)
            .padding(horizontal = 20.dp, vertical = 16.dp),
    ) {
        Text(
            text = stringResource(Res.string.tense_detail_example_title),
            style = BrainDropTheme.type.label,
            color = BrainDropTheme.semantics.ink400,
        )
        Text(
            text = highlightedExample(
                tense.formExample.english,
                tense.formExample.highlight,
                BrainDropTheme.semantics.aspectColor(tense.aspect.name),
            ),
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 15.sp),
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(top = 10.dp),
        )
        Row(modifier = Modifier.padding(top = 6.dp).height(IntrinsicSize.Min)) {
            Box(modifier = Modifier.fillMaxHeight().width(2.dp).background(MaterialTheme.colorScheme.outline))
            Text(
                text = tense.formExample.russian,
                style = BrainDropTheme.type.translation.copy(fontSize = 13.sp),
                color = BrainDropTheme.semantics.ink500,
                modifier = Modifier.padding(start = 11.dp),
            )
        }
    }
}

@Composable
private fun DetailSectionCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    background: Color = MaterialTheme.colorScheme.surface,
    withShadow: Boolean = true,
    content: @Composable ColumnScope.() -> Unit,
) {
    val shape = BrainDropTheme.shapes.lg
    Column(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (withShadow) {
                    Modifier.brainDropCard(shape)
                } else {
                    Modifier.clip(shape).background(background, shape)
                },
            ).clickable(onClick = onClick)
            .padding(horizontal = 18.dp, vertical = 15.dp),
        content = content,
    )
}

@Composable
private fun SectionHeaderRow(
    title: String,
    titleColor: Color = MaterialTheme.colorScheme.onSurface,
    chevronColor: Color = BrainDropTheme.semantics.ink400,
    expanded: Boolean = false,
    showChevron: Boolean = true,
    leading: (@Composable () -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null,
) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        leading?.invoke()
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 14.5.sp, fontWeight = FontWeight.Bold),
            color = titleColor,
            modifier = Modifier.weight(1f),
        )
        trailing?.invoke()
        if (showChevron) {
            if (expanded) {
                BrainDropIcons.ChevronUp(iconSize = 16.dp, tint = chevronColor)
            } else {
                BrainDropIcons.ChevronDown(iconSize = 16.dp, tint = chevronColor)
            }
        } else {
            BrainDropIcons.ChevronRight(iconSize = 16.dp, tint = chevronColor)
        }
    }
}

@Composable
private fun UsageCaseItem(case: TenseUsageCase) {
    Column {
        Text(
            text = case.description,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp, fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface,
        )
        Row(modifier = Modifier.padding(top = 6.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.width(2.dp).height(28.dp).background(MaterialTheme.colorScheme.outline))
            Column(modifier = Modifier.padding(start = 11.dp)) {
                Text(
                    text = case.example.english,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.5.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = case.example.russian,
                    style = BrainDropTheme.type.translation.copy(fontSize = 12.5.sp),
                    color = BrainDropTheme.semantics.ink500,
                )
            }
        }
    }
}

@Composable
private fun UsageSectionRow(
    cases: List<TenseUsageCase>,
    expanded: Boolean,
    onToggle: () -> Unit,
) {
    DetailSectionCard(onClick = onToggle) {
        SectionHeaderRow(
            title = stringResource(Res.string.tense_detail_usage_title),
            expanded = expanded,
            trailing = {
                Text(
                    text = "${cases.size}",
                    style = BrainDropTheme.type.counter,
                    color = BrainDropTheme.semantics.ink400,
                )
            },
        )
        val visibleCases = if (expanded) cases else cases.take(1)
        Column(
            modifier = Modifier.padding(top = BrainDropTheme.spacing.sm),
            verticalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.sm),
        ) {
            visibleCases.forEach { case -> UsageCaseItem(case) }
        }
    }
}

@Composable
private fun MarkersSectionRow(
    tense: Tense,
    expanded: Boolean,
    onToggle: () -> Unit,
) {
    DetailSectionCard(onClick = onToggle) {
        SectionHeaderRow(
            title = stringResource(Res.string.tense_detail_markers_title),
            expanded = expanded,
            trailing = if (!expanded) {
                {
                    Text(
                        text = markersPreview(tense.markers),
                        style = BrainDropTheme.type.verbForms.copy(fontSize = 12.sp),
                        color = BrainDropTheme.semantics.ink400,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.widthIn(max = 150.dp),
                    )
                }
            } else {
                null
            },
        )
        if (expanded) {
            Column(modifier = Modifier.padding(top = BrainDropTheme.spacing.sm)) {
                MarkerChipsRow(tense.markers)
                Spacer(Modifier.height(BrainDropTheme.spacing.md))
                tense.markerExamples.forEachIndexed { index, example ->
                    MarkerExampleRow(example, tense.markers)
                    if (index != tense.markerExamples.lastIndex) Spacer(Modifier.height(BrainDropTheme.spacing.sm))
                }
            }
        }
    }
}

private fun markersPreview(markers: List<String>): String {
    val head = markers.take(3).joinToString(" · ")
    return if (markers.size > 3) "$head · …" else head
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
private fun SpecialNotesSectionRow(
    notes: List<TenseUsageCase>,
    expanded: Boolean,
    onToggle: () -> Unit,
) {
    DetailSectionCard(onClick = onToggle) {
        SectionHeaderRow(
            title = stringResource(Res.string.tense_detail_special_notes_title),
            expanded = expanded,
            trailing = {
                Text(
                    text = "${notes.size}",
                    style = BrainDropTheme.type.counter,
                    color = BrainDropTheme.semantics.ink400,
                )
            },
        )
        if (expanded) {
            Column(
                modifier = Modifier.padding(top = BrainDropTheme.spacing.sm),
                verticalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.sm),
            ) {
                notes.forEach { note -> UsageCaseItem(note) }
            }
        }
    }
}

@Composable
private fun MistakeSectionRow(
    text: String,
    expanded: Boolean,
    onToggle: () -> Unit,
) {
    val semantics = BrainDropTheme.semantics
    DetailSectionCard(onClick = onToggle, background = semantics.incorrectTint, withShadow = false) {
        SectionHeaderRow(
            title = stringResource(Res.string.tense_detail_mistake_title),
            titleColor = semantics.incorrectInk,
            chevronColor = semantics.incorrectInk.copy(alpha = 0.55f),
            expanded = expanded,
            leading = { BrainDropIcons.Alert(iconSize = 18.dp, tint = semantics.incorrect) },
        )
        if (expanded) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = semantics.incorrectInk,
                modifier = Modifier.padding(top = BrainDropTheme.spacing.xs),
            )
        }
    }
}

@Composable
private fun ConfusedWithRow(
    partnerTitle: String,
    onClick: () -> Unit,
) {
    DetailSectionCard(onClick = onClick) {
        SectionHeaderRow(
            title = stringResource(Res.string.tense_detail_confused_with_format, partnerTitle),
            showChevron = false,
            trailing = {
                Text(
                    text = stringResource(Res.string.tense_detail_open_comparison),
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                    color = BrainDropTheme.semantics.ink400,
                )
            },
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
