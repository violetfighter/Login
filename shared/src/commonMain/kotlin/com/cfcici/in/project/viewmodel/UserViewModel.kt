package com.cfcici.`in`.project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cfcici.`in`.project.data.database.User
import com.cfcici.`in`.project.data.database.UserCar
import com.cfcici.`in`.project.data.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(val repository: UserRepository): ViewModel()
{
    //ViewModel should not have suspended functions
    fun insertUserVM(usernameFromVM: String, passwordFromVM: String, dateOfBirthFromVM: String, emailIDFromVM: String){
        viewModelScope.launch {//Run this code asynchronously, and keep it associated with this ViewModel.
            //asynchronous code, it means code that can start a task without making the rest of the program wait for that task to finish.
            repository.insertUserRepo(
                User(
                    // Create new object when we add new users
                    usernameUser = usernameFromVM,// we need to use username from User
                    passwordUser = passwordFromVM,
                    dateOfBirthUser = dateOfBirthFromVM,
                    emailIdUser = emailIDFromVM
                )
            )
        }
    }
    //fun getAllUser(): List<User> {
        //return repository.getUserRepo()
        // Add later}
    //fun deleteUser(user: User){
       // repository.deleteUserRepo(user)
        // Add later}
    // onResult is a function that give to emailExistVM()
    // After you finish checking the database, give me the true or false result

    fun emailExistVM(emailIDFromVM: String, onResult: (Boolean) -> Unit){
        viewModelScope.launch {
            val isEmailExist = repository.emailExistsRepo(emailIDFromVM)
            onResult(isEmailExist)
        }
    }

    //get all the user item like user id, username, email id, date of birth
    fun getUserByUsernameVM(getUserByUsernameFromVM: String, onResult: (User?) -> Unit){
        viewModelScope.launch {
            val userVM = repository.getUserByUsernameRepo(getUserByUsernameFromVM)
            onResult(userVM)
        }
    }

    fun loginCheckerVM(usernameFromVM: String, passwordFromVM: String, onResult: (Boolean) -> Unit){
        viewModelScope.launch {
            val isPasswordAndUsernameExist = repository.loginCheckerRepo(usernameFromVM, passwordFromVM)
            onResult(isPasswordAndUsernameExist)
        }
    }

    fun insertUserCarVM(userIdUserVM: Int, brandFromVM: String, modelFromVM: String, yearFromVM: Int?, colourFromVM: String,
                        seriesFromVM: String?, typeOfSeriesFromVM: String?, collectorNoUserVM: String?, photoUserFromVM: String){
        viewModelScope.launch {
            repository.insertUserCarRepo(
                UserCar(
                    userIdUser = userIdUserVM,
                    brandUser = brandFromVM,
                    modelUser = modelFromVM,
                    yearUser = yearFromVM,
                    colourUser = colourFromVM,
                    seriesUser = seriesFromVM,
                    typeOfSeriesUser = typeOfSeriesFromVM,
                    collectorNoUser = collectorNoUserVM,
                    photoUser = photoUserFromVM
                )
            )
        }
    }

    fun getCarOnSearchBarVM(userIdUserVM: Int, modelUserVM: String, onResult: (List<UserCar>) -> Unit){
        viewModelScope.launch {
            val carSearch = repository.getCarOnSearchBarRepo(userIdUserVM, modelUserVM)
            onResult(carSearch)
        }
    }

    fun deleteUserCarVM(userIdUserVM: Int, brandFromVM: String, modelFromVM: String, yearFromVM: Int?, colourFromVM: String,
                          seriesFromVM: String?, typeOfSeriesFromVM: String?, collectorNoUserVM: String?, photoUserFromVM: String){
        viewModelScope.launch {
            repository.deleteUserCarRepo(
                UserCar(
                    userIdUser = userIdUserVM,
                    brandUser = brandFromVM,
                    modelUser = modelFromVM,
                    yearUser = yearFromVM,
                    colourUser = colourFromVM,
                    seriesUser = seriesFromVM,
                    typeOfSeriesUser = typeOfSeriesFromVM,
                    collectorNoUser = collectorNoUserVM,
                    photoUser = photoUserFromVM
                )
            )
        }
    }
}