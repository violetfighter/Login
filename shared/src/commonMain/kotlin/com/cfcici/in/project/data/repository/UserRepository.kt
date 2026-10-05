package com.cfcici.`in`.project.data.repository

import com.cfcici.`in`.project.data.database.UserCar
import com.cfcici.`in`.project.data.database.UserDao
import com.cfcici.`in`.project.data.database.UserSelectedBrandCars
import com.cfcici.`in`.project.network.ApiCar
import com.cfcici.`in`.project.network.ApiDao
import com.cfcici.`in`.project.network.ApiUser
import com.cfcici.`in`.project.network.CreateUserRequest
import com.cfcici.`in`.project.network.LoginResultApiUser
import com.cfcici.`in`.project.ui.AppTheme
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.flow.Flow

class UserRepository (
    private val userDaoFromRepo: UserDao,
    private val apiDao: ApiDao
){

    suspend fun insertUserRepo(userRepo: CreateUserRequest): Boolean{
        //userDaoFromRepo.insert(userRepo)
        return apiDao.createUser(userRepo)
    }

    suspend fun usernameExistsRepo(usernameRepo: String): Boolean{
        //val existingUser = userDaoFromRepo.getUsernameIdDuplication(usernameRepo)
        //return existingUser != null
        return apiDao.usernameExistsApi(usernameRepo)
    }

    //suspend and Flow won't get along
    //suspend = "pause here, do work, hand back one final value."
    //Flow = "here's an ongoing stream of values that arrive over time, no pausing needed to declare it."
    suspend fun getUserDetailsRepo(userIdRepo: Int): ApiUser? {
        //return userDaoFromRepo.getAll(userIdRepo)
        return apiDao.getUserByIdApi(userIdRepo)
    }

    //suspend fun deleteUserRepo(userRepo: User){
        //userDaoFromRepo.deleteUser(userRepo)
        //apiDao.deleteUserApi(userRepo)
    //}

    suspend fun deleteUserRepo(userId: Int): Boolean = apiDao.deleteUserApi(userId)

    suspend fun updateCarEditRepo(userCarRepo: ApiCar): Boolean = apiDao.carEdit(userCarRepo)
    suspend fun updateProfileEditRepo(userProfileRepo: ApiUser): Boolean{
        //return userDaoFromRepo.updateProfileEdit(userProfileRepo)
        return apiDao.profilePicture(userProfileRepo.usernameUser, userProfileRepo.userPhotoUser)

    }

    suspend fun  emailExistsRepo(emailRepo: String): Boolean{
        //val existingUser = userDaoFromRepo.getEmailIdDuplication(email)
        //return existingUser != null
        return apiDao.emailExistsApi(emailRepo)
    }

    /*
    suspend fun getUserByUsernameRepo(usernameRepo: String): GetUserByUsernameApiUser? {
        //return userDaoFromRepo.getUserByUsername(usernameUserDao = usernameRepo)
        return apiDao.getUserByUsernameApi(usernameRepo)
    }*/

    //########################################################################################################################################################################

    suspend fun insertUserOwnedCarRepo(car: ApiCar): Boolean = apiDao.createCarApi(car)

    suspend fun loginCheckerRepo(usernameRepo: String, passwordRepo: String): LoginResultApiUser?{
        return  apiDao.loginCheckerApi(usernameRepo, passwordRepo)
    }

    suspend fun deleteUserOwnedCarRepo(userCarRepo: Int): Boolean = apiDao.deleteCarApi(userCarRepo)


    // Get cars belonging to one specific brand
    suspend fun getUserOwnedCarsByBrandRepo(userId: Int, carBrand: String): List<ApiCar> {
        return apiDao.getCarsByBrandApi(userId, carBrand)
    }

    suspend fun totalCarsUserOwnRepo(userIdRepo: Int): Int? {
        //return userDaoFromRepo.totalCarsUserOwn(userIdDao = userIdRepo)
        return apiDao.totalCarUserOwnApi(userIdRepo)
    }


    //########################################################################################################################################################################

    /*
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
*/
    suspend fun sendPasswordResetEmailRepo(email: String): Result<Unit> { // remove
        return try {
            Firebase.auth.sendPasswordResetEmail(email)
            Result.success(Unit)
        }
        catch (e: Exception) {
            println("sendPasswordResetEmail failed: ${e.message} &&&")
            Result.failure(e)
        }
    }

    suspend fun  updateSelectedThemeRepo(userId: Int, theme: AppTheme): Boolean{
        return apiDao.themeUpdateApi(userId, theme.name)
    }

    suspend fun updateDarkModeRepo(userId: Int, isDark: Boolean) : Boolean{
        return apiDao.darkModeApi(userId, isDark)
    }

    suspend fun usernameChangeRepo(userId: Int, username: String): String?{
        return apiDao.usernameChange(userId, username)
    }

    suspend fun dobChangeRepo(userId: Int, username: String): String?{
        return apiDao.dobChange(userId, username)
    }

}