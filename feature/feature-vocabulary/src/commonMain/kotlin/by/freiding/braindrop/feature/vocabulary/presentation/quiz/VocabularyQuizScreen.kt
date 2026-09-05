package by.freiding.braindrop.feature.vocabulary.presentation.quiz

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import by.freiding.braindrop.core.common.AppClock
import by.freiding.braindrop.core.navigation.Routes
import by.freiding.braindrop.core.ui.BrainDropTheme
import by.freiding.braindrop.core.ui.component.BrainDropButton
import by.freiding.braindrop.core.ui.component.BrainDropButtonStyle
import by.freiding.braindrop.core.ui.component.BrainDropIconButton
import by.freiding.braindrop.core.ui.icon.BrainDropIcons
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkProgress
import by.freiding.braindrop.feature.vocabulary.domain.model.VocabularyQuizMode
import by.freiding.braindrop.feature.vocabulary.domain.model.VocabularyQuizQuestion
import by.freiding.braindrop.feature.vocabulary.presentation.common.highlightGap
import kotlinx.datetime.LocalDate
import kotlinx.datetime.daysUntil
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun VocabularyQuizScreen(
    mode: String,
    navController: NavController,
    viewModel: VocabularyQuizViewModel = koinViewModel {
        parametersOf(VocabularyQuizMode.fromRoute(mode))
    },
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is VocabularyQuizUiEffect.NavigateBack -> navController.popBackStack()
                is VocabularyQuizUiEffect.NavigateToDetail ->
                    navController.navigate(Routes.VocabularyChunkDetail(effect.chunkId))
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        QuizHeader(
            position = state.position,
            total = state.total,
            elapsedSeconds = state.elapsedSeconds,
            showProgress = !state.isFinished,
            onBack = { viewModel.onEvent(VocabularyQuizUiEvent.NavigateBack) },
        )

        when {
            state.error != null -> QuizMessage(
                title = "Не удалось загрузить квиз",
                body = state.error.orEmpty(),
                onBack = { viewModel.onEvent(VocabularyQuizUiEvent.NavigateBack) },
            )
            state.isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Загрузка…", style = MaterialTheme.typography.bodyLarge)
            }
            state.isEmpty -> QuizMessage(
                title = "Нечего спрашивать",
                body = "Добавьте чанки в изучение и возвращайтесь.",
                onBack = { viewModel.onEvent(VocabularyQuizUiEvent.NavigateBack) },
            )
            state.isFinished -> QuizResult(
                state = state,
                onRetryMistakes = { viewModel.onEvent(VocabularyQuizUiEvent.RetryMistakes) },
                onRestart = { viewModel.onEvent(VocabularyQuizUiEvent.Restart) },
                onBack = { viewModel.onEvent(VocabularyQuizUiEvent.NavigateBack) },
                onMistakeClicked = { id -> viewModel.onEvent(VocabularyQuizUiEvent.MistakeClicked(id)) },
            )
            state.currentQuestion != null -> QuizQuestion(state = state, onEvent = viewModel::onEvent)
        }
    }
}

@Composable
private fun QuizHeader(
    position: Int,
    total: Int,
    elapsedSeconds: Int,
    showProgress: Boolean,
    onBack: () -> Unit,
) {
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
            text = "Vocabulary Quiz",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.weight(1f).padding(start = BrainDropTheme.spacing.xxs),
        )
        if (showProgress && total > 0) {
            Column(horizontalAlignment = Alignment.End, modifier = Modifier.padding(end = BrainDropTheme.spacing.sm)) {
                Text(
                    text = "$position/$total",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = timeText(elapsedSeconds),
                    style = BrainDropTheme.type.counter,
                    color = BrainDropTheme.semantics.ink400,
                )
            }
        }
    }
}

@Composable
private fun QuizQuestion(
    state: VocabularyQuizUiState,
    onEvent: (VocabularyQuizUiEvent) -> Unit,
) {
    val question = state.currentQuestion ?: return
    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .padding(BrainDropTheme.spacing.md),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.sm),
        ) {
            if (state.isCloze) {
                ClozeBody(
                    state = state,
                    question = question,
                    onSelect = { onEvent(VocabularyQuizUiEvent.OptionSelected(it)) },
                )
            } else {
                TypingBody(state = state, question = question, onEvent = onEvent)
            }
        }

        QuizFooter(state = state, onEvent = onEvent)
    }
}

