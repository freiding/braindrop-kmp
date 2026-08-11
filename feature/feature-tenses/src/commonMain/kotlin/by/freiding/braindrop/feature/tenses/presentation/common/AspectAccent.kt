package by.freiding.braindrop.feature.tenses.presentation.common

import androidx.compose.runtime.Composable
import by.freiding.braindrop.feature.tenses.Res
import by.freiding.braindrop.feature.tenses.domain.model.TenseAspect
import by.freiding.braindrop.feature.tenses.tense_aspect_continuous_description
import by.freiding.braindrop.feature.tenses.tense_aspect_continuous_label
import by.freiding.braindrop.feature.tenses.tense_aspect_perfect_continuous_description
import by.freiding.braindrop.feature.tenses.tense_aspect_perfect_continuous_label
import by.freiding.braindrop.feature.tenses.tense_aspect_perfect_description
import by.freiding.braindrop.feature.tenses.tense_aspect_perfect_label
import by.freiding.braindrop.feature.tenses.tense_aspect_simple_description
import by.freiding.braindrop.feature.tenses.tense_aspect_simple_label
import org.jetbrains.compose.resources.stringResource

/**
 * The one-line "big idea" behind each aspect, independent of time — the mental model that lets a
 * learner reason about a tense they haven't memorized yet ("this is Perfect, so it's about the
 * result") instead of treating all 12 tenses as unrelated facts.
 */
internal data class AspectAccent(
    val label: String,
    val description: String,
)

@Composable
internal fun aspectAccent(aspect: TenseAspect): AspectAccent =
    when (aspect) {
        TenseAspect.SIMPLE -> AspectAccent(
            label = stringResource(Res.string.tense_aspect_simple_label),
            description = stringResource(Res.string.tense_aspect_simple_description),
        )
        TenseAspect.CONTINUOUS -> AspectAccent(
            label = stringResource(Res.string.tense_aspect_continuous_label),
            description = stringResource(Res.string.tense_aspect_continuous_description),
        )
        TenseAspect.PERFECT -> AspectAccent(
            label = stringResource(Res.string.tense_aspect_perfect_label),
            description = stringResource(Res.string.tense_aspect_perfect_description),
        )
        TenseAspect.PERFECT_CONTINUOUS -> AspectAccent(
            label = stringResource(Res.string.tense_aspect_perfect_continuous_label),
            description = stringResource(Res.string.tense_aspect_perfect_continuous_description),
        )
    }
