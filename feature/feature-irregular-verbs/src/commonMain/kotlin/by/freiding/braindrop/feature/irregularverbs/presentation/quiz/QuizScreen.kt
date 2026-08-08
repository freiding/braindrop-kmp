package by.freiding.braindrop.feature.irregularverbs.presentation.quiz

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
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
import by.freiding.braindrop.core.ui.component.SegmentedBar
import by.freiding.braindrop.core.ui.component.StatusCard
import by.freiding.braindrop.core.ui.component.brainDropCard
import by.freiding.braindrop.core.ui.icon.BrainDropIcons
import by.freiding.braindrop.feature.irregularverbs.Res
import by.freiding.braindrop.feature.irregularverbs.cd_close
import by.freiding.braindrop.feature.irregularverbs.domain.model.IrregularVerb
import by.freiding.braindrop.feature.irregularverbs.domain.model.QuizQuestion
import by.freiding.braindrop.feature.irregularverbs.domain.model.QuizType
import by.freiding.braindrop.feature.irregularverbs.error_retry
import by.freiding.braindrop.feature.irregularverbs.presentation.common.familyDescription
import by.freiding.braindrop.feature.irregularverbs.presentation.common.groupHint
import by.freiding.braindrop.feature.irregularverbs.presentation.common.highlightedExample
import by.freiding.braindrop.feature.irregularverbs.presentation.common.verbFormsLine
import by.freiding.braindrop.feature.irregularverbs.quiz_empty_action
import by.freiding.braindrop.feature.irregularverbs.quiz_empty_body
import by.freiding.braindrop.feature.irregularverbs.quiz_empty_title
import by.freiding.braindrop.feature.irregularverbs.quiz_error_body
import by.freiding.braindrop.feature.irregularverbs.quiz_error_title
import by.freiding.braindrop.feature.irregularverbs.quiz_footer_hint
import by.freiding.braindrop.feature.irregularverbs.quiz_mistake_row_format
import by.freiding.braindrop.feature.irregularverbs.quiz_mistakes_section_title
import by.freiding.braindrop.feature.irregularverbs.quiz_next
import by.freiding.braindrop.feature.irregularverbs.quiz_progress_count
import by.freiding.braindrop.feature.irregularverbs.quiz_prompt_en_ru
import by.freiding.braindrop.feature.irregularverbs.quiz_prompt_forms
import by.freiding.braindrop.feature.irregularverbs.quiz_prompt_ru_en
import by.freiding.braindrop.feature.irregularverbs.quiz_result_percent_correct
import by.freiding.braindrop.feature.irregularverbs.quiz_result_subtitle_mistakes
import by.freiding.braindrop.feature.irregularverbs.quiz_result_subtitle_perfect
import by.freiding.braindrop.feature.irregularverbs.quiz_result_title_good
import by.freiding.braindrop.feature.irregularverbs.quiz_result_title_great
import by.freiding.braindrop.feature.irregularverbs.quiz_result_title_meh
import by.freiding.braindrop.feature.irregularverbs.quiz_retry_again
import by.freiding.braindrop.feature.irregularverbs.quiz_retry_mistakes
import by.freiding.braindrop.feature.irregularverbs.quiz_tile_progress_label
import by.freiding.braindrop.feature.irregularverbs.quiz_tile_streak_caption
import by.freiding.braindrop.feature.irregularverbs.quiz_tile_time_label
import by.freiding.braindrop.feature.irregularverbs.quiz_to_list
import by.freiding.braindrop.feature.irregularverbs.quiz_your_answer
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun QuizScreen(
    mode: String,
    navController: NavController,
    viewModel: QuizViewModel = koinViewModel(parameters = { parametersOf(QuizType.fromString(mode)) }),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is QuizUiEffect.NavigateBack -> navController.popBackStack()
                is QuizUiEffect.NavigateToVerbDetail ->
                    navController.navigate(Routes.IrregularVerbDetail(effect.verbId))
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        when {
            state.isLoading -> BrainDropLoadingIndicator()

            state.error != null -> ErrorStatusCard(
                title = stringResource(Res.string.quiz_error_title),
                body = stringResource(Res.string.quiz_error_body),
                retryText = stringResource(Res.string.error_retry),
                onRetry = { viewModel.onEvent(QuizUiEvent.RestartQuiz) },
                secondaryText = stringResource(Res.string.quiz_to_list),
                onSecondary = { viewModel.onEvent(QuizUiEvent.NavigateBack) },
            )

            state.isEmpty -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                StatusCard(
                    icon = { BrainDropIcons.Check(iconSize = 30.dp, tint = BrainDropTheme.semantics.correct) },
                    iconBackground = BrainDropTheme.semantics.correctTint,
                    title = stringResource(Res.string.quiz_empty_title),
                    body = stringResource(Res.string.quiz_empty_body),
                    primaryAction = {
                        BrainDropButton(
                            text = stringResource(Res.string.quiz_empty_action),
                            style = BrainDropButtonStyle.OUTLINED,
                            onClick = { viewModel.onEvent(QuizUiEvent.NavigateBack) },
                        )
                    },
                )
            }

            state.isFinished -> QuizResultContent(
                state = state,
                onRetryMistakes = { viewModel.onEvent(QuizUiEvent.RetryMistakes) },
                onRestart = { viewModel.onEvent(QuizUiEvent.RestartQuiz) },
                onBack = { viewModel.onEvent(QuizUiEvent.NavigateBack) },
                onMistakeClick = { verbId -> viewModel.onEvent(QuizUiEvent.MistakeClicked(verbId)) },
            )

            state.currentQuestion != null -> QuizQuestionContent(
                state = state,
                onClose = { viewModel.onEvent(QuizUiEvent.NavigateBack) },
                onAnswerSelected = { viewModel.onEvent(QuizUiEvent.AnswerSelected(it)) },
                onNext = { viewModel.onEvent(QuizUiEvent.NextQuestion) },
            )
        }
    }
}

