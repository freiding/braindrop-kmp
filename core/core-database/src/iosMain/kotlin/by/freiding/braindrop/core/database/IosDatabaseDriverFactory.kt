package by.freiding.braindrop.core.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import by.freiding.braindrop.database.AppDatabase

class IosDatabaseDriverFactory : DatabaseDriverFactory {
    override fun create(): SqlDriver = NativeSqliteDriver(AppDatabase.Schema, "braindrop.db")
}
