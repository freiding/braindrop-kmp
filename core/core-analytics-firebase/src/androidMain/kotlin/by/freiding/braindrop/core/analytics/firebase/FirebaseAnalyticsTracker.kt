package by.freiding.braindrop.core.analytics.firebase

import android.content.Context
import android.os.Bundle
import by.freiding.braindrop.core.analytics.AnalyticsTracker
import com.google.firebase.analytics.FirebaseAnalytics

/**
 * [AnalyticsTracker] backed by Firebase Analytics. Bound only when Firebase has been
 * initialized from a bundled `google-services.json`; see [isFirebaseAvailable].
 */
internal class FirebaseAnalyticsTracker(
    context: Context,
) : AnalyticsTracker {
    private val firebaseAnalytics = FirebaseAnalytics.getInstance(context)

    override fun logEvent(
        name: String,
        params: Map<String, Any?>,
    ) {
        firebaseAnalytics.logEvent(name, params.toBundleOrNull())
    }

    override fun logScreenView(
        screenName: String,
        screenClass: String?,
    ) {
        firebaseAnalytics.logEvent(
            FirebaseAnalytics.Event.SCREEN_VIEW,
            Bundle().apply {
                putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
                screenClass?.let { putString(FirebaseAnalytics.Param.SCREEN_CLASS, it) }
            },
        )
    }

    override fun setUserProperty(
        name: String,
        value: String?,
    ) {
        firebaseAnalytics.setUserProperty(name, value)
    }

    override fun setUserId(userId: String?) {
        firebaseAnalytics.setUserId(userId)
    }

    override fun setCollectionEnabled(enabled: Boolean) {
        firebaseAnalytics.setAnalyticsCollectionEnabled(enabled)
    }
}

private fun Map<String, Any?>.toBundleOrNull(): Bundle? {
    if (isEmpty()) return null
    return Bundle().apply {
        for ((key, value) in this@toBundleOrNull) {
            when (value) {
                null -> Unit
                is String -> putString(key, value)
                is Int -> putInt(key, value)
                is Long -> putLong(key, value)
                is Double -> putDouble(key, value)
                is Float -> putFloat(key, value)
                is Boolean -> putBoolean(key, value)
                else -> putString(key, value.toString())
            }
        }
    }
}