@Composable
private fun QuizQuestionContent(
    state: QuizUiState,
    onClose: () -> Unit,
    onAnswerSelected: (String) -> Unit,
    onNext: () -> Unit,
) {
    val question = state.currentQuestion ?: return
    val semanticsForSegments = BrainDropTheme.semantics
    val currentColor = MaterialTheme.colorScheme.primary
    val aheadColor = MaterialTheme.colorScheme.outline

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(
                horizontal = BrainDropTheme.spacing.sm,
                vertical = BrainDropTheme.spacing.xxs,
            ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.sm),
        ) {
            BrainDropIconButton(onClick = onClose, contentDescription = stringResource(Res.string.cd_close)) {
                BrainDropIcons.Close(iconSize = 20.dp, tint = MaterialTheme.colorScheme.onSurface)
            }
            SegmentedBar(
                segmentCount = state.totalQuestions,
                height = 7.dp,
                gap = 3.dp,
                cornerRadius = 3.dp,
                modifier = Modifier.weight(1f),
            ) { index ->
                when (state.answerHistory.getOrNull(index)) {
                    true -> semanticsForSegments.correct
                    false -> semanticsForSegments.incorrect
                    null -> if (index == state.currentIndex) currentColor else aheadColor
                }
            }
            Text(
                text = stringResource(Res.string.quiz_progress_count, state.currentIndex + 1, state.totalQuestions),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        // A Column inside verticalScroll is measured with an unbounded max height, so a plain
        // Modifier.weight(1f) spacer inside it would collapse to zero instead of pushing the
        // answer options down. BoxWithConstraints supplies a known min height so the scrollable
        // Column can still report "at least the viewport" and let its weighted spacer work —
        // options sit at the bottom when content is short, and the whole thing scrolls normally
        // when the question/mistake card content overflows.
        BoxWithConstraints(modifier = Modifier.weight(1f)) {
            val minContentHeight = maxHeight
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = minContentHeight)
                    .padding(horizontal = 20.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
                Spacer(Modifier.height(BrainDropTheme.spacing.xs))
                QuestionCard(question = question, isAnswered = state.isAnswered)
                if (state.isAnswered && state.selectedAnswer != question.correctAnswer) {
                    Spacer(Modifier.height(BrainDropTheme.spacing.md))
                    MistakeBreakdownCard(verb = question.verb)
                }
                Spacer(Modifier.weight(1f))
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    question.options.forEach { option ->
                        val optionState = when {
                            !state.isAnswered -> AnswerOptionState.IDLE
                            option == question.correctAnswer -> AnswerOptionState.CORRECT
                            option == state.selectedAnswer -> AnswerOptionState.WRONG_SELECTED
                            else -> AnswerOptionState.MUTED
                        }
                        AnswerButton(text = option, state = optionState, onClick = { onAnswerSelected(option) })
                    }
                }
                Spacer(Modifier.height(BrainDropTheme.spacing.sm))
            }
        }

        QuizFooter(isAnswered = state.isAnswered, onNext = onNext)
    }
}

