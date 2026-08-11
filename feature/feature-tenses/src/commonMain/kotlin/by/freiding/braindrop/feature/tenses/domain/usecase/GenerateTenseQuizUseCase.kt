package by.freiding.braindrop.feature.tenses.domain.usecase

import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.tenses.domain.model.Tense
import by.freiding.braindrop.feature.tenses.domain.model.TenseComparison
import by.freiding.braindrop.feature.tenses.domain.model.TenseQuizQuestion
import by.freiding.braindrop.feature.tenses.domain.model.TenseQuizType
import by.freiding.braindrop.feature.tenses.domain.repository.TenseRepository

class GenerateTenseQuizUseCase(
    private val repository: TenseRepository,
) {
    /**
     * @param restrictToTenseIds if set, the session is built strictly from these tenses ("Retry
     *   mistakes" mode); otherwise the session is drawn from unlearned tenses. An empty list of
     *   unlearned tenses isn't an error but a valid result: "nothing to ask".
     */
    @Suppress("ReturnCount") // sequential Result-propagation guard clauses, not deep branching
    suspend operator fun invoke(
        type: TenseQuizType,
        sessionSize: Int = 10,
        restrictToTenseIds: List<String>? = null,
    ): Result<List<TenseQuizQuestion>> {
        val allTensesResult = repository.getTensesWithProgress()
        if (allTensesResult is Result.Error) return Result.Error(allTensesResult.exception)
        val allTenses = (allTensesResult as Result.Success).data.map { it.tense }

        val comparisonsResult = repository.getComparisons()
        if (comparisonsResult is Result.Error) return Result.Error(comparisonsResult.exception)
        val comparisons = (comparisonsResult as Result.Success).data

        val pool = if (restrictToTenseIds != null) {
            allTenses.filter { it.id in restrictToTenseIds }
        } else {
            val unlearnedResult = repository.getUnlearnedTenses()
            if (unlearnedResult is Result.Error) return unlearnedResult
            (unlearnedResult as Result.Success).data
        }

        if (pool.isEmpty()) return Result.Success(emptyList())

        val questions = when (type) {
            TenseQuizType.FORM ->
                pool.shuffled().take(sessionSize).map { buildFormQuestion(it, allTenses) }

            TenseQuizType.MARKER_MATCH ->
                pool
                    .filter { it.markerExamples.isNotEmpty() }
                    .shuffled()
                    .take(sessionSize)
                    .map { buildMarkerQuestion(it, allTenses) }

            TenseQuizType.DISCRIMINATION ->
                buildDiscriminationQuestions(allTenses, pool, comparisons, sessionSize)

            TenseQuizType.MIXED_REVIEW ->
                buildMixedQuestions(pool, allTenses, comparisons, sessionSize)
        }

        return Result.Success(questions.shuffled())
    }

    private fun buildFormQuestion(
        tense: Tense,
        allTenses: List<Tense>,
    ): TenseQuizQuestion {
        val scenario = tense.scenarios.keys.random()
        val correctForm = tense.scenarios.getValue(scenario)
        // Distractors are drawn from the tenses this one is most often confused with first —
        // that both makes the quiz harder in a meaningful way and keeps `commonMistake` (written
        // about exactly that confusion) a relevant explanation if the user picks a wrong option.
        val otherTenses = allTenses.filter { it.id != tense.id }
        val confused = otherTenses.filter { it.id in tense.confusedWith }
        val padding = otherTenses.filterNot { it.id in tense.confusedWith }.shuffled()
        val distractorTenses = (confused.shuffled() + padding).distinctBy { it.id }.take(3)
        val distractors = distractorTenses.map { it.scenarios.getValue(scenario).highlight }
        val blanked = correctForm.english.replaceFirst(correctForm.highlight, "___")
        return TenseQuizQuestion(
            tenseId = tense.id,
            type = TenseQuizType.FORM,
            questionText = blanked,
            correctAnswer = correctForm.highlight,
            options = (listOf(correctForm.highlight) + distractors).shuffled(),
            explanation = tense.commonMistake,
        )
    }

    private fun buildMarkerQuestion(
        tense: Tense,
        allTenses: List<Tense>,
    ): TenseQuizQuestion {
        val example = tense.markerExamples.random()
        val otherTenses = allTenses.filter { it.id != tense.id }
        val confused = otherTenses.filter { it.id in tense.confusedWith }
        val padding = otherTenses.filterNot { it.id in tense.confusedWith }.shuffled()
        val distractors = (confused.shuffled() + padding).distinctBy { it.id }.take(3)
        val options = (listOf(tense) + distractors).shuffled().map { it.titleEn }
        // markerExamples are authored to always contain one of this tense's own markers, so this
        // lookup reliably finds the word that gives the sentence away.
        val marker = tense.markers.firstOrNull { example.english.contains(it, ignoreCase = true) }
        val explanation = if (marker != null) {
            "Слово «$marker» — маркер времени ${tense.titleEn}. ${tense.commonMistake}"
        } else {
            tense.commonMistake
        }
        return TenseQuizQuestion(
            tenseId = tense.id,
            type = TenseQuizType.MARKER_MATCH,
            questionText = example.english,
            correctAnswer = tense.titleEn,
            options = options,
            explanation = explanation,
        )
    }

    private fun buildDiscriminationQuestions(
        allTenses: List<Tense>,
        pool: List<Tense>,
        comparisons: List<TenseComparison>,
        sessionSize: Int,
    ): List<TenseQuizQuestion> {
        val poolIds = pool.map { it.id }.toSet()
        val tenseById = allTenses.associateBy { it.id }
        val eligible = comparisons
            .filter { it.tenseIdA in poolIds || it.tenseIdB in poolIds }
            .ifEmpty { comparisons }

        return eligible.shuffled().take(sessionSize).mapNotNull { comparison ->
            val tenseA = tenseById[comparison.tenseIdA] ?: return@mapNotNull null
            val tenseB = tenseById[comparison.tenseIdB] ?: return@mapNotNull null
            val sharedScenario = tenseA.scenarios.keys
                .intersect(tenseB.scenarios.keys)
                .randomOrNull()
                ?: return@mapNotNull null
            val formA = tenseA.scenarios.getValue(sharedScenario)
            val formB = tenseB.scenarios.getValue(sharedScenario)

            val askA = when {
                comparison.tenseIdA in poolIds && comparison.tenseIdB !in poolIds -> true
                comparison.tenseIdB in poolIds && comparison.tenseIdA !in poolIds -> false
                else -> listOf(true, false).random()
            }
            val correctTense = if (askA) tenseA else tenseB
            val correctForm = if (askA) formA else formB
            val otherForm = if (askA) formB else formA

            TenseQuizQuestion(
                tenseId = correctTense.id,
                type = TenseQuizType.DISCRIMINATION,
                questionText = correctForm.english.replaceFirst(correctForm.highlight, "___"),
                correctAnswer = correctForm.highlight,
                options = listOf(correctForm.highlight, otherForm.highlight).shuffled(),
                // The comparison's own tip is written as a direct A-vs-B rule, so it doubles as
                // the "why this one, not that one" proof when the user picks the wrong side.
                explanation = comparison.tip,
            )
        }
    }

    private fun buildMixedQuestions(
        pool: List<Tense>,
        allTenses: List<Tense>,
        comparisons: List<TenseComparison>,
        sessionSize: Int,
    ): List<TenseQuizQuestion> {
        val formCount = sessionSize / 3
        val markerCount = sessionSize / 3
        val discriminationCount = sessionSize - formCount - markerCount

        val formQuestions = pool.shuffled().take(formCount).map { buildFormQuestion(it, allTenses) }

        val markerPool = pool.filter { it.markerExamples.isNotEmpty() }.ifEmpty { allTenses }
        val markerQuestions = markerPool.shuffled().take(markerCount).map { buildMarkerQuestion(it, allTenses) }

        val discriminationQuestions = buildDiscriminationQuestions(allTenses, pool, comparisons, discriminationCount)

        return formQuestions + markerQuestions + discriminationQuestions
    }
}
