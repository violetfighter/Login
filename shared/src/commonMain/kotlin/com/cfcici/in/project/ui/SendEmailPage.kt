package com.cfcici.`in`.project.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.input.key.Key
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SentEmailPage(onBackToLogin: () -> Unit,
                  onCodeVerified: (String, String) -> Unit,
                  viewModel: UserViewModel){
    var emailError by remember { mutableStateOf<String?>(null) }
    val email = rememberTextFieldState()
    var statusMessage by remember { mutableStateOf<String?>(null) }
    val usernameFont = FontFamily(Font(Res.font.Amarante_Regular))
    val scope = rememberCoroutineScope()
    var showPincodeCard by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()){

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
                        shape = RoundedCornerShape(50.dp),
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
                        Text(text = it, color = Color.Red, fontSize = 13.sp, fontFamily = usernameFont)
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

                            isLoading = true
                            viewModel.forgotPasswordVM(emailText) { success, emailNotFind ->
                                isLoading = false
                                when{
                                    success -> {
                                        statusMessage =  "Email Sent"
                                        scope.launch {
                                            delay(timeMillis = 1000)
                                            showPincodeCard = true
                                        }
                                    }

                                    emailNotFind -> statusMessage = "Email is not Registered"
                                    else -> statusMessage = "Something went wrong @SendEmailPage"
                                }
                            }
                        },
                        enabled = !isLoading, // avoid multiple tab on button while loading
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(26.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF0396B))
                    )

                    {
                        if (isLoading){
                            androidx.compose.material3.CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = Color.White,
                                strokeWidth = 2.dp)
                        }else{
                            Text("Send email",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = usernameFont)
                        }

                    }

                    statusMessage?.let {
                        Text(text = it,
                            color = Color.White,
                            fontSize = 13.sp,
                            fontFamily = usernameFont)
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

        AnimatedVisibility(
            visible = showPincodeCard,
            enter = scaleIn(animationSpec = tween(300)) + fadeIn(animationSpec = tween(300)),
            exit = scaleOut(animationSpec = tween(300)) + fadeOut(animationSpec = tween(300))
        ) {
            UserCodeEnter(
                userEmail = email.text.toString().trim(),
                onDismiss = { showPincodeCard = false },
                onVerify = {email, code ->
                    showPincodeCard = false
                    onCodeVerified(email, code)/// ???????????????????
                },
                userViewModel = viewModel
            )
        }
    }
}

@Composable
fun UserCodeEnter(
    userEmail: String,
    onDismiss: () -> Unit,
    onVerify: (email: String, code: String) -> Unit, /// ???????????????????
    userViewModel: UserViewModel
)
{
    val usernameFont = FontFamily(Font(Res.font.Amarante_Regular))
    val accent = Color(0xFFF0396B)
    val digits = remember { mutableStateListOf("", "", "", "", "", "") }
    val focusRequesters = remember { List(6) { FocusRequester() } }
    var focusedIndex by remember { mutableStateOf(0) }
    val isComplete = digits.all { it.isNotEmpty() }
    var codeError by remember { mutableStateOf<String?>(null) }
    var isVerifyingAndLoading by remember { mutableStateOf(false) }/// ???????????????????

    LaunchedEffect(Unit) { focusRequesters[0].requestFocus() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .imePadding(),// shrink the area for the keyboard
            //.clickable(indication = null, interactionSource = remember { MutableInteractionSource() }) { onDismiss() },
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .clickable(indication = null, interactionSource = remember { MutableInteractionSource() }) {},
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF2C256F)),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.12f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 28.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = "Enter reset code",
                    fontSize = 26.sp,
                    fontFamily = usernameFont,
                    style = TextStyle(
                        brush = Brush.linearGradient(listOf(Color(0xFFF0396B), Color(0xFFF0883C)))
                    ),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "We sent a 6-digit code to given email",
                    fontSize = 14.sp,
                    fontFamily = usernameFont,
                    color = Color.White.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    for (i in 0 until 6) {
                        val isFocused = focusedIndex == i // focusedIndex tell you which box currently had keyboard focus
                        val marker = "\u200B"
                        BasicTextField(
                            value = TextFieldValue(
                                text = marker + digits[i],
                                selection = TextRange(marker.length + digits[i].length) // always at the end
                            ),
                            onValueChange = { new ->
                                val d = new.text.filter { it.isDigit() }
                                when {
                                    d.isNotEmpty() -> {
                                        digits[i] = d.last().toString()
                                        if (i < 5) focusRequesters[i + 1].requestFocus()
                                    }
                                    digits[i].isNotEmpty() -> digits[i] = ""
                                    i > 0 -> {
                                        digits[i - 1] = ""
                                        focusRequesters[i - 1].requestFocus()
                                    }
                                }
                            }
                            ,
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(0.8f)
                                .focusRequester(focusRequesters[i])
                                .onFocusChanged { if (it.isFocused) focusedIndex = i }
                                .onKeyEvent { event ->
                                    if (
                                        event.type == KeyEventType.KeyDown &&
                                        event.key == Key.Backspace
                                    ) {
                                        if (digits[i].isNotEmpty()) {
                                            // The current box has a digit.
                                            // Delete the digit in this box.
                                            digits[i] = ""
                                        } else if (i > 0) {
                                            // The current box is empty.
                                            // Move to the previous box and delete its digit.
                                            digits[i - 1] = ""
                                            focusRequesters[i - 1].requestFocus()
                                        }

                                        true
                                    } else {
                                        false
                                    }
                                },
                            singleLine = true,
                            textStyle = TextStyle(
                                color = Color.White,
                                fontSize = 22.sp,
                                fontFamily = usernameFont,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            ),
                            cursorBrush = SolidColor(Color.Transparent),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            decorationBox = { inner ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(
                                            Color.White.copy(alpha = if (isFocused) 0.14f else 0.08f),
                                            RoundedCornerShape(14.dp)
                                        )
                                        .border(
                                            width = if (isFocused) 2.dp else 1.dp,
                                            color = if (isFocused) accent else Color.White.copy(alpha = 0.25f),
                                            shape = RoundedCornerShape(14.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) { inner() }
                            }
                        )
                    }
                }

                codeError?.let{
                    Text(
                        text = it,
                        color = Color.Red,
                        fontSize = 13.sp,
                        fontFamily = usernameFont)
                }


                Button(
                    onClick = {
                        val code = digits.joinToString("")
                        codeError = null
                        isVerifyingAndLoading = true// verification request started
                        userViewModel.verifyPasswordVM(userEmail, code){
                            success ->
                            isVerifyingAndLoading = false // verification request finished
                            if(success){
                                onVerify(userEmail, code)
                            } else {
                                codeError = "Invalid or expired code. Try again."
                            }
                        }
                    },
                    enabled = isComplete,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(26.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = accent,
                        disabledContainerColor = accent.copy(alpha = 0.3f),
                        disabledContentColor = Color.White.copy(alpha = 0.6f)
                    )
                )
                {
                    if (isVerifyingAndLoading) {
                        androidx.compose.material3.CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            text = "Verify",
                            fontSize = 15.sp,
                            color = Color.White,
                            fontFamily = usernameFont)
                    }
                }

                TextButton(onClick = onDismiss) {
                    Text(
                        text = "Cancel",
                        fontFamily = usernameFont,
                        color = Color(0xFFFF9800))
                }
            }
        }
    }
}