@Composable
private fun QuestionCard(
    question: QuizQuestion,
    isAnswered: Boolean,
) {
    val promptRes = when (question.type) {
        QuizType.EN_TO_RU -> Res.string.quiz_prompt_en_ru
        QuizType.RU_TO_EN -> Res.string.quiz_prompt_ru_en
        QuizType.VERB_FORMS -> Res.string.quiz_prompt_forms
    }
    val semantics = BrainDropTheme.semantics
    val horizontalPadding by animateFloatAsState(if (isAnswered) 22f else 26f)
    val fontSize by animateFloatAsState(if (isAnswered) 36f else 40f)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .brainDropCard(BrainDropTheme.shapes.xl)
            .padding(horizontal = horizontalPadding.dp, vertical = horizontalPadding.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = stringResource(promptRes), style = BrainDropTheme.type.label, color = semantics.ink400)
        Spacer(Modifier.height(BrainDropTheme.spacing.md))
        Text(
            text = question.questionText,
            style = BrainDropTheme.type.display.copy(fontSize = fontSize.sp, lineHeight = (fontSize + 2).sp),
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(BrainDropTheme.spacing.md))
        val familyDescription = familyDescription(question.verb.group)
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .background(semantics.groupSurface(question.verb.group.name), BrainDropTheme.shapes.md)
                .padding(horizontal = BrainDropTheme.spacing.sm),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.xs),
        ) {
            Text(
                text = semantics.familyLabel(question.verb.group.name),
                style = BrainDropTheme.type.family,
                color = semantics.familyColor(question.verb.group.name),
                modifier = Modifier.semantics { contentDescription = familyDescription },
            )
            Text(
                text = groupHint(question.verb.group),
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                color = semantics.groupInk(question.verb.group.name),
            )
        }
    }
}

@Composable
private fun MistakeBreakdownCard(verb: IrregularVerb) {
    val semantics = BrainDropTheme.semantics
    val forms = verbFormsLine(verb)
    val example = verb.examples.firstOrNull()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(semantics.mistakeCardSurface, BrainDropTheme.shapes.lg)
            .padding(horizontal = 18.dp, vertical = BrainDropTheme.spacing.md),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.Bottom,
        ) {
            Text(
                text = verb.baseForm,
                style = MaterialTheme.typography.titleSmall.copy(fontSize = 18.sp),
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
            )
            Text(
                text = forms,
                style = BrainDropTheme.type.verbForms.copy(fontSize = 14.sp),
                color = semantics.mistakeCardAccent,
            )
            Text(
                text = verb.translation,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.5.sp),
                color = semantics.mistakeCardMuted,
            )
        }
        if (example != null) {
            Spacer(Modifier.height(BrainDropTheme.spacing.xs))
            Text(
                text = highlightedExample(example.english, listOf(verb.pastSimple, verb.pastParticiple), Color.White),
                style = MaterialTheme.typography.bodySmall,
                color = semantics.mistakeCardHint,
            )
        }
    }
}

