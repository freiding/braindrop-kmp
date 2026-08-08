package by.freiding.braindrop.feature.irregularverbs.domain.usecase

import by.freiding.braindrop.core.common.AppException
import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.irregularverbs.domain.model.QuizType
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GenerateQuizUseCaseTest {

    private val allVerbs = (1..6).map { verbFixture(id = "verb$it") }
    private val allVerbsWithProgress = Result.Success(allVerbs.map { verbWithProgressFixture(it) })

    @Test
    fun `restrictToVerbIds builds the session strictly from those verbs`() = runTest {
        val repository = FakeIrregularVerbRepository(verbsWithProgress = allVerbsWithProgress)
        val useCase = GenerateQuizUseCase(repository)

        val result = useCase(QuizType.EN_TO_RU, restrictToVerbIds = listOf("verb2", "verb4"))

        check(result is Result.Success)
        assertEquals(setOf("verb2", "verb4"), result.data.map { it.verb.id }.toSet())
    }

    @Test
    fun `empty unlearned verbs is a valid empty result, not an error`() = runTest {
        val repository = FakeIrregularVerbRepository(
            verbsWithProgress = allVerbsWithProgress,
            unlearnedVerbs = Result.Success(emptyList()),
        )
        val useCase = GenerateQuizUseCase(repository)

        val result = useCase(QuizType.EN_TO_RU)

        assertEquals(Result.Success(emptyList()), result)
    }

    @Test
    fun `error loading all verbs propagates before the unlearned branch runs`() = runTest {
        val exception = AppException.DatabaseException("boom")
        val repository = FakeIrregularVerbRepository(verbsWithProgress = Result.Error(exception))
        val useCase = GenerateQuizUseCase(repository)

        val result = useCase(QuizType.EN_TO_RU)

        assertEquals(Result.Error(exception), result)
    }

    @Test
    fun `error loading unlearned verbs propagates`() = runTest {
        val exception = AppException.DatabaseException("boom")
        val repository = FakeIrregularVerbRepository(
            verbsWithProgress = allVerbsWithProgress,
            unlearnedVerbs = Result.Error(exception),
        )
        val useCase = GenerateQuizUseCase(repository)

        val result = useCase(QuizType.EN_TO_RU)

        assertEquals(Result.Error(exception), result)
    }

    @Test
    fun `session size caps the number of questions drawn from unlearned verbs`() = runTest {
        val repository = FakeIrregularVerbRepository(
            verbsWithProgress = allVerbsWithProgress,
            unlearnedVerbs = Result.Success(allVerbs),
        )
        val useCase = GenerateQuizUseCase(repository)

        val result = useCase(QuizType.EN_TO_RU, sessionSize = 3)

        check(result is Result.Success)
        assertEquals(3, result.data.size)
    }

    @Test
    fun `each question offers the correct answer among its options`() = runTest {
        val repository = FakeIrregularVerbRepository(
            verbsWithProgress = allVerbsWithProgress,
            unlearnedVerbs = Result.Success(allVerbs),
        )
        val useCase = GenerateQuizUseCase(repository)

        val result = useCase(QuizType.VERB_FORMS, sessionSize = allVerbs.size)

        check(result is Result.Success)
        result.data.forEach { question ->
            assertTrue(question.correctAnswer in question.options)
            assertEquals(4, question.options.size)
        }
    }
}
