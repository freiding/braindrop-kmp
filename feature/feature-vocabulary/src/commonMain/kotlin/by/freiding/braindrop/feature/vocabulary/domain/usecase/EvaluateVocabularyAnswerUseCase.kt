package by.freiding.braindrop.feature.vocabulary.domain.usecase

import by.freiding.braindrop.feature.vocabulary.domain.model.VocabularyQuizQuestion
import by.freiding.braindrop.feature.vocabulary.domain.model.VocabularyQuizType

/**
 * Checks a quiz answer. CLOZE is an exact (case-insensitive) match against the missing word;
 * TYPING is lenient — case, surrounding whitespace, doubled spaces and trailing punctuation are
 * all ignored so "Meet a deadline." still counts as "meet a deadline".
 */
class EvaluateVocabularyAnswerUseCase {
    operator fun invoke(
        question: VocabularyQuizQuestion,
        answer: String,
    ): Boolean =
        when (question.type) {
            VocabularyQuizType.CLOZE -> answer.trim().equals(question.correctAnswer.trim(), ignoreCase = true)
            VocabularyQuizType.TYPING -> normalize(answer) == normalize(question.correctAnswer)
        }

    private fun normalize(value: String): String =
        value
            .trim()
            .lowercase()
            .replace(PUNCTUATION, "")
            .replace(WHITESPACE, " ")

    private companion object {
        val WHITESPACE = Regex("\\s+")
        val PUNCTUATION = Regex("[.,!?;:\"'()]")
    }
}