private enum class AnswerOptionState { IDLE, CORRECT, WRONG_SELECTED, MUTED }

/** Background/border/text colors for one [AnswerOptionState] — computed together so AnswerButton doesn't switch on `state` three separate times. */
private data class AnswerButtonPalette(
    val background: Color,
    val border: Color,
    val text: Color,
)

@Composable
private fun answerButtonPalette(state: AnswerOptionState): AnswerButtonPalette {
    val semantics = BrainDropTheme.semantics
    return when (state) {
        AnswerOptionState.IDLE -> AnswerButtonPalette(
            background = MaterialTheme.colorScheme.surface,
            border = MaterialTheme.colorScheme.outline,
            text = MaterialTheme.colorScheme.onSurface,
        )
        AnswerOptionState.CORRECT -> AnswerButtonPalette(
            background = semantics.correctTint,
            border = semantics.correct,
            text = semantics.correctInk,
        )
        AnswerOptionState.WRONG_SELECTED -> AnswerButtonPalette(
            background = semantics.incorrectTint,
            border = semantics.incorrect,
            text = semantics.incorrectInk,
        )
        AnswerOptionState.MUTED -> AnswerButtonPalette(
            background = semantics.answerMutedSurface,
            border = semantics.answerMutedOutline,
            text = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun AnswerButton(
    text: String,
    state: AnswerOptionState,
    onClick: () -> Unit,
) {
    val semantics = BrainDropTheme.semantics
    val target = answerButtonPalette(state)
    val bg by animateColorAsState(target.background, tween(300))
    val border by animateColorAsState(target.border, tween(300))
    val textColor by animateColorAsState(target.text, tween(300))
    val textAlpha = if (state == AnswerOptionState.MUTED) 0.55f else 1f

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp)
            .clip(BrainDropTheme.shapes.button)
            .background(bg, BrainDropTheme.shapes.button)
            .border(1.5.dp, border, BrainDropTheme.shapes.button)
            .clickable(enabled = state == AnswerOptionState.IDLE, onClick = onClick)
            .padding(horizontal = 18.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(13.dp),
    ) {
        Box(modifier = Modifier.size(24.dp), contentAlignment = Alignment.Center) {
            when (state) {
                AnswerOptionState.CORRECT -> Box(
                    modifier = Modifier.size(24.dp).background(semantics.correct, BrainDropTheme.shapes.chip),
                    contentAlignment = Alignment.Center,
                ) { BrainDropIcons.Check(iconSize = 14.dp, tint = Color.White, strokeWidth = 2.2.dp) }

                AnswerOptionState.WRONG_SELECTED -> Box(
                    modifier = Modifier.size(24.dp).background(semantics.incorrect, BrainDropTheme.shapes.chip),
                    contentAlignment = Alignment.Center,
                ) { BrainDropIcons.Close(iconSize = 13.dp, tint = Color.White, strokeWidth = 2.2.dp) }

                else -> Box(
                    modifier = Modifier
                        .size(24.dp)
                        .border(1.5.dp, semantics.answerIdleOutline, BrainDropTheme.shapes.chip),
                )
            }
        }
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 16.sp,
                fontWeight = if (state ==
                    AnswerOptionState.IDLE
                ) {
                    FontWeight.SemiBold
                } else {
                    FontWeight.ExtraBold
                },
            ),
            color = textColor.copy(alpha = textAlpha),
            modifier = Modifier.weight(1f),
        )
        if (state == AnswerOptionState.WRONG_SELECTED) {
            Text(
                text = stringResource(Res.string.quiz_your_answer),
                style = BrainDropTheme.type.counter,
                color = semantics.incorrectInk,
            )
        }
    }
}

