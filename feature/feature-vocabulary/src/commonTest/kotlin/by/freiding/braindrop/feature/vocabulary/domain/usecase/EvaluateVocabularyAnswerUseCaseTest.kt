package by.freiding.braindrop.feature.vocabulary.domain.usecase

import by.freiding.braindrop.feature.vocabulary.domain.model.VocabularyQuizQuestion
import by.freiding.braindrop.feature.vocabulary.domain.model.VocabularyQuizType
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class EvaluateVocabularyAnswerUseCaseTest {
    private val evaluate = EvaluateVocabularyAnswerUseCase()

    private fun cloze(correct: String) =
        VocabularyQuizQuestion(
            chunk = chunkFixture("c"),
            type = VocabularyQuizType.CLOZE,
            sentence = "We must ____ today.",
            sentenceTranslation = "",
            gapWord = correct,
            maskedPattern = null,
            correctAnswer = correct,
            options = listOf(correct, "x", "y", "z"),
            explanation = null,
        )

    private fun typing(correct: String) =
        VocabularyQuizQuestion(
            chunk = chunkFixture("c"),
            type = VocabularyQuizType.TYPING,
            sentence = "",
            sentenceTranslation = "",
            gapWord = null,
            maskedPattern = "m___ a d______",
            correctAnswer = correct,
            options = emptyList(),
            explanation = null,
        )

    @Test
    fun `cloze accepts a case-insensitive exact match`() {
        assertTrue(evaluate(cloze("make"), "Make"))
        assertFalse(evaluate(cloze("make"), "made"))
    }

    @Test
    fun `typing ignores case and outer spaces and double spaces and trailing punctuation`() {
        val q = typing("make a decision")
        assertTrue(evaluate(q, "make a decision"))
        assertTrue(evaluate(q, "  Make a  decision. "))
        assertTrue(evaluate(q, "MAKE A DECISION!"))
        assertFalse(evaluate(q, "make the decision"))
    }
}
