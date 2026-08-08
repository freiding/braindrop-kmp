package by.freiding.braindrop.feature.home.domain.model

/**
 * A study category on Home. Its title and description aren't stored here as text —
 * they're localized in the presentation layer by [id], see StudyCategoryCard.
 */
data class StudyCategory(
    val id: String,
    val icon: String,
    val totalItems: Int,
    val studiedCount: Int = 0,
    val isAvailable: Boolean = true,
    /** Secondary counter for the category card, e.g. the number of verb groups. */
    val secondaryCount: Int? = null,
)
