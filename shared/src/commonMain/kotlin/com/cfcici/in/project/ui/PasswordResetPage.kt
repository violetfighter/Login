package com.cfcici.`in`.project.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.cfcici.`in`.project.viewmodel.UserViewModel
import login.shared.generated.resources.Amarante_Regular
import login.shared.generated.resources.Res
import org.jetbrains.compose.resources.Font

@Composable
fun PasswordResetPage(
    emailPR: String,
    onBackToLogin: () -> Unit,
    userViewModel: UserViewModel
){
    val usernameFont = FontFamily(Font(Res.font.Amarante_Regular))
    var newPasswordFirst by remember { mutableStateOf("") }
    var newPasswordSecond by remember { mutableStateOf("") }
    var passwordFocusedFirst by remember { mutableStateOf(false) }
    var passwordFocusedSecond by remember { mutableStateOf(false) }
    var passwordErrorFirst by remember { mutableStateOf<String?>(null) }
    var passwordErrorSecond  by remember { mutableStateOf<String?>(null) }
    var showFillupMessage by remember { mutableStateOf(false) }
    var showPasswordSameErrorMessage by remember { mutableStateOf(false) }
    var saveError by remember { mutableStateOf<String?>(null) }
    val scrollState = rememberScrollState()


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF140F22))
            //.background(Brush.linearGradient(colors = listOf(Color(0xFF140F22), Color(0xFF72243E))))
    )
    {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .imePadding()
                    .padding(horizontal = 25.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Text(
                    text = "Change your password",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = usernameFont,
                    style = TextStyle(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFFF0396B), Color(0xFFF0883C))
                        )
                    ),
                    textAlign = TextAlign.Center,
                )

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    PasswordPage(
                        password = newPasswordFirst,
                        passwordChecker = { newPasswordFirst = it },
                        isError = passwordErrorFirst != null,
                        onPasswordFocus = {
                            passwordFocusedFirst = true
                            passwordFocusedSecond = false
                            passwordErrorFirst = null
                        },
                        supportingText = {
                            if (passwordErrorFirst != null) Text("Password is invalid")
                        },
                        passwordLabel = "Enter new password"
                    )
                    if (passwordFocusedFirst) {
                        PopUpMessage(newPasswordFirst)
                    }
                }

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    PasswordPage(
                        password = newPasswordSecond,
                        passwordChecker = { newPasswordSecond = it },
                        isError = passwordErrorSecond != null,
                        onPasswordFocus = {
                            passwordFocusedSecond = true
                            passwordFocusedFirst = false
                            passwordErrorSecond = null
                        },
                        supportingText = {
                            if (passwordErrorSecond != null) Text("Password is invalid")
                        },
                        passwordLabel = "Enter confirm password"
                    )
                    if (passwordFocusedSecond) {
                        PopUpMessage(newPasswordSecond)
                    }
                }

                Button(
                    modifier = Modifier.padding(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF0396B)),
                    onClick = {

                        if (newPasswordFirst.isEmpty() || newPasswordSecond.isEmpty()){
                            showFillupMessage = true
                        }

                        else if (newPasswordFirst != newPasswordSecond){
                            showPasswordSameErrorMessage = true
                        }
                        else if (isValidPassword(passwordPP = newPasswordSecond).isNotEmpty()){
                            passwordErrorFirst = "Password is invalid"
                            passwordErrorSecond = "Password is invalid"
                        }

                        else
                        {
                            passwordErrorFirst = null
                            passwordErrorSecond = null
                            userViewModel.newPasswordVM(emailPR, newPasswordSecond){
                                    success ->
                                if (success) onBackToLogin()
                                else saveError = "Could not save the new password. Please try again."
                            }
                        }
                    }
                ){
                    Text("Reset password",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = usernameFont
                    )
                }

                TextButton(
                    onClick = { onBackToLogin() }
                ){
                    Text(
                        text = "Cancel",
                        fontFamily = usernameFont,
                        color = Color(0xFFFF9800)
                    )
                }

                if (showFillupMessage){
                    Dialog(onDismissRequest = {showFillupMessage = false}){
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .padding(16.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF140F22))
                        ) {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Text(
                                    text = "You need to fill up everything",
                                    color = Color.White,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f)
                                        .wrapContentSize(Alignment.Center),
                                    textAlign = TextAlign.Center,
                                    fontFamily = usernameFont
                                )
                                Button(
                                    onClick = { showFillupMessage = false },
                                    modifier = Modifier.padding(8.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF0396B))
                                ) {
                                    Text("Try again", fontFamily = usernameFont)
                                }
                            }
                        }
                    }
                }

                if(showPasswordSameErrorMessage){
                    Dialog(onDismissRequest = {showPasswordSameErrorMessage = false}){
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .padding(vertical = 16.dp, horizontal = 10.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF140F22))
                        ) {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Text(
                                    text = "Password should be same",
                                    color = Color.White,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f)
                                        .wrapContentSize(Alignment.Center),
                                    textAlign = TextAlign.Center,
                                    fontFamily = usernameFont
                                )
                                Button(
                                    onClick = { showPasswordSameErrorMessage = false },
                                    modifier = Modifier.padding(8.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF0396B))
                                ) {
                                    Text("Try again", fontFamily = usernameFont)
                                }
                            }
                        }
                    }
                }
            }

    }
}