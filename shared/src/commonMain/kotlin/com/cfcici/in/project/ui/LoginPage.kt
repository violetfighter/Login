package com.cfcici.`in`.project.ui


import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import com.cfcici.`in`.project.data.database.User
import com.cfcici.`in`.project.viewmodel.UserViewModel
import kotlinx.coroutines.delay
import login.shared.generated.resources.Amarante_Regular
import login.shared.generated.resources.Res
import login.shared.generated.resources.neonderthaw
import org.jetbrains.compose.resources.Font


@Composable

fun LoginPage(onLoginClick: (String, Int) -> Unit,
              onGoToNewAccount: () -> Unit ,
              onSentEmailPage:() -> Unit,
              userViewModel: UserViewModel) {
    val userName = rememberTextFieldState()
    var userPassword by remember { mutableStateOf("") }
    val usernameFont = FontFamily(Font(Res.font.Amarante_Regular))
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState()}
    var isLoading by remember { mutableStateOf(false) }
    var showInvalidMessage  by remember { mutableStateOf(false) }
    var showFillupMessage by remember { mutableStateOf(false) }
    val gradientColors = listOf( Color(0xFFFF9800), Color(0xFFF0396B))
    var usernameError by remember { mutableStateOf<String?>(null) }//this variable can contain a String OR nothing (null).
    var passwordError by remember { mutableStateOf<String?>(null) }
    val hotWheelsFont = FontFamily(Font(Res.font.neonderthaw, FontWeight.Normal))
    val infiniteTransition = rememberInfiniteTransition(label = "neoFlicker")
    val flickerAlpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1.20f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing), repeatMode = RepeatMode.Reverse),
        label = "flickerAlpha"
    )

    Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            }
        ){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF140F22)),
                contentAlignment = Alignment.Center
            )
            {
                NeonSpeedwayBackgroundInLoginPage(//keeps running continuously behind everything
                    modifier = Modifier.fillMaxSize()
                )
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
                        /*
                        Text(
                            text = "Wheelhouse",
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
                            text = "Wheelhouse",
                            fontFamily = hotWheelsFont,
                            fontSize = 60.sp,
                            fontWeight = FontWeight.Bold,
                            style = TextStyle(
                                brush = Brush.linearGradient(
                                    colors = gradientColors)),
                            //color = Color(0xFFF1493).copy(alpha = 0.6f),
                            modifier = Modifier
                                .blur(90.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                                .graphicsLayer(alpha = flickerAlpha * 0.85f)
                                .padding(24.dp)
                        )*/

                        Text(
                            text = "Wheelhouse",
                            //modifier = Modifier
                                //.fillMaxHeight()
                                //.padding(10.dp),
                            fontFamily = hotWheelsFont,
                            fontSize = 70.sp,
                            fontWeight = FontWeight.Bold,
                            style = TextStyle(
                                brush = Brush.linearGradient(
                                    colors = gradientColors
                                )
                            ),
                        )
                    }

                    OutlinedTextField(// TextField itself will create box
                        state = userName,// store whatever user enter
                        lineLimits = TextFieldLineLimits.SingleLine,
                        label = { Text(text = "Enter Your Username", fontFamily = usernameFont)},
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Person,
                                contentDescription = "Username Icon",
                                tint = Color(0xFFF0396B),
                            )
                        },
                        inputTransformation = InputTransformation.maxLength(16),
                        textStyle = TextStyle(fontSize = 20.sp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFFF0396B),
                            unfocusedBorderColor = Color(0xFFF0396B),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            unfocusedLabelColor = Color.LightGray,
                            focusedLabelColor = Color(0xFFF0396B),
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
                        passwordChecker = { userPassword = it },
                        isError = passwordError != null,
                        onPasswordFocus = {passwordError = null},
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
                                    isLoading = false
                                }else{
                                    usernameError = null
                                }

                                if (userPassword.isEmpty()){
                                    passwordError = "Password is required"
                                    isLoading = false
                                }else if(isValidPassword(passwordPP = userPassword).isNotEmpty()){
                                    //passwordError = "Password is invalid"
                                    isLoading = false
                                } else{
                                    passwordError = null
                                }

                                if (userPassword.isEmpty() || userName.text.isEmpty() )
                                {
                                    isLoading = false // need add alert dialogue
                                    showFillupMessage = true

                                }
                            else {
                                    isLoading = true
                                    userViewModel.loginCheckerVM(userName.text.toString(), userPassword) { userId ->
                                        if (userId != null) {
                                            onLoginClick(userName.text.toString(), userId)
                                        }else{
                                        isLoading = false
                                        showInvalidMessage = true
                                    }
                                }
                            }
                        }
                    ) {
                        if (isLoading) {
                            androidx.compose.material3.CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(text = "Create", fontFamily = usernameFont) // make create loading animation
                        }
                    }

                    if (showInvalidMessage){
                        Dialog(onDismissRequest = {showInvalidMessage= false}){
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
                                        text = "Invalid username or password.",
                                        color = Color.White,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .weight(1f)
                                            .wrapContentSize(Alignment.Center),
                                        textAlign = TextAlign.Center,
                                        fontFamily = usernameFont
                                    )
                                    Button(
                                        onClick = { showInvalidMessage = false },
                                        modifier = Modifier.padding(8.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF0396B))
                                    ) {
                                        Text("Try again", fontFamily = usernameFont)
                                    }
                                }
                            }
                        }
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

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    )
                    {
                        Text(
                            text = "Forgot Password?",
                            modifier = Modifier
                                .padding(top = 40.dp, start = 0.dp)
                                .clickable{ onSentEmailPage() },
                            fontSize = 15.sp,
                            color = Color(0xFFFF9800),
                            fontWeight = FontWeight.Normal,
                            fontFamily = usernameFont
                        )

                        Text(
                            text = "Sign Up",
                            modifier = Modifier
                                .padding(top = 40.dp, start = 0.dp)
                                .clickable { onGoToNewAccount() },  // navigate to  new account
                            color = Color(0xFFFF9800),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal,
                            fontFamily = usernameFont
                        )

                    }
                }
            }
        }
}


