package com.cfcici.`in`.project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cfcici.`in`.project.data.database.User
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

        // Add later
    //}

    //fun deleteUser(user: User){
       // repository.deleteUserRepo(user)
        // Add later
    //}

    // onResult is a function that give to emailExistVM()
    // After you finish checking the database, give me the true or false result
    fun emailExistVM(emailIDFromVM: String, onResult: (Boolean) -> Unit){
        viewModelScope.launch {
            val isEmailExist = repository.emailExistsRepo(emailIDFromVM)
            onResult(isEmailExist)
        }
    }

    fun loginCheckerVM(usernameFromVM: String, passwordFromVM: String, onResult: (Boolean) -> Unit){
        viewModelScope.launch {
            val isPasswordAndUsernameExist = repository.loginCheckerRepo(usernameFromVM, passwordFromVM)
            onResult(isPasswordAndUsernameExist)
        }

    }
}