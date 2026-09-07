package com.cfcici.`in`.project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cfcici.`in`.project.data.database.User
import com.cfcici.`in`.project.data.database.UserCar
import com.cfcici.`in`.project.data.database.UserSelectedBrandCars
import com.cfcici.`in`.project.data.repository.FirestoreUserRepository
import com.cfcici.`in`.project.data.repository.UserRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class UserViewModel(val repository: UserRepository): ViewModel()
{
    private val firestoreUserRepository = FirestoreUserRepository()


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
            try {

                val authResult = Firebase.auth.createUserWithEmailAndPassword(emailIDFromVM, passwordFromVM)// create the Auth account, returns uid
                val authUserId = authResult.user?.uid

                if (authUserId != null) {
                    val newUser = User(
                        usernameUser = usernameFromVM,
                        passwordUser = passwordFromVM,
                        dateOfBirthUser = dateOfBirthFromVM,
                        emailIdUser = emailIDFromVM
                    )
                    repository.insertUserRepo(newUser)// save data locally
                    firestoreUserRepository.syncUserToFirestore(authUserId, newUser) // save data in cloud/Firestore

                    onResult(true, null)//back up to the UI
                } else {
                    onResult(false, "Account creation failed in cloud or room")//back up to the UI
                }
            } catch (e: dev.gitlive.firebase.auth.FirebaseAuthUserCollisionException) { // checks for duplicate emails
                onResult(false, "Email already registered")
            } catch (e: Exception) {
                e.printStackTrace()
                val message = e.message ?: "No message"
                val errorType = e::class.simpleName ?: "UnknownError"

                if (message.contains("network", ignoreCase = true) || message.contains("GMS", ignoreCase = true)) {
                    onResult(false, "Network/Config error: Please check GMS and internet connection")
                } else {
                    onResult(false, "Error [$errorType]: $message")
                }
            }
        }
    }
//Your other functions (insertUserVM, updateCarEditVM, etc.) all launch a coroutine because they're calling suspend fun
// that do work and finish — insert this row, update that row, done.
// Those need viewModelScope.launch { } because suspend functions can only be called from inside a coroutine.

//getAllUserVM is fundamentally different: it's not "do a task and finish," it's "give me an open pipe that keeps delivering values."
// You don't launch a pipe — you just hand it to whoever wants to drink from it (in this case, SettingsPage/ProfilePage via collectAsState()).
    fun getUserDetailsVM(userIdUserVM: Int): Flow<User?> = repository.getUserDetailsRepo(userIdUserVM)

    fun updateCarEditVM(userCarEdit: UserCar, onResult: () -> Unit){
        viewModelScope.launch {
            repository.updateCarEditRepo(userCarEdit)
            onResult()
        }
    }

    fun updateProfileEditVM(userProfileEdit: User, onResult: () -> Unit){
        viewModelScope.launch {
            repository.updateProfileEditRepo(userProfileEdit)
            onResult()
        }
    }

    fun deleteUserVM(userDelete: User, onResult: () -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUserRepo(userDelete)
            onResult()
        }
    }
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

    fun totalCarsUserOwnVM(userId: Int): Flow<Int> = repository.totalCarsUserOwnRepo(userId)


    //get all the user item like user id, username, email id, date of birth
    fun getUserByUsernameVM(getUserByUsernameFromVM: String, onResult: (User?) -> Unit){
        viewModelScope.launch {
            val userVM = repository.getUserByUsernameRepo(getUserByUsernameFromVM)
            onResult(userVM)
        }
    }

    fun loginCheckerVM(usernameFromVM: String, passwordFromVM: String, onResult: (Boolean) -> Unit){
        /*viewModelScope.launch {
            val isPasswordAndUsernameExist = repository.loginCheckerRepo(usernameFromVM, passwordFromVM)
            onResult(isPasswordAndUsernameExist)
        }*/
        viewModelScope.launch {
            try {
                // 1. Fetch user from Room to get their email
                val localUser = repository.getUserByUsernameRepo(usernameFromVM)// it looks in Room for username = parvathi

                if (localUser != null) {
                    // 2. Try to sign in to Firebase Auth using the email from Room
                    val authResult = Firebase.auth.signInWithEmailAndPassword(localUser.emailIdUser, passwordFromVM)// so it stores in locally (Room)
                    val authId = authResult.user?.uid

                    // App sends Firebase -> Firebase checks its own Authentication database -> if it has it -> then login succeeds

                    if (authId != null) {
                        // 3. Success! Check if we need to sync a new password to Room/Firestore
                        // if the new password is same as old password it does nothing but if not it will update
                        if (localUser.passwordUser != passwordFromVM) {
                            val updatedUser = localUser.copy(passwordUser = passwordFromVM)

                            // Update Room
                            repository.updateProfileEditRepo(updatedUser)

                            // Update Firestore
                            firestoreUserRepository.syncUserToFirestore(authId, updatedUser)

                            println("Password synced across all platforms!")
                        }
                        onResult(true)
                    } else {
                        onResult(false)
                    }
                } else {
                    // Username not found in Room
                    onResult(false)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                onResult(false)
            }
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
                    carPhotoUser = photoUserFromVM
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
    }

    fun sendPasswordReset(email: String, onResult: (Boolean, Boolean) -> Unit) {
        viewModelScope.launch {
            val result = repository.sendPasswordResetEmail(email)
            
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
}
