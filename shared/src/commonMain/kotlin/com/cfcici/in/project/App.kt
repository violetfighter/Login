package com.cfcici.`in`.project

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.cfcici.`in`.project.data.database.AppDatabase
import com.cfcici.`in`.project.data.repository.UserRepository
import com.cfcici.`in`.project.ui.LoginPage
import com.cfcici.`in`.project.ui.ProfilePage
import com.cfcici.`in`.project.viewmodel.UserViewModel
import kotlinx.serialization.Serializable

@Serializable
object LoginRoute

@Serializable//?
data class ProfileRoute(
    val username: String,
    val password: String
)

@Composable

fun App(db: AppDatabase) {
    val repository = UserRepository(db.userDao())
    val viewModel = UserViewModel(repository)
    MaterialTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = LoginRoute
        ) {
            composable<LoginRoute> {
                LoginPage(
                    onLoginClick = { username, password ->
                        viewModel.insertUser(username, password)
                        navController.navigate(
                            ProfileRoute(
                                username = username,
                                password = password
                            )
                        )
                    }
                )
            }

            composable<ProfileRoute> { backStackEntry ->
                // takes serialization data and reconstructs the ProfileRoute object
                val profile: ProfileRoute = backStackEntry.toRoute()
                ProfilePage(
                    usernamePP = profile.username,
                    passwordPP = profile.password
                )
            }
        }
    }
}
