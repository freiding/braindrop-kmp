package by.freiding.braindrop

import androidx.compose.ui.window.ComposeUIViewController
import by.freiding.braindrop.core.common.di.commonModule
import by.freiding.braindrop.core.database.DatabaseDriverFactory
import by.freiding.braindrop.core.database.IosDatabaseDriverFactory
import by.freiding.braindrop.core.database.di.databaseModule
import by.freiding.braindrop.feature.home.di.homeModule
import org.koin.core.context.startKoin
import org.koin.dsl.module

@Suppress("ktlint:standard:function-naming", "FunctionNaming")
fun MainViewController() =
    ComposeUIViewController {
        App()
    }

fun initKoin() {
    startKoin {
        modules(
            module { single<DatabaseDriverFactory> { IosDatabaseDriverFactory() } },
            commonModule,
            databaseModule,
            homeModule,
        )
    }
}
