package by.freiding.braindrop.feature.phrasalverbs.presentation.quiz

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import by.freiding.braindrop.core.navigation.Routes
import by.freiding.braindrop.core.ui.BrainDropTheme
import by.freiding.braindrop.core.ui.component.BrainDropButton
import by.freiding.braindrop.core.ui.component.BrainDropButtonStyle
import by.freiding.braindrop.core.ui.component.BrainDropIconButton
import by.freiding.braindrop.core.ui.icon.BrainDropIcons
import by.freiding.braindrop.feature.phrasalverbs.domain.model.PhrasalVerbQuizType
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun PhrasalVerbsQuizScreen(
    mode: String,
    navController: NavController,
    viewModel: PhrasalVerbsQuizViewModel = koinViewModel {
        val type = runCatching { PhrasalVerbQuizType.valueOf(mode) }
            .getOrDefault(PhrasalVerbQuizType.DEFINITION_TO_VERB)
        parametersOf(type)
    },
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is PhrasalVerbsQuizUiEffect.NavigateBack -> navController.popBackStack()
                is PhrasalVerbsQuizUiEffect.NavigateToDetail ->
                    navController.navigate(Routes.PhrasalVerbDetail(effect.verbId))
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        QuizHeader(
            current = state.currentIndex + 1,
            total = state.totalQuestions,
            elapsedSeconds = state.elapsedSeconds,
            onBack = { viewModel.onEvent(PhrasalVerbsQuizUiEvent.NavigateBack) },
        )

        when {
            state.error != null -> QuizError(
                message = state.error ?: "Ошибка",
                onBack = { viewModel.onEvent(PhrasalVerbsQuizUiEvent.NavigateBack) },
            )
            state.isLoading -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Загрузка...", style = MaterialTheme.typography.bodyLarge)
            }
            state.isEmpty -> QuizEmpty(
                onBack = { viewModel.onEvent(PhrasalVerbsQuizUiEvent.NavigateBack) },
            )
            state.isFinished -> QuizResult(
                state = state,
                onRetryMistakes = { viewModel.onEvent(PhrasalVerbsQuizUiEvent.RetryMistakes) },
                onRestart = { viewModel.onEvent(PhrasalVerbsQuizUiEvent.RestartQuiz) },
                onNavigateBack = { viewModel.onEvent(PhrasalVerbsQuizUiEvent.NavigateBack) },
                onMistakeClicked = { verbId -> viewModel.onEvent(PhrasalVerbsQuizUiEvent.MistakeClicked(verbId)) },
            )
            state.currentQuestion != null -> QuizQuestion(
                state = state,
                onAnswerSelected = { answer -> viewModel.onEvent(PhrasalVerbsQuizUiEvent.AnswerSelected(answer)) },
                onNext = { viewModel.onEvent(PhrasalVerbsQuizUiEvent.NextQuestion) },
            )
        }
    }
}

