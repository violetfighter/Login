package com.cfcici.`in`.project.ui

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
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import com.cfcici.`in`.project.viewmodel.UserViewModel
import dev.chrisbanes.haze.HazeInput
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.blur.HazeBlurStyle
import dev.chrisbanes.haze.blur.hazeBlur
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun NewAccountPage(
    onCreateNewAccount: (String, String, String, String) -> Unit,
    onBackToLogin: () -> Unit,
    userViewModel: UserViewModel
)
{
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
    val hazeState = remember { HazeState() }
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

    LaunchedEffect(newEmailID.text){
        emailError = emailChecker(newEmailID.text.toString())
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
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                //.background(Color.Black)
                .background(
                    Brush.linearGradient(colors = listOf(Color(0xFFF0396B), Color(0xFF1A1A1A), Color(0xFFF0555C)))
                )
                .hazeSource(state = hazeState)// mark this as the blur source
                .statusBarsPadding(), // pushes content below the statues bar
            contentAlignment = Alignment.Center
        ){
            Card(
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    //.fillMaxHeight()
                    //.height(5.dp)
                    .padding(20.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .hazeBlur(
                        input = HazeInput.Sources(hazeState),
                        style = HazeBlurStyle {
                            blurRadius(50.dp)
                        }
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Black.copy(alpha = 0.5f)
                )

            ){
                Column(
                    modifier = Modifier
                        //.fillMaxSize()
                        .fillMaxWidth()
                        .imePadding()
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
                        label = { Text("Username")},
                        inputTransformation = InputTransformation.maxLength(16),
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged{
                                if(it.isFocused)// When your click on the inbox it gives true
                                    usernameError = null },
                        shape = RoundedCornerShape(50.dp),
                        lineLimits = TextFieldLineLimits.SingleLine,
                        textStyle = TextStyle(fontSize = 20.sp),
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
                                Text("Username is required")
                        }
                    )

                    OutlinedTextField(
                        state = newEmailID,
                        label = { Text("Email ID") },
                        modifier = Modifier.fillMaxWidth()
                            .onFocusChanged{
                                if(it.isFocused)
                                    emailError = null
                            },
                        shape = RoundedCornerShape(50.dp),
                        lineLimits = TextFieldLineLimits.SingleLine,
                        textStyle = TextStyle(fontSize = 20.sp),
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
                        label = { Text("Date of Birth") },
                        shape = RoundedCornerShape(50.dp),
                        lineLimits = TextFieldLineLimits.SingleLine,
                        textStyle = TextStyle(fontSize = 20.sp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged{
                                if(it.isFocused)
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
                            cursorColor = Color.White),
                        trailingIcon = {
                            IconButton(
                                onClick = {showCalendar = true}){
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = "Select the date",
                                    tint = Color(0xFFF0555C)
                                )
                                if (showCalendar){
                                    Calender(
                                        onDateSelected = {
                                            date -> selectedDate = date
                                            newDOB.edit {//edit { } is the API for programmatically changing what's inside a TextFieldState as opposed to the user typing into it
                                                replace(0, length, formatDate(date))/////?????
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
                            if(dateOfBirthError != null){
                                Text("Date of Birth is required")
                            }
                        }
                    )

                    OutlinedTextField(
                        state = newPassword,
                        label = {Text("Password")},
                        shape = RoundedCornerShape(50.dp),
                        inputTransformation = InputTransformation.maxLength(16),
                        modifier = Modifier.fillMaxWidth()
                            .onFocusChanged{
                                passwordFocused = it.isFocused
                                if (it.isFocused)
                                    passwordError = null
                            },
                        lineLimits = TextFieldLineLimits.SingleLine,
                        textStyle = TextStyle(fontSize = 20.sp),
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
                            if(passwordError != null){
                                Text(passwordError!!)
                            }
                        }
                    )
                    if (passwordFocused){
                        PopUpMessage(newPassword.text.toString())
                    }


                    Button(
                        modifier = Modifier.padding(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF0396B), contentColor = Color.White),
                        onClick =
                            {
                                if(newEmailID.text.isEmpty()){
                                    emailError = "Email is required"
                                } else{
                                    emailError = emailChecker(newEmailID.text.toString()) }

                                if (newUserName.text.isEmpty()){
                                    usernameError = ""//= "Username is required"
                                }else
                                    usernameError = null

                                if(newDOB.text.isEmpty()){
                                    dateOfBirthError = ""
                                }else
                                    dateOfBirthError = null

                                if(newPassword.text.isEmpty()){
                                    passwordError = "Password is required"
                                } else if(isValidPassword(passwordPP = newPassword.text.toString()).isNotEmpty()){
                                    passwordError = "Password is invalid"
                                }
                                else
                                    passwordError = null

                               if (newUserName.text.isEmpty() || newPassword.text.isEmpty() || newDOB.text.isEmpty() || newEmailID.text.isEmpty() || passwordError != null || emailError != null)
                                {
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = "Need to fill up everything.",
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                }
                                else{
                                    userViewModel.emailExistVM((newEmailID.text.toString())){
                                        exists ->
                                        if(exists){
                                            emailError = "Email already exists"
                                        }else
                                        { // Send the values to App so it can save on room database
                                            onCreateNewAccount(newUserName.text.toString(),newPassword.text.toString(), newDOB.text.toString(), newEmailID.text.toString())
                                            scope.launch {
                                                snackbarHostState.showSnackbar(
                                                    message = "Successfully created the account.",
                                                    duration = SnackbarDuration.Short
                                                )
                                                onBackToLogin()
                                            }
                                        }
                                    }
                                }
                            }
                    ) {
                        Text(text = "Create")
                    }

                    Row(
                        modifier = Modifier.padding(5.dp).fillMaxSize(),
                        horizontalArrangement = Arrangement.Center)
                    {
                        Text(
                            text = ("Already have an account? "),
                            modifier = Modifier
                                .padding(top = 30.dp),
                            fontSize = 15.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Normal
                        )
                        Text(
                            text = ("Login"),
                            modifier = Modifier
                                .padding(top = 30.dp, start = 3.dp)
                                .clickable{onBackToLogin()},
                            color = Color(0xFFFF9800),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
            }
        }
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
    val datePickerState = rememberDatePickerState(// Stores the info
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis <= kotlin.time.Clock.System.now().toEpochMilliseconds()
            }
        }
    )
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(
            surface = Color(0xFF1A1A1A),
            surfaceContainerHigh = Color(0xFF1A1A1A),
            onSurface = Color.White,
            primary = Color(0xFFF0396B)
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
                        contentColor = Color(0xFFF0396B)
                    )
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = onDismiss,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color(0xFFF0396B)
                    )
                ) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    containerColor = Color(0xFF1A1A1A),
                    titleContentColor = Color(0xFFFF9800),
                    headlineContentColor = Color(0xFFF0396B),
                    weekdayContentColor = Color(0xFF9E9E9E),
                    subheadContentColor = Color(0xFFBDBDBD),
                    yearContentColor = Color.White,
                    currentYearContentColor = Color(0xFFF0396B),
                    selectedYearContentColor = Color.White,
                    selectedYearContainerColor = Color(0xFFF0396B),
                    dayContentColor = Color.White,
                    selectedDayContentColor = Color.White,
                    selectedDayContainerColor = Color(0xFFF0396B),
                    todayContentColor = Color(0xFFFF9800),
                    todayDateBorderColor = Color(0xFFFF9800),
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

fun emailChecker(email: String): String?{//gives string
    val regex = Regex(pattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    return when{
        email.isEmpty() -> null//LaunchedEffect will always check the inbox. If you don't use it will show error from the beginning itself
        !regex.matches(email) -> "Invalid email format"
        else -> null
    }
}


