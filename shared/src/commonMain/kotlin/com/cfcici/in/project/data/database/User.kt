package com.cfcici.`in`.project.data.database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "users",
    indices = [Index(value = ["usernameUser"], unique = true)]) // it checks if the user is in the database before
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val usernameUser: String,
    val passwordUser: String,
    val dateOfBirthUser: String,
    val emailIdUser: String,

)