package com.cfcici.`in`.project.data.repository

import com.cfcici.`in`.project.data.database.User
import com.cfcici.`in`.project.data.database.UserCar
import com.cfcici.`in`.project.data.database.UserDao

class UserRepository (
    private val userDaoFromRepo: UserDao){

    suspend fun insertUserRepo(userRepo: User){
        userDaoFromRepo.insert(userRepo)
    }

    suspend fun deleteUserRepo(userRepo: User){
        userDaoFromRepo.delete(userRepo)
    }

    suspend fun getUserRepo(): List<User>{
        return userDaoFromRepo.getAll()
    }

    suspend fun  emailExistsRepo(email: String): Boolean{
        val existingUser = userDaoFromRepo.getEmailIdDuplication(email)
        return existingUser != null
    }

    suspend fun getUserByUsernameRepo(usernameRepo: String): User?{
        return userDaoFromRepo.getUserByUsername(usernameUserDao = usernameRepo)
    }

    suspend fun loginCheckerRepo(username: String, password: String): Boolean{
        val loginUser = userDaoFromRepo.loginChecker(username, password)
        return loginUser != null
    }

    suspend fun insertUserCarRepo(userCarRepo: UserCar){
         userDaoFromRepo.insertUserCar(userCarRepo)
    }

    suspend fun deleteUserCarRepo(userCarRepo: UserCar){
        userDaoFromRepo.deleteUserCar(userCarRepo)
    }

    suspend fun getCarOnSearchBarRepo(userIdUserRepo: Int, modelUserRepo: String): List<UserCar>{
        return userDaoFromRepo.getCarOnSearchBar(userIdUserRepo, modelUserRepo)
    }
}