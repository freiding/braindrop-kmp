package by.freiding.braindrop.core.common.di

import by.freiding.braindrop.core.common.AppDispatchers
import org.koin.dsl.module

val commonModule = module {
    single { AppDispatchers() }
}
