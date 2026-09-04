package by.freiding.braindrop.core.analytics.firebase

import by.freiding.braindrop.core.analytics.CrashReporter
import com.google.firebase.crashlytics.FirebaseCrashlytics

/**
 * [CrashReporter] backed by Firebase Crashlytics. Uncaught exceptions are captured
 * automatically by the Crashlytics SDK; [recordException] covers handled errors.
 */
internal class FirebaseCrashReporter : CrashReporter {
    private val crashlytics = FirebaseCrashlytics.getInstance()

    override fun recordException(
        throwable: Throwable,
        context: Map<String, Any?>,
    ) {
        for ((key, value) in context) {
            setCustomKey(key, value)
        }
        crashlytics.recordException(throwable)
    }

    override fun log(message: String) {
        crashlytics.log(message)
    }

    override fun setCustomKey(
        key: String,
        value: Any?,
    ) {
        when (value) {
            null -> Unit
            is String -> crashlytics.setCustomKey(key, value)
            is Int -> crashlytics.setCustomKey(key, value)
            is Long -> crashlytics.setCustomKey(key, value)
            is Double -> crashlytics.setCustomKey(key, value)
            is Float -> crashlytics.setCustomKey(key, value)
            is Boolean -> crashlytics.setCustomKey(key, value)
            else -> crashlytics.setCustomKey(key, value.toString())
        }
    }

    override fun setUserId(userId: String?) {
        crashlytics.setUserId(userId.orEmpty())
    }

    override fun setCollectionEnabled(enabled: Boolean) {
        crashlytics.isCrashlyticsCollectionEnabled = enabled
    }
}
