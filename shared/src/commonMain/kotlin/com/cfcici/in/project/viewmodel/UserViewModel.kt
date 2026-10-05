package com.cfcici.`in`.project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cfcici.`in`.project.data.database.UserCar
import com.cfcici.`in`.project.data.database.UserSelectedBrandCars
import com.cfcici.`in`.project.data.repository.UserRepository
import com.cfcici.`in`.project.network.ApiCar
import com.cfcici.`in`.project.network.CreateUserRequest
import com.cfcici.`in`.project.network.ApiUser
import com.cfcici.`in`.project.ui.AppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(val repository: UserRepository): ViewModel()
{
    private val _totalCars = MutableStateFlow<Int?>(null)
    val totalCars: StateFlow<Int?> = _totalCars


    //ViewModel should not have suspended functions

    //Suspend functions (one-shot) — need onResult, because the ViewModel function itself can't return
    // a value directly out of a viewModelScope.launch { } coroutine:

    //Flow functions (ongoing stream) — no callback needed, since a Flow is already a value you can hand back directly
    // and let the composable collect from over time:
    /*
    fun insertUserVM(usernameFromVM: String, passwordFromVM: String, dateOfBirthFromVM: String, emailIDFromVM: String, onResult: (Boolean) -> Unit){
        viewModelScope.launch {//Run this code asynchronously, and keep it associated with this ViewModel.
            //asynchronous code, it means code that can start a task without making the rest of the program wait for that task to finish.
            try {

                //Create the account in Firebase Auth first
                val authResult = Firebase.auth.createUserWithEmailAndPassword(emailIDFromVM, passwordFromVM)
                val authUserId = authResult.user?.uid

                if(authUserId != null){
                    // Still insert into Room, same as before
                    val newUser = User(
                        // Create new object when we add new users
                        usernameUser = usernameFromVM,// we need to use username from User
                        passwordUser = passwordFromVM,
                        dateOfBirthUser = dateOfBirthFromVM,
                        emailIdUser = emailIDFromVM
                    )
                    repository.insertUserRepo(newUser)

                    //Sync Profile details to Firestore, keyed by the Auth uid
                    firestoreUserRepository.syncUserToFirestore(authUserId, newUser)

                    onResult(true)
                }else{
                    onResult(false)
                }
            } catch (e: dev.gitlive.firebase.auth.FirebaseAuthUserCollisionException) {
                onResult(false)
                // could pass a specific error message up: "Email already registered"
            } catch (e: Exception) {
                e.printStackTrace()
                onResult(false)
            }
        }
    }*/


    fun insertUserVM(
        usernameFromVM: String, passwordFromVM: String, dateOfBirthFromVM: String, emailIDFromVM: String,
        onResult: (Boolean, String?) -> Unit  // success, errorMessage
    ) {
        viewModelScope.launch {
            val success = repository.insertUserRepo(
                CreateUserRequest(
                    usernameUser = usernameFromVM,
                    passwordUser = passwordFromVM,
                    dateOfBirthUser = dateOfBirthFromVM,
                    emailIdUser = emailIDFromVM
                )
            )
            if (success) {
                onResult(true, null)
            } else {
                onResult(false, "Could not create the account. Check your connection and try again.")
            }
        }
    }
//Your other functions (insertUserVM, updateCarEditVM, etc.) all launch a coroutine because they're calling suspend fun
// that do work and finish — insert this row, update that row, done.
// Those need viewModelScope.launch { } because suspend functions can only be called from inside a coroutine.

//getAllUserVM is fundamentally different: it's not "do a task and finish," it's "give me an open pipe that keeps delivering values."
// You don't launch a pipe — you just hand it to whoever wants to drink from it (in this case, SettingsPage/ProfilePage via collectAsState()).
    suspend fun getUserDetailsVM(userIdUserVM: Int): ApiUser? {
        return repository.getUserDetailsRepo(userIdUserVM)
    }

    fun updateCarEditVM(car: ApiCar, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            onResult(repository.updateCarEditRepo(car))
        }
    }

    fun updateProfileEditVM(userProfileEdit: ApiUser, onResult: () -> Unit){ // stupid me????
        viewModelScope.launch {
            repository.updateProfileEditRepo(userProfileEdit)
            onResult()
        }
    }

    // UserViewModel
    fun deleteUserVM(userId: Int, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            onResult(repository.deleteUserRepo(userId))
        }
    }

    /*fun deleteUserVM(userDelete: User, onResult: () -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUserRepo(userDelete)
            onResult()
        }
    }*/
    // onResult is a function that give to emailExistVM()
    // After you finish checking the database, give me the true or false result

    fun emailExistVM(emailIDFromVM: String, onResult: (Boolean) -> Unit){
        viewModelScope.launch {
            val isEmailExist = repository.emailExistsRepo(emailIDFromVM)
            onResult(isEmailExist)
        }
    }

    fun usernameExistsVM(usernameIDFromVM: String, onResult: (Boolean) -> Unit){
        viewModelScope.launch {
            val isUsernameExist = repository.usernameExistsRepo(usernameIDFromVM)
            onResult(isUsernameExist)
        }
    }

    fun totalCarsUserOwnVM(userId: Int, onResult: (Int?) -> Unit)
    {
        viewModelScope.launch {
            onResult(repository.totalCarsUserOwnRepo(userId))
        }
    }

