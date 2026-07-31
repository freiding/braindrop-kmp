package by.freiding.braindrop.core.database

import app.cash.sqldelight.db.SqlDriver

interface DatabaseDriverFactory {
    fun create(): SqlDriver
}
