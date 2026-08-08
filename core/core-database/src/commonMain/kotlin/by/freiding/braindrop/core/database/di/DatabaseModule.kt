package by.freiding.braindrop.core.database.di

import by.freiding.braindrop.core.database.DatabaseDriverFactory
import by.freiding.braindrop.core.database.datasource.DailyActivityDataSource
import by.freiding.braindrop.database.AppDatabase
import org.koin.dsl.module

val databaseModule = module {
    single { AppDatabase(get<DatabaseDriverFactory>().create()) }
    single { get<AppDatabase>().studyProgressQueries }
    single { get<AppDatabase>().irregularVerbProgressQueries }
    single { get<AppDatabase>().dailyActivityQueries }
    single { DailyActivityDataSource(get()) }
}
