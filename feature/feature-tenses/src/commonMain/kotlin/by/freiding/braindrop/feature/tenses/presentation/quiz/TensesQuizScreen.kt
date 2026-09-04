package by.freiding.braindrop.feature.tenses.presentation.quiz

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
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
import by.freiding.braindrop.feature.tenses.Res
import by.freiding.braindrop.feature.tenses.cd_close
import by.freiding.braindrop.feature.tenses.domain.model.TenseQuizType
import by.freiding.braindrop.feature.tenses.error_retry
import by.freiding.braindrop.feature.tenses.presentation.common.formatTenseId
import by.freiding.braindrop.feature.tenses.tenses_quiz_empty_action
import by.freiding.braindrop.feature.tenses.tenses_quiz_empty_body
import by.freiding.braindrop.feature.tenses.tenses_quiz_empty_title
import by.freiding.braindrop.feature.tenses.tenses_quiz_error_body
import by.freiding.braindrop.feature.tenses.tenses_quiz_error_title
import by.freiding.braindrop.feature.tenses.tenses_quiz_footer_hint
import by.freiding.braindrop.feature.tenses.tenses_quiz_mistake_row_format
import by.freiding.braindrop.feature.tenses.tenses_quiz_mistakes_section_title
import by.freiding.braindrop.feature.tenses.tenses_quiz_next
import by.freiding.braindrop.feature.tenses.tenses_quiz_progress_count
import by.freiding.braindrop.feature.tenses.tenses_quiz_result_percent_correct
import by.freiding.braindrop.feature.tenses.tenses_quiz_result_subtitle_mistakes
import by.freiding.braindrop.feature.tenses.tenses_quiz_result_subtitle_perfect
import by.freiding.braindrop.feature.tenses.tenses_quiz_result_title_good
import by.freiding.braindrop.feature.tenses.tenses_quiz_result_title_great
import by.freiding.braindrop.feature.tenses.tenses_quiz_result_title_meh
import by.freiding.braindrop.feature.tenses.tenses_quiz_retry_again
import by.freiding.braindrop.feature.tenses.tenses_quiz_retry_mistakes
import by.freiding.braindrop.feature.tenses.tenses_quiz_tile_progress_label
import by.freiding.braindrop.feature.tenses.tenses_quiz_tile_streak_caption
import by.freiding.braindrop.feature.tenses.tenses_quiz_tile_time_label
import by.freiding.braindrop.feature.tenses.tenses_quiz_to_list
import by.freiding.braindrop.feature.tenses.tenses_quiz_your_answer
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun TensesQuizScreen(
    mode: String,
    navController: NavController,
    viewModel: TensesQuizViewModel = koinViewModel(parameters = { parametersOf(TenseQuizType.fromString(mode)) }),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is TensesQuizUiEffect.NavigateBack -> navController.popBackStack()
                is TensesQuizUiEffect.NavigateToTenseDetail ->
                    navController.navigate(Routes.TenseDetail(effect.tenseId))
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        when {
            state.isLoading -> BrainDropLoadingIndicator()

            state.error != null -> ErrorStatusCard(
                title = stringResource(Res.string.tenses_quiz_error_title),
                body = stringResource(Res.string.tenses_quiz_error_body),
                retryText = stringResource(Res.string.error_retry),
                onRetry = { viewModel.onEvent(TensesQuizUiEvent.RestartQuiz) },
                secondaryText = stringResource(Res.string.tenses_quiz_to_list),
                onSecondary = { viewModel.onEvent(TensesQuizUiEvent.NavigateBack) },
            )

            state.isEmpty -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                StatusCard(
                    icon = { BrainDropIcons.Check(iconSize = 30.dp, tint = BrainDropTheme.semantics.correct) },
                    iconBackground = BrainDropTheme.semantics.correctTint,
                    title = stringResource(Res.string.tenses_quiz_empty_title),
                    body = stringResource(Res.string.tenses_quiz_empty_body),
                    primaryAction = {
                        BrainDropButton(
                            text = stringResource(Res.string.tenses_quiz_empty_action),
                            style = BrainDropButtonStyle.OUTLINED,
                            onClick = { viewModel.onEvent(TensesQuizUiEvent.NavigateBack) },
                        )
                    },
                )
            }

            state.isFinished -> QuizResultContent(
                state = state,
                onRetryMistakes = { viewModel.onEvent(TensesQuizUiEvent.RetryMistakes) },
                onRestart = { viewModel.onEvent(TensesQuizUiEvent.RestartQuiz) },
                onBack = { viewModel.onEvent(TensesQuizUiEvent.NavigateBack) },
                onMistakeClick = { tenseId -> viewModel.onEvent(TensesQuizUiEvent.MistakeClicked(tenseId)) },
            )

            state.currentQuestion != null -> QuizQuestionContent(
                state = state,
                onClose = { viewModel.onEvent(TensesQuizUiEvent.NavigateBack) },
                onAnswerSelected = { viewModel.onEvent(TensesQuizUiEvent.AnswerSelected(it)) },
                onNext = { viewModel.onEvent(TensesQuizUiEvent.NextQuestion) },
            )
        }
    }
}

