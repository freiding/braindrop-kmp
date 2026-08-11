package by.freiding.braindrop.feature.tenses.domain.usecase

import by.freiding.braindrop.core.common.AppException
import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.tenses.domain.model.Tense
import by.freiding.braindrop.feature.tenses.domain.model.TenseAspect
import by.freiding.braindrop.feature.tenses.domain.model.TenseComparison
import by.freiding.braindrop.feature.tenses.domain.model.TenseExample
import by.freiding.braindrop.feature.tenses.domain.model.TenseFormExample
import by.freiding.braindrop.feature.tenses.domain.model.TenseFormulas
import by.freiding.braindrop.feature.tenses.domain.model.TenseProgress
import by.freiding.braindrop.feature.tenses.domain.model.TenseScenario
import by.freiding.braindrop.feature.tenses.domain.model.TenseTime
import by.freiding.braindrop.feature.tenses.domain.model.TenseUsageCase
import by.freiding.braindrop.feature.tenses.domain.model.TenseWithProgress
import by.freiding.braindrop.feature.tenses.domain.repository.TenseRepository

/**
 * Hand-written fake instead of a mocking library: lets tests configure each method's result
 * independently (including Result.Error) and records calls where a test needs to assert on them.
 */
class FakeTenseRepository(
    private var tensesWithProgress: Result<List<TenseWithProgress>> = Result.Success(emptyList()),
    private var unlearnedTenses: Result<List<Tense>> = Result.Success(emptyList()),
    private var comparisons: Result<List<TenseComparison>> = Result.Success(emptyList()),
    private var streakDays: Result<Int> = Result.Success(0),
) : TenseRepository {
    val recordedAnswers = mutableListOf<Pair<String, Boolean>>()
    val toggledTenseIds = mutableListOf<String>()

    fun setTensesWithProgress(result: Result<List<TenseWithProgress>>) {
        tensesWithProgress = result
    }

    fun setUnlearnedTenses(result: Result<List<Tense>>) {
        unlearnedTenses = result
    }

    fun setComparisons(result: Result<List<TenseComparison>>) {
        comparisons = result
    }

    fun setStreakDays(result: Result<Int>) {
        streakDays = result
    }

    override suspend fun getTensesWithProgress(): Result<List<TenseWithProgress>> = tensesWithProgress

    override suspend fun getTenseDetail(tenseId: String): Result<TenseWithProgress> {
        val match = (tensesWithProgress as? Result.Success)?.data?.firstOrNull { it.tense.id == tenseId }
        return if (match != null) {
            Result.Success(match)
        } else {
            Result.Error(AppException.DatabaseException("Tense not found: $tenseId"))
        }
    }

    override suspend fun getComparisons(): Result<List<TenseComparison>> = comparisons

    override suspend fun getComparison(comparisonId: String): Result<TenseComparison> {
        val match = (comparisons as? Result.Success)?.data?.firstOrNull { it.id == comparisonId }
        return if (match != null) {
            Result.Success(match)
        } else {
            Result.Error(AppException.DatabaseException("Comparison not found: $comparisonId"))
        }
    }

    override suspend fun toggleLearned(tenseId: String): Result<Unit> {
        toggledTenseIds += tenseId
        return Result.Success(Unit)
    }

    override suspend fun recordAnswer(
        tenseId: String,
        isCorrect: Boolean,
    ): Result<Unit> {
        recordedAnswers += tenseId to isCorrect
        return Result.Success(Unit)
    }

    override suspend fun getUnlearnedTenses(): Result<List<Tense>> = unlearnedTenses

    override suspend fun getStreakDays(): Result<Int> = streakDays
}

fun tenseFixture(
    id: String,
    time: TenseTime = TenseTime.PRESENT,
    aspect: TenseAspect = TenseAspect.SIMPLE,
    confusedWith: List<String> = emptyList(),
): Tense {
    val scenarios = TenseScenario.entries.associateWith { scenario ->
        TenseFormExample(
            english = "Subject ${scenario.name.lowercase()} for $id.",
            russian = "${id}_ru_$scenario",
            highlight = id,
        )
    }
    return Tense(
        id = id,
        time = time,
        aspect = aspect,
        titleEn = id,
        titleRu = "${id}_ru",
        formulas = TenseFormulas(affirmative = "aff", negative = "neg", question = "q"),
        formExample = scenarios.getValue(TenseScenario.READ_BOOK),
        scenarios = scenarios,
        usageCases = listOf(TenseUsageCase("case", TenseExample("case en", "case ru"))),
        markers = listOf("marker1"),
        markerExamples = listOf(TenseExample("Example with marker1 in it.", "ru example")),
        commonMistake = "mistake",
        confusedWith = confusedWith,
        specialNotes = emptyList(),
    )
}

fun tenseWithProgressFixture(
    tense: Tense,
    isLearned: Boolean = false,
): TenseWithProgress =
    TenseWithProgress(
        tense = tense,
        progress = TenseProgress(tenseId = tense.id, isLearned = isLearned, timesCorrect = 0, timesIncorrect = 0),
    )

fun comparisonFixture(
    id: String,
    tenseIdA: String,
    tenseIdB: String,
): TenseComparison =
    TenseComparison(
        id = id,
        tenseIdA = tenseIdA,
        tenseIdB = tenseIdB,
        tip = "tip",
        pointsA = listOf("pointA"),
        pointsB = listOf("pointB"),
        exampleA = TenseExample("en A", "ru A"),
        exampleB = TenseExample("en B", "ru B"),
    )
