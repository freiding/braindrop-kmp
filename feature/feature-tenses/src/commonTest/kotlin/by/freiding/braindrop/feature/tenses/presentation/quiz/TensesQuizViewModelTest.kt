package by.freiding.braindrop.feature.tenses.presentation.quiz

import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.tenses.domain.model.TenseQuizType
import by.freiding.braindrop.feature.tenses.domain.usecase.FakeTenseRepository
import by.freiding.braindrop.feature.tenses.domain.usecase.GenerateTenseQuizUseCase
import by.freiding.braindrop.feature.tenses.domain.usecase.GetStreakDaysUseCase
import by.freiding.braindrop.feature.tenses.domain.usecase.GetTensesUseCase
import by.freiding.braindrop.feature.tenses.domain.usecase.SubmitTenseQuizAnswerUseCase
import by.freiding.braindrop.feature.tenses.domain.usecase.tenseFixture
import by.freiding.braindrop.feature.tenses.domain.usecase.tenseWithProgressFixture
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * TensesQuizViewModel starts an infinite `delay(1000)` ticker while a question is on screen,
 * cancelled only when the quiz finishes (or the ViewModel is cleared) — see the equivalent note
 * on the irregular-verbs QuizViewModelTest for why every test drains the session in a `finally`
 * block using runCurrent() rather than advanceUntilIdle() for the steps in between.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class TensesQuizViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun buildViewModel(repository: FakeTenseRepository): TensesQuizViewModel =
        TensesQuizViewModel(
            quizType = TenseQuizType.FORM,
            generateQuiz = GenerateTenseQuizUseCase(repository),
            submitAnswer = SubmitTenseQuizAnswerUseCase(repository),
            getStreakDays = GetStreakDaysUseCase(repository),
            getTenses = GetTensesUseCase(repository),
        )

    /** Answers whatever question is on screen (correctly) until the quiz finishes. */
    private fun finishSession(viewModel: TensesQuizViewModel) {
        while (!viewModel.state.value.isFinished) {
            val question = viewModel.state.value.currentQuestion ?: break
            viewModel.onEvent(TensesQuizUiEvent.AnswerSelected(question.correctAnswer))
            testDispatcher.scheduler.runCurrent()
            viewModel.onEvent(TensesQuizUiEvent.NextQuestion)
            testDispatcher.scheduler.runCurrent()
        }
        testDispatcher.scheduler.advanceUntilIdle()
    }

    @Test
    fun `loading a quiz populates a question for every unlearned tense`() =
        runTest(testDispatcher) {
            val tenses = listOf(tenseFixture("tense1"), tenseFixture("tense2"))
            val repository = FakeTenseRepository(
                tensesWithProgress = Result.Success(tenses.map { tenseWithProgressFixture(it) }),
                unlearnedTenses = Result.Success(tenses),
            )
            val viewModel = buildViewModel(repository)
            testDispatcher.scheduler.runCurrent()
            try {
                val state = viewModel.state.value
                assertEquals(false, state.isLoading)
                assertEquals(2, state.totalQuestions)
                assertEquals(0, state.currentIndex)
            } finally {
                finishSession(viewModel)
            }
        }

    @Test
    fun `selecting the correct answer increments score and marks the question answered`() =
        runTest(testDispatcher) {
            val tenses = listOf(tenseFixture("tense1"), tenseFixture("tense2"))
            val repository = FakeTenseRepository(
                tensesWithProgress = Result.Success(tenses.map { tenseWithProgressFixture(it) }),
                unlearnedTenses = Result.Success(tenses),
            )
            val viewModel = buildViewModel(repository)
            testDispatcher.scheduler.runCurrent()
            try {
                val answeredTenseId = viewModel.state.value.currentQuestion!!
                    .tenseId
                val correctAnswer = viewModel.state.value.currentQuestion!!
                    .correctAnswer
                viewModel.onEvent(TensesQuizUiEvent.AnswerSelected(correctAnswer))
                testDispatcher.scheduler.runCurrent()

                val state = viewModel.state.value
                assertEquals(1, state.score)
                assertTrue(state.isAnswered)
                assertTrue(state.mistakes.isEmpty())
                assertEquals(true, state.answerHistory[state.currentIndex])
                assertEquals(listOf(answeredTenseId to true), repository.recordedAnswers)
            } finally {
                finishSession(viewModel)
            }
        }

    @Test
    fun `selecting a wrong answer records a mistake without incrementing score`() =
        runTest(testDispatcher) {
            // Needs at least two unlearned tenses — with only one, GenerateTenseQuizUseCase can't
            // build a distractor, so the question would have a single option and no wrong choice.
            val tenses = listOf(tenseFixture("tense1"), tenseFixture("tense2"))
            val repository = FakeTenseRepository(
                tensesWithProgress = Result.Success(tenses.map { tenseWithProgressFixture(it) }),
                unlearnedTenses = Result.Success(tenses),
            )
            val viewModel = buildViewModel(repository)
            testDispatcher.scheduler.runCurrent()
            try {
                val question = viewModel.state.value.currentQuestion!!
                val wrongAnswer = question.options.first { it != question.correctAnswer }
                viewModel.onEvent(TensesQuizUiEvent.AnswerSelected(wrongAnswer))
                testDispatcher.scheduler.runCurrent()

                val state = viewModel.state.value
                assertEquals(0, state.score)
                assertEquals(1, state.mistakes.size)
                assertEquals(
                    question.tenseId,
                    state.mistakes
                        .single()
                        .question.tenseId,
                )
                assertEquals(wrongAnswer, state.mistakes.single().userAnswerText)
            } finally {
                finishSession(viewModel)
            }
        }

    @Test
    fun `retrying mistakes restricts the next session to exactly the missed tenses`() =
        runTest(testDispatcher) {
            val tenses = listOf(tenseFixture("tense1"), tenseFixture("tense2"))
            val repository = FakeTenseRepository(
                tensesWithProgress = Result.Success(tenses.map { tenseWithProgressFixture(it) }),
                unlearnedTenses = Result.Success(tenses),
            )
            val viewModel = buildViewModel(repository)
            testDispatcher.scheduler.runCurrent()
            try {
                repeat(2) {
                    val question = viewModel.state.value.currentQuestion!!
                    val wrongAnswer = question.options.first { it != question.correctAnswer }
                    viewModel.onEvent(TensesQuizUiEvent.AnswerSelected(wrongAnswer))
                    testDispatcher.scheduler.runCurrent()
                    viewModel.onEvent(TensesQuizUiEvent.NextQuestion)
                    testDispatcher.scheduler.runCurrent()
                }
                assertTrue(viewModel.state.value.isFinished)
                assertEquals(2, viewModel.state.value.mistakes.size)

                viewModel.onEvent(TensesQuizUiEvent.RetryMistakes)
                testDispatcher.scheduler.runCurrent()

                val retryState = viewModel.state.value
                assertEquals(2, retryState.totalQuestions)
                assertEquals(setOf("tense1", "tense2"), retryState.questions.map { it.tenseId }.toSet())
            } finally {
                finishSession(viewModel)
            }
        }

    @Test
    fun `restarting the quiz resets score and question index`() =
        runTest(testDispatcher) {
            val tenses = listOf(tenseFixture("tense1"), tenseFixture("tense2"))
            val repository = FakeTenseRepository(
                tensesWithProgress = Result.Success(tenses.map { tenseWithProgressFixture(it) }),
                unlearnedTenses = Result.Success(tenses),
            )
            val viewModel = buildViewModel(repository)
            testDispatcher.scheduler.runCurrent()
            finishSession(viewModel)
            assertTrue(viewModel.state.value.score > 0)

            try {
                viewModel.onEvent(TensesQuizUiEvent.RestartQuiz)
                testDispatcher.scheduler.runCurrent()

                val restarted = viewModel.state.value
                assertEquals(0, restarted.score)
                assertEquals(0, restarted.currentIndex)
                assertTrue(restarted.mistakes.isEmpty())
                assertEquals(false, restarted.isFinished)
            } finally {
                finishSession(viewModel)
            }
        }
}
