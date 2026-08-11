package by.freiding.braindrop.feature.tenses.domain.usecase

import by.freiding.braindrop.core.common.AppException
import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.tenses.domain.model.TenseQuizType
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GenerateTenseQuizUseCaseTest {
    private val allTenses = (1..6).map { tenseFixture(id = "tense$it") }
    private val allTensesWithProgress = Result.Success(allTenses.map { tenseWithProgressFixture(it) })
    private val comparisons = Result.Success(
        listOf(
            comparisonFixture("c1", "tense1", "tense2"),
            comparisonFixture("c2", "tense3", "tense4"),
        ),
    )

    @Test
    fun `restrictToTenseIds builds the session strictly from those tenses`() =
        runTest {
            val repository = FakeTenseRepository(tensesWithProgress = allTensesWithProgress, comparisons = comparisons)
            val useCase = GenerateTenseQuizUseCase(repository)

            val result = useCase(TenseQuizType.FORM, restrictToTenseIds = listOf("tense2", "tense4"))

            check(result is Result.Success)
            assertEquals(setOf("tense2", "tense4"), result.data.map { it.tenseId }.toSet())
        }

    @Test
    fun `empty unlearned tenses is a valid empty result, not an error`() =
        runTest {
            val repository = FakeTenseRepository(
                tensesWithProgress = allTensesWithProgress,
                unlearnedTenses = Result.Success(emptyList()),
                comparisons = comparisons,
            )
            val useCase = GenerateTenseQuizUseCase(repository)

            val result = useCase(TenseQuizType.FORM)

            assertEquals(Result.Success(emptyList()), result)
        }

    @Test
    fun `error loading all tenses propagates before the unlearned branch runs`() =
        runTest {
            val exception = AppException.DatabaseException("boom")
            val repository = FakeTenseRepository(tensesWithProgress = Result.Error(exception))
            val useCase = GenerateTenseQuizUseCase(repository)

            val result = useCase(TenseQuizType.FORM)

            assertEquals(Result.Error(exception), result)
        }

    @Test
    fun `error loading unlearned tenses propagates`() =
        runTest {
            val exception = AppException.DatabaseException("boom")
            val repository = FakeTenseRepository(
                tensesWithProgress = allTensesWithProgress,
                unlearnedTenses = Result.Error(exception),
                comparisons = comparisons,
            )
            val useCase = GenerateTenseQuizUseCase(repository)

            val result = useCase(TenseQuizType.FORM)

            assertEquals(Result.Error(exception), result)
        }

    @Test
    fun `session size caps the number of FORM questions drawn from unlearned tenses`() =
        runTest {
            val repository = FakeTenseRepository(
                tensesWithProgress = allTensesWithProgress,
                unlearnedTenses = Result.Success(allTenses),
                comparisons = comparisons,
            )
            val useCase = GenerateTenseQuizUseCase(repository)

            val result = useCase(TenseQuizType.FORM, sessionSize = 3)

            check(result is Result.Success)
            assertEquals(3, result.data.size)
        }

    @Test
    fun `FORM questions offer the correct answer plus three distractors from the same scenario`() =
        runTest {
            val repository = FakeTenseRepository(
                tensesWithProgress = allTensesWithProgress,
                unlearnedTenses = Result.Success(allTenses),
                comparisons = comparisons,
            )
            val useCase = GenerateTenseQuizUseCase(repository)

            val result = useCase(TenseQuizType.FORM, sessionSize = allTenses.size)

            check(result is Result.Success)
            result.data.forEach { question ->
                assertEquals(TenseQuizType.FORM, question.type)
                assertTrue(question.correctAnswer in question.options)
                assertEquals(4, question.options.size)
                assertTrue(question.questionText.contains("___"))
            }
        }

    @Test
    fun `MARKER_MATCH questions offer the tense's Russian title among the options`() =
        runTest {
            val repository = FakeTenseRepository(
                tensesWithProgress = allTensesWithProgress,
                unlearnedTenses = Result.Success(allTenses),
                comparisons = comparisons,
            )
            val useCase = GenerateTenseQuizUseCase(repository)

            val result = useCase(TenseQuizType.MARKER_MATCH, sessionSize = allTenses.size)

            check(result is Result.Success)
            result.data.forEach { question ->
                assertEquals(TenseQuizType.MARKER_MATCH, question.type)
                assertTrue(question.correctAnswer in question.options)
            }
        }

    @Test
    fun `DISCRIMINATION questions are a binary choice between the compared tenses' forms`() =
        runTest {
            val repository = FakeTenseRepository(
                tensesWithProgress = allTensesWithProgress,
                unlearnedTenses = Result.Success(allTenses),
                comparisons = comparisons,
            )
            val useCase = GenerateTenseQuizUseCase(repository)

            val result = useCase(TenseQuizType.DISCRIMINATION, sessionSize = 10)

            check(result is Result.Success)
            assertTrue(result.data.isNotEmpty())
            result.data.forEach { question ->
                assertEquals(TenseQuizType.DISCRIMINATION, question.type)
                assertEquals(2, question.options.size)
                assertTrue(question.correctAnswer in question.options)
            }
        }

    @Test
    fun `MIXED_REVIEW combines question kinds up to the session size`() =
        runTest {
            val repository = FakeTenseRepository(
                tensesWithProgress = allTensesWithProgress,
                unlearnedTenses = Result.Success(allTenses),
                comparisons = comparisons,
            )
            val useCase = GenerateTenseQuizUseCase(repository)

            val result = useCase(TenseQuizType.MIXED_REVIEW, sessionSize = 6)

            check(result is Result.Success)
            assertTrue(result.data.isNotEmpty())
            assertTrue(result.data.size <= 6)
            result.data.forEach { question ->
                assertTrue(question.correctAnswer in question.options)
            }
        }
}
