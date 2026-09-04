package by.freiding.braindrop.core.analytics

/**
 * Vendor-neutral crash and non-fatal error reporting contract.
 *
 * Fatal crashes are captured automatically by the active adapter. Call [recordException]
 * for handled errors that should still surface in the dashboard (for example, the error
 * branch of a `Result`).
 */
interface CrashReporter {
    /** Records a handled (non-fatal) exception with optional structured context attached as custom keys. */
    fun recordException(
        throwable: Throwable,
        context: Map<String, Any?> = emptyMap(),
    )

    /** Adds a breadcrumb-style log line attached to the next crash report. */
    fun log(message: String)

    /** Attaches a custom key/value shown on every subsequent crash report. */
    fun setCustomKey(
        key: String,
        value: Any?,
    )

    /** Associates crash reports with [userId], or clears the association when null. */
    fun setUserId(userId: String?)

    /** Toggles crash collection at runtime, e.g. after a consent prompt. */
    fun setCollectionEnabled(enabled: Boolean)
}
