package by.freiding.braindrop.core.analytics.firebase

import android.content.Context
import com.google.firebase.FirebaseApp

/**
 * True when Firebase has been initialized from a bundled `google-services.json`
 * (its `FirebaseInitProvider` runs before [android.app.Application.onCreate]).
 *
 * The app uses this to choose between [by.freiding.braindrop.core.analytics.firebase.di.firebaseAnalyticsModule]
 * and the no-op module, so contributor and CI builds without the file still start cleanly.
 */
fun isFirebaseAvailable(context: Context): Boolean = FirebaseApp.getApps(context).isNotEmpty()