@Composable
private fun QuizHeader(
    current: Int,
    total: Int,
    elapsedSeconds: Int,
    onBack: () -> Unit,
) {
    val minutes = elapsedSeconds / 60
    val seconds = elapsedSeconds % 60
    val secondsPadded = if (seconds < 10) "0$seconds" else "$seconds"
    val timeText = "$minutes:$secondsPadded"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .statusBarsPadding()
            .padding(horizontal = BrainDropTheme.spacing.xs, vertical = BrainDropTheme.spacing.xxs),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BrainDropIconButton(onClick = onBack, contentDescription = "Назад") {
            BrainDropIcons.ChevronLeft(iconSize = 22.dp, tint = MaterialTheme.colorScheme.onSurface)
        }
        Text(
            text = "Phrasal Verbs Quiz",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.weight(1f).padding(start = BrainDropTheme.spacing.xxs),
        )
        Column(horizontalAlignment = Alignment.End) {
            if (total > 0) {
                Text(
                    text = "$current/$total",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
            Text(text = timeText, style = BrainDropTheme.type.counter, color = BrainDropTheme.semantics.ink400)
        }
    }
}

@Composable
private fun QuizQuestion(
    state: PhrasalVerbsQuizUiState,
    onAnswerSelected: (String) -> Unit,
    onNext: () -> Unit,
) {
    val question = state.currentQuestion ?: return
    val semantics = BrainDropTheme.semantics

    Column(
        modifier = Modifier.fillMaxSize().navigationBarsPadding().padding(BrainDropTheme.spacing.md),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.sm)) {
            val promptLabel = when (question.type) {
                PhrasalVerbQuizType.DEFINITION_TO_VERB -> "ВЫБЕРИ ФРАЗОВЫЙ ГЛАГОЛ"
                PhrasalVerbQuizType.VERB_TO_TRANSLATION -> "ПЕРЕВЕДИ НА РУССКИЙ"
                PhrasalVerbQuizType.FILL_PARTICLE -> "ВЫБЕРИ ЧАСТИЦУ"
            }
            Text(
                text = promptLabel,
                style = BrainDropTheme.type.label,
                color = semantics.ink400,
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(BrainDropTheme.shapes.card)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(BrainDropTheme.spacing.lg),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = question.questionText,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            Spacer(Modifier.height(BrainDropTheme.spacing.xs))

            question.options.forEach { option ->
                val isSelected = state.selectedAnswer == option
                val isCorrect = option == question.correctAnswer
                val backgroundColor = when {
                    !state.isAnswered -> MaterialTheme.colorScheme.surface
                    isCorrect -> semantics.correctTint
                    isSelected -> semantics.incorrectTint
                    else -> MaterialTheme.colorScheme.surface
                }
                val borderColor = when {
                    !state.isAnswered -> MaterialTheme.colorScheme.outline
                    isCorrect -> semantics.correct
                    isSelected -> semantics.incorrect
                    else -> MaterialTheme.colorScheme.outlineVariant
                }
                val contentColor = when {
                    !state.isAnswered -> MaterialTheme.colorScheme.onSurface
                    isCorrect -> semantics.correct
                    isSelected -> semantics.incorrect
                    else -> MaterialTheme.colorScheme.onSurface
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(BrainDropTheme.shapes.md)
                        .background(backgroundColor, BrainDropTheme.shapes.md)
                        .border(1.5.dp, borderColor, BrainDropTheme.shapes.md)
                        .clickable(enabled = !state.isAnswered) { onAnswerSelected(option) }
                        .padding(horizontal = BrainDropTheme.spacing.md, vertical = BrainDropTheme.spacing.sm),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = option,
                        style = MaterialTheme.typography.bodyLarge,
                        color = contentColor,
                        fontWeight = if (isSelected ||
                            isCorrect &&
                            state.isAnswered
                        ) {
                            FontWeight.Bold
                        } else {
                            FontWeight.Normal
                        },
                    )
                    if (state.isAnswered && isCorrect) {
                        BrainDropIcons.Check(iconSize = 18.dp, tint = semantics.correct)
                    } else if (state.isAnswered && isSelected && !isCorrect) {
                        BrainDropIcons.Close(iconSize = 18.dp, tint = semantics.incorrect)
                    }
                }
            }
        }

        if (state.isAnswered) {
            BrainDropButton(
                text = if (state.currentIndex + 1 < state.totalQuestions) "Дальше" else "Завершить",
                onClick = onNext,
                modifier = Modifier.fillMaxWidth(),
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(BrainDropTheme.spacing.quizFooterHeight)
                    .clip(BrainDropTheme.shapes.md)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Выберите вариант — кнопка появится здесь",
                    style = MaterialTheme.typography.bodyMedium,
                    color = semantics.ink400,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
private fun QuizResult(
    state: PhrasalVerbsQuizUiState,
    onRetryMistakes: () -> Unit,
    onRestart: () -> Unit,
    onNavigateBack: () -> Unit,
    onMistakeClicked: (String) -> Unit,
) {
    val semantics = BrainDropTheme.semantics
    val ratio = if (state.totalQuestions > 0) state.score.toFloat() / state.totalQuestions else 0f
    val percent = (ratio * 100).toInt()

    val titleText = when {
        percent == 100 -> "Отличная сессия!"
        percent >= 70 -> "Хорошая сессия!"
        else -> "Есть над чем поработать"
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().navigationBarsPadding().padding(BrainDropTheme.spacing.md),
        verticalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.md),
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.xs),
            ) {
                Text(
                    text = "${state.score}/${state.totalQuestions}",
                    style = MaterialTheme.typography.displaySmall.copy(fontSize = 40.sp),
                    fontWeight = FontWeight.ExtraBold,
                    color = semantics.scoreRing(ratio),
                )
                Text(text = titleText, style = MaterialTheme.typography.titleLarge)
                Text(
                    text = "$percent% верно",
                    style = BrainDropTheme.type.label,
                    color = semantics.ink400,
                )
                if (state.streakDays > 0) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        BrainDropIcons.Flame(iconSize = 18.dp, tint = semantics.streak)
                        Text(
                            text = "${state.streakDays} дн. подряд",
                            style = MaterialTheme.typography.bodyMedium,
                            color = semantics.streak,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }
            }
        }

        if (state.mistakes.isNotEmpty()) {
            item {
                Text(
                    text = "ОШИБКИ · ${state.mistakes.size}",
                    style = BrainDropTheme.type.label,
                    color = semantics.ink400,
                )
            }
            items(state.mistakes) { mistake ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(BrainDropTheme.shapes.md)
                        .background(MaterialTheme.colorScheme.surface)
                        .border(1.dp, MaterialTheme.colorScheme.outlineVariant, BrainDropTheme.shapes.md)
                        .clickable { onMistakeClicked(mistake.verb.id) }
                        .padding(BrainDropTheme.spacing.sm),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column {
                        Text(
                            text = mistake.verb.fullForm,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = "вы ответили: «${mistake.userAnswerText}»",
                            style = MaterialTheme.typography.bodySmall,
                            color = semantics.ink500,
                        )
                    }
                    BrainDropIcons.ChevronRight(iconSize = 16.dp, tint = semantics.ink400)
                }
            }
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.xs)) {
                if (state.mistakes.isNotEmpty()) {
                    BrainDropButton(
                        text = "Пройти ошибки ещё раз",
                        onClick = onRetryMistakes,
                        modifier = Modifier.fillMaxWidth(),
                        style = BrainDropButtonStyle.FILLED,
                    )
                }
                BrainDropButton(
                    text = "Ещё раз",
                    onClick = onRestart,
                    modifier = Modifier.fillMaxWidth(),
                    style = BrainDropButtonStyle.OUTLINED,
                )
                BrainDropButton(
                    text = "К списку глаголов",
                    onClick = onNavigateBack,
                    modifier = Modifier.fillMaxWidth(),
                    style = BrainDropButtonStyle.OUTLINED,
                )
            }
        }
    }
}

