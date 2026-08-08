package by.freiding.braindrop.feature.irregularverbs.presentation.common

import by.freiding.braindrop.feature.irregularverbs.domain.model.IrregularVerb

/** "arose · arisen" — past simple and past participle, as shown next to the base form in the list row and mistake breakdown. */
internal fun verbFormsLine(verb: IrregularVerb): String = "${verb.pastSimple} · ${verb.pastParticiple}"
