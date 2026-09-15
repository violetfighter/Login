@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
//Warning sign
package com.cfcici.`in`.project.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL

@Database(entities = [User::class, UserCar::class, UserSelectedBrandCars::class], version = 16)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("KotlinNoActualForExpect")// Warning signs
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}

internal const val dbFileName = "app_room_db.db"

val MIGRATION_ADD_THEME = object : Migration(15, 16) {
    override fun migrate(connection: SQLiteConnection) {
        // If version 15 didn't have isDarkMode, add it here.
        // If it did, this migration can be empty or handle other changes.
        // Based on recent changes, we'll ensure the column exists safely.
        connection.execSQL("ALTER TABLE Users ADD COLUMN isDarkMode INTEGER NOT NULL DEFAULT 1")
    }
}
