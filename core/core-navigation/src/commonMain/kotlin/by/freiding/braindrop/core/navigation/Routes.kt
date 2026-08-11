package by.freiding.braindrop.core.navigation

import kotlinx.serialization.Serializable

sealed interface Routes {
    @Serializable
    data object Home : Routes

    @Serializable
    data object Profile : Routes

    @Serializable
    data object Progress : Routes

    @Serializable
    data object IrregularVerbsList : Routes

    @Serializable
    data class IrregularVerbDetail(
        val verbId: String,
    ) : Routes

    @Serializable
    data class IrregularVerbsQuiz(
        val mode: String = "EN_TO_RU",
    ) : Routes

    @Serializable
    data object TensesList : Routes

    @Serializable
    data class TenseDetail(
        val tenseId: String,
    ) : Routes

    @Serializable
    data object TenseComparisons : Routes

    @Serializable
    data object TenseCheatSheet : Routes

    @Serializable
    data class TensesQuiz(
        val mode: String = "MIXED_REVIEW",
    ) : Routes
}
