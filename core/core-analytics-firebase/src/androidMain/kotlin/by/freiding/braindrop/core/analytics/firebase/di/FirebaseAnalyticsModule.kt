package by.freiding.braindrop.core.analytics.firebase.di

import by.freiding.braindrop.core.analytics.AnalyticsTracker
import by.freiding.braindrop.core.analytics.CrashReporter
import by.freiding.braindrop.core.analytics.firebase.FirebaseAnalyticsTracker
import by.freiding.braindrop.core.analytics.firebase.FirebaseCrashReporter
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Binds the Firebase-backed [AnalyticsTracker] and [CrashReporter]. Loaded only on
 * Android builds that ship a `google-services.json`; see `BrainDropApplication`.
 */
val firebaseAnalyticsModule = module {
    single<AnalyticsTracker> { FirebaseAnalyticsTracker(androidContext()) }
    single<CrashReporter> { FirebaseCrashReporter() }
}