@Composable
private fun QuizQuestionContent(
    state: TensesQuizUiState,
    onClose: () -> Unit,
    onAnswerSelected: (String) -> Unit,
    onNext: () -> Unit,
) {
    val question = state.currentQuestion ?: return
    val tense = state.tensesById[question.tenseId]
    val semanticsForSegments = BrainDropTheme.semantics
    val currentColor = MaterialTheme.colorScheme.primary
    val aheadColor = MaterialTheme.colorScheme.outline
    val showMistake = state.isAnswered && state.selectedAnswer != question.correctAnswer

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(
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
                text = stringResource(
                    Res.string.tenses_quiz_progress_count,
                    state.currentIndex + 1,
                    state.totalQuestions,
                ),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        // The question pane is the only weighted child here, so it always gets exactly
        // (available height - options/mistake card's natural height), regardless of whether that
        // content is short (empty space stays inside the scrollable pane, under the card) or tall
        // (the pane scrolls internally). Options never move when the mistake card appears below
        // them — unlike a Spacer(weight(1f)) inside a verticalScroll'd Column sized via
        // heightIn(min = ...), which only behaves once total content fits under that minimum and
        // otherwise snaps to packed layout, jumping the options up right as the mistake card
        // appears.
        Column(modifier = Modifier.weight(1f).fillMaxWidth().padding(horizontal = 20.dp)) {
            Spacer(Modifier.height(BrainDropTheme.spacing.xs))
            Column(
                modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()),
            ) {
                QuestionCard(question = question, tense = tense)
            }
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                question.options.forEach { option ->
                    val optionState = when {
                        !state.isAnswered -> AnswerOptionState.IDLE
                        option == question.correctAnswer -> AnswerOptionState.CORRECT
                        option == state.selectedAnswer -> AnswerOptionState.WRONG_SELECTED
                        else -> AnswerOptionState.MUTED
                    }
                    AnswerButton(
                        text = option,
                        state = optionState,
                        mono = question.type != TenseQuizType.MARKER_MATCH,
                        onClick = { onAnswerSelected(option) },
                    )
                }
            }
            if (showMistake) {
                Spacer(Modifier.height(BrainDropTheme.spacing.md))
                MistakeBreakdownCard(question = question, tense = tense)
            }
            Spacer(Modifier.height(BrainDropTheme.spacing.sm))
        }

        QuizFooter(isAnswered = state.isAnswered, onNext = onNext)
    }
}

