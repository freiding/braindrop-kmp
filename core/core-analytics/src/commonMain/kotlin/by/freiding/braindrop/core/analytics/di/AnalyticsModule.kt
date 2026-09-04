package by.freiding.braindrop.core.analytics.di

import by.freiding.braindrop.core.analytics.AnalyticsTracker
import by.freiding.braindrop.core.analytics.CrashReporter
import by.freiding.braindrop.core.analytics.NoOpAnalyticsTracker
import by.freiding.braindrop.core.analytics.NoOpCrashReporter
import org.koin.dsl.module

/**
 * Fallback bindings used when no real analytics backend is available: iOS, and Android
 * builds without a `google-services.json`. The app decides between this and
 * `firebaseAnalyticsModule` at startup.
 */
val noOpAnalyticsModule = module {
    single<AnalyticsTracker> { NoOpAnalyticsTracker }
    single<CrashReporter> { NoOpCrashReporter }
}
