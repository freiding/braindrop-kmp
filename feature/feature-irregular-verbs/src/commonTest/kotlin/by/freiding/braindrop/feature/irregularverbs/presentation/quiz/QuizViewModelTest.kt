package by.freiding.braindrop.feature.irregularverbs.presentation.quiz

import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.irregularverbs.domain.model.QuizType
import by.freiding.braindrop.feature.irregularverbs.domain.usecase.FakeIrregularVerbRepository
import by.freiding.braindrop.feature.irregularverbs.domain.usecase.GenerateQuizUseCase
import by.freiding.braindrop.feature.irregularverbs.domain.usecase.GetStreakDaysUseCase
import by.freiding.braindrop.feature.irregularverbs.domain.usecase.SubmitQuizAnswerUseCase
import by.freiding.braindrop.feature.irregularverbs.domain.usecase.verbFixture
import by.freiding.braindrop.feature.irregularverbs.domain.usecase.verbWithProgressFixture
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
 * QuizViewModel starts an infinite `delay(1000)` ticker while a question is on screen, cancelled
 * only when the quiz finishes (or the ViewModel is cleared). Under StandardTestDispatcher,
 * advanceUntilIdle() while that ticker is still parked would spin forever re-scheduling it — and
 * runTest itself drains to idle at the end of every test, ticker or not — so every test drives
 * the quiz to isFinished (cancelling the ticker) in a `finally` block before returning, even if
 * an assertion above it fails. runCurrent() (not advanceUntilIdle()) is used for the steps in
 * between, since it doesn't try to advance past the ticker's still-pending delay.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class QuizViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun buildViewModel(repository: FakeIrregularVerbRepository): QuizViewModel = QuizViewModel(
        quizType = QuizType.EN_TO_RU,
        generateQuiz = GenerateQuizUseCase(repository),
        submitAnswer = SubmitQuizAnswerUseCase(repository),
        getStreakDays = GetStreakDaysUseCase(repository),
    )

    /** Answers whatever question is on screen (correctly) until the quiz finishes. */
    private fun finishSession(viewModel: QuizViewModel) {
        while (!viewModel.state.value.isFinished) {
            val question = viewModel.state.value.currentQuestion ?: break
            viewModel.onEvent(QuizUiEvent.AnswerSelected(question.correctAnswer))
            testDispatcher.scheduler.runCurrent()
            viewModel.onEvent(QuizUiEvent.NextQuestion)
            testDispatcher.scheduler.runCurrent()
        }
        testDispatcher.scheduler.advanceUntilIdle()
    }

    @Test
    fun `loading a quiz populates a question for every unlearned verb`() = runTest(testDispatcher) {
        val verbs = listOf(verbFixture("verb1"), verbFixture("verb2"))
        val repository = FakeIrregularVerbRepository(
            verbsWithProgress = Result.Success(verbs.map { verbWithProgressFixture(it) }),
            unlearnedVerbs = Result.Success(verbs),
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
    fun `selecting the correct answer increments score and marks the question answered`() = runTest(testDispatcher) {
        val verbs = listOf(verbFixture("verb1"), verbFixture("verb2"))
        val repository = FakeIrregularVerbRepository(
            verbsWithProgress = Result.Success(verbs.map { verbWithProgressFixture(it) }),
            unlearnedVerbs = Result.Success(verbs),
        )
        val viewModel = buildViewModel(repository)
        testDispatcher.scheduler.runCurrent()
        try {
            val answeredVerbId = viewModel.state.value.currentQuestion!!.verb.id
            val correctAnswer = viewModel.state.value.currentQuestion!!.correctAnswer
            viewModel.onEvent(QuizUiEvent.AnswerSelected(correctAnswer))
            testDispatcher.scheduler.runCurrent()

            val state = viewModel.state.value
            assertEquals(1, state.score)
            assertTrue(state.isAnswered)
            assertTrue(state.mistakes.isEmpty())
            assertEquals(true, state.answerHistory[state.currentIndex])
            assertEquals(listOf(answeredVerbId to true), repository.recordedAnswers)
        } finally {
            finishSession(viewModel)
        }
    }

    @Test
    fun `selecting a wrong answer records a mistake without incrementing score`() = runTest(testDispatcher) {
        // Needs at least two unlearned verbs — with only one, GenerateQuizUseCase can't build a
        // distractor, so the question would have a single option and no "wrong" choice to pick.
        val verbs = listOf(verbFixture("verb1"), verbFixture("verb2"))
        val repository = FakeIrregularVerbRepository(
            verbsWithProgress = Result.Success(verbs.map { verbWithProgressFixture(it) }),
            unlearnedVerbs = Result.Success(verbs),
        )
        val viewModel = buildViewModel(repository)
        testDispatcher.scheduler.runCurrent()
        try {
            val question = viewModel.state.value.currentQuestion!!
            val wrongAnswer = question.options.first { it != question.correctAnswer }
            viewModel.onEvent(QuizUiEvent.AnswerSelected(wrongAnswer))
            testDispatcher.scheduler.runCurrent()

            val state = viewModel.state.value
            assertEquals(0, state.score)
            assertEquals(1, state.mistakes.size)
            assertEquals(question.verb.id, state.mistakes.single().verb.id)
            assertEquals(wrongAnswer, state.mistakes.single().userAnswerText)
        } finally {
            finishSession(viewModel)
        }
    }

    @Test
    fun `retrying mistakes restricts the next session to exactly the missed verbs`() = runTest(testDispatcher) {
        val verbs = listOf(verbFixture("verb1"), verbFixture("verb2"))
        val repository = FakeIrregularVerbRepository(
            verbsWithProgress = Result.Success(verbs.map { verbWithProgressFixture(it) }),
            unlearnedVerbs = Result.Success(verbs),
        )
        val viewModel = buildViewModel(repository)
        testDispatcher.scheduler.runCurrent()
        try {
            // Answer both questions incorrectly so both verbs land in `mistakes`.
            repeat(2) {
                val question = viewModel.state.value.currentQuestion!!
                val wrongAnswer = question.options.first { it != question.correctAnswer }
                viewModel.onEvent(QuizUiEvent.AnswerSelected(wrongAnswer))
                testDispatcher.scheduler.runCurrent()
                viewModel.onEvent(QuizUiEvent.NextQuestion)
                testDispatcher.scheduler.runCurrent()
            }
            assertTrue(viewModel.state.value.isFinished)
            assertEquals(2, viewModel.state.value.mistakes.size)

            viewModel.onEvent(QuizUiEvent.RetryMistakes)
            testDispatcher.scheduler.runCurrent()

            val retryState = viewModel.state.value
            assertEquals(2, retryState.totalQuestions)
            assertEquals(setOf("verb1", "verb2"), retryState.questions.map { it.verb.id }.toSet())
        } finally {
            finishSession(viewModel)
        }
    }

    @Test
    fun `restarting the quiz resets score and question index`() = runTest(testDispatcher) {
        val verbs = listOf(verbFixture("verb1"), verbFixture("verb2"))
        val repository = FakeIrregularVerbRepository(
            verbsWithProgress = Result.Success(verbs.map { verbWithProgressFixture(it) }),
            unlearnedVerbs = Result.Success(verbs),
        )
        val viewModel = buildViewModel(repository)
        testDispatcher.scheduler.runCurrent()
        finishSession(viewModel)
        assertTrue(viewModel.state.value.score > 0)

        try {
            viewModel.onEvent(QuizUiEvent.RestartQuiz)
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
