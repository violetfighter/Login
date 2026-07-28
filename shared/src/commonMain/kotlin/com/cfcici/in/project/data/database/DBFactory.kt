package com.cfcici.`in`.project.data.database

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class DBFactory(context: Any?) {
    fun createDatabase(): AppDatabase
}
