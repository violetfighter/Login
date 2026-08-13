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
        }
    // onResult is used to notify the UI that the database operation has finished.
// We cannot simply call onConfirmation() immediately after insertUserOwnedCarVM()
// because the database insertion happens inside a coroutine and may not be finished yet.
// The ViewModel calls onResult() only after the repository finishes inserting the car.
// Then onResult calls onConfirmation(), which updates the UI by closing the dialog
// and refreshing the car list.
// Flow: Insert car → Room finishes → onResult() → onConfirmation() → UI updates

        // onResult notifies the UI after the database insertion is finished.
// It then calls onConfirmation() to close the dialog and refresh the car list.
// We need this because the database operation runs inside a coroutine.
    }
    fun getUserOwnedCarsByBrandVM(userIdUserVM: Int, brandVM: String, onResult: (List<UserCar>) -> Unit){
         viewModelScope.launch {
             onResult(repository.getUserOwnedCarsByBrandRepo(userIdUserVM, brandVM))
         }
    }

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

    fun deleteSelectedCarBrandVM(userCarBrandVM: UserSelectedBrandCars, onResult: () -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteSelectedCarBrandRepo(userCarBrandVM)
            onResult()
        }
    }
}