/*
    //get all the user item like user id, username, email id, date of birth
    fun getUserByUsernameVM(getUserByUsernameFromVM: String, onResult: (GetUserByUsernameApiUser?) -> Unit){
        viewModelScope.launch {
            val userVM = repository.getUserByUsernameRepo(getUserByUsernameFromVM)
            onResult(userVM)
        }
    }*/

    fun loginCheckerVM(usernameFromVM: String, passwordFromVM: String, onResult: (Int?) -> Unit) {
        viewModelScope.launch {
            val result = repository.loginCheckerRepo(usernameFromVM, passwordFromVM)
            onResult(result?.userId)
        }
    }

    //Think of it like a walkie-talkie: insertUserOwnedCarVM doesn't know or care what happens after the insert — it just presses the button and says "done"
    // by calling onResult(). Whoever's listening on the other end decides what to do with that signal.
    fun insertUserOwnedCarVM(
        userIdUserVM: Int, brandFromVM: String, modelFromVM: String, yearFromVM: Int?,
        colourFromVM: String, seriesFromVM: String?, typeOfSeriesFromVM: String?,
        collectorNoUserVM: String?, photoUserFromVM: String,
        onResult: (Boolean, String?) -> Unit
    )
    {
        viewModelScope.launch {
            val success = repository.insertUserOwnedCarRepo(
                ApiCar(
                    userIdCar = userIdUserVM,
                    brandCar = brandFromVM,
                    modelCar = modelFromVM,
                    yearCar = yearFromVM?.toString(),
                    colourCar = colourFromVM,
                    seriesCar = seriesFromVM,
                    typeOfSeriesCar = typeOfSeriesFromVM,
                    collectorNoCar = collectorNoUserVM,
                    carPhotoCar = photoUserFromVM
                )
            )
            if (success) onResult(true, null)
            else onResult(false, "Could not save the car. Check your connection and try again.")
        }
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

    fun getUserOwnedCarsByBrandVM(userId: Int, carBrand: String, onResult: (List<ApiCar>) -> Unit
    ) {
        viewModelScope.launch {
            onResult(repository.getUserOwnedCarsByBrandRepo(userId, carBrand))
        }
    }

    fun deleteUserOwnedCarVM(userCarVM: Int, onResult: (Boolean) -> Unit){
        viewModelScope.launch {
                onResult(repository.deleteUserOwnedCarRepo(userCarVM))
            // The reason using the onResul() is that when we delete entire row
        // it doesn't show live update of the new version. So when we add onResult and use display function
            // it will show the new version
        }
    }

    /*

    fun insertSelectedCarBrandVM(userIdUserVM: Int, selectedCarBrandVM: String, onResult: () -> Unit){
        viewModelScope.launch {
            repository.insertSelectedCarBrandRepo(
                UserSelectedBrandCars(
                    userIdFromUsers = userIdUserVM,
                    selectedBrandName = selectedCarBrandVM
                )
            )
            onResult() // <- runs after the suspend insert finishes, same coroutine
        }
    }

    fun getSelectedCarBrandsVM(userIdUserVM: Int, onResult: (List<UserSelectedBrandCars>) -> Unit){
        viewModelScope.launch {
            val brand = repository.getSelectedCarBrandsRepo(userIdUserVM)
            onResult(brand)
        }
    }

    fun deleteSelectedCarBrandVM( userIdVM: Int, userCarBrandVM: String, onResult: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteSelectedCarBrandRepo(userIdVM, userCarBrandVM)
            onResult()
        }
    }*/

    fun sendPasswordResetVM(email: String, onResult: (Boolean, Boolean) -> Unit) {
        viewModelScope.launch {
            val result = repository.sendPasswordResetEmailRepo(email)
            
            if (result.isSuccess) {
                onResult(true, false)
            } else {
                val exception = result.exceptionOrNull()
                if (exception is dev.gitlive.firebase.auth.FirebaseAuthInvalidUserException) {
                    println("Error Email doesn't exist @@@")
                    onResult(false, true) // success=false, emailNotRegistered=true
                    //first Boolean = "did it succeed" (no)
                    // second Boolean = "was it specifically because the email doesn't exist" (yes)
                } else {
                    println("Error sending reset email: ${exception?.message} @@@")
                    onResult(false, false) // success=false, emailNotRegistered=false
                }
            }
        }
    }

    fun updateSelectedThemeVM(userId: Int, theme: AppTheme, onResult: () -> Unit){
        viewModelScope.launch {
            repository.updateSelectedThemeRepo(userId, theme)
            onResult()
        }
    }

    fun updateDarkModeVM(userId: Int, isDark: Boolean, onResult: () -> Unit) {
        viewModelScope.launch {
            repository.updateDarkModeRepo(userId, isDark)
            onResult()
        }
    }

    fun usernameChangeVM(userId: Int, username: String, onResult: (String?) -> Unit) {
        viewModelScope.launch {
            onResult(repository.usernameChangeRepo(userId, username))
        }
    }

    fun dobChangeVM(userId: Int, dob: String, onResult: (String?) -> Unit) {
        viewModelScope.launch {
            onResult(repository.dobChangeRepo(userId, dob))
        }
    }
}
