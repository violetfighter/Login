package com.cfcici.`in`.project.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PasswordPage(
    password: String,//current password
    passwordChecker: (String) -> Unit,//function that sends the new password back to the parent
    isError: Boolean = false,
    supportingText: @Composable () -> Unit = {}
) {//to get user password from login page
    // We need to check is password visible or not
    // So we created boolean state
    var passwordDisplay by remember { mutableStateOf(false) }// gives password = false
    var passwordFocused by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        OutlinedTextField(
            value = password,
            onValueChange = {if (it.length <=16){ passwordChecker( it )}},//??????
            singleLine = true,
            textStyle = TextStyle(fontSize = 20.sp),

            // to hide the text/password
            visualTransformation =
                if (passwordDisplay) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },

            // eye icon for password to hide and show
            trailingIcon = {
                val passwordIcon = if (passwordDisplay)
                    Icons.Filled.Visibility
                else
                    Icons.Filled.VisibilityOff

                IconButton(
                    onClick = {
                        passwordDisplay = !passwordDisplay
                    }
                ){
                    Icon(
                        imageVector = passwordIcon,
                        contentDescription = if (passwordDisplay){"Hide Password"} else {"Show Password"},
                        tint = Color.DarkGray
                    )
                }
            },
            label = {Text("Enter your password")},
            shape = RoundedCornerShape(50.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFF0396B),
                unfocusedBorderColor = Color(0xFFF0396B),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                unfocusedLabelColor = Color.DarkGray,
                focusedLabelColor = Color.DarkGray,
                errorTextColor = Color.White,
                cursorColor = Color.White
            ),
            modifier = Modifier
                .width(320.dp)
                .onFocusChanged {
                    passwordFocused = it.isFocused
                },

            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Password Icon",
                    tint = Color.White
                    )
            },
            isError = isError,
            supportingText = supportingText
        )
        if (passwordFocused) {
            PopUpMessage(password)
        }
    }
}
// When user click the box it shows the message
@Composable
fun PopUpMessage(password: (String)) {

    val message  = isValidPassword(password)

    Surface(
        modifier = Modifier
            .width(400.dp)
            .padding(top = 1.dp, start = 10.dp, end = 10.dp),
       // .blur(radius = 10.dp)
        shape = RoundedCornerShape(7.dp),
        color = Color.Transparent,//0xFF1A1A1A
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
        ) {

            message.forEach {  error ->
                Text(text = error,
                    color = Color(0xFFF83C3C)
                )}
        }
    }
}
// isValidPassword should be outside the Composable otherwise it cannot call to other files
fun isValidPassword(passwordPP: String): List<String>{

    val errorMessage = mutableListOf<String>()

    if ((passwordPP.length < 8) || (passwordPP.length > 16)){
        errorMessage.add("• Must be between 8 and 16 characters") }

    if (passwordPP.firstOrNull { it.isDigit() } == null){
        errorMessage.add("• At least one digit") }

    if (passwordPP.firstOrNull {it.isLowerCase()} == null){
        errorMessage.add("• At least one lowercase letter") }

    if (passwordPP.firstOrNull {it.isUpperCase()} == null) {
        errorMessage.add("• At least one uppercase letter") }

    if (passwordPP.firstOrNull { !it.isLetterOrDigit() } == null) {
        errorMessage.add("• At least one special character") }// check for special characters

    return errorMessage
}