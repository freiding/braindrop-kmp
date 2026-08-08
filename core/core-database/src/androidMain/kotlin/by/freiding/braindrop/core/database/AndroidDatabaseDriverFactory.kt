package by.freiding.braindrop.core.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import by.freiding.braindrop.database.AppDatabase

class AndroidDatabaseDriverFactory(
    private val context: Context,
) : DatabaseDriverFactory {
    override fun create(): SqlDriver = AndroidSqliteDriver(AppDatabase.Schema, context, "braindrop.db")
}
