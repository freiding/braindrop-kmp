package by.freiding.braindrop.core.analytics

/**
 * No-op [AnalyticsTracker] used where no backend is configured — iOS today, Android
 * builds without a `google-services.json`, unit tests, and Compose previews.
 */
object NoOpAnalyticsTracker : AnalyticsTracker {
    override fun logEvent(
        name: String,
        params: Map<String, Any?>,
    ) = Unit

    override fun logScreenView(
        screenName: String,
        screenClass: String?,
    ) = Unit

    override fun setUserProperty(
        name: String,
        value: String?,
    ) = Unit

    override fun setUserId(userId: String?) = Unit

    override fun setCollectionEnabled(enabled: Boolean) = Unit
}

/**
 * No-op [CrashReporter] used where no backend is configured — iOS today, Android
 * builds without a `google-services.json`, unit tests, and Compose previews.
 */
object NoOpCrashReporter : CrashReporter {
    override fun recordException(
        throwable: Throwable,
        context: Map<String, Any?>,
    ) = Unit

    override fun log(message: String) = Unit

    override fun setCustomKey(
        key: String,
        value: Any?,
    ) = Unit

    override fun setUserId(userId: String?) = Unit

    override fun setCollectionEnabled(enabled: Boolean) = Unit
}
