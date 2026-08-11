package by.freiding.braindrop

import android.app.Application
import by.freiding.braindrop.core.common.di.commonModule
import by.freiding.braindrop.core.database.AndroidDatabaseDriverFactory
import by.freiding.braindrop.core.database.DatabaseDriverFactory
import by.freiding.braindrop.core.database.di.databaseModule
import by.freiding.braindrop.feature.home.di.homeModule
import by.freiding.braindrop.feature.irregularverbs.di.irregularVerbsModule
import by.freiding.braindrop.feature.tenses.di.tensesModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class BrainDropApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BrainDropApplication)
            modules(
                module { single<DatabaseDriverFactory> { AndroidDatabaseDriverFactory(androidContext()) } },
                commonModule,
                databaseModule,
                homeModule,
                irregularVerbsModule,
                tensesModule,
            )
        }
    }
}
