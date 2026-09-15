package com.cfcici.`in`.project.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontFamily
import com.cfcici.`in`.project.viewmodel.UserViewModel
import io.github.vinceglb.confettikit.compose.ConfettiKit
import io.github.vinceglb.confettikit.core.Party
import io.github.vinceglb.confettikit.core.emitter.Emitter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import login.shared.generated.resources.Amarante_Regular
import login.shared.generated.resources.Res
import org.jetbrains.compose.resources.Font

import kotlin.time.Duration.Companion.milliseconds


enum class NewAccountState {FORM, SUCCESS}

@Composable
fun NewAccountPage(
    onCreateNewAccount: (String, String, String, String, (Boolean, String?) -> Unit) -> Unit,
    onBackToLogin: () -> Unit,
    userViewModel: UserViewModel
)
{
    val usernameFont = FontFamily(Font(Res.font.Amarante_Regular))
    val newUserName = rememberTextFieldState()
    val newPassword = rememberTextFieldState()
    val newEmailID = rememberTextFieldState()
    val newDOB = rememberTextFieldState()
    var showCalendar by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<Long?>(null) }
    val snackbarHostState = remember { SnackbarHostState()}
    var passwordFocused by remember { mutableStateOf(false) }
    val gradientColors = listOf(Color(0xFFF0396B), Color(0xFFF0555C), Color(0xFFF0883C))
    val scope = rememberCoroutineScope()
    //val hazeState = remember { HazeState() }
    val dobInteractionSource = remember { MutableInteractionSource() }
    val isDobPressed by dobInteractionSource.collectIsPressedAsState()
    //reads from that same object and gives you a live Boolean — true while the field is actively being pressed, false otherwise.
    //And send to LaunchedEffect
    var dateOfBirthError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var usernameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    //String? means the value can be either a String or null.
    //(null) is the starting value — meaning initially, there's no error.

    var screenState by remember { mutableStateOf(NewAccountState.FORM) }
    var isLoading by remember { mutableStateOf(false) }
    val formAlpha by animateFloatAsState(
        targetValue = if(screenState == NewAccountState.FORM) 1f else 0f,///????
        animationSpec = tween(400),
        label = "formAlpha"
    )

    var emailErrorShaker by remember { mutableStateOf(0) }
    var usernameErrorShaker by remember { mutableStateOf(0) }
    var passwordErrorShaker by remember { mutableStateOf(0) }
    var dobErrorShaker by remember { mutableStateOf(0) }

    var showConfetti by remember { mutableStateOf(false) }

    LaunchedEffect(screenState){
        if (screenState == NewAccountState.SUCCESS){
            delay(1300)//lets the checkmark bounce in and sit for a beat before navigating
           // onNewAccountSuccess()
        }
    }

    //LaunchedEffect(newEmailID.text){//When something happens on the screen, run this code as a side effect.
        //emailError = emailChecker(newEmailID.text.toString())
    //}
    LaunchedEffect(newEmailID.text) {
        if (newEmailID.text.isNotEmpty()) {
            if (!emailChecker(newEmailID.text.toString())) {
                emailError = "Invalid email format"
            } else {
                emailError = null
            }
        }
    }

    LaunchedEffect(isDobPressed) {
        if (isDobPressed) {
            showCalendar = true
        }
    }
    Scaffold (
        snackbarHost = {
        SnackbarHost(hostState = snackbarHostState)
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Color(0xFF140F22)
                    //Brush.linearGradient(colors = listOf(Color(0xFFF0396B), Color(0xFF1A1A1A), Color(0xFFF0555C)))
                ),
            //.hazeSource(state = hazeState)// mark this as the blur source
            //.statusBarsPadding(), // pushes content below the statues bar
            contentAlignment = Alignment.Center
        )
        {
            NeonSpeedwayBackground(//keeps running continuously behind everything
                modifier = Modifier.fillMaxSize()
            )
            Card(
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    //.fillMaxHeight()
                    //.height(5.dp)
                    .padding(20.dp)
                    .clip(RoundedCornerShape(24.dp)),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Black.copy(alpha = 0.5f)
                    //containerColor = Color.Transparent
                )

            ) {
                Column(
                    modifier = Modifier
                        //.fillMaxSize()
                        .fillMaxWidth()
                        .imePadding()//Dynamically adjusts bottom padding when the keyboard opens
                        .graphicsLayer(alpha = formAlpha)
                        .verticalScroll(rememberScrollState())
                        .padding(24.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Create New Account",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = usernameFont,
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            brush = Brush.linearGradient(
                                colors = gradientColors
                            )
                        ),
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    OutlinedTextField(
                        state = newUserName,
                        label = { Text("Username", fontFamily = usernameFont) },
                        inputTransformation = InputTransformation.maxLength(16),
                        modifier = Modifier
                            .fillMaxWidth()
                            .shake(trigger = usernameErrorShaker)
                            .onFocusChanged {
                                if (it.isFocused)// When your click on the inbox it gives true
                                    usernameError = null
                            },
                        shape = RoundedCornerShape(50.dp),
                        lineLimits = TextFieldLineLimits.SingleLine,
                        textStyle = TextStyle(fontSize = 20.sp, fontFamily = usernameFont),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFFF0555C),
                            unfocusedTextColor = Color.White,
                            focusedTextColor = Color.White,
                            unfocusedBorderColor = Color(0xFFF0555C),
                            unfocusedLabelColor = Color(0xFFF0555C),
                            focusedLabelColor = Color(0xFFF0555C),
                            errorTextColor = Color.White,
                            cursorColor = Color.White
                        ),
                        isError = usernameError != null,// read as does usernameError have any error false -> shows nothing
                        supportingText = {
                            if (usernameError != null)
                                Text(usernameError!!)
                        }
                    )

                    OutlinedTextField(
                        state = newEmailID,
                        label = { Text("Email ID", fontFamily = usernameFont) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .shake(trigger = emailErrorShaker)
                            .onFocusChanged {
                                if (it.isFocused)
                                    emailError = null
                            },
                        shape = RoundedCornerShape(50.dp),
                        lineLimits = TextFieldLineLimits.SingleLine,
                        textStyle = TextStyle(fontSize = 20.sp, fontFamily = usernameFont),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFFF0555C),
                            unfocusedTextColor = Color.White,
                            focusedTextColor = Color.White,
                            unfocusedBorderColor = Color(0xFFF0555C),
                            unfocusedLabelColor = Color(0xFFF0555C),
                            focusedLabelColor = Color(0xFFF0555C),
                            errorTextColor = Color.White,
                            cursorColor = Color.White
                        ),
                        isError = emailError != null,
                        supportingText = {
                            if (emailError != null)
                                Text(emailError!!)
                            // passwordError is a String OR null (String?)
                            // The "!!" tells Kotlin: "I already checked it's not null, so just treat it as a normal String"
                            // Safe here because the "if" right before it already confirmed passwordError != null otherwise it will crash the app
                        }
                    )

                    OutlinedTextField(
                        state = newDOB,
                        label = { Text("Date of Birth", fontFamily = usernameFont) },
                        shape = RoundedCornerShape(50.dp),
                        lineLimits = TextFieldLineLimits.SingleLine,
                        textStyle = TextStyle(fontSize = 20.sp, fontFamily = usernameFont),
                        modifier = Modifier
                            .fillMaxWidth()
                            .shake(trigger = dobErrorShaker)
                            .onFocusChanged {
                                if (it.isFocused)
                                    dateOfBirthError = null
                            },
                        readOnly = true, // blocks typing
                        interactionSource = dobInteractionSource,
                        //dobInteraction lets know that if it is pressed or clicked to interaction
                        //so it tells outlinedTextField to do whatever next
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFFF0555C),
                            unfocusedTextColor = Color.White,
                            focusedTextColor = Color.White,
                            unfocusedBorderColor = Color(0xFFF0555C),
                            unfocusedLabelColor = Color(0xFFF0555C),
                            focusedLabelColor = Color(0xFFF0555C),
                            errorTextColor = Color.White,
                            cursorColor = Color.White
                        ),
                        trailingIcon = {
                            IconButton(
                                onClick = { showCalendar = true }) {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = "Select the date",
                                    tint = Color(0xFFF0555C)
                                )
                                if (showCalendar) {
                                    Calender(
                                        onDateSelected = { date ->
                                            selectedDate = date
                                            newDOB.edit {//edit { } is the API for programmatically changing what's inside a TextFieldState as opposed to the user typing into it
                                                replace(0, length, formatDate(date))
                                            }
                                        },
                                        onDismiss = {
                                            showCalendar = false
                                        }
                                    )
                                }
                            }
                        },
                        isError = dateOfBirthError != null,
                        supportingText = {
                            if (dateOfBirthError != null) {
                                Text("Date of Birth is required", fontFamily = usernameFont)
                            }
                        }
                    )

                    OutlinedTextField(
                        state = newPassword,
                        label = { Text("Password", fontFamily = usernameFont) },
                        shape = RoundedCornerShape(50.dp),
                        inputTransformation = InputTransformation.maxLength(16),
                        modifier = Modifier
                            .fillMaxWidth()
                            .shake(trigger = passwordErrorShaker)
                            .onFocusChanged {
                                passwordFocused = it.isFocused
                                if (it.isFocused)
                                    passwordError = null
                            },
                        lineLimits = TextFieldLineLimits.SingleLine,
                        textStyle = TextStyle(fontSize = 20.sp, fontFamily = usernameFont),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFFF0555C),
                            unfocusedTextColor = Color.White,
                            focusedTextColor = Color.White,
                            unfocusedBorderColor = Color(0xFFF0555C),
                            unfocusedLabelColor = Color(0xFFF0555C),
                            focusedLabelColor = Color(0xFFF0555C),
                            errorTextColor = Color.White,
                            cursorColor = Color.White,
                        ),
                        isError = passwordError != null,
                        supportingText = {
                            if (passwordError != null) {
                                Text(passwordError!!)
                            }
                        }
                    )

                    if (passwordFocused) {
                        PopUpMessage(newPassword.text.toString())
                    }


                    Button(
                        modifier = Modifier.padding(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFF0396B),
                            contentColor = Color.White
                        ),
                        onClick =
                            {
                                var isValid = true

                                if (newEmailID.text.isEmpty()) {
                                    emailError = "Email is required"
                                    emailErrorShaker++
                                    isValid = false
                                } else if (!emailChecker(newEmailID.text.toString())) {
                                    emailError = "Invalid email format"
                                    emailErrorShaker++
                                    isValid = false
                                } else {
                                    emailError = null
                                }

                                if (newUserName.text.isEmpty()) {
                                    usernameError = "Username is required"
                                    usernameErrorShaker++
                                    isValid = false
                                } else
                                    usernameError = null

                                if (newDOB.text.isEmpty()) {
                                    dateOfBirthError = ""
                                    dobErrorShaker++
                                    isValid = false
                                } else
                                    dateOfBirthError = null

                                if (newPassword.text.isEmpty()) {
                                    passwordError = "Password is required"
                                    passwordErrorShaker++
                                    isValid = false
                                } else if (isValidPassword(passwordPP = newPassword.text.toString()).isNotEmpty()) {
                                    passwordError = "Password is invalid"
                                    passwordErrorShaker++
                                    isValid = false
                                } else
                                    passwordError = null
                                /*
                               if (newUserName.text.isEmpty() || newPassword.text.isEmpty() || newDOB.text.isEmpty() || newEmailID.text.isEmpty() || passwordError != null || emailError != null)
                                {
                                    scope.launch {
                                        val snackbarJob = launch {
                                            snackbarHostState.showSnackbar(
                                                message = "Need to fill up everything.",
                                                duration = SnackbarDuration.Indefinite
                                            )
                                        }
                                        delay(2000)
                                        snackbarJob.cancel()
                                    }
                                }*/

                                if (isValid) {
                                    isLoading = true
                                    userViewModel.emailExistVM((newEmailID.text.toString()))
                                    { emailExists ->

                                        userViewModel.usernameExistsVM(newUserName.text.toString())
                                        { usernameExists ->

                                            if (emailExists) {
                                                emailError = "Email already exists"
                                                emailErrorShaker++
                                                isLoading = false
                                            }
                                            if (usernameExists) {
                                                usernameError = "Username already exists"
                                                usernameErrorShaker++
                                                isLoading = false
                                            }
                                            if (!emailExists && !usernameExists) {
                                                // Send the values to App so it can save on room database
                                                onCreateNewAccount(
                                                    newUserName.text.toString(),
                                                    newPassword.text.toString(),
                                                    newDOB.text.toString(),
                                                    newEmailID.text.toString()
                                                ) { success, errorMessage ->
                                                    isLoading = false
                                                    if (success) {
                                                        scope.launch {
                                                            //val snackbarJob = launch {
                                                                //snackbarHostState.showSnackbar(
                                                                    //message = "Successfully created the account.",
                                                                    //duration = SnackbarDuration.Short
                                                                //)
                                                           // }
                                                            showConfetti = true
                                                            delay(timeMillis = 3500)
                                                            //snackbarJob.cancel()
                                                            onBackToLogin()
                                                        }
                                                    } else {
                                                        emailError = errorMessage
                                                            ?: "Something went wrong — please try again @NewAccountPage"
                                                    }
                                                }
                                            }
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

                    Row(
                        modifier = Modifier.padding(5.dp).fillMaxSize(),
                        horizontalArrangement = Arrangement.Center
                    )
                    {
                        Text(
                            text = ("Already have an account? "),
                            modifier = Modifier
                                .padding(top = 30.dp),
                            fontSize = 15.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Normal,
                            fontFamily = usernameFont
                        )
                        Text(
                            text = ("Login"),
                            modifier = Modifier
                                .padding(top = 30.dp, start = 3.dp)
                                .clickable { onBackToLogin() },
                            color = Color(0xFFD4537E),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal,
                            fontFamily = usernameFont
                        )
                    }
                }
            }
            if (showConfetti) {
                //Solid background layer to mask/hide the "Create New Account" form
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF140F22))
                )

                //Tick mark positioned in the center, behind the confetti
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AnimatedSuccessTick()

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "You Successfully Created New Account!",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFF0555C),
                        textAlign = TextAlign.Center,
                        fontFamily = usernameFont
                    )
                }

                //Confetti explosion rendered over the tick mark without an opaque background
                ConfettiKit(
                    modifier = Modifier.fillMaxSize(),
                    parties = listOf(
                        Party(
                            speed = 0f,
                            maxSpeed = 30f,
                            damping = 0.9f,
                            spread = 360,
                            colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
                            emitter = Emitter(duration = 100.milliseconds).max(100),
                        )
                    )
                )
            }
        }
    }
}
@Composable
fun AnimatedSuccessTick() {

    var startAnimation by remember { mutableStateOf(false) }

    val progress by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 600,
            easing = FastOutSlowInEasing
        ),
        label = "tick mark"
    )

    LaunchedEffect(Unit) {
        startAnimation = true
    }

    Canvas(
        modifier = Modifier.size(100.dp)
    ) {

        val path = Path().apply {
            moveTo(
                size.width * 0.25f,
                size.height * 0.52f
            )

            lineTo(
                size.width * 0.43f,
                size.height * 0.68f
            )

            lineTo(
                size.width * 0.75f,
                size.height * 0.32f
            )
        }

        val pathMeasure = PathMeasure()
        pathMeasure.setPath(path, false)

        val animatedPath = Path()

        pathMeasure.getSegment(
            startDistance = 0f,
            stopDistance = pathMeasure.length * progress,
            destination = animatedPath,
            startWithMoveTo = true
        )

        drawPath(
            path = animatedPath,
            color = Color(0xFFF0555C),
            style = Stroke(
                width = 8.dp.toPx(),
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
            )
        )
    }
}
// This calendar is for pop one
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Calender(

    // Reason we use long is that selected date is represented by milliseconds
    // July 30, 2026 -> some long number
    // Reason we use long is that selected date is represented by milliseconds
    // July 30, 2026 -> some long number
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit // a function that takes nothing and return nothing
// it mainly used to close the calendar.
){
    val usernameFont = FontFamily(Font(Res.font.Amarante_Regular))
    val datePickerState = rememberDatePickerState(// Stores the info
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis <= kotlin.time.Clock.System.now().toEpochMilliseconds()
            }
        }
    )
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(
            surface = Color(0xFF140F22),
            surfaceContainerHigh = Color(0xFF140F22),
            onSurface = Color.White,
            primary = Color(0xFFF0396B)
        ),
        typography = MaterialTheme.typography.copy(
            titleLarge = MaterialTheme.typography.titleLarge.copy(
                fontFamily = usernameFont
            ),
            headlineLarge = MaterialTheme.typography.headlineLarge.copy(
                fontFamily = usernameFont
            ),
            bodyLarge = MaterialTheme.typography.bodyLarge.copy(
                fontFamily = usernameFont
            ),
            labelLarge = MaterialTheme.typography.labelLarge.copy(
                fontFamily = usernameFont
            )
        )
    ) {

        // this creates the model pop-up

        // this creates the model pop-up
        DatePickerDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(
                    onClick = {
//When "OK" is tapped: it reads whatever date the user tapped (datePickerState.selectedDateMillis,
// which is null if nothing was tapped), hands it up to the caller via onDateSelected(...), then closes the dialog via onDismiss().
                        onDateSelected(datePickerState.selectedDateMillis)
                        onDismiss()
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color(0xFFAFA9EC)
                    )
                ) {
                    Text("OK", fontFamily = usernameFont)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = onDismiss,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color(0xFFAFA9EC)
                    )
                ) {
                    Text(text = "Cancel", fontFamily = usernameFont)
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    containerColor = Color(0xFF140F22),
                    titleContentColor = Color.White,
                    headlineContentColor = Color(0xFFAFA9EC),
                    weekdayContentColor = Color(0xFF9E9E9E),
                    subheadContentColor = Color(0xFFBDBDBD),
                    yearContentColor = Color.White,
                    currentYearContentColor = Color(0xFFAFA9EC),
                    selectedYearContentColor = Color.White,
                    selectedYearContainerColor = Color(0xFFF0396B),
                    dayContentColor = Color.White,
                    selectedDayContentColor = Color.White,
                    selectedDayContainerColor = Color(0xFFF0396B),
                    todayContentColor = Color(0xFFAFA9EC),
                    todayDateBorderColor = Color(0xFFAFA9EC),
                    navigationContentColor = Color.White
                )
            )
        }
    }
}
//Computer stores the data as current time based and convert it to computer based number.
//formatDate() function exists specifically to take that raw number and translate it into something a human actually wants to read