@Composable
private fun ClozeBody(
    state: VocabularyQuizUiState,
    question: VocabularyQuizQuestion,
    onSelect: (String) -> Unit,
) {
    val semantics = BrainDropTheme.semantics
    Text(text = "ЗАПОЛНИ ПРОПУСК", style = BrainDropTheme.type.label, color = semantics.ink400)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(BrainDropTheme.shapes.card)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(BrainDropTheme.spacing.lg),
    ) {
        Column {
            Text(
                text = highlightGap(question.sentence, "____", MaterialTheme.colorScheme.primary),
                style = MaterialTheme.typography.headlineSmall.copy(fontSize = 20.sp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = question.sentenceTranslation,
                style = MaterialTheme.typography.bodySmall,
                color = semantics.ink500,
                textAlign = TextAlign.Center,
            )
        }
    }

    question.options.forEach { option ->
        ClozeOption(
            option = option,
            checked = state.checked,
            isCorrect = option == question.correctAnswer,
            isPicked = state.selectedOption == option,
            onSelect = { onSelect(option) },
        )
    }

    if (state.checked && question.explanation != null) {
        ExplanationCard(question.explanation)
    }
}

@Composable
private fun ClozeOption(
    option: String,
    checked: Boolean,
    isCorrect: Boolean,
    isPicked: Boolean,
    onSelect: () -> Unit,
) {
    val semantics = BrainDropTheme.semantics
    val container = when {
        !checked -> MaterialTheme.colorScheme.surface
        isCorrect -> semantics.correctTint
        isPicked -> semantics.incorrectTint
        else -> MaterialTheme.colorScheme.surface
    }
    val border = when {
        !checked -> MaterialTheme.colorScheme.outline
        isCorrect -> semantics.correct
        isPicked -> semantics.incorrect
        else -> MaterialTheme.colorScheme.outlineVariant
    }
    val content = if (checked && isCorrect) {
        semantics.correct
    } else if (checked && isPicked) {
        semantics.incorrect
    } else {
        MaterialTheme.colorScheme.onSurface
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(BrainDropTheme.shapes.md)
            .background(container, BrainDropTheme.shapes.md)
            .border(1.5.dp, border, BrainDropTheme.shapes.md)
            .clickable(enabled = !checked, onClick = onSelect)
            .padding(horizontal = BrainDropTheme.spacing.md, vertical = BrainDropTheme.spacing.sm),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = option,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = if (checked && (isCorrect || isPicked)) FontWeight.Bold else FontWeight.Normal,
            color = content,
        )
        if (checked && isCorrect) {
            BrainDropIcons.Check(iconSize = 18.dp, tint = semantics.correct)
        } else if (checked && isPicked) {
            BrainDropIcons.Close(iconSize = 18.dp, tint = semantics.incorrect)
        }
    }
}

