package by.freiding.braindrop.feature.vocabulary.presentation.session

import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.vocabulary.domain.srs.RecallGrade
import by.freiding.braindrop.feature.vocabulary.domain.usecase.FakeChunkRepository
import by.freiding.braindrop.feature.vocabulary.domain.usecase.GetDueChunksUseCase
import by.freiding.braindrop.feature.vocabulary.domain.usecase.GradeChunkUseCase
import by.freiding.braindrop.feature.vocabulary.domain.usecase.PreviewRecallIntervalsUseCase
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
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Like the tenses quiz VM, this one runs an infinite `delay(1000)` ticker while cards are on
 * screen. Every test drains the session in a `finally` block so the ticker is cancelled.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class VocabularyCardSessionViewModelTest {
    private val dispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setUp() = Dispatchers.setMain(dispatcher)

    @AfterTest
    fun tearDown() = Dispatchers.resetMain()

    private fun buildViewModel(repo: FakeChunkRepository) =
        VocabularyCardSessionViewModel(
            getDueChunks = GetDueChunksUseCase(repo),
            gradeChunk = GradeChunkUseCase(repo),
            previewIntervals = PreviewRecallIntervalsUseCase(),
        )

    private fun repoWith(vararg ids: String): FakeChunkRepository {
        val chunks = ids.map { chunkWithProgressFixture(chunkFixture(it)) }
        return FakeChunkRepository(chunks)
    }

    private fun drain(vm: VocabularyCardSessionViewModel) {
        var guard = 0
        while (!vm.state.value.isFinished && vm.state.value.currentCard != null && guard++ < 50) {
            vm.onEvent(VocabularyCardSessionUiEvent.Grade(RecallGrade.KNOW))
            dispatcher.scheduler.runCurrent()
        }
        dispatcher.scheduler.advanceUntilIdle()
    }

    @Test
    fun `grading KNOW advances to the next card and records the grade`() =
        runTest(dispatcher) {
            val repo = repoWith("a", "b")
            val vm = buildViewModel(repo)
            dispatcher.scheduler.runCurrent()
            try {
                assertEquals(2, vm.state.value.total)
                vm.onEvent(VocabularyCardSessionUiEvent.Grade(RecallGrade.KNOW))
                dispatcher.scheduler.runCurrent()

                assertEquals(1, vm.state.value.currentIndex)
                assertFalse(vm.state.value.isRevealed)
                assertEquals(listOf("a" to RecallGrade.KNOW), repo.gradedCalls)
            } finally {
                drain(vm)
            }
        }

    @Test
    fun `DONT_KNOW re-queues the card so it comes back this session`() =
        runTest(dispatcher) {
            val repo = repoWith("a", "b")
            val vm = buildViewModel(repo)
            dispatcher.scheduler.runCurrent()
            try {
                vm.onEvent(VocabularyCardSessionUiEvent.Grade(RecallGrade.DONT_KNOW))
                dispatcher.scheduler.runCurrent()

                assertEquals(3, vm.state.value.total, "the missed card is appended")
                assertEquals(
                    "a",
                    vm.state.value.queue
                        .last()
                        .chunk.id,
                )
            } finally {
                drain(vm)
            }
        }

    @Test
    fun `session finishes once every card is graded`() =
        runTest(dispatcher) {
            val repo = repoWith("a", "b")
            val vm = buildViewModel(repo)
            dispatcher.scheduler.runCurrent()
            drain(vm)

            assertTrue(vm.state.value.isFinished)
            assertEquals(2, vm.state.value.reviewedCount)
        }

    @Test
    fun `an empty due queue reports the empty state`() =
        runTest(dispatcher) {
            val repo = FakeChunkRepository(emptyList())
            repo.dueChunks = Result.Success(emptyList())
            val vm = buildViewModel(repo)
            dispatcher.scheduler.runCurrent()

            assertTrue(vm.state.value.isEmpty)
        }
}
