package by.freiding.braindrop.feature.irregularverbs.presentation.quiz

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import by.freiding.braindrop.feature.irregularverbs.domain.model.QuizType
import by.freiding.braindrop.feature.irregularverbs.Res
import by.freiding.braindrop.feature.irregularverbs.nav_back
import by.freiding.braindrop.feature.irregularverbs.quiz_back
import by.freiding.braindrop.feature.irregularverbs.quiz_correct_answers
import by.freiding.braindrop.feature.irregularverbs.quiz_finished_title
import by.freiding.braindrop.feature.irregularverbs.quiz_next
import by.freiding.braindrop.feature.irregularverbs.quiz_retry
import by.freiding.braindrop.feature.irregularverbs.quiz_title
import by.freiding.braindrop.feature.irregularverbs.quiz_to_list
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
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
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.quiz_title)) },
                navigationIcon = {
                    Text(
                        text = stringResource(Res.string.nav_back),
                        modifier = Modifier
                            .clickable { viewModel.onEvent(QuizUiEvent.NavigateBack) }
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                },
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            contentAlignment = Alignment.Center,
        ) {
            when {
                state.isLoading -> CircularProgressIndicator()
                state.error != null -> ErrorContent(
                    message = state.error!!,
                    onBack = { viewModel.onEvent(QuizUiEvent.NavigateBack) },
                )
                state.isFinished -> FinishedContent(
                    score = state.score,
                    total = state.totalQuestions,
                    onRestart = { viewModel.onEvent(QuizUiEvent.RestartQuiz) },
                    onBack = { viewModel.onEvent(QuizUiEvent.NavigateBack) },
                )
                state.currentQuestion != null -> QuizContent(
                    state = state,
                    onAnswerSelected = { viewModel.onEvent(QuizUiEvent.AnswerSelected(it)) },
                    onNext = { viewModel.onEvent(QuizUiEvent.NextQuestion) },
                )
            }
        }
    }
}

@Composable
private fun QuizContent(
    state: QuizUiState,
    onAnswerSelected: (String) -> Unit,
    onNext: () -> Unit,
) {
    val question = state.currentQuestion ?: return

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        LinearProgressIndicator(
            progress = { (state.currentIndex + 1).toFloat() / state.totalQuestions },
            modifier = Modifier.fillMaxWidth(),
        )
        Text(
            text = "${state.currentIndex + 1} / ${state.totalQuestions}",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = question.questionText,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.weight(1f))
        question.options.forEach { option ->
            AnswerButton(
                text = option,
                isSelected = state.selectedAnswer == option,
                isCorrect = option == question.correctAnswer,
                isAnswered = state.isAnswered,
                onClick = { onAnswerSelected(option) },
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        if (state.isAnswered) {
            Button(
                onClick = onNext,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(stringResource(Res.string.quiz_next))
            }
        }
    }
}

@Composable
private fun AnswerButton(
    text: String,
    isSelected: Boolean,
    isCorrect: Boolean,
    isAnswered: Boolean,
    onClick: () -> Unit,
) {
    val containerColor by animateColorAsState(
        targetValue = when {
            !isAnswered -> MaterialTheme.colorScheme.surface
            isCorrect -> Color(0xFF4CAF50)
            isSelected && !isCorrect -> Color(0xFFF44336)
            else -> MaterialTheme.colorScheme.surface
        },
        animationSpec = tween(300),
        label = "answerColor",
    )
    val contentColor = when {
        !isAnswered -> MaterialTheme.colorScheme.onSurface
        isCorrect || (isSelected && !isCorrect) -> Color.White
        else -> MaterialTheme.colorScheme.onSurface
    }
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        enabled = !isAnswered,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = containerColor,
            disabledContentColor = contentColor,
        ),
    ) {
        Text(text = text, textAlign = TextAlign.Center)
    }
}

@Composable
private fun FinishedContent(
    score: Int,
    total: Int,
    onRestart: () -> Unit,
    onBack: () -> Unit,
) {
    Column(
        modifier = Modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = stringResource(Res.string.quiz_finished_title),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = stringResource(Res.string.quiz_correct_answers, score, total),
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onRestart, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(Res.string.quiz_retry))
        }
        OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(Res.string.quiz_to_list))
        }
    }
}

@Composable
private fun ErrorContent(message: String, onBack: () -> Unit) {
    Column(
        modifier = Modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
        )
        Button(onClick = onBack) { Text(stringResource(Res.string.quiz_back)) }
    }
}
