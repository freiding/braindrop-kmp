package by.freiding.braindrop.core.ui

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.freiding.braindrop.core.ui.generated.resources.Res
import by.freiding.braindrop.core.ui.generated.resources.ibm_plex_mono_medium
import by.freiding.braindrop.core.ui.generated.resources.ibm_plex_mono_semibold
import by.freiding.braindrop.core.ui.generated.resources.manrope_bold
import by.freiding.braindrop.core.ui.generated.resources.manrope_extrabold
import by.freiding.braindrop.core.ui.generated.resources.manrope_medium
import by.freiding.braindrop.core.ui.generated.resources.manrope_semibold
import org.jetbrains.compose.resources.Font

/*
 * BrainDrop design system — light theme.
 *
 * Tokens match the design reference design_handoff_braindrop_redesign/BrainDrop.dc.html,
 * block 1a (Foundations). Layout unit: 1 px at width 390 = 1 dp.
 *
 * Dark theme isn't designed in this iteration: BrainDropTheme ignores
 * isSystemInDarkTheme and always returns the light scheme. Don't plug in
 * darkColorScheme() mechanically — semantic correct/incorrect colors need
 * a separate design pass.
 */

// ---------------------------------------------------------------- palette

private object Palette {
    val primary = Color(0xFF2B5FD9)
    val primaryInk = Color(0xFF1B3F97)
    val primaryTint = Color(0xFFE8EFFE)

    val ink900 = Color(0xFF101A2B)
    val ink600 = Color(0xFF4A5A72)
    val ink500 = Color(0xFF6B7A90)
    val ink400 = Color(0xFF8494A8)

    val surface = Color(0xFFFFFFFF)
    val canvas = Color(0xFFF4F6F9)
    val sunken = Color(0xFFEAEEF4)
    val border = Color(0xFFDFE5EE)
    val borderSoft = Color(0xFFF0F3F7)

    val correct = Color(0xFF17A05E)
    val correctTint = Color(0xFFE4F6EC)
    val correctInk = Color(0xFF0C6B3E)

    val incorrect = Color(0xFFD8443C)
    val incorrectTint = Color(0xFFFBEAE8)
    val incorrectInk = Color(0xFF96261F)

    val streak = Color(0xFFE8A32B)
    val streakTint = Color(0xFFFDF2DE)
    val streakInk = Color(0xFF8A5E0C)
}

private val BrainDropLightColorScheme = lightColorScheme(
    primary = Palette.primary,
    onPrimary = Color.White,
    primaryContainer = Palette.primaryTint,
    onPrimaryContainer = Palette.primaryInk,
    secondary = Palette.primaryInk,
    onSecondary = Color.White,
    secondaryContainer = Palette.primaryTint,
    onSecondaryContainer = Palette.primaryInk,
    tertiary = Palette.ink500,
    onTertiary = Color.White,
    background = Palette.canvas,
    onBackground = Palette.ink900,
    surface = Palette.surface,
    onSurface = Palette.ink900,
    surfaceVariant = Palette.sunken,
    onSurfaceVariant = Palette.ink600,
    surfaceContainerLowest = Palette.surface,
    surfaceContainerLow = Palette.canvas,
    surfaceContainer = Palette.canvas,
    surfaceContainerHigh = Palette.sunken,
    outline = Palette.border,
    outlineVariant = Palette.borderSoft,
    error = Palette.incorrect,
    onError = Color.White,
    errorContainer = Palette.incorrectTint,
    onErrorContainer = Palette.incorrectInk,
    scrim = Color(0x66101A2B),
)

// ------------------------------------------------------------- semantics

/**
 * Everything that doesn't fit into Material 3 roles: answer statuses, streak, extra
 * ink-scale steps, and verb group color coding.
 *
 * groupColor / groupSurface / groupInk take the VerbGroup name (String) so that
 * core-ui doesn't depend on the feature module. Keys are VerbGroup.name.
 */
