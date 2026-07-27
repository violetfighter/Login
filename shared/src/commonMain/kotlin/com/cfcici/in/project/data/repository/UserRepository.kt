package com.cfcici.`in`.project.data.repository

import com.cfcici.`in`.project.data.database.User
import com.cfcici.`in`.project.data.database.UserDao

class UserRepository (
    private val userDaoFromRepo: UserDao
){
    suspend fun insertUserRepo(user: User){
        userDaoFromRepo.insert(user)
    }
    suspend fun deleteUserRepo(user: User){
        userDaoFromRepo.delete(user)
    }
    suspend fun getUserRepo(): List<User>{
        return userDaoFromRepo.getAll()
    }
}