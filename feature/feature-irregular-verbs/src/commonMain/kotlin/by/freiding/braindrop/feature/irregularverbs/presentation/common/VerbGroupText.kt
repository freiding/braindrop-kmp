package by.freiding.braindrop.feature.irregularverbs.presentation.common

import androidx.compose.runtime.Composable
import by.freiding.braindrop.feature.irregularverbs.Res
import by.freiding.braindrop.feature.irregularverbs.domain.model.VerbGroup
import by.freiding.braindrop.feature.irregularverbs.verb_group_aaa_hint
import by.freiding.braindrop.feature.irregularverbs.verb_group_aaa_title
import by.freiding.braindrop.feature.irregularverbs.verb_group_family_aaa
import by.freiding.braindrop.feature.irregularverbs.verb_group_family_abb
import by.freiding.braindrop.feature.irregularverbs.verb_group_family_abc
import by.freiding.braindrop.feature.irregularverbs.verb_group_aba_hint
import by.freiding.braindrop.feature.irregularverbs.verb_group_aba_title
import by.freiding.braindrop.feature.irregularverbs.verb_group_abb_aid_hint
import by.freiding.braindrop.feature.irregularverbs.verb_group_abb_aid_title
import by.freiding.braindrop.feature.irregularverbs.verb_group_abb_other_hint
import by.freiding.braindrop.feature.irregularverbs.verb_group_abb_other_title
import by.freiding.braindrop.feature.irregularverbs.verb_group_abb_ought_hint
import by.freiding.braindrop.feature.irregularverbs.verb_group_abb_ought_title
import by.freiding.braindrop.feature.irregularverbs.verb_group_abb_ound_hint
import by.freiding.braindrop.feature.irregularverbs.verb_group_abb_ound_title
import by.freiding.braindrop.feature.irregularverbs.verb_group_abb_t_hint
import by.freiding.braindrop.feature.irregularverbs.verb_group_abb_t_title
import by.freiding.braindrop.feature.irregularverbs.verb_group_abb_ung_hint
import by.freiding.braindrop.feature.irregularverbs.verb_group_abb_ung_title
import by.freiding.braindrop.feature.irregularverbs.verb_group_abc_ewn_hint
import by.freiding.braindrop.feature.irregularverbs.verb_group_abc_ewn_title
import by.freiding.braindrop.feature.irregularverbs.verb_group_abc_ian_hint
import by.freiding.braindrop.feature.irregularverbs.verb_group_abc_ian_title
import by.freiding.braindrop.feature.irregularverbs.verb_group_abc_o_hint
import by.freiding.braindrop.feature.irregularverbs.verb_group_abc_o_title
import by.freiding.braindrop.feature.irregularverbs.verb_group_abc_other_hint
import by.freiding.braindrop.feature.irregularverbs.verb_group_abc_other_title
import org.jetbrains.compose.resources.stringResource

/** Localized group name/hint — used in the list, detail header, and quiz. */
@Composable
internal fun groupTitle(group: VerbGroup): String = when (group) {
    VerbGroup.AAA -> stringResource(Res.string.verb_group_aaa_title)
    VerbGroup.ABA -> stringResource(Res.string.verb_group_aba_title)
    VerbGroup.ABB_OUGHT -> stringResource(Res.string.verb_group_abb_ought_title)
    VerbGroup.ABB_OUND -> stringResource(Res.string.verb_group_abb_ound_title)
    VerbGroup.ABB_UNG -> stringResource(Res.string.verb_group_abb_ung_title)
    VerbGroup.ABB_T -> stringResource(Res.string.verb_group_abb_t_title)
    VerbGroup.ABB_AID -> stringResource(Res.string.verb_group_abb_aid_title)
    VerbGroup.ABB_OTHER -> stringResource(Res.string.verb_group_abb_other_title)
    VerbGroup.ABC_IAN -> stringResource(Res.string.verb_group_abc_ian_title)
    VerbGroup.ABC_EWN -> stringResource(Res.string.verb_group_abc_ewn_title)
    VerbGroup.ABC_O -> stringResource(Res.string.verb_group_abc_o_title)
    VerbGroup.ABC_OTHER -> stringResource(Res.string.verb_group_abc_other_title)
}

/** Description of the family's morphological pattern — used as contentDescription for the "A·A·A" / "A·B·B" / "A·B·C" label. */
@Composable
internal fun familyDescription(group: VerbGroup): String = when {
    group.name.startsWith("ABB") -> stringResource(Res.string.verb_group_family_abb)
    group.name.startsWith("ABC") -> stringResource(Res.string.verb_group_family_abc)
    else -> stringResource(Res.string.verb_group_family_aaa)
}

@Composable
internal fun groupHint(group: VerbGroup): String = when (group) {
    VerbGroup.AAA -> stringResource(Res.string.verb_group_aaa_hint)
    VerbGroup.ABA -> stringResource(Res.string.verb_group_aba_hint)
    VerbGroup.ABB_OUGHT -> stringResource(Res.string.verb_group_abb_ought_hint)
    VerbGroup.ABB_OUND -> stringResource(Res.string.verb_group_abb_ound_hint)
    VerbGroup.ABB_UNG -> stringResource(Res.string.verb_group_abb_ung_hint)
    VerbGroup.ABB_T -> stringResource(Res.string.verb_group_abb_t_hint)
    VerbGroup.ABB_AID -> stringResource(Res.string.verb_group_abb_aid_hint)
    VerbGroup.ABB_OTHER -> stringResource(Res.string.verb_group_abb_other_hint)
    VerbGroup.ABC_IAN -> stringResource(Res.string.verb_group_abc_ian_hint)
    VerbGroup.ABC_EWN -> stringResource(Res.string.verb_group_abc_ewn_hint)
    VerbGroup.ABC_O -> stringResource(Res.string.verb_group_abc_o_hint)
    VerbGroup.ABC_OTHER -> stringResource(Res.string.verb_group_abc_other_hint)
}
