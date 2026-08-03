@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
//Warning sign
package com.cfcici.`in`.project.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor

@Database(entities = [User::class], version = 2)

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
