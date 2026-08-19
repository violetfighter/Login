package com.cfcici.`in`.project.data.repository

import com.cfcici.`in`.project.data.database.User
import com.cfcici.`in`.project.data.database.UserCar
import com.cfcici.`in`.project.data.database.UserDao
import com.cfcici.`in`.project.data.database.UserSelectedBrandCars

class UserRepository (
    private val userDaoFromRepo: UserDao){

    suspend fun insertUserRepo(userRepo: User){
        userDaoFromRepo.insert(userRepo)
    }
    suspend fun getUserRepo(): List<User>{
        return userDaoFromRepo.getAll()
    }

    suspend fun deleteUserRepo(userRepo: User){
        userDaoFromRepo.deleteUser(userRepo)
    }

    suspend fun updateCarEditRepo(userCarRepo: UserCar){
        return userDaoFromRepo.updateCarEdit(userCarRepo)
    }

    suspend fun updateProfileEditRepo(userProfileRepo: User){
        return userDaoFromRepo.updateProfileEdit(userProfileRepo)
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

    //########################################################################################################################################################################

    suspend fun insertUserOwnedCarRepo(userOwnedCarRepo: UserCar){
         userDaoFromRepo.insertUserOwnedCar(userOwnedCarRepo)
    }

    suspend fun deleteUserOwnedCarRepo(userCarRepo: UserCar){
        userDaoFromRepo.deleteUserOwnedCar(userCarRepo)
    }

    // Get cars belonging to one specific brand
    suspend fun getUserOwnedCarsByBrandRepo(userIdRepo: Int, brandRepo: String): List<UserCar>{
        return userDaoFromRepo.getUserOwnedCarsByBrands(userIdRepo, brandRepo)
    }

    //########################################################################################################################################################################

    suspend fun getCarOnSearchBarRepo(userIdUserRepo: Int, modelUserRepo: String): List<UserCar>{
        return userDaoFromRepo.getCarOnSearchBar(userIdUserRepo, modelUserRepo)
    }

    suspend fun insertSelectedCarBrandRepo(selectedBrandCarsRepo: UserSelectedBrandCars){
        userDaoFromRepo.insertSelectedCarBrand(selectedBrandCarsRepo)
    }

    //get selected brands
    suspend fun getSelectedCarBrandsRepo(userIdRepo: Int): List<UserSelectedBrandCars> {
        return userDaoFromRepo.getSelectedCarBrands(userIdRepo)
    }

    //delete selected brand and cars at same time so we put both function in one repo
    suspend fun deleteSelectedCarBrandRepo(userId: Int, selectedBrandCarsRepo: String) {
        userDaoFromRepo.deleteUserOwnedCarsByBrand(userId, selectedBrandCarsRepo)
        userDaoFromRepo.deleteSelectedBrand(userId, selectedBrandCarsRepo
        )
    }
}