@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.cfcici.`in`.project.data.database

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers

actual class DBFactory actual constructor(private val context: Any?) {

    actual fun createDatabase(): AppDatabase {
        val appContext = context as Context
        val dbFile = appContext.getDatabasePath(dbFileName)

        return Room.databaseBuilder<AppDatabase>(
            appContext,
            dbFile.absolutePath,
            factory = { AppDatabaseConstructor.initialize() }
        )
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }
}
