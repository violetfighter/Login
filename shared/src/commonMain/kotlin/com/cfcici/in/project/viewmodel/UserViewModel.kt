package com.cfcici.`in`.project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cfcici.`in`.project.data.database.User
import com.cfcici.`in`.project.data.database.UserCar
import com.cfcici.`in`.project.data.database.UserSelectedBrandCars
import com.cfcici.`in`.project.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
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

    //Think of it like a walkie-talkie: insertUserOwnedCarVM doesn't know or care what happens after the insert — it just presses the button and says "done"
    // by calling onResult(). Whoever's listening on the other end decides what to do with that signal.
    fun insertUserOwnedCarVM(userIdUserVM: Int, brandFromVM: String, modelFromVM: String, yearFromVM: Int?, colourFromVM: String,
                        seriesFromVM: String?, typeOfSeriesFromVM: String?, collectorNoUserVM: String?, photoUserFromVM: String, onResult: () -> Unit){
        viewModelScope.launch {
            repository.insertUserOwnedCarRepo(
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
            onResult() //<- runs whatever function was passed in, once insert is done
        }//Here onResult does carry data — (List<UserCar>) -> Unit — because a fetch naturally produces something to hand back: the list of cars.
    // onResult(theList) passes that list to whoever's listening.
       // But an insert doesn't produce a list. It's a write operation — its only meaningful signal is "the write finished." There's no UserCar list to hand back,
    // because inserting doesn't create one. So the callback type is () -> Unit: no payload, just a "done" signal.
    }
    fun getUserOwnedCarsByBrandVM(userIdUserVM: Int, brandVM: String, onResult: (List<UserCar>) -> Unit){
         viewModelScope.launch {
             onResult(repository.getUserOwnedCarsByBrandRepo(userIdUserVM, brandVM))
         }
    }
/*
    fun deleteUserOwnedCarVM(userIdUserVM: Int, brandFromVM: String, modelFromVM: String, yearFromVM: Int?, colourFromVM: String,
                          seriesFromVM: String?, typeOfSeriesFromVM: String?, collectorNoUserVM: String?, photoUserFromVM: String,){
        viewModelScope.launch {
            repository.deleteUserOwnedCarRepo(
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
    }*/

    fun deleteUserOwnedCarVM(userCarVM: UserCar, onResult: () -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUserOwnedCarRepo(userCarVM)
                onResult()
            // The reason using the onResul() is that when we delete entire row
        // it doesn't show live update of the new version. So when we add onResult and use display function
            // it will show the new version
        }
    }

    fun insertSelectedCarBrandVM(userIdUserVM: Int, selectedCarBrandVM: String){
        viewModelScope.launch {
            repository.insertSelectedCarBrandRepo(
                UserSelectedBrandCars(
                    userIdFromUsers = userIdUserVM,
                    selectedBrandName = selectedCarBrandVM
                )
            )
        }
    }

    fun getSelectedCarBrandsVM(userIdUserVM: Int, onResult: (List<UserSelectedBrandCars>) -> Unit){
        viewModelScope.launch {
            val brand = repository.getSelectedCarBrandsRepo(userIdUserVM)
            onResult(brand)
        }
    }
}