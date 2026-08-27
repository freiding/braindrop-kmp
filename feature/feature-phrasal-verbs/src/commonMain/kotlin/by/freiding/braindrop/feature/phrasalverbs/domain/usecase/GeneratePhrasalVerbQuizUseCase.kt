package by.freiding.braindrop.feature.phrasalverbs.domain.usecase

import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.phrasalverbs.domain.model.PhrasalVerb
import by.freiding.braindrop.feature.phrasalverbs.domain.model.PhrasalVerbQuizQuestion
import by.freiding.braindrop.feature.phrasalverbs.domain.model.PhrasalVerbQuizType
import by.freiding.braindrop.feature.phrasalverbs.domain.repository.PhrasalVerbRepository

class GeneratePhrasalVerbQuizUseCase(
    private val repository: PhrasalVerbRepository,
) {
    /**
     * @param restrictToVerbIds when set, builds the session from these verb IDs only (retry-mistakes mode).
     */
    suspend operator fun invoke(
        type: PhrasalVerbQuizType,
        sessionSize: Int = 10,
        restrictToVerbIds: List<String>? = null,
    ): Result<List<PhrasalVerbQuizQuestion>> {
        val allResult = repository.getVerbsWithProgress()
        if (allResult is Result.Error) return Result.Error(allResult.exception)
        val allVerbs = (allResult as Result.Success).data.map { it.verb }

        val session = if (restrictToVerbIds != null) {
            allVerbs.filter { it.id in restrictToVerbIds }
        } else {
            val unlearnedResult = repository.getUnlearnedVerbs()
            if (unlearnedResult is Result.Error) return unlearnedResult
            (unlearnedResult as Result.Success).data.shuffled().take(sessionSize)
        }

        if (session.isEmpty()) return Result.Success(emptyList())

        val questions = session.mapNotNull { verb -> buildQuestion(verb, type, allVerbs) }
        return Result.Success(questions)
    }

    private fun buildQuestion(
        verb: PhrasalVerb,
        type: PhrasalVerbQuizType,
        allVerbs: List<PhrasalVerb>,
    ): PhrasalVerbQuizQuestion? {
        val meaning = verb.meanings.firstOrNull() ?: return null
        val others = allVerbs.filter { it.id != verb.id }.shuffled()

        return when (type) {
            PhrasalVerbQuizType.DEFINITION_TO_VERB -> {
                val correct = verb.fullForm
                val wrong = others.take(3).map { it.fullForm }
                PhrasalVerbQuizQuestion(
                    verb = verb,
                    meaningIndex = 0,
                    type = type,
                    questionText = meaning.definition,
                    correctAnswer = correct,
                    options = (listOf(correct) + wrong).shuffled(),
                )
            }
            PhrasalVerbQuizType.VERB_TO_TRANSLATION -> {
                val correct = meaning.translation
                val wrong = others.take(3).mapNotNull { it.meanings.firstOrNull()?.translation }
                PhrasalVerbQuizQuestion(
                    verb = verb,
                    meaningIndex = 0,
                    type = type,
                    questionText = verb.fullForm,
                    correctAnswer = correct,
                    options = (listOf(correct) + wrong).shuffled(),
                )
            }
            PhrasalVerbQuizType.FILL_PARTICLE -> {
                val correct = verb.particle
                val wrong = others
                    .map { it.particle }
                    .distinct()
                    .filter { it != correct }
                    .take(3)
                PhrasalVerbQuizQuestion(
                    verb = verb,
                    meaningIndex = 0,
                    type = type,
                    questionText = "${verb.verb} ___ (${meaning.translation})",
                    correctAnswer = correct,
                    options = (listOf(correct) + wrong).shuffled(),
                )
            }
        }
    }
}
