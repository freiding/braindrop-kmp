package by.freiding.braindrop.feature.vocabulary.domain.model

/** Where a chunk sits in the review schedule — drives the list-row badge and the detail SRS strip. */
sealed interface ReviewStatus {
    /** Never reviewed. */
    data object New : ReviewStatus

    /** Due today (or overdue). */
    data object DueToday : ReviewStatus

    /** Due in [days] days (always >= 1). */
    data class DueInDays(
        val days: Int,
    ) : ReviewStatus

    /** Box reached the learned threshold. */
    data object Learned : ReviewStatus
}
