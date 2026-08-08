package by.freiding.braindrop.feature.irregularverbs.domain.usecase

import by.freiding.braindrop.core.common.AppException
import by.freiding.braindrop.core.common.Result
import by.freiding.braindrop.feature.irregularverbs.domain.model.IrregularVerb
import by.freiding.braindrop.feature.irregularverbs.domain.model.VerbProgress
import by.freiding.braindrop.feature.irregularverbs.domain.model.VerbWithProgress
import by.freiding.braindrop.feature.irregularverbs.domain.repository.IrregularVerbRepository

/**
 * Hand-written fake instead of a mocking library: lets tests configure each method's result
 * independently (including Result.Error) and records calls where a test needs to assert on them.
 */
class FakeIrregularVerbRepository(
    private var verbsWithProgress: Result<List<VerbWithProgress>> = Result.Success(emptyList()),
    private var unlearnedVerbs: Result<List<IrregularVerb>> = Result.Success(emptyList()),
    private var streakDays: Result<Int> = Result.Success(0),
) : IrregularVerbRepository {

    val recordedAnswers = mutableListOf<Pair<String, Boolean>>()
    val toggledVerbIds = mutableListOf<String>()

    fun setVerbsWithProgress(result: Result<List<VerbWithProgress>>) {
        verbsWithProgress = result
    }

    fun setUnlearnedVerbs(result: Result<List<IrregularVerb>>) {
        unlearnedVerbs = result
    }

    fun setStreakDays(result: Result<Int>) {
        streakDays = result
    }

    override suspend fun getVerbsWithProgress(): Result<List<VerbWithProgress>> = verbsWithProgress

    override suspend fun getVerbDetail(verbId: String): Result<VerbWithProgress> {
        val match = (verbsWithProgress as? Result.Success)?.data?.firstOrNull { it.verb.id == verbId }
        return if (match != null) {
            Result.Success(match)
        } else {
            Result.Error(AppException.DatabaseException("Verb not found: $verbId"))
        }
    }

    override suspend fun toggleLearned(verbId: String): Result<Unit> {
        toggledVerbIds += verbId
        return Result.Success(Unit)
    }

    override suspend fun recordAnswer(verbId: String, isCorrect: Boolean): Result<Unit> {
        recordedAnswers += verbId to isCorrect
        return Result.Success(Unit)
    }

    override suspend fun getUnlearnedVerbs(): Result<List<IrregularVerb>> = unlearnedVerbs

    override suspend fun getStreakDays(): Result<Int> = streakDays
}

fun verbFixture(
    id: String,
    baseForm: String = id,
    pastSimple: String = "${id}_past",
    pastParticiple: String = "${id}_participle",
    translation: String = "${id}_ru",
): IrregularVerb = IrregularVerb(
    id = id,
    baseForm = baseForm,
    pastSimple = pastSimple,
    pastParticiple = pastParticiple,
    translation = translation,
    examples = emptyList(),
)

fun verbWithProgressFixture(
    verb: IrregularVerb,
    isLearned: Boolean = false,
): VerbWithProgress = VerbWithProgress(
    verb = verb,
    progress = VerbProgress(verbId = verb.id, isLearned = isLearned),
)
