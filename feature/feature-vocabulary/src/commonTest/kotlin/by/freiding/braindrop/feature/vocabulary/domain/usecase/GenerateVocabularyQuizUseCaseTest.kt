package by.freiding.braindrop.feature.vocabulary.domain.usecase

import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.vocabulary.domain.model.VocabularyQuizMode
import by.freiding.braindrop.feature.vocabulary.domain.model.VocabularyQuizType
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GenerateVocabularyQuizUseCaseTest {
    private fun repositoryWith(vararg ids: String): FakeChunkRepository {
        val chunks = ids.map { chunkWithProgressFixture(chunkFixture(it, text = "$it a thing")) }
        return FakeChunkRepository(chunks)
    }

    @Test
    fun `cloze blanks the first word and offers three distractors`() =
        runTest {
            val repo = repositoryWith("alpha", "bravo", "charlie", "delta")
            val result = GenerateVocabularyQuizUseCase(repo)(VocabularyQuizMode.CLOZE)

            val questions = (result as Result.Success).data
            assertEquals(4, questions.size)
            questions.forEach { q ->
                assertEquals(VocabularyQuizType.CLOZE, q.type)
                assertEquals(q.chunk.firstWord, q.correctAnswer)
                assertEquals(4, q.options.size)
                assertTrue(q.correctAnswer in q.options)
                assertTrue("____" in q.sentence)
            }
        }

    @Test
    fun `typing asks for the whole chunk and carries a masked pattern`() =
        runTest {
            val repo = repositoryWith("alpha", "bravo")
            val result = GenerateVocabularyQuizUseCase(repo)(VocabularyQuizMode.TYPING)

            val questions = (result as Result.Success).data
            questions.forEach { q ->
                assertEquals(VocabularyQuizType.TYPING, q.type)
                assertEquals(q.chunk.text, q.correctAnswer)
                assertTrue(q.options.isEmpty())
                assertTrue(!q.maskedPattern.isNullOrBlank())
            }
        }

    @Test
    fun `mixed mode alternates cloze and typing`() =
        runTest {
            val repo = repositoryWith("alpha", "bravo", "charlie", "delta")
            repo.sessionChunks = repo.chunksResult
            val result = GenerateVocabularyQuizUseCase(repo)(VocabularyQuizMode.MIXED)

            val types = (result as Result.Success).data.map { it.type }
            assertEquals(VocabularyQuizType.CLOZE, types[0])
            assertEquals(VocabularyQuizType.TYPING, types[1])
        }

    @Test
    fun `retry-mistakes restricts the session to the given ids`() =
        runTest {
            val repo = repositoryWith("alpha", "bravo", "charlie", "delta")
            val result = GenerateVocabularyQuizUseCase(repo)(
                VocabularyQuizMode.TYPING,
                restrictToChunkIds = listOf("bravo", "delta"),
            )

            val ids = (result as Result.Success).data.map { it.chunk.id }.toSet()
            assertEquals(setOf("bravo", "delta"), ids)
        }

    @Test
    fun `an empty pool yields no questions`() =
        runTest {
            val repo = FakeChunkRepository(emptyList())
            val result = GenerateVocabularyQuizUseCase(repo)(VocabularyQuizMode.MIXED)
            assertTrue((result as Result.Success).data.isEmpty())
        }
}
