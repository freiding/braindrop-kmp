package by.freiding.braindrop.feature.irregularverbs.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import by.freiding.braindrop.core.ui.BrainDropTheme
import by.freiding.braindrop.core.ui.component.BrainDropButton
import by.freiding.braindrop.core.ui.component.BrainDropButtonStyle
import by.freiding.braindrop.core.ui.component.BrainDropIconButton
import by.freiding.braindrop.core.ui.component.BrainDropLoadingIndicator
import by.freiding.braindrop.core.ui.component.ErrorStatusCard
import by.freiding.braindrop.core.ui.component.brainDropCard
import by.freiding.braindrop.core.ui.icon.BrainDropIcons
import by.freiding.braindrop.feature.irregularverbs.Res
import by.freiding.braindrop.feature.irregularverbs.cd_back
import by.freiding.braindrop.feature.irregularverbs.domain.model.IrregularVerb
import by.freiding.braindrop.feature.irregularverbs.domain.model.VerbExample
import by.freiding.braindrop.feature.irregularverbs.domain.model.VerbGroup
import by.freiding.braindrop.feature.irregularverbs.error_generic
import by.freiding.braindrop.feature.irregularverbs.error_retry
import by.freiding.braindrop.feature.irregularverbs.presentation.common.groupTitle
import by.freiding.braindrop.feature.irregularverbs.presentation.common.highlightedExample
import by.freiding.braindrop.feature.irregularverbs.verb_detail_examples
import by.freiding.braindrop.feature.irregularverbs.verb_detail_label_base_form
import by.freiding.braindrop.feature.irregularverbs.verb_detail_label_past_participle
import by.freiding.braindrop.feature.irregularverbs.verb_detail_label_past_simple
import by.freiding.braindrop.feature.irregularverbs.verb_detail_mark_learned
import by.freiding.braindrop.feature.irregularverbs.verb_detail_unmark_learned
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun VerbDetailScreen(
    verbId: String,
    navController: NavController,
    viewModel: VerbDetailViewModel = koinViewModel(parameters = { parametersOf(verbId) }),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is VerbDetailUiEffect.NavigateBack -> navController.popBackStack()
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Column(modifier = Modifier.fillMaxSize()) {
            DetailHeader(
                group = state.verbWithProgress?.verb?.group,
                onBack = { viewModel.onEvent(VerbDetailUiEvent.NavigateBack) },
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

                    state.verbWithProgress != null -> {
                        val item = state.verbWithProgress!!
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .padding(horizontal = 20.dp)
                                .padding(bottom = 96.dp),
                            verticalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.md),
                        ) {
                            VerbFormsCard(verb = item.verb)
                            ExamplesCard(examples = item.verb.examples, verb = item.verb)
                        }
                    }
                }
            }
        }

        if (state.verbWithProgress != null) {
            LearnButtonBar(
                isLearned = state.verbWithProgress!!.progress.isLearned,
                onToggle = { viewModel.onEvent(VerbDetailUiEvent.ToggleLearned) },
                modifier = Modifier.align(Alignment.BottomCenter),
            )
        }
    }
}

@Composable
private fun DetailHeader(
    group: VerbGroup?,
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
        if (group != null) {
            Row(
                modifier = Modifier
                    .height(30.dp)
                    .background(semantics.groupSurface(group.name), RoundedCornerShape(15.dp))
                    .padding(horizontal = BrainDropTheme.spacing.sm),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(
                            6.dp,
                        ).background(semantics.groupColor(group.name), BrainDropTheme.shapes.chip),
                )
                Text(
                    text = groupTitle(group),
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp, fontWeight = FontWeight.Bold),
                    color = semantics.groupInk(group.name),
                )
            }
        }
    }
}

@Composable
private fun VerbFormsCard(verb: IrregularVerb) {
    val labelColor = BrainDropTheme.semantics.formsLabelInk
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer, BrainDropTheme.shapes.xl)
            .padding(top = 22.dp, start = 20.dp, end = 20.dp, bottom = BrainDropTheme.spacing.xs),
    ) {
        Text(
            text = verb.baseForm,
            style = BrainDropTheme.type.display.copy(fontSize = 34.sp, lineHeight = 36.sp),
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(Modifier.height(BrainDropTheme.spacing.xs))
        Text(
            text = verb.translation,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 15.5.sp),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
        Spacer(Modifier.height(18.dp))
        HorizontalDivider(color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.16f))

        VerbFormRow(stringResource(Res.string.verb_detail_label_base_form), verb.baseForm, labelColor)
        HorizontalDivider(color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.12f))
        VerbFormRow(stringResource(Res.string.verb_detail_label_past_simple), verb.pastSimple, labelColor)
        HorizontalDivider(color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.12f))
        VerbFormRow(stringResource(Res.string.verb_detail_label_past_participle), verb.pastParticiple, labelColor)
    }
}

@Composable
private fun VerbFormRow(
    label: String,
    form: String,
    labelColor: Color,
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = BrainDropTheme.spacing.sm),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = label, style = BrainDropTheme.type.label, color = labelColor)
        Text(text = form, style = BrainDropTheme.type.verbForm, color = MaterialTheme.colorScheme.onSurface)
    }
}

@Composable
private fun ExamplesCard(
    examples: List<VerbExample>,
    verb: IrregularVerb,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .brainDropCard(BrainDropTheme.shapes.xl)
            .padding(horizontal = 20.dp, vertical = 18.dp),
    ) {
        Text(
            text = stringResource(Res.string.verb_detail_examples),
            style = BrainDropTheme.type.label,
            color = BrainDropTheme.semantics.ink400,
        )
        Spacer(Modifier.height(BrainDropTheme.spacing.md))
        val highlightForms = listOf(verb.pastSimple, verb.pastParticiple)
        examples.forEachIndexed { index, example ->
            Text(
                text = highlightedExample(example.english, highlightForms, MaterialTheme.colorScheme.primary),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Row(modifier = Modifier.padding(top = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.width(2.dp).height(14.dp).background(MaterialTheme.colorScheme.outline),
                )
                Text(
                    text = example.russian,
                    style = BrainDropTheme.type.translation,
                    color = BrainDropTheme.semantics.ink500,
                    modifier = Modifier.padding(start = 11.dp),
                )
            }
            if (index != examples.lastIndex) Spacer(Modifier.height(14.dp))
        }
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
                .background(
                    background,
                ).padding(start = 20.dp, end = 20.dp, bottom = 20.dp, top = 2.dp),
        ) {
            if (isLearned) {
                BrainDropButton(
                    text = stringResource(Res.string.verb_detail_unmark_learned),
                    onClick = onToggle,
                    style = BrainDropButtonStyle.OUTLINED,
                    height = 54.dp,
                    cornerRadius = 16.dp,
                    fillWidth = true,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            } else {
                BrainDropButton(
                    text = stringResource(Res.string.verb_detail_mark_learned),
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
