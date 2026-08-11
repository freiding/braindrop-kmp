package by.freiding.braindrop.feature.tenses.presentation.common

/** "present_continuous" -> "Present Continuous" — used wherever a tense id needs a display label without a full lookup. */
internal fun formatTenseId(id: String): String =
    id.split("_").joinToString(" ") { it.replaceFirstChar(Char::uppercase) }