@Composable
private fun TypingBody(
    state: VocabularyQuizUiState,
    question: VocabularyQuizQuestion,
    onEvent: (VocabularyQuizUiEvent) -> Unit,
) {
    val semantics = BrainDropTheme.semantics
    Text(text = "НАПИШИ ЧАНК ПО-АНГЛИЙСКИ", style = BrainDropTheme.type.label, color = semantics.ink400)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(BrainDropTheme.shapes.card)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(BrainDropTheme.spacing.lg),
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = question.chunk.translation,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = "${question.maskedPattern.orEmpty()} · ${question.chunk.wordCount} слова",
                style = BrainDropTheme.type.counter,
                color = semantics.ink500,
            )
        }
    }

    val fieldBorder = when {
        !state.checked -> MaterialTheme.colorScheme.primary
        state.lastAnswerCorrect -> semantics.correct
        else -> semantics.incorrect
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(BrainDropTheme.shapes.md)
            .background(MaterialTheme.colorScheme.surface)
            .border(1.5.dp, fieldBorder, BrainDropTheme.shapes.md)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(modifier = Modifier.weight(1f)) {
            if (state.typedAnswer.isEmpty() && !state.checked) {
                Text(
                    text = hintPrefix(question, state.hintLevel),
                    style = MaterialTheme.typography.titleSmall,
                    color = semantics.ink400,
                )
            }
            BasicTextField(
                value = state.typedAnswer,
                onValueChange = { onEvent(VocabularyQuizUiEvent.TypedAnswerChanged(it)) },
                enabled = !state.checked,
                singleLine = true,
                textStyle = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.onSurface),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    imeAction = ImeAction.Done,
                ),
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }

    if (!state.checked) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            HintChip("Показать первую букву") { onEvent(VocabularyQuizUiEvent.RevealHint) }
            HintChip("Не помню") { onEvent(VocabularyQuizUiEvent.GiveUp) }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(BrainDropTheme.shapes.md)
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 14.dp, vertical = 12.dp),
        ) {
            Text(text = "КОНТЕКСТ, ЕСЛИ ЗАСТРЯЛ", style = BrainDropTheme.type.label, color = semantics.ink400)
            Spacer(Modifier.height(8.dp))
            Text(
                text = question.sentence,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(BrainDropTheme.shapes.md)
                .background(if (state.lastAnswerCorrect) semantics.correctTint else semantics.incorrectTint)
                .padding(horizontal = 14.dp, vertical = 12.dp),
        ) {
            Text(
                text = if (state.lastAnswerCorrect) "Верно" else "Правильный ответ",
                style = BrainDropTheme.type.label,
                color = if (state.lastAnswerCorrect) semantics.correctInk else semantics.incorrectInk,
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = question.correctAnswer,
                style = MaterialTheme.typography.titleSmall,
                color = if (state.lastAnswerCorrect) semantics.correctInk else semantics.incorrectInk,
            )
        }
    }
}

