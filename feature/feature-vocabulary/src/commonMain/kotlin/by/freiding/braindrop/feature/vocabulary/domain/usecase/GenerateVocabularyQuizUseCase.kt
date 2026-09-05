package by.freiding.braindrop.feature.vocabulary.domain.usecase

import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.vocabulary.domain.model.Chunk
import by.freiding.braindrop.feature.vocabulary.domain.model.VocabularyQuizMode
import by.freiding.braindrop.feature.vocabulary.domain.model.VocabularyQuizQuestion
import by.freiding.braindrop.feature.vocabulary.domain.model.VocabularyQuizType
import by.freiding.braindrop.feature.vocabulary.domain.repository.ChunkRepository

/**
 * Builds a quiz session. Mirrors `GeneratePhrasalVerbQuizUseCase`: the pool is the repository's
 * session chunks (due first, then new), or exactly [restrictToChunkIds] in retry-mistakes mode.
 *
 * CLOZE blanks the chunk's first word (the collocationally bound part — "make" in
 * "make a decision") and offers three first-word distractors from other chunks. TYPING asks for
 * the whole chunk, shown as a masked skeleton ("m___ a d______").
 */
class GenerateVocabularyQuizUseCase(
    private val repository: ChunkRepository,
) {
    suspend operator fun invoke(
        mode: VocabularyQuizMode,
        sessionSize: Int = DEFAULT_SESSION_SIZE,
        restrictToChunkIds: List<String>? = null,
    ): Result<List<VocabularyQuizQuestion>> {
        val poolResult = repository.getChunksWithProgress()
        if (poolResult is Result.Error) return poolResult
        val allChunks = (poolResult as Result.Success).data.map { it.chunk }

        val session = if (restrictToChunkIds != null) {
            val byId = allChunks.associateBy { it.id }
            restrictToChunkIds.mapNotNull { byId[it] }
        } else {
            when (val picked = repository.getSessionChunks(sessionSize)) {
                is Result.Error -> return picked
                is Result.Success -> picked.data.map { it.chunk }
            }
        }
        if (session.isEmpty()) return Result.Success(emptyList())

        val questions = session.mapIndexedNotNull { index, chunk ->
            val type = when (mode) {
                VocabularyQuizMode.CLOZE -> VocabularyQuizType.CLOZE
                VocabularyQuizMode.TYPING -> VocabularyQuizType.TYPING
                VocabularyQuizMode.MIXED -> if (index % 2 == 0) VocabularyQuizType.CLOZE else VocabularyQuizType.TYPING
            }
            buildQuestion(chunk, type, allChunks)
        }
        return Result.Success(questions)
    }

    private fun buildQuestion(
        chunk: Chunk,
        type: VocabularyQuizType,
        allChunks: List<Chunk>,
    ): VocabularyQuizQuestion? =
        when (type) {
            VocabularyQuizType.CLOZE -> buildCloze(chunk, allChunks)
            VocabularyQuizType.TYPING -> buildTyping(chunk)
        }

    private fun buildCloze(
        chunk: Chunk,
        allChunks: List<Chunk>,
    ): VocabularyQuizQuestion? {
        val gap = chunk.firstWord
        val sentenceWithGap = gap
            .takeIf { it.isNotBlank() }
            ?.let { replaceFirstWord(chunk.primaryExample.english, it) }
        val distractors = allChunks
            .asSequence()
            .filter { it.id != chunk.id }
            .map { it.firstWord }
            .filter { it.isNotBlank() && !it.equals(gap, ignoreCase = true) }
            .distinct()
            .shuffled()
            .take(DISTRACTOR_COUNT)
            .toList()
        if (sentenceWithGap == null || distractors.size < DISTRACTOR_COUNT) return null

        return VocabularyQuizQuestion(
            chunk = chunk,
            type = VocabularyQuizType.CLOZE,
            sentence = sentenceWithGap,
            sentenceTranslation = chunk.primaryExample.russian,
            gapWord = gap,
            maskedPattern = null,
            correctAnswer = gap,
            options = (distractors + gap).shuffled(),
            explanation = chunk.commonError?.explanation
                ?: "С «${chunk.headword}» используется «$gap».",
        )
    }

    private fun buildTyping(chunk: Chunk): VocabularyQuizQuestion =
        VocabularyQuizQuestion(
            chunk = chunk,
            type = VocabularyQuizType.TYPING,
            sentence = maskChunkInSentence(chunk.primaryExample.english, chunk.text),
            sentenceTranslation = chunk.primaryExample.russian,
            gapWord = null,
            maskedPattern = maskPattern(chunk.text),
            correctAnswer = chunk.text,
            options = emptyList(),
            explanation = null,
        )

    /** "make a decision" -> "m___ a d______" (words of 1-2 letters are shown whole). */
    private fun maskPattern(text: String): String =
        text
            .trim()
            .split(WHITESPACE)
            .joinToString(" ") { word ->
                if (word.length <= 2) word else word.first() + "_".repeat(word.length - 1)
            }

    /**
     * Masks the chunk inside a sentence so the "context if stuck" card can't spell out the answer.
     * Falls back to blanking each significant word of the chunk when the exact phrase isn't present
     * (the sentence often uses an inflected form, e.g. "attend the meeting" for "attend a meeting").
     */
    private fun maskChunkInSentence(
        sentence: String,
        chunk: String,
    ): String {
        val words = chunk.trim().split(WHITESPACE)
        val exact = sentence.indexOf(chunk, ignoreCase = true)
        if (exact != -1) {
            val blank = words.joinToString(" ") { "_".repeat(it.length) }
            return sentence.replaceRange(exact, exact + chunk.length, blank)
        }
        var result = sentence
        words.filter { it.length >= 3 }.forEach { word ->
            result = Regex("\\b${Regex.escape(word)}\\w*", RegexOption.IGNORE_CASE)
                .replace(result) { "_".repeat(it.value.length) }
        }
        return result
    }

    /** Replaces the first whole-word occurrence of [word] in [sentence] with "____". */
    private fun replaceFirstWord(
        sentence: String,
        word: String,
    ): String? {
        val regex = Regex("\\b${Regex.escape(word)}\\b", RegexOption.IGNORE_CASE)
        val match = regex.find(sentence) ?: return null
        return sentence.replaceRange(match.range, GAP)
    }

    private companion object {
        const val DEFAULT_SESSION_SIZE = 10
        const val DISTRACTOR_COUNT = 3
        const val GAP = "____"
        val WHITESPACE = Regex("\\s+")
    }
}
