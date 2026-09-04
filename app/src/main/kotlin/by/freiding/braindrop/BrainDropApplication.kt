package by.freiding.braindrop

import android.app.Application
import android.util.Log
import by.freiding.braindrop.core.analytics.di.noOpAnalyticsModule
import by.freiding.braindrop.core.analytics.firebase.di.firebaseAnalyticsModule
import by.freiding.braindrop.core.analytics.firebase.isFirebaseAvailable
import by.freiding.braindrop.core.common.di.commonModule
import by.freiding.braindrop.core.database.AndroidDatabaseDriverFactory
import by.freiding.braindrop.core.database.DatabaseDriverFactory
import by.freiding.braindrop.core.database.di.databaseModule
import by.freiding.braindrop.feature.home.di.homeModule
import by.freiding.braindrop.feature.irregularverbs.di.irregularVerbsModule
import by.freiding.braindrop.feature.phrasalverbs.di.phrasalVerbsModule
import by.freiding.braindrop.feature.profile.di.profileModule
import by.freiding.braindrop.feature.tenses.di.tensesModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class BrainDropApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Firebase auto-initializes from google-services.json before onCreate runs. When the
        // file is absent (contributor / CI builds) the real backend is unavailable and analytics
        // degrades to the no-op implementations.
        val firebaseReady = isFirebaseAvailable(this)
        val analyticsModule = if (firebaseReady) firebaseAnalyticsModule else noOpAnalyticsModule
        Log.i(TAG, "Analytics backend: ${if (firebaseReady) "Firebase" else "no-op"}")

        startKoin {
            androidContext(this@BrainDropApplication)
            modules(
                module { single<DatabaseDriverFactory> { AndroidDatabaseDriverFactory(androidContext()) } },
                commonModule,
                databaseModule,
                analyticsModule,
                homeModule,
                irregularVerbsModule,
                tensesModule,
                phrasalVerbsModule,
                profileModule,
            )
        }
    }

    private companion object {
        const val TAG = "BrainDrop"
    }
}