@Immutable
data class BrainDropSemantics(
    val correct: Color = Palette.correct,
    val correctTint: Color = Palette.correctTint,
    val correctInk: Color = Palette.correctInk,
    val incorrect: Color = Palette.incorrect,
    val incorrectTint: Color = Palette.incorrectTint,
    val incorrectInk: Color = Palette.incorrectInk,
    val learned: Color = Palette.correct,
    val streak: Color = Palette.streak,
    val streakTint: Color = Palette.streakTint,
    val streakInk: Color = Palette.streakInk,
    val ink500: Color = Palette.ink500,
    val ink400: Color = Palette.ink400,
    val soonSurface: Color = Color(0xFFEEF1F6),
    val soonBorder: Color = Color(0xFFCFD8E4),
    val soonTile: Color = Color(0xFFE3E8F0),
    val soonInk: Color = Color(0xFF6B7A90),
    val answerIdleOutline: Color = Color(0xFFC3CDDB),
    val answerMutedSurface: Color = Color(0xFFF7F9FC),
    val answerMutedOutline: Color = Color(0xFFE7EBF2),
    val searchFieldSurface: Color = Color(0xFFF1F4F9),
    val searchFieldOutline: Color = Color(0xFFE3E8F0),
    /** Quiz mistake-breakdown card: always dark-on-light regardless of theme, so it's a fixed color, not an M3 role. */
    val mistakeCardSurface: Color = Color(0xFF101A2B),
    val mistakeCardAccent: Color = Color(0xFF8FB0F2),
    val mistakeCardMuted: Color = Color(0xFFB9C4D3),
    val mistakeCardHint: Color = Color(0xFF8494A8),
    /** Label color in the verb detail's forms card (base form / past simple / past participle). */
    val formsLabelInk: Color = Color(0xFF4E6DB5),
) {
    /** Group tag color: a 4×40dp strip in the row, border-start on the sticky header. */
    fun groupColor(group: String): Color =
        when (group) {
            "AAA" -> Color(0xFF0F7F73)
            "ABA" -> Color(0xFF3FA096)
            "ABB_OUGHT" -> Color(0xFF3F3FBF)
            "ABB_OUND" -> Color(0xFF4F4FCB)
            "ABB_UNG" -> Color(0xFF5F60D5)
            "ABB_T" -> Color(0xFF5257D3)
            "ABB_AID" -> Color(0xFF7E84E4)
            "ABB_OTHER" -> Color(0xFF8E95EA)
            "ABC_IAN" -> Color(0xFFC2620F)
            "ABC_EWN" -> Color(0xFFCE7315)
            "ABC_O" -> Color(0xFFD9851F)
            else -> Color(0xFFE0972E) // ABC_OTHER
        }

    /** Base family color — the A·A·A / A·B·B / A·B·C label and the counter in the header. */
    fun familyColor(group: String): Color =
        when (family(group)) {
            Family.AAA -> Color(0xFF0F7F73)
            Family.ABB -> Color(0xFF5257D3)
            Family.ABC -> Color(0xFFC2620F)
        }

    /** Background of the group's sticky header; also the color of the vertical translation rule inside the group. */
    fun groupSurface(group: String): Color =
        when (family(group)) {
            Family.AAA -> Color(0xFFE9F4F3)
            Family.ABB -> Color(0xFFEEEEFB)
            Family.ABC -> Color(0xFFFBF0E4)
        }

    /** Group header color on groupSurface. */
    fun groupInk(group: String): Color =
        when (family(group)) {
            Family.AAA -> Color(0xFF0B5E55)
            Family.ABB -> Color(0xFF33359B)
            Family.ABC -> Color(0xFF8A4A0B)
        }

    /** Hint color under the group header. */
    fun groupHintInk(group: String): Color =
        when (family(group)) {
            Family.AAA -> Color(0xFF3E7C76)
            Family.ABB -> Color(0xFF5A5EA8)
            Family.ABC -> Color(0xFF9C6220)
        }

    /** Forms-scheme label printed in the group header and chip. */
    fun familyLabel(group: String): String =
        when (family(group)) {
            Family.AAA -> "A·A·A"
            Family.ABB -> "A·B·B"
            Family.ABC -> "A·B·C"
        }

    /** Ring color on the quiz result screen. */
    fun scoreRing(ratio: Float): Color =
        when {
            ratio >= 0.7f -> correct
            ratio >= 0.4f -> streak
            else -> incorrect
        }

    /**
     * Section color for the tenses list grid (Present / Past / Future). Takes the TenseTime
     * name (String) so that core-ui doesn't depend on the feature module — same convention as
     * groupColor(group: String) above.
     */
    fun tenseTimeColor(time: String): Color =
        when (time) {
            "PRESENT" -> Color(0xFF0F7F73)
            "PAST" -> Color(0xFF5257D3)
            else -> Color(0xFFC2620F) // FUTURE
        }

    /** Background tint for the tenses list grid section header. */
    fun tenseTimeSurface(time: String): Color =
        when (time) {
            "PRESENT" -> Color(0xFFE9F4F3)
            "PAST" -> Color(0xFFEEEEFB)
            else -> Color(0xFFFBF0E4) // FUTURE
        }

    /**
     * Aspect color: the tenses module's hard axis (Simple / Continuous / Perfect / Perfect
     * Continuous) gets the color; time (Present / Past / Future) is read from grouping and the
     * tab instead. Used for the 4dp stripe in the list row, the dot in the cheat sheet, and the
     * accent in the detail example. Takes TenseAspect.name (String) so core-ui doesn't depend on
     * the feature module — same convention as tenseTimeColor(time: String) above.
     */
    fun aspectColor(aspect: String): Color =
        when (aspect) {
            "SIMPLE" -> Color(0xFF0F7F73)
            "CONTINUOUS" -> Color(0xFF5257D3)
            "PERFECT" -> Color(0xFFC2620F)
            else -> Color(0xFFA03D86) // PERFECT_CONTINUOUS
        }

    /** Background of the formulas card on the detail screen, the aspect chip, and the comparison side panel. */
    fun aspectSurface(aspect: String): Color =
        when (aspect) {
            "SIMPLE" -> Color(0xFFE9F4F3)
            "CONTINUOUS" -> Color(0xFFEEEEFB)
            "PERFECT" -> Color(0xFFFBF0E4)
            else -> Color(0xFFF8EAF5)
        }

    /** Text on aspectSurface: titleRu in the formulas card, the chip and panel headers. */
    fun aspectInk(aspect: String): Color =
        when (aspect) {
            "SIMPLE" -> Color(0xFF0B5E55)
            "CONTINUOUS" -> Color(0xFF33359B)
            "PERFECT" -> Color(0xFF8A4A0B)
            else -> Color(0xFF78295F)
        }

    /** Muted text on aspectSurface: formula labels, comparison bullet points. */
    fun aspectMutedInk(aspect: String): Color =
        when (aspect) {
            "SIMPLE" -> Color(0xFF3E7C76)
            "CONTINUOUS" -> Color(0xFF5A5EA8)
            "PERFECT" -> Color(0xFF9C6220)
            else -> Color(0xFF944E80)
        }

    /** Short column label in the tense matrix map: S / C / P / PC. */
    fun aspectShortLabel(aspect: String): String =
        when (aspect) {
            "SIMPLE" -> "S"
            "CONTINUOUS" -> "C"
            "PERFECT" -> "P"
            else -> "PC"
        }

    private enum class Family { AAA, ABB, ABC }

    private fun family(group: String): Family =
        when {
            group.startsWith("ABB") -> Family.ABB
            group.startsWith("ABC") -> Family.ABC
            else -> Family.AAA // AAA, ABA
        }
}

