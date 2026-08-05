package com.cfcici.`in`.project.ui


import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.ui.graphics.Brush
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontFamily
import com.cfcici.`in`.project.viewmodel.UserViewModel
import login.shared.generated.resources.Res
import login.shared.generated.resources.neonderthaw
import org.jetbrains.compose.resources.Font


@Composable

fun LoginPage(onLoginClick: (String, String) -> Unit, onGoToNewAccount: () -> Unit , userViewModel: UserViewModel) {
    val userName = rememberTextFieldState()
    var userPassword by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState()}
    val orange = Color(0xFFFF9800)
    val gradientColors = listOf( Red, Blue)
    var usernameError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    val hotWheelsFont = FontFamily(Font(Res.font.neonderthaw, FontWeight.Normal))
    val infiniteTransition = rememberInfiniteTransition(label = "newoFlicker")
    val flickerAlpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1.20f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing), repeatMode = RepeatMode.Reverse
        ),
        label = "flickerAlpha"
    )

    Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            }
        ){
            Box(
                modifier = Modifier.fillMaxSize().background(Color.Black)
            )
            {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .imePadding()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.padding(10.dp)
                    ){
                        // Large soft glow
                        Text(
                            text = "Hot Wheels",
                            fontFamily = hotWheelsFont,
                            fontSize = 60.sp,
                            fontWeight = FontWeight.Bold,
                            style = TextStyle(
                                brush = Brush.linearGradient(colors = gradientColors)),
                            //color = Color.Magenta.copy(alpha = 0.7f),
                            modifier = Modifier
                                .blur(radius = 10.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                                .graphicsLayer(alpha = flickerAlpha)
                                .padding(24.dp)
                        )
                        //  Small soft glow
                        Text(
                            text = "Hot Wheels",
                            fontFamily = hotWheelsFont,
                            fontSize = 60.sp,
                            fontWeight = FontWeight.Bold,
                            style = TextStyle(
                                brush = Brush.linearGradient(
                                    colors = gradientColors)),
                            //color = Color(0xFFFF1493).copy(alpha = 0.6f),
                            modifier = Modifier
                                .blur(90.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                                .graphicsLayer(alpha = flickerAlpha * 0.85f)
                                .padding(24.dp)
                        )
                        Text(
                            text = "Hot Wheels",
                            //modifier = Modifier
                                //.fillMaxHeight()
                                //.padding(10.dp),
                            fontFamily = hotWheelsFont,
                            fontSize = 60.sp,
                            fontWeight = FontWeight.Bold,
                            style = TextStyle(
                                brush = Brush.linearGradient(
                                    colors = gradientColors
                                )
                            ),
                        )
                    }

                    Text(
                        text = "Log in to your account",
                        modifier = Modifier.padding(10.dp),
                        fontSize = 25.sp,
                        //MaterialTheme.colorScheme.onPrimaryContainer
                        //is say give the appropriate or matching automatically
                        //Remember onPrimaryContainer -> text/icon colour PrimaryContainer -> background
                        color = Color.White,
                        fontWeight = FontWeight.Light
                    )

                    OutlinedTextField(// TextFiled itself will create box
                        state = userName,// store whatever user enter
                        lineLimits = TextFieldLineLimits.SingleLine,
                        label = { Text("Enter Your Username") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Username Icon",
                                tint = Color.White
                            )
                        },
                        inputTransformation = InputTransformation.maxLength(16),
                        textStyle = TextStyle(fontSize = 20.sp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.DarkGray,
                            unfocusedBorderColor = Color.DarkGray,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            unfocusedLabelColor = Color.DarkGray,
                            focusedLabelColor = Color.DarkGray,
                            errorTextColor = Color.White,
                            cursorColor = Color.White
                        ),
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .width(320.dp)
                            .onFocusChanged{
                                if(it.isFocused)
                                    usernameError = null },
                        isError = usernameError != null,
                        supportingText = {
                            if(usernameError != null)
                                Text("Username is required")
                        }
                    )

                    PasswordPage(
                        password = userPassword,
                        onPasswordChange = { userPassword = it },
                        isError = passwordError != null,
                        supportingText = {
                            if(passwordError != null){
                                Text(passwordError!!)
                            }
                        }
                    )

                    Button(
                        modifier = Modifier.padding(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF0396B))  ,
                        onClick =
                            {
                                if(userName.text.isEmpty()){
                                    usernameError = ""
                                }else{
                                    usernameError = null
                                }

                                if (userPassword.isEmpty()){
                                    passwordError = "Password is required"
                                }else if(!isValidPassword(passwordPP = userPassword)){
                                    passwordError = "Password is invalid"
                                } else{
                                    passwordError = null
                                }

                                if (userPassword.isEmpty() || userName.text.isEmpty() || passwordError != null)
                                {
                                    scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "Need to fill up everything.",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }
                            else {
                                userViewModel.loginCheckerVM( userName.text.toString(), userPassword){
                                    exists ->
                                    if (exists){
                                        onLoginClick(// Send the username and password to App
                                            userName.text.toString(),
                                            userPassword
                                        )
                                    }else{
                                        scope.launch {
                                            snackbarHostState.showSnackbar(
                                                message = "Invalid username or password.",
                                                duration = SnackbarDuration.Short
                                            )
                                        }
                                    }
                                }
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
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Click Here",
                            modifier = Modifier
                                .padding(vertical = 5.dp)
                                .clickable { onGoToNewAccount() },  // navigate to  new account
                            color = Color(0xFFFF9800),
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )


                    }
                }
            }
        }

}