@Composable
private fun QuizFooter(
    isAnswered: Boolean,
    onNext: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxWidth().height(BrainDropTheme.spacing.quizFooterHeight).padding(horizontal = 20.dp),
        contentAlignment = Alignment.Center,
    ) {
        AnimatedContent(
            targetState = isAnswered,
            transitionSpec = {
                (fadeIn(tween(200)) togetherWith fadeOut(tween(200)))
            },
            label = "quizFooter",
        ) { answered ->
            if (!answered) {
                Text(
                    text = stringResource(Res.string.quiz_footer_hint),
                    style = MaterialTheme.typography.bodyMedium,
                    color = BrainDropTheme.semantics.ink400,
                    textAlign = TextAlign.Center,
                )
            } else {
                BrainDropButton(
                    text = stringResource(Res.string.quiz_next),
                    onClick = onNext,
                    height = 56.dp,
                    cornerRadius = 16.dp,
                    fillWidth = true,
                    trailingIcon = { BrainDropIcons.ChevronRight(iconSize = 16.dp, tint = Color.White) },
                )
            }
        }
    }
}

@Composable
private fun QuizResultContent(
    state: QuizUiState,
    onRetryMistakes: () -> Unit,
    onRestart: () -> Unit,
    onBack: () -> Unit,
    onMistakeClick: (String) -> Unit,
) {
    val semantics = BrainDropTheme.semantics
    val total = state.totalQuestions
    val ratio = if (total > 0) state.score.toFloat() / total else 0f
    val percent = (ratio * 100).let { kotlin.math.round(it).toInt() }
    val ringColor = semantics.scoreRing(ratio)

    val title = when {
        ratio >= 0.9f -> stringResource(Res.string.quiz_result_title_great)
        ratio >= 0.6f -> stringResource(Res.string.quiz_result_title_good)
        else -> stringResource(Res.string.quiz_result_title_meh)
    }
    val subtitle = if (state.mistakes.isEmpty()) {
        stringResource(Res.string.quiz_result_subtitle_perfect)
    } else {
        pluralStringResource(Res.plurals.quiz_result_subtitle_mistakes, state.mistakes.size, state.mistakes.size)
    }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .brainDropCard(BrainDropTheme.shapes.xxl)
                .padding(horizontal = 20.dp, vertical = 26.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ScoreRing(ratio = ratio, ringColor = ringColor, trackColor = MaterialTheme.colorScheme.surfaceVariant) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = "${state.score}",
                            style = BrainDropTheme.type.display.copy(fontSize = 40.sp),
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        Text(
                            text = "/$total",
                            style = BrainDropTheme.type.display.copy(fontSize = 24.sp),
                            color = semantics.ink400,
                        )
                    }
                    Text(
                        text = stringResource(Res.string.quiz_result_percent_correct, percent),
                        style = BrainDropTheme.type.label.copy(letterSpacing = 0.88.sp),
                        color = ringColor,
                    )
                }
            }
            Spacer(Modifier.height(18.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.xs),
            ) {
                ResultTile(
                    value = "${state.streakDays}",
                    caption = pluralStringResource(Res.plurals.quiz_tile_streak_caption, state.streakDays),
                    modifier = Modifier.weight(1f),
                )
                ResultTile(
                    value = formatElapsed(state.elapsedSeconds),
                    caption = stringResource(Res.string.quiz_tile_time_label),
                    modifier = Modifier.weight(1f),
                )
                ResultTile(
                    value = "+${state.score}",
                    caption = stringResource(Res.string.quiz_tile_progress_label),
                    valueColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f),
                )
            }
        }

        if (state.mistakes.isNotEmpty()) {
            Text(
                text = stringResource(Res.string.quiz_mistakes_section_title, state.mistakes.size),
                style = BrainDropTheme.type.label,
                color = semantics.ink400,
                modifier = Modifier.padding(top = 22.dp, bottom = BrainDropTheme.spacing.sm),
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .brainDropCard(BrainDropTheme.shapes.lg),
            ) {
                state.mistakes.forEachIndexed { index, mistake ->
                    MistakeRow(mistake = mistake, onClick = { onMistakeClick(mistake.verb.id) })
                    if (index != state.mistakes.lastIndex) {
                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.outlineVariant,
                            modifier = Modifier.padding(start = BrainDropTheme.spacing.md),
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(BrainDropTheme.spacing.md))
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            if (state.mistakes.isNotEmpty()) {
                BrainDropButton(
                    text = stringResource(Res.string.quiz_retry_mistakes),
                    onClick = onRetryMistakes,
                    height = 56.dp,
                    cornerRadius = 16.dp,
                    fillWidth = true,
                )
            } else {
                BrainDropButton(
                    text = stringResource(Res.string.quiz_retry_again),
                    onClick = onRestart,
                    height = 56.dp,
                    cornerRadius = 16.dp,
                    fillWidth = true,
                )
            }
            BrainDropButton(
                text = stringResource(Res.string.quiz_to_list),
                onClick = onBack,
                style = BrainDropButtonStyle.TEXT,
                height = 52.dp,
                fillWidth = true,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Spacer(Modifier.height(6.dp))
    }
}

@Composable
private fun ResultTile(
    value: String,
    caption: String,
    modifier: Modifier = Modifier,
    valueColor: Color = Color.Unspecified,
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background, BrainDropTheme.shapes.md)
            .padding(vertical = BrainDropTheme.spacing.sm, horizontal = BrainDropTheme.spacing.xs),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp),
            color = if (valueColor == Color.Unspecified) MaterialTheme.colorScheme.onSurface else valueColor,
        )
        Text(
            text = caption,
            style = MaterialTheme.typography.labelMedium.copy(fontSize = 11.sp),
            color = BrainDropTheme.semantics.ink400,
        )
    }
}

