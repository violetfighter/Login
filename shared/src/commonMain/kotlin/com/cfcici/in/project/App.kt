package com.cfcici.`in`.project

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cfcici.`in`.project.data.database.User
import com.cfcici.`in`.project.ui.LoginPage
import com.cfcici.`in`.project.ui.ProfilePage



@Composable
@Preview
fun App() {
    MaterialTheme {

        val navController = rememberNavController()

        // These variables temporarily store the username and password.
        // They are NOT saved to a database.
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        NavHost(
            navController = navController,
            startDestination = "login"
        ) {

            composable("login") {

                LoginPage(
                    onLoginClick = { user, pass ->

                        username = user
                        password = pass

                        navController.navigate("profile")
                    }
                )
            }

            composable("profile") {

                ProfilePage(
                    usernamePP = username,
                    passwordPP = password
                )
            }
        }
    }
}