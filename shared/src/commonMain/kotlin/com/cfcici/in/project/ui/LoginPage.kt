package com.cfcici.`in`.project.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun LoginPage(onLoginClick: (String, String) -> Unit) {
    val userName = rememberTextFieldState()
    val userPassword = rememberTextFieldState()
    val isPasswordFocused by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState()}

        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            }
        ){

            Box(
                modifier = Modifier.fillMaxSize().background(Color.White)
            )
            {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Welcome",
                        modifier = Modifier.padding(10.dp),
                        fontSize = 60.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Log in to your account",
                        modifier = Modifier.padding(10.dp),
                        fontSize = 25.sp,
                        //MaterialTheme.colorScheme.onPrimaryContainer
                        //is say give the appropriate or matching automatically
                        //Remember onPrimaryContainer -> text/icon colour PrimaryContainer -> background
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontWeight = FontWeight.Light
                    )

                    TextField(// TextFiled itself will create box
                        state = userName,// store whatever user enter
                        lineLimits = TextFieldLineLimits.SingleLine,

                        placeholder = { Text("Enter Your Username") },
                        inputTransformation = InputTransformation.maxLength(16),
                        textStyle = TextStyle(fontSize = 20.sp),
                        colors = TextFieldDefaults.colors(Color(0xFF34426B)),
                        shape = RoundedCornerShape(7.dp),
                        modifier = Modifier.background(color = Color(0xFFE1E2EC))
                    )

                    //UserNamePage(state = userName)

                    Spacer(modifier = Modifier.height(16.dp))

                    //TextField(
                    //state = userPassword,
                    //lineLimits = TextFieldLineLimits.SingleLine,
                    //placeholder = { Text("Enter Your Password")},
                    //inputTransformation = InputTransformation.maxLength(16),
                    //)


                    PasswordPage(state = userPassword)

                    Button(
                        modifier = Modifier.padding(16.dp),
                        onClick = {
                            if (!isValidPassword(passwordPP = userPassword.text.toString()))
                            {
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "Password is invalid.",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }else {
                                onLoginClick(// Send the username and password to App
                                    userName.text.toString(),
                                    userPassword.text.toString()
                                )
                            }
                        }
                    ) {
                        Text(text = "Log In")
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.padding(5.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Don't have an account? ",
                            modifier = Modifier
                                .padding(vertical = 5.dp),
                            fontSize = 17.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontWeight = FontWeight.Light
                        )

                        Text(
                            text = "Click Here",
                            modifier = Modifier.padding(vertical = 5.dp)
                                .clickable {
                                    // Got to next page
                                    //onClick()
                                },
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )


                    }
                }
            }
        }

}
