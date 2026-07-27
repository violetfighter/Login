package com.cfcici.`in`.project.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PasswordPage(state: TextFieldState) {//to get user password from login page
    // We need to check is password visible or not
    // So we created boolean state
    var passwordDisplay by remember { mutableStateOf(false) }// gives password = false
    // Because you need create custom password you need to use BasicSecureTextField instead of TexField

    BasicSecureTextField(
        state = state,// Right state -> the TextFieldState you passed into PasswordPage()
        inputTransformation = InputTransformation.maxLength(16),
        textStyle = TextStyle(fontSize  = 20.sp),
        //Now we need to hide password so we use TextObfuscationMode.
        //It tells BasicSecureTextField how to display the password
        textObfuscationMode =
            if (passwordDisplay) {
                TextObfuscationMode.Visible
            } else {
                TextObfuscationMode.RevealLastTyped
            },

        modifier = Modifier
            .width(320.dp)
            .padding(horizontal = 16.dp)
            .background(
                color = Color(0xFFE1E2EC),
                shape = RoundedCornerShape(7.dp)
            )
            .padding(horizontal = 16.dp, vertical = 14.dp),

        decorator = { innerTextField ->

            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                if (state.text.isEmpty()) {
                    Text(
                        text = "Enter Your Password",
                        color = Color.Gray,
                        fontSize = 15.sp
                    )
                }
                innerTextField()
                Text(
                    text = if (passwordDisplay) "Hide" else "Show",
                    color = Color(0xFF34426B),
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable {
                            passwordDisplay = !passwordDisplay
                        }
                )
            }
        }
    )
}