private enum class AnswerOptionState { IDLE, CORRECT, WRONG_SELECTED, MUTED }

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
    mono: Boolean,
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
            style = (if (mono) BrainDropTheme.type.verbForms else MaterialTheme.typography.bodyLarge).copy(
                fontSize = 15.5.sp,
                fontWeight = if (state == AnswerOptionState.IDLE) FontWeight.SemiBold else FontWeight.ExtraBold,
            ),
            color = textColor.copy(alpha = textAlpha),
            modifier = Modifier.weight(1f),
        )
        if (state == AnswerOptionState.WRONG_SELECTED) {
            Text(
                text = stringResource(Res.string.tenses_quiz_your_answer),
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
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .height(
                BrainDropTheme.spacing.quizFooterHeight,
            ).padding(horizontal = 20.dp),
        contentAlignment = Alignment.Center,
    ) {
        AnimatedContent(
            targetState = isAnswered,
            transitionSpec = { fadeIn(tween(200)) togetherWith fadeOut(tween(200)) },
            label = "quizFooter",
        ) { answered ->
            if (!answered) {
                Text(
                    text = stringResource(Res.string.tenses_quiz_footer_hint),
                    style = MaterialTheme.typography.bodyMedium,
                    color = BrainDropTheme.semantics.ink400,
                    textAlign = TextAlign.Center,
                )
            } else {
                BrainDropButton(
                    text = stringResource(Res.string.tenses_quiz_next),
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
    state: TensesQuizUiState,
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
        ratio >= 0.9f -> stringResource(Res.string.tenses_quiz_result_title_great)
        ratio >= 0.6f -> stringResource(Res.string.tenses_quiz_result_title_good)
        else -> stringResource(Res.string.tenses_quiz_result_title_meh)
    }
    val subtitle = if (state.mistakes.isEmpty()) {
        stringResource(Res.string.tenses_quiz_result_subtitle_perfect)
    } else {
        pluralStringResource(Res.plurals.tenses_quiz_result_subtitle_mistakes, state.mistakes.size, state.mistakes.size)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .navigationBarsPadding()
            .padding(20.dp),
    ) {
        ScoreSummaryCard(
            state = state,
            total = total,
            ratio = ratio,
            ringColor = ringColor,
            percent = percent,
            title = title,
            subtitle = subtitle,
        )

        if (state.mistakes.isNotEmpty()) {
            MistakesSection(mistakes = state.mistakes, onMistakeClick = onMistakeClick)
        }

        Spacer(Modifier.height(BrainDropTheme.spacing.md))
        ResultActions(
            hasMistakes = state.mistakes.isNotEmpty(),
            onRetryMistakes = onRetryMistakes,
            onRestart = onRestart,
            onBack = onBack,
        )
        Spacer(Modifier.height(6.dp))
    }
}

@Composable
private fun ScoreSummaryCard(
    state: TensesQuizUiState,
    total: Int,
    ratio: Float,
    ringColor: Color,
    percent: Int,
    title: String,
    subtitle: String,
) {
    val semantics = BrainDropTheme.semantics
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
                    text = stringResource(Res.string.tenses_quiz_result_percent_correct, percent),
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
                caption = pluralStringResource(Res.plurals.tenses_quiz_tile_streak_caption, state.streakDays),
                modifier = Modifier.weight(1f),
            )
            ResultTile(
                value = formatElapsed(state.elapsedSeconds),
                caption = stringResource(Res.string.tenses_quiz_tile_time_label),
                modifier = Modifier.weight(1f),
            )
            ResultTile(
                value = "${state.score}",
                caption = stringResource(Res.string.tenses_quiz_tile_progress_label),
                valueColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun MistakesSection(
    mistakes: List<TenseQuizMistake>,
    onMistakeClick: (String) -> Unit,
) {
    Text(
        text = stringResource(Res.string.tenses_quiz_mistakes_section_title, mistakes.size),
        style = BrainDropTheme.type.label,
        color = BrainDropTheme.semantics.ink400,
        modifier = Modifier.padding(top = 22.dp, bottom = BrainDropTheme.spacing.sm),
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .brainDropCard(BrainDropTheme.shapes.lg),
    ) {
        mistakes.forEachIndexed { index, mistake ->
            MistakeRow(mistake = mistake, onClick = { onMistakeClick(mistake.question.tenseId) })
            if (index != mistakes.lastIndex) {
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.outlineVariant,
                    modifier = Modifier.padding(start = BrainDropTheme.spacing.md),
                )
            }
        }
    }
}

@Composable
private fun ResultActions(
    hasMistakes: Boolean,
    onRetryMistakes: () -> Unit,
    onRestart: () -> Unit,
    onBack: () -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        if (hasMistakes) {
            BrainDropButton(
                text = stringResource(Res.string.tenses_quiz_retry_mistakes),
                onClick = onRetryMistakes,
                height = 56.dp,
                cornerRadius = 16.dp,
                fillWidth = true,
            )
        } else {
            BrainDropButton(
                text = stringResource(Res.string.tenses_quiz_retry_again),
                onClick = onRestart,
                height = 56.dp,
                cornerRadius = 16.dp,
                fillWidth = true,
            )
        }
        BrainDropButton(
            text = stringResource(Res.string.tenses_quiz_to_list),
            onClick = onBack,
            style = BrainDropButtonStyle.TEXT,
            height = 52.dp,
            fillWidth = true,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        )
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
    mistake: TenseQuizMistake,
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
            Text(
                text = formatTenseId(mistake.question.tenseId),
                style = BrainDropTheme.type.verbBase.copy(fontSize = 15.sp),
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = stringResource(
                    Res.string.tenses_quiz_mistake_row_format,
                    mistake.question.correctAnswer,
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
