package by.freiding.braindrop.feature.vocabulary.presentation.quiz

import by.freiding.braindrop.feature.vocabulary.domain.model.VocabularyQuizMode
import by.freiding.braindrop.feature.vocabulary.domain.usecase.EvaluateVocabularyAnswerUseCase
import by.freiding.braindrop.feature.vocabulary.domain.usecase.FakeChunkRepository
import by.freiding.braindrop.feature.vocabulary.domain.usecase.GenerateVocabularyQuizUseCase
import by.freiding.braindrop.feature.vocabulary.domain.usecase.GetVocabularyStreakDaysUseCase
import by.freiding.braindrop.feature.vocabulary.domain.usecase.SubmitVocabularyQuizAnswerUseCase
import by.freiding.braindrop.feature.vocabulary.domain.usecase.chunkFixture
import by.freiding.braindrop.feature.vocabulary.domain.usecase.chunkWithProgressFixture
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

@OptIn(ExperimentalCoroutinesApi::class)
class VocabularyQuizViewModelTest {
    private val dispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setUp() = Dispatchers.setMain(dispatcher)

    @AfterTest
    fun tearDown() = Dispatchers.resetMain()

    private fun repoWith(vararg ids: String): FakeChunkRepository {
        val chunks = ids.map { chunkWithProgressFixture(chunkFixture(it, text = "$it a thing")) }
        return FakeChunkRepository(chunks)
    }

    private fun buildViewModel(
        repo: FakeChunkRepository,
        mode: VocabularyQuizMode = VocabularyQuizMode.CLOZE,
    ) = VocabularyQuizViewModel(
        mode = mode,
        generateQuiz = GenerateVocabularyQuizUseCase(repo),
        evaluateAnswer = EvaluateVocabularyAnswerUseCase(),
        submitAnswer = SubmitVocabularyQuizAnswerUseCase(repo),
        getStreakDays = GetVocabularyStreakDaysUseCase(repo),
    )

    private fun finish(vm: VocabularyQuizViewModel) {
        var guard = 0
        while (!vm.state.value.isFinished && guard++ < 50) {
            val q = vm.state.value.currentQuestion ?: break
            vm.onEvent(VocabularyQuizUiEvent.OptionSelected(q.correctAnswer))
            dispatcher.scheduler.runCurrent()
            vm.onEvent(VocabularyQuizUiEvent.Next)
            dispatcher.scheduler.runCurrent()
        }
        dispatcher.scheduler.advanceUntilIdle()
    }

    @Test
    fun `a correct cloze answer increments the score and marks the question answered`() =
        runTest(dispatcher) {
            val repo = repoWith("alpha", "bravo", "charlie", "delta")
            val vm = buildViewModel(repo)
            dispatcher.scheduler.runCurrent()
            try {
                val q = vm.state.value.currentQuestion!!
                vm.onEvent(VocabularyQuizUiEvent.OptionSelected(q.correctAnswer))
                dispatcher.scheduler.runCurrent()

                assertEquals(1, vm.state.value.score)
                assertTrue(vm.state.value.isAnswered)
                assertTrue(
                    vm.state.value.mistakes
                        .isEmpty(),
                )
                assertEquals(true, vm.state.value.answerHistory[0])
            } finally {
                finish(vm)
            }
        }

    @Test
    fun `a wrong cloze answer records a mistake without scoring`() =
        runTest(dispatcher) {
            val repo = repoWith("alpha", "bravo", "charlie", "delta")
            val vm = buildViewModel(repo)
            dispatcher.scheduler.runCurrent()
            try {
                val q = vm.state.value.currentQuestion!!
                val wrong = q.options.first { it != q.correctAnswer }
                vm.onEvent(VocabularyQuizUiEvent.OptionSelected(wrong))
                dispatcher.scheduler.runCurrent()

                assertEquals(0, vm.state.value.score)
                assertEquals(1, vm.state.value.mistakes.size)
                assertEquals(
                    q.chunk.id,
                    vm.state.value.mistakes
                        .single()
                        .chunk.id,
                )
            } finally {
                finish(vm)
            }
        }

    @Test
    fun `retrying mistakes restricts the next session to the missed chunks`() =
        runTest(dispatcher) {
            val repo = repoWith("alpha", "bravo", "charlie", "delta")
            val vm = buildViewModel(repo)
            dispatcher.scheduler.runCurrent()
            try {
                while (!vm.state.value.isFinished) {
                    val q = vm.state.value.currentQuestion!!
                    val wrong = q.options.first { it != q.correctAnswer }
                    vm.onEvent(VocabularyQuizUiEvent.OptionSelected(wrong))
                    dispatcher.scheduler.runCurrent()
                    vm.onEvent(VocabularyQuizUiEvent.Next)
                    dispatcher.scheduler.runCurrent()
                }
                val missed = vm.state.value.mistakes
                    .map { it.chunk.id }
                    .toSet()
                assertEquals(4, missed.size)

                vm.onEvent(VocabularyQuizUiEvent.RetryMistakes)
                dispatcher.scheduler.runCurrent()

                assertEquals(
                    missed,
                    vm.state.value.questions
                        .map { it.chunk.id }
                        .toSet(),
                )
            } finally {
                finish(vm)
            }
        }

    @Test
    fun `restart resets score and index and mistakes`() =
        runTest(dispatcher) {
            val repo = repoWith("alpha", "bravo", "charlie", "delta")
            val vm = buildViewModel(repo)
            dispatcher.scheduler.runCurrent()
            finish(vm)
            assertTrue(vm.state.value.score > 0)
            try {
                vm.onEvent(VocabularyQuizUiEvent.Restart)
                dispatcher.scheduler.runCurrent()

                assertEquals(0, vm.state.value.score)
                assertEquals(0, vm.state.value.currentIndex)
                assertTrue(
                    vm.state.value.mistakes
                        .isEmpty(),
                )
                assertEquals(false, vm.state.value.isFinished)
            } finally {
                finish(vm)
            }
        }
}