@Composable
private fun QuizEmpty(onBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(BrainDropTheme.spacing.lg),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        BrainDropIcons.Check(iconSize = 40.dp, tint = BrainDropTheme.semantics.correct)
        Spacer(Modifier.height(BrainDropTheme.spacing.md))
        Text(
            text = "Нечего спрашивать — всё изучено",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(BrainDropTheme.spacing.xs))
        Text(
            text = "Вы уже знаете все глаголы в этом наборе.",
            style = MaterialTheme.typography.bodyMedium,
            color = BrainDropTheme.semantics.ink500,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(BrainDropTheme.spacing.lg))
        BrainDropButton(
            text = "К списку",
            onClick = onBack,
            modifier = Modifier.fillMaxWidth(),
            style = BrainDropButtonStyle.OUTLINED,
        )
    }
}

@Composable
private fun QuizError(
    message: String,
    onBack: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(BrainDropTheme.spacing.lg),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        BrainDropIcons.Alert(iconSize = 40.dp, tint = BrainDropTheme.semantics.incorrect)
        Spacer(Modifier.height(BrainDropTheme.spacing.md))
        Text(text = "Не удалось загрузить квиз", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(BrainDropTheme.spacing.xs))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = BrainDropTheme.semantics.ink500,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(BrainDropTheme.spacing.lg))
        BrainDropButton(
            text = "Назад",
            onClick = onBack,
            modifier = Modifier.fillMaxWidth(),
            style = BrainDropButtonStyle.OUTLINED,
        )
    }
}