// --------------------------------------------------------------- spacing

@Immutable
data class BrainDropSpacing(
    val xxs: Dp = 4.dp,
    val xs: Dp = 8.dp,
    val sm: Dp = 12.dp,
    val md: Dp = 16.dp,
    val lg: Dp = 24.dp,
    val xl: Dp = 32.dp,
    val xxl: Dp = 48.dp,
    /** Minimum touch target: the back button, header icons. */
    val touchTarget: Dp = 44.dp,
    /** The quiz footer is always reserved — the layout shouldn't jump. */
    val quizFooterHeight: Dp = 96.dp,
)

// ---------------------------------------------------------------- shapes

@Immutable
data class BrainDropShapes(
    val sm: Shape = RoundedCornerShape(8.dp),
    val md: Shape = RoundedCornerShape(14.dp),
    val button: Shape = RoundedCornerShape(16.dp),
    val lg: Shape = RoundedCornerShape(18.dp),
    val card: Shape = RoundedCornerShape(20.dp),
    val xl: Shape = RoundedCornerShape(22.dp),
    val xxl: Shape = RoundedCornerShape(24.dp),
    val chip: Shape = RoundedCornerShape(percent = 50),
)

private val Material3Shapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(14.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(20.dp),
    extraLarge = RoundedCornerShape(24.dp),
)

