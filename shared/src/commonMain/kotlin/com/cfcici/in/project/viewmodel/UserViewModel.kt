package com.cfcici.`in`.project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cfcici.`in`.project.data.database.User
import com.cfcici.`in`.project.data.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(val repository: UserRepository): ViewModel()
{
    //ViewModel should not have suspended functions
    fun insertUser(usernameFromVM: String, passwordFromVM: String){
        viewModelScope.launch {
            repository.insertUserRepo(
                User(
                    // Create new object when we add new users
                    username = usernameFromVM,// we need to use username from User
                    password = passwordFromVM
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

}