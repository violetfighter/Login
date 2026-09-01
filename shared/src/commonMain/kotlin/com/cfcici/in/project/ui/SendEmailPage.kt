package com.cfcici.`in`.project.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cfcici.`in`.project.viewmodel.UserViewModel

@Composable
fun SentEmailPage(viewModel: UserViewModel){
    var emailError by remember { mutableStateOf<String?>(null) }
    val email = rememberTextFieldState()
    var statusMessage by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Forgot your Password?",
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            style = TextStyle(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFFF0396B), Color(0xFFF0555C), Color(0xFFF0883C))
                )
            ),
            textAlign = TextAlign.Center
        )

        OutlinedTextField(
            state = email,
            lineLimits = TextFieldLineLimits.SingleLine,
            label = { Text("Enter your emailId") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFF0555C),
                unfocusedTextColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedBorderColor = Color(0xFFF0555C),
                unfocusedLabelColor = Color(0xFFF0555C),
                focusedLabelColor = Color(0xFFF0555C),
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

                viewModel.sendPasswordReset(emailText) { success ->
                    statusMessage = if (success) {
                        "Email sent successfully!"
                    } else {
                        "Failed to send email."
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(26.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF0555C))
        ) {
            Text("Send email", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }

        statusMessage?.let {
            Text(text = it, color = Color.White, fontSize = 13.sp)
        }
    }
}

