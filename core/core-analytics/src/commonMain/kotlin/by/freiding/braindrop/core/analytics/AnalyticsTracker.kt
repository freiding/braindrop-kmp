package by.freiding.braindrop.core.analytics

/**
 * Vendor-neutral analytics contract.
 *
 * Presentation and domain code depend only on this interface. The concrete backend
 * (Firebase today, Sentry or PostHog tomorrow) lives behind a separate adapter module
 * and is swapped by changing which Koin module is loaded at startup — see
 * `core-analytics-firebase` and `noOpAnalyticsModule`.
 */
interface AnalyticsTracker {
    /**
     * Logs a custom event.
     *
     * @param name snake_case event name, e.g. `quiz_completed`.
     * @param params flat map of primitive values (`String`, `Boolean`, `Int`, `Long`,
     * `Double`, `Float`). Adapters coerce unsupported types to their string form.
     */
    fun logEvent(
        name: String,
        params: Map<String, Any?> = emptyMap(),
    )

    /**
     * Logs a screen view.
     *
     * @param screenName human-readable screen identifier.
     * @param screenClass optional grouping key, e.g. the composable or route name.
     */
    fun logScreenView(
        screenName: String,
        screenClass: String? = null,
    )

    /** Sets a durable user property used for audience segmentation, or clears it when [value] is null. */
    fun setUserProperty(
        name: String,
        value: String?,
    )

    /** Associates subsequent events with [userId], or clears the association when null. */
    fun setUserId(userId: String?)

    /** Toggles collection at runtime, e.g. after a consent prompt. */
    fun setCollectionEnabled(enabled: Boolean)
}
