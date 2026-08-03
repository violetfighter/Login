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
import com.cfcici.`in`.project.ui.NewAccountPage
import com.cfcici.`in`.project.ui.ProfilePage
import com.cfcici.`in`.project.viewmodel.UserViewModel
import kotlinx.serialization.Serializable

@Serializable
object LoginRoute // Object means it doesn't accept any values

@Serializable//?
data class ProfileRoute(
    val username: String,
    val password: String,
    //val dateOfBirthApp: String,
    //val emailIdAPP: String
)

@Serializable
object NewAccountRoute

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
                    //onLoginClick goes to Login
                    onLoginClick = { username, password ->
                        //viewModel.insertUser(usernameFromVM = username, passwordFromVM = password)
                        navController.navigate(
                            ProfileRoute(
                                username = username,
                                password = password
                            )
                        )
                    },// When user click here in Login Page
                    onGoToNewAccount = {
                        navController.navigate(NewAccountRoute)
                    },
                    userViewModel = viewModel
                )
            }
// backStack Entry represent the current values of username, password...

            composable<ProfileRoute> { backStackEntry ->
                // takes serialization data and reconstructs the ProfileRoute object
                val profile: ProfileRoute = backStackEntry.toRoute()
                ProfilePage(
                    usernamePP = profile.username,
                    passwordPP = profile.password
                )
            }
            // So you don't want the get or give the values to any other page
            // just when user click button go back to login page
            composable <NewAccountRoute>{
                NewAccountPage(
                    onCreateNewAccount = { usernameNewAccountRoute, passwordNewAccountRoute, dataOfBirthNewAccountRoute, emailIdNewAccountRoute->
                        viewModel.insertUserVM(usernameFromVM = usernameNewAccountRoute, passwordFromVM = passwordNewAccountRoute, dateOfBirthFromVM = dataOfBirthNewAccountRoute, emailIDFromVM = emailIdNewAccountRoute)
                        //navController.navigate(LoginRoute)
                    },
                // When you click login in New Account Page it will go back to Login Page
                onBackToLogin = {
                    navController.navigate(LoginRoute)
                                },
                    userViewModel = viewModel///????????
                )
            }
        }
    }
}
