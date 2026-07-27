package com.cfcici.`in`.project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cfcici.`in`.project.data.database.User
import com.cfcici.`in`.project.data.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(val repository: UserRepository): ViewModel()
{
    suspend fun insertUser(usernameFromVM: String, passwordFromVM: String){
        repository.insertUserRepo(
            User(
                // Create new object when we add new users
                username = usernameFromVM,// we need to use username from User
                password = passwordFromVM
            )
        )
    }

    suspend fun getAllUser(): List<User> {
        return repository.getUserRepo()
        // Add later
    }

    suspend fun deleteUser(user: User){
        repository.deleteUserRepo(user)
        // Add later
    }

}