@Composable
private fun ExplanationCard(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(BrainDropTheme.shapes.md)
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 14.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Box(
            modifier = Modifier
                .width(2.dp)
                .height(40.dp)
                .background(MaterialTheme.colorScheme.primaryContainer),
        )
        Column {
            Text(text = "ПОЧЕМУ ТАК", style = BrainDropTheme.type.label, color = BrainDropTheme.semantics.ink400)
            Spacer(Modifier.height(6.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun HintChip(
    label: String,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .clip(BrainDropTheme.shapes.lg)
            .border(1.5.dp, MaterialTheme.colorScheme.outline, BrainDropTheme.shapes.lg)
            .background(MaterialTheme.colorScheme.surface)
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 8.dp),
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun QuizFooter(
    state: VocabularyQuizUiState,
    onEvent: (VocabularyQuizUiEvent) -> Unit,
) {
    Spacer(Modifier.height(BrainDropTheme.spacing.sm))
    when {
        state.checked -> BrainDropButton(
            text = if (state.position < state.total) "Дальше" else "Завершить",
            onClick = { onEvent(VocabularyQuizUiEvent.Next) },
            modifier = Modifier.fillMaxWidth(),
        )
        !state.isCloze -> BrainDropButton(
            text = "Проверить",
            onClick = { onEvent(VocabularyQuizUiEvent.CheckTyping) },
            modifier = Modifier.fillMaxWidth(),
        )
        else -> Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(BrainDropTheme.spacing.quizFooterHeight)
                .clip(BrainDropTheme.shapes.md)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "Выберите вариант",
                style = MaterialTheme.typography.bodyMedium,
                color = BrainDropTheme.semantics.ink400,
            )
        }
    }
}

@Composable
private fun QuizResult(
    state: VocabularyQuizUiState,
    onRetryMistakes: () -> Unit,
    onRestart: () -> Unit,
    onBack: () -> Unit,
    onMistakeClicked: (String) -> Unit,
) {
    val semantics = BrainDropTheme.semantics
    val ratio = if (state.total > 0) state.score.toFloat() / state.total else 0f
    val percent = (ratio * 100).toInt()
    val title = when {
        percent == 100 -> "Отличная сессия!"
        percent >= 70 -> "Хорошая сессия!"
        else -> "Есть над чем поработать"
    }
    val today = LocalDate.parse(AppClock.todayIso())
    val buckets = reviewBuckets(state.scheduled, today)

    LazyColumn(
        modifier = Modifier.fillMaxSize().navigationBarsPadding().padding(BrainDropTheme.spacing.md),
        verticalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.md),
    ) {
        item { ResultSummary(state = state, title = title, ratio = ratio, percent = percent) }
        item { ScheduleCard(buckets = buckets) }

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
                        .clickable { onMistakeClicked(mistake.chunk.id) }
                        .padding(BrainDropTheme.spacing.sm),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = mistake.userAnswer.ifBlank { "—" },
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = "верно: ${mistake.correctAnswer}",
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
                    )
                }
                BrainDropButton(
                    text = "Ещё раз",
                    onClick = onRestart,
                    style = BrainDropButtonStyle.OUTLINED,
                    modifier = Modifier.fillMaxWidth(),
                )
                BrainDropButton(
                    text = "К списку",
                    onClick = onBack,
                    style = BrainDropButtonStyle.OUTLINED,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

@Composable
private fun ResultSummary(
    state: VocabularyQuizUiState,
    title: String,
    ratio: Float,
    percent: Int,
) {
    val semantics = BrainDropTheme.semantics
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(BrainDropTheme.spacing.xs),
    ) {
        Text(
            text = "${state.score}/${state.total}",
            style = BrainDropTheme.type.display.copy(fontSize = 40.sp),
            color = semantics.scoreRing(ratio),
        )
        Text(text = title, style = MaterialTheme.typography.titleLarge)
        Text(text = "$percent% ВЕРНО", style = BrainDropTheme.type.label, color = semantics.ink400)
        if (state.streakDays > 0) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
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

@Composable
private fun ScheduleCard(buckets: ReviewBuckets) {
    val semantics = BrainDropTheme.semantics
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(BrainDropTheme.shapes.card)
            .background(semantics.mistakeCardSurface)
            .padding(BrainDropTheme.spacing.md),
    ) {
        Text(
            text = "РАСПИСАНИЕ ПОВТОРЕНИЙ",
            style = BrainDropTheme.type.label,
            color = semantics.mistakeCardHint,
        )
        Spacer(Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            ScheduleTile("сегодня", buckets.today, Color.White, Modifier.weight(1f))
            ScheduleTile("завтра", buckets.tomorrow, semantics.mistakeCardAccent, Modifier.weight(1f))
            ScheduleTile("позже", buckets.later, semantics.mistakeCardAccent, Modifier.weight(1f))
        }
    }
}

@Composable
private fun ScheduleTile(
    label: String,
    count: Int,
    valueColor: Color,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = count.toString(),
            style = MaterialTheme.typography.titleLarge,
            color = valueColor,
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = BrainDropTheme.semantics.mistakeCardMuted,
        )
    }
}

@Composable
private fun QuizMessage(
    title: String,
    body: String,
    onBack: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(BrainDropTheme.spacing.lg),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = title, style = MaterialTheme.typography.titleMedium, textAlign = TextAlign.Center)
        Spacer(Modifier.height(BrainDropTheme.spacing.xs))
        Text(
            text = body,
            style = MaterialTheme.typography.bodyMedium,
            color = BrainDropTheme.semantics.ink500,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(BrainDropTheme.spacing.lg))
        BrainDropButton(
            text = "Назад",
            onClick = onBack,
            style = BrainDropButtonStyle.OUTLINED,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

private data class ReviewBuckets(
    val today: Int,
    val tomorrow: Int,
    val later: Int,
)

private fun reviewBuckets(
    scheduled: List<ChunkProgress>,
    today: LocalDate,
): ReviewBuckets {
    var t = 0
    var tm = 0
    var l = 0
    scheduled.forEach { progress ->
        val days = today.daysUntil(progress.dueDate ?: today)
        when {
            days <= 0 -> t++
            days == 1 -> tm++
            else -> l++
        }
    }
    return ReviewBuckets(t, tm, l)
}

private fun hintPrefix(
    question: VocabularyQuizQuestion,
    hintLevel: Int,
): String =
    when (hintLevel) {
        0 -> ""
        1 -> question.correctAnswer.split(" ").joinToString(" ") { it.take(1) }
        else -> question.maskedPattern.orEmpty()
    }

private fun timeText(totalSeconds: Int): String {
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "$minutes:${seconds.toString().padStart(2, '0')}"
}