// ------------------------------------------------------------ typography

@Composable
private fun manrope(): FontFamily =
    FontFamily(
        Font(Res.font.manrope_medium, FontWeight.Medium),
        Font(Res.font.manrope_semibold, FontWeight.SemiBold),
        Font(Res.font.manrope_bold, FontWeight.Bold),
        Font(Res.font.manrope_extrabold, FontWeight.ExtraBold),
    )

@Composable
private fun plexMono(): FontFamily =
    FontFamily(
        Font(Res.font.ibm_plex_mono_medium, FontWeight.Medium),
        Font(Res.font.ibm_plex_mono_semibold, FontWeight.SemiBold),
    )

/**
 * Styles that don't have a matching Material 3 role: verb forms, mono labels
 * for grammar terms, counters. Italics aren't used anywhere in the system —
 * the Russian translation is set apart by weight, size, ink500 color, and a
 * 2dp vertical rule on the left.
 */
@Immutable
data class BrainDropType(
    /** Result score, quiz word, base form on the detail screen. */
    val display: TextStyle,
    /** Verb base form in the list row. */
    val verbBase: TextStyle,
    /** "arose · arisen" — mono, next to the base form. */
    val verbForms: TextStyle,
    /** Verb form in VerbFormsCard, to the right of the EN label. */
    val verbForm: TextStyle,
    /** BASE FORM / PAST SIMPLE / EXAMPLES — mono, UPPERCASE, letterSpacing .09em. */
    val label: TextStyle,
    /** 47/179, 4/10 — mono. */
    val counter: TextStyle,
    /** A·B·C — family label. */
    val family: TextStyle,
    /** Russian translation in the list row and examples. */
    val translation: TextStyle,
    /** Button labels. */
    val button: TextStyle,
)

// ----------------------------------------------------------------- theme

// These token classes are pure defaults with no per-composition state, so a single shared
// instance is enough — avoids reallocating one on every BrainDropTheme recomposition.
private val DefaultBrainDropSemantics = BrainDropSemantics()
private val DefaultBrainDropSpacing = BrainDropSpacing()
private val DefaultBrainDropShapes = BrainDropShapes()

val LocalBrainDropSemantics: ProvidableCompositionLocal<BrainDropSemantics> =
    staticCompositionLocalOf { DefaultBrainDropSemantics }
val LocalBrainDropSpacing: ProvidableCompositionLocal<BrainDropSpacing> =
    staticCompositionLocalOf { DefaultBrainDropSpacing }
val LocalBrainDropShapes: ProvidableCompositionLocal<BrainDropShapes> =
    staticCompositionLocalOf { DefaultBrainDropShapes }
val LocalBrainDropType: ProvidableCompositionLocal<BrainDropType> =
    staticCompositionLocalOf { error("BrainDropType not provided — wrap the UI in BrainDropTheme") }

/** Access points: BrainDropTheme.semantics, .spacing, .shapes, .type */
object BrainDropTheme {
    val semantics: BrainDropSemantics
        @Composable get() = LocalBrainDropSemantics.current
    val spacing: BrainDropSpacing
        @Composable get() = LocalBrainDropSpacing.current
    val shapes: BrainDropShapes
        @Composable get() = LocalBrainDropShapes.current
    val type: BrainDropType
        @Composable get() = LocalBrainDropType.current
}