@Composable
fun NeonSpeedwayBackgroundInLoginPage(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "NewAccountBg")
    val orb1 by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(9000, easing = LinearEasing), RepeatMode.Reverse),
        label = "orb1"
    )
    val orb2 by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(12000, easing = LinearEasing), RepeatMode.Reverse),
        label = "orb2"
    )

    val orb4 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            tween(9000, easing = LinearEasing),
            RepeatMode.Reverse
        ),
        label = "orb4"
    )

    val orb5 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            tween(13000, easing = LinearEasing),
            RepeatMode.Reverse
        ),
        label = "orb5"
    )

    val orb6 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            tween(17000, easing = LinearEasing),
            RepeatMode.Reverse
        ),
        label = "orb6"
    )

    val orb7 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            tween(11000, easing = LinearEasing),
            RepeatMode.Reverse
        ),
        label = "orb7"
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height

//**************************************************************************************************
        // shade out circle

        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color(0x4DD4537E), Color.Transparent)
            ),
            radius = 220f,
            center = Offset(w * (0.1f + 0.8f * orb1), h * (0.1f + 0.8f * orb2))
        )

        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color(0x593C3489), Color.Transparent)
            ),
            radius = 260f,// fixed size of a circle
            center = Offset(w * (0.9f - 0.8f * orb2), h * (0.2f + 0.7f * orb1))
        )


        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color(0xFF574FBB), Color.Transparent)
            ),
            radius = 230f,// fixed size of a circle
            center = Offset(w * (0.2f + 0.6f * orb1), h * (0.8f - 0.6f * orb2))
        )

//**************************************************************************************************
        // see circle all time
        /*
                val center3 = Offset(w * (0.2f + 0.6f * orb1), h * (0.8f - 0.6f * orb2))
                val radius3 = w * 0.75f
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(Color(0xFF574FBB), Color.Transparent),
                        center = center3,

                    ),
                    radius = 230f,
                    center = center3
                )
                */

//**************************************************************************************************
        //Glowy/Under the screen circle

        val center4 = Offset(
            w * (0.1f + 0.8f * orb4),
            h * (0.2f + 0.2f * orb4)
        )
        val radius4 = w * 0.75f
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color(0x4DD4537E), Color.Transparent),
                center = center4,
                radius = 350f
            ),
            radius = radius4,
            center = center4
        )

        val center5 = Offset(
            w * (0.7f + 0.15f * orb5),
            h * (0.1f + 0.8f * orb5)
        )
        val radius5 = w * 0.65f
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color(0xFF574FBB), Color.Transparent),
                center = center5,
                radius = 250f//this controls the gradient, not the actual circle size.
            ),
            radius = radius5,
            center = center5
        )

        val center6 = Offset(
            w * (0.05f + 0.5f * orb6),
            h * (0.9f - 0.7f * orb6)
        )
        val radius6 = w * 0.55f
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color(0xFF784FBB), Color.Transparent),
                center = center6,
                radius = 450f
            ),
            radius = radius6,
            center = center6
        )

        val center7 = Offset(
            w * (0.9f - 0.7f * orb7),
            h * (0.7f - 0.4f * orb7)
        )
        val radius7 = w * 0.45f
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color(0xFFAD456F), Color.Transparent),
                center = center7,
                radius = 200f
            ),
            radius = radius7,
            center = center7
        )
    }
}