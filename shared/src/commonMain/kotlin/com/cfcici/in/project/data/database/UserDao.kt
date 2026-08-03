package com.cfcici.`in`.project.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    suspend fun getAll(): List<User>

    @Insert
    suspend fun insert(vararg user: User)

    @Delete
    suspend fun delete(user: User)

    //Asynchronously check my user table for this email and return true if it exists,  otherise return false
    // Means look inside  the user table and find the email already exist
    @Query("SELECT * FROM users WHERE emailIdUser = :email LIMIT 1")// email is a parameter
    suspend fun getUserByEmail(email: String): User?

    //AND guarantees that both conditions have to be true for the same row at the same time,
    // so there's no way for SQL to accidentally pair a username from one row with a password from a different row.
    // Either one single row satisfies both conditions together, or no row does — there's no in-between, no mixing.
    @Query("SELECT * FROM users WHERE usernameUser = :username AND passwordUser = :password LIMIT 1")
    suspend fun loginChecker(username: String, password: String): User?
}