@Composable
fun BrainDropTheme(content: @Composable () -> Unit) {
    // Dark theme isn't designed in this iteration — see the file-level note above. Reading
    // isSystemInDarkTheme() here (even unused) would subscribe this whole subtree to dark-mode
    // changes for no effect, so it's deliberately not called.

    val sans = manrope()
    val mono = plexMono()

    val typography = remember(sans, mono) {
        Typography(
            // h1 — headline on Home
            headlineMedium = TextStyle(
                fontFamily = sans,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 25.sp,
                lineHeight = 30.5.sp,
                letterSpacing = (-0.5).sp,
            ),
            // h2 — screen header
            headlineSmall = TextStyle(
                fontFamily = sans,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp,
                lineHeight = 24.sp,
                letterSpacing = (-0.3).sp,
            ),
            // Result card / empty state title
            titleLarge = TextStyle(
                fontFamily = sans,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 22.sp,
                lineHeight = 27.5.sp,
                letterSpacing = (-0.33).sp,
            ),
            // Category card title, group header
            titleMedium = TextStyle(
                fontFamily = sans,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 16.5.sp,
                lineHeight = 20.5.sp,
            ),
            titleSmall = TextStyle(
                fontFamily = sans,
                fontWeight = FontWeight.Bold,
                fontSize = 14.5.sp,
                lineHeight = 17.5.sp,
            ),
            // Examples, answer options
            bodyLarge = TextStyle(
                fontFamily = sans,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                lineHeight = 21.75.sp,
            ),
            // Descriptions, subtitles
            bodyMedium = TextStyle(
                fontFamily = sans,
                fontWeight = FontWeight.Medium,
                fontSize = 13.5.sp,
                lineHeight = 20.25.sp,
            ),
            bodySmall = TextStyle(
                fontFamily = sans,
                fontWeight = FontWeight.Medium,
                fontSize = 12.5.sp,
                lineHeight = 17.5.sp,
            ),
            // Filter chips
            labelLarge = TextStyle(
                fontFamily = sans,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                lineHeight = 13.sp,
            ),
            labelMedium = TextStyle(
                fontFamily = sans,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                lineHeight = 15.6.sp,
            ),
            labelSmall = TextStyle(
                fontFamily = mono,
                fontWeight = FontWeight.SemiBold,
                fontSize = 11.sp,
                lineHeight = 11.sp,
                letterSpacing = 0.9.sp,
            ),
        )
    }

    val brainDropType = remember(sans, mono) {
        BrainDropType(
            display = TextStyle(
                fontFamily = sans,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 40.sp,
                lineHeight = 42.sp,
                letterSpacing = (-1.2).sp,
            ),
            verbBase = TextStyle(
                fontFamily = sans,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 17.sp,
                lineHeight = 20.4.sp,
            ),
            verbForms = TextStyle(
                fontFamily = mono,
                fontWeight = FontWeight.Medium,
                fontSize = 13.5.sp,
                lineHeight = 16.2.sp,
            ),
            verbForm = TextStyle(
                fontFamily = sans,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp,
                lineHeight = 20.sp,
            ),
            label = TextStyle(
                fontFamily = mono,
                fontWeight = FontWeight.SemiBold,
                fontSize = 10.5.sp,
                lineHeight = 10.5.sp,
                letterSpacing = 0.95.sp,
            ),
            counter = TextStyle(
                fontFamily = mono,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                lineHeight = 12.sp,
            ),
            family = TextStyle(
                fontFamily = mono,
                fontWeight = FontWeight.SemiBold,
                fontSize = 10.sp,
                lineHeight = 10.sp,
                letterSpacing = 0.8.sp,
            ),
            translation = TextStyle(
                fontFamily = sans,
                fontWeight = FontWeight.Medium,
                fontSize = 13.sp,
                lineHeight = 18.2.sp,
            ),
            button = TextStyle(
                fontFamily = sans,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 16.sp,
                lineHeight = 16.sp,
            ),
        )
    }

    androidx.compose.runtime.CompositionLocalProvider(
        LocalBrainDropSemantics provides DefaultBrainDropSemantics,
        LocalBrainDropSpacing provides DefaultBrainDropSpacing,
        LocalBrainDropShapes provides DefaultBrainDropShapes,
        LocalBrainDropType provides brainDropType,
    ) {
        MaterialTheme(
            colorScheme = BrainDropLightColorScheme,
            typography = typography,
            shapes = Material3Shapes,
            content = content,
        )
    }
}
