package by.freiding.braindrop.feature.irregularverbs.domain.usecase

import by.freiding.braindrop.core.common.AppException
import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.irregularverbs.domain.model.IrregularVerb
import by.freiding.braindrop.feature.irregularverbs.domain.model.QuizQuestion
import by.freiding.braindrop.feature.irregularverbs.domain.model.QuizType
import by.freiding.braindrop.feature.irregularverbs.domain.repository.IrregularVerbRepository

class GenerateQuizUseCase(private val repository: IrregularVerbRepository) {

    suspend operator fun invoke(type: QuizType, sessionSize: Int = 10): Result<List<QuizQuestion>> {
        val unlearnedResult = repository.getUnlearnedVerbs()
        if (unlearnedResult is Result.Error) return unlearnedResult

        val allVerbsResult = repository.getVerbsWithProgress()
        if (allVerbsResult is Result.Error) return Result.Error(allVerbsResult.exception)

        val unlearned = (unlearnedResult as Result.Success).data
        val allVerbs = (allVerbsResult as Result.Success).data.map { it.verb }

        if (unlearned.size < MIN_VERBS_FOR_QUIZ) {
            return Result.Error(
                AppException.UnknownException(
                    IllegalStateException("Not enough unlearned verbs to start a quiz (need at least $MIN_VERBS_FOR_QUIZ).")
                )
            )
        }

        val session = unlearned.shuffled().take(sessionSize)
        val questions = session.map { verb -> buildQuestion(verb, type, allVerbs) }
        return Result.Success(questions)
    }

    private fun buildQuestion(verb: IrregularVerb, type: QuizType, allVerbs: List<IrregularVerb>): QuizQuestion {
        val others = allVerbs.filter { it.id != verb.id }.shuffled()
        return when (type) {
            QuizType.EN_TO_RU -> {
                val correct = verb.translation
                val wrong = others.take(3).map { it.translation }
                QuizQuestion(
                    verb = verb,
                    type = type,
                    questionText = "Как переводится глагол «${verb.baseForm}»?",
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
                    questionText = "Переведите на английский: «${verb.translation}»",
                    correctAnswer = correct,
                    options = (listOf(correct) + wrong).shuffled(),
                )
            }
            QuizType.VERB_FORMS -> {
                val askPastSimple = (0..1).random() == 0
                val correct = if (askPastSimple) verb.pastSimple else verb.pastParticiple
                val wrong = if (askPastSimple) {
                    others.take(3).map { it.pastSimple }
                } else {
                    others.take(3).map { it.pastParticiple }
                }
                val formLabel = if (askPastSimple) "Past Simple" else "Past Participle"
                QuizQuestion(
                    verb = verb,
                    type = type,
                    questionText = "Выберите $formLabel для глагола «${verb.baseForm}»:",
                    correctAnswer = correct,
                    options = (listOf(correct) + wrong).shuffled(),
                )
            }
        }
    }

    companion object {
        private const val MIN_VERBS_FOR_QUIZ = 4
    }
}