//Takes a nullable Long and returns a readable String. If nothing was passed, it just returns an empty string rather than crashing.
fun formatDate(dateMillis: Long?): String {
    if (dateMillis == null) return ""

    val instant = Instant.fromEpochMilliseconds(dateMillis)//Converts the raw millisecond number into a proper Instant object — a structured representation of "this exact moment in time," using kotlinx.datetime's API.
    val date = instant.toLocalDateTime(TimeZone.currentSystemDefault()).date//Converts that instant into a calendar date (day/month/year), adjusted for the device's current timezone — this is what turns "a huge millisecond number" into "August 2, 2026."

    return "${date.dayOfMonth}/${date.monthNumber}/${date.year}"// display the DD/MM/YYYY
}

fun emailChecker(email: String): Boolean{//gives string
    val regex = Regex(pattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    return when{
        email.isEmpty() -> true//LaunchedEffect will always check the inbox. If you don't use it will show error from the beginning itself
        !regex.matches(email) -> false
        else -> true
    }
}

fun Modifier.shake(
    trigger: Int,
    iterations: Int = 4,
    translateX: Float = 10f,
    rotateY: Float = 7f,
    duration: Int = 80
): Modifier = composed {

    val shake = remember { Animatable(0f) }

    LaunchedEffect(trigger) {
        if (trigger > 0) {
            repeat(iterations) { i ->

                val direction = if (i % 2 == 0) 1f else -1f

                shake.animateTo(
                    targetValue = direction,
                    animationSpec = tween(duration)
                )
            }

            shake.animateTo(
                targetValue = 0f,
                animationSpec = tween(duration)
            )
        }
    }

    graphicsLayer {
        translationX = shake.value * translateX
        rotationY = shake.value * rotateY
    }
}
//to get animated balls movement in the background
@Composable
fun NeonSpeedwayBackground(modifier: Modifier = Modifier) {
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

// I need confetti/firework when user create new account. It should be glowy shiny each one of them like illuminated yellow.