@Composable
private fun MistakeRow(
    mistake: QuizMistake,
    onClick: () -> Unit,
) {
    val semantics = BrainDropTheme.semantics
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = BrainDropTheme.spacing.md, vertical = 13.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.sm),
    ) {
        Box(
            modifier = Modifier.size(22.dp).background(semantics.incorrectTint, BrainDropTheme.shapes.chip),
            contentAlignment = Alignment.Center,
        ) { BrainDropIcons.Close(iconSize = 11.dp, tint = semantics.incorrect, strokeWidth = 2.dp) }
        Column(modifier = Modifier.weight(1f)) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.xs),
                verticalAlignment = Alignment.Bottom,
            ) {
                Text(
                    text = mistake.verb.baseForm,
                    style = BrainDropTheme.type.verbBase.copy(fontSize = 15.sp),
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = verbFormsLine(mistake.verb),
                    style = BrainDropTheme.type.verbForms.copy(fontSize = 13.sp),
                    color = semantics.ink400,
                )
            }
            Text(
                text = stringResource(
                    Res.string.quiz_mistake_row_format,
                    mistake.verb.translation,
                    mistake.userAnswerText,
                ),
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.5.sp),
                color = semantics.ink500,
            )
        }
        BrainDropIcons.ChevronRight(iconSize = 17.dp, tint = semantics.answerIdleOutline, strokeWidth = 2.dp)
    }
}

@Composable
private fun ScoreRing(
    ratio: Float,
    ringColor: Color,
    trackColor: Color,
    modifier: Modifier = Modifier,
    ringSize: Dp = 148.dp,
    strokeWidth: Dp = 14.dp,
    content: @Composable () -> Unit,
) {
    val animatedRatio = remember { Animatable(0f) }
    LaunchedEffect(ratio) {
        animatedRatio.animateTo(ratio, animationSpec = tween(700, easing = FastOutSlowInEasing))
    }
    Box(modifier = modifier.size(ringSize), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokePx = strokeWidth.toPx()
            val inset = strokePx / 2
            val arcSize = Size(size.width - strokePx, size.height - strokePx)
            val topLeft = Offset(inset, inset)
            val stroke = Stroke(width = strokePx, cap = StrokeCap.Round)
            drawArc(
                color = trackColor,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = stroke,
            )
            drawArc(
                color = ringColor,
                startAngle = -90f,
                sweepAngle = 360f * animatedRatio.value,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = stroke,
            )
        }
        content()
    }
}

private fun formatElapsed(totalSeconds: Int): String {
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "$minutes:${seconds.toString().padStart(2, '0')}"
}
