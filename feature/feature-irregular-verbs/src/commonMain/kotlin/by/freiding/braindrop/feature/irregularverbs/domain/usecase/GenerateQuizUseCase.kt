package by.freiding.braindrop.feature.irregularverbs.domain.usecase

import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.irregularverbs.domain.model.IrregularVerb
import by.freiding.braindrop.feature.irregularverbs.domain.model.QuizQuestion
import by.freiding.braindrop.feature.irregularverbs.domain.model.QuizType
import by.freiding.braindrop.feature.irregularverbs.domain.repository.IrregularVerbRepository

class GenerateQuizUseCase(
    private val repository: IrregularVerbRepository,
) {
    /**
     * @param restrictToVerbIds if set, the session is built strictly from these verbs ("Retry
     *   mistakes" mode); otherwise the session is drawn from unlearned verbs.
     *   An empty list of unlearned verbs isn't an error but a valid result: "nothing to ask".
     */
    suspend operator fun invoke(
        type: QuizType,
        sessionSize: Int = 10,
        restrictToVerbIds: List<String>? = null,
    ): Result<List<QuizQuestion>> {
        val allVerbsResult = repository.getVerbsWithProgress()
        if (allVerbsResult is Result.Error) return Result.Error(allVerbsResult.exception)
        val allVerbs = (allVerbsResult as Result.Success).data.map { it.verb }

        val session = if (restrictToVerbIds != null) {
            allVerbs.filter { it.id in restrictToVerbIds }
        } else {
            val unlearnedResult = repository.getUnlearnedVerbs()
            if (unlearnedResult is Result.Error) return unlearnedResult
            (unlearnedResult as Result.Success).data.shuffled().take(sessionSize)
        }

        if (session.isEmpty()) return Result.Success(emptyList())

        val questions = session.map { verb -> buildQuestion(verb, type, allVerbs) }
        return Result.Success(questions)
    }

    private fun buildQuestion(
        verb: IrregularVerb,
        type: QuizType,
        allVerbs: List<IrregularVerb>,
    ): QuizQuestion {
        val others = allVerbs.filter { it.id != verb.id }.shuffled()
        return when (type) {
            QuizType.EN_TO_RU -> {
                val correct = verb.translation
                val wrong = others.take(3).map { it.translation }
                QuizQuestion(
                    verb = verb,
                    type = type,
                    questionText = verb.baseForm,
                    correctAnswer = correct,
                    options = (listOf(correct) + wrong).shuffled(),
                )
            }
            QuizType.RU_TO_EN -> {
                val correct = verb.baseForm
                val wrong = others.take(3).map { it.baseForm }
                QuizQuestion(
                    verb = verb,
                    type = type,
                    questionText = verb.translation,
                    correctAnswer = correct,
                    options = (listOf(correct) + wrong).shuffled(),
                )
            }
            QuizType.VERB_FORMS -> {
                val askPastSimple = (0..1).random()
                val correct = if (askPastSimple == 0) verb.pastSimple else verb.pastParticiple
                val wrong = if (askPastSimple == 0) {
                    others.take(3).map { it.pastSimple }
                } else {
                    others.take(3).map { it.pastParticiple }
                }
                QuizQuestion(
                    verb = verb,
                    type = type,
                    questionText = verb.baseForm,
                    correctAnswer = correct,
                    options = (listOf(correct) + wrong).shuffled(),
                )
            }
        }
    }
}
