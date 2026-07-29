package com.cfcici.`in`.project.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PasswordPage(state: TextFieldState) {//to get user password from login page
    // We need to check is password visible or not
    // So we created boolean state
    var passwordDisplay by remember { mutableStateOf(false) }// gives password = false
    var passwordFocused by remember { mutableStateOf(false) }
    // Because you need create custom password you need to use BasicSecureTextField instead of TextField,
    // but we can also use BasicTextField

    Column{

        BasicSecureTextField(
            state = state,// Right state -> the TextFieldState you passed into PasswordPage()

            inputTransformation = InputTransformation.maxLength(16),
            textStyle = TextStyle(fontSize = 20.sp),// Cursor's/ size
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
                .onFocusChanged{
                    passwordFocused = it.isFocused
                }
                .padding(horizontal = 16.dp, vertical = 14.dp),
            decorator = { innerTextField ->

                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (state.text.isEmpty()) {
                        Text(
                            text = "Enter Your Password",
                            color = Color(0xFF34426B),
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
        if (passwordFocused){// Checks is event clicked or not
            PopUpMessage()
        }
    }

}
// When user click the box it shows the message
@Composable
fun PopUpMessage() {
    Surface(
        modifier = Modifier
            .width(320.dp)
            .padding(top = 14.dp, start = 16.dp, end = 16.dp),
        shape = RoundedCornerShape(7.dp),
        color = Color(0xFFE1E2EC)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = "Password requirements:",
                fontWeight = FontWeight.Bold
            )
            Text("• Must be between 12 and 16 characters")
            Text("• At least one digit")
            Text("• At least one uppercase letter")
            Text("• At least one lowercase letter")
            Text("• At least one special character")
        }
    }
}
// isValidPassword should be outside the Composable otherwise it cannot call to other files
fun isValidPassword(passwordPP: String): Boolean{
    if ((passwordPP.length < 12) || (passwordPP.length > 16)){
        return false }
    if (passwordPP.firstOrNull { it.isDigit() } == null){
        return false }
    if (passwordPP.firstOrNull {it.isLowerCase()} == null){
        return false }
    if (passwordPP.firstOrNull {it.isUpperCase()} == null) {
        return false }
    if (passwordPP.firstOrNull { !it.isLetterOrDigit() } == null) {
        return false}// check for special characters
    return true
}