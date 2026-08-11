package by.freiding.braindrop.feature.tenses.presentation.common

import by.freiding.braindrop.feature.tenses.domain.model.TenseAspect

/**
 * The one-line "big idea" behind each aspect, independent of time — the mental model that lets a
 * learner reason about a tense they haven't memorized yet ("this is Perfect, so it's about the
 * result") instead of treating all 12 tenses as unrelated facts.
 */
internal data class AspectAccent(
    val label: String,
    val description: String,
)

internal fun aspectAccent(aspect: TenseAspect): AspectAccent =
    when (aspect) {
        TenseAspect.SIMPLE -> AspectAccent(
            label = "ФАКТ",
            description = "Simple — это факт, привычка или законченное действие. Не важен ни " +
                "процесс, ни результат «здесь и сейчас» — просто что происходит, происходило " +
                "или произойдёт.",
        )
        TenseAspect.CONTINUOUS -> AspectAccent(
            label = "ПРОЦЕСС",
            description = "Continuous — это процесс в развитии. Важно, что действие " +
                "происходит, происходило или будет происходить в конкретный момент, а не то, " +
                "чем оно закончилось.",
        )
        TenseAspect.PERFECT -> AspectAccent(
            label = "РЕЗУЛЬТАТ",
            description = "Perfect — это результат к моменту. Важна связь между двумя точками " +
                "во времени и сам факт завершения действия, а не то, сколько оно длилось.",
        )
        TenseAspect.PERFECT_CONTINUOUS -> AspectAccent(
            label = "ДЛИТЕЛЬНОСТЬ",
            description = "Perfect Continuous — это длительность процесса вплоть до момента. " +
                "Важно, сколько действие уже длится, длилось или будет длиться, а не факт его " +
                "завершения.",
        )
    }
