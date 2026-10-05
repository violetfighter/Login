package com.cfcici.`in`.project.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import com.cfcici.`in`.project.viewmodel.UserViewModel
import login.shared.generated.resources.Amarante_Regular
import login.shared.generated.resources.Res
import org.jetbrains.compose.resources.Font

@Composable
fun SentEmailPage(onBackToLogin: () -> Unit, viewModel: UserViewModel){
    var emailError by remember { mutableStateOf<String?>(null) }
    val email = rememberTextFieldState()
    var statusMessage by remember { mutableStateOf<String?>(null) }
    val usernameFont = FontFamily(Font(Res.font.Amarante_Regular))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.linearGradient(colors = listOf(Color(0xFF3C3489), Color(0xFF72243E))))
    )
    {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Text(
                    text = "Forgot your password?",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = usernameFont,
                    style = TextStyle(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFFF0396B), Color(0xFFF0883C))
                        )
                    ),
                    textAlign = TextAlign.Center
                )

                OutlinedTextField(
                    state = email,
                    lineLimits = TextFieldLineLimits.SingleLine,
                    label = { Text("Enter your email Id", fontFamily = usernameFont) },
                    textStyle = TextStyle(fontFamily = usernameFont),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFF0396B),
                        unfocusedTextColor = Color.White,
                        focusedTextColor = Color.White,
                        unfocusedBorderColor = Color(0xFFF0396B),
                        unfocusedLabelColor = Color.LightGray,
                        focusedLabelColor = Color(0xFFF0396B),
                        errorTextColor = Color.White,
                        cursorColor = Color.White),
                )

                emailError?.let {
                    Text(text = it, color = Color.Red, fontSize = 13.sp)
                }

                Button(
                    onClick = {
                        val emailText = email.text.toString().trim()

                        if (emailText.isEmpty()) {
                            emailError = "Please enter your email"
                            return@Button
                        }

                        emailError = null
                        statusMessage = null

                        viewModel.sendPasswordResetVM(emailText) { // remove this firebase
                                success, emailNotFind ->
                            statusMessage = when{
                                success -> "Email Sent"
                                emailNotFind -> "Email is not Registered"
                                else -> "Something went wrong @SendEmailPage"
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(26.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF0396B))
                ) {
                    Text("Send email", fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = usernameFont)
                }

                statusMessage?.let {
                    Text(text = it, color = Color.White, fontSize = 13.sp)
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
            }
        }

    }
}

