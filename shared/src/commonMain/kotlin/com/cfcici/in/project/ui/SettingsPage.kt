package com.cfcici.`in`.project.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.NoPhotography
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import login.shared.generated.resources.Res
import login.shared.generated.resources.profile_icon
import org.jetbrains.compose.resources.painterResource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.decodeToImageBitmap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.cfcici.`in`.project.CameraCapture
import com.cfcici.`in`.project.ImageStorage
import com.cfcici.`in`.project.viewmodel.UserViewModel
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import kotlin.let

@Composable
fun SettingsPage(userIdSP: Int, goBackToProfilePage:(String, Int) -> Unit, userViewModel: UserViewModel, imageStorage: ImageStorage)

//Reason why we use collectAsState
//Subscribes to the Flow — it starts collecting values from userViewModel.getUser(userIdSP) the moment your composable enters composition.
//Wraps each emitted value in Compose State —
//           every time the Flow emits a new value (because Room detected the row changed),
//           collectAsState() updates a State<User?> object behind the scenes, which automatically triggers recomposition of anything reading it.
{
    val user by userViewModel.getUserDetailsVM(userIdSP).collectAsState(initial = null)
    var usernameSP by remember { mutableStateOf("") }
    var emailSP by remember { mutableStateOf("") }
    var dobSP by remember { mutableStateOf("") }
    var passwordSP by remember { mutableStateOf("") }

    var passwordDisplay by remember { mutableStateOf(false) }
    var passwordFocus by remember { mutableStateOf(false) }
    var updateButton by remember { mutableStateOf(false) }

    var showCalendar by remember { mutableStateOf(false) }
    val dobInteractionSource = remember { MutableInteractionSource() }
    val isDobPressed by dobInteractionSource.collectIsPressedAsState()

    var showProfilePhotoOptions by remember { mutableStateOf(false) }
    var showCamera by remember { mutableStateOf(false) }
    var pendingCamera by remember { mutableStateOf(false) }


    val scope = rememberCoroutineScope()
    var selectedProfileBitmap by remember { mutableStateOf<ImageBitmap?>(null) }// turns byte to pixeled pics like it shows on UI and this is a local storage not saved on db
    var selectedImageBytes by remember { mutableStateOf<ByteArray?>(null) } // turns to byte
    val image = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = scope,
        onResult = { byteArrays ->
            byteArrays.firstOrNull()?.let { bytes ->
                selectedImageBytes = bytes
                selectedProfileBitmap = bytes.decodeToImageBitmap()
                val fileName = "profil_${userIdSP}_${kotlin.time.Clock.System.now().toEpochMilliseconds()}.jpg"
                val imagePath = imageStorage.saveImageToFile(bytes, fileName)
                println("Saved profile image path: $imagePath")

                user?.let{
                    currentUser ->
                    val updatedImage = currentUser.copy(userPhotoUser = imagePath)
                    userViewModel.updateProfileEditVM(updatedImage){
                        println("Profile photo update completed successfully")
                    }
                }
            }
        }
    )

    LaunchedEffect(pendingCamera) {
        if (pendingCamera) {
            pendingCamera = false
            showCamera = true
        }
    }

    LaunchedEffect(isDobPressed){
        if (isDobPressed){
            showCalendar = true
        }
    }

    LaunchedEffect(user){
        user?.let{
            usernameSP = it.usernameUser
            emailSP = it.emailIdUser
            dobSP = it.dateOfBirthUser
            passwordSP = it.passwordUser
        }
    }
    /*
    Equivalent to the above the code
    if (user != null) {
    usernameSP = user.usernameUser
    emailSP = user.emailIdUser
    dobSP = user.dateOfBirthUser
    passwordSP = user.passwordUser
    }
     */
    Box(
        modifier = Modifier
            .fillMaxSize()
            //.padding(10.dp)
            .background(Color.Black)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .background(color = Color(0xFFF0396B),
                        shape = RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 0.dp,
                            bottomStart = 20.dp,
                            bottomEnd = 20.dp)
                    )
            )
            Spacer(modifier = Modifier.height(150.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(15.dp)
            )
            {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    TextField(
                        value = usernameSP,
                        onValueChange = {usernameSP = it},
                        readOnly = true,
                        textStyle = TextStyle(fontSize = 20.sp),
                        label = { Text(
                            text = "Username", // Should be username already there
                            color = Color(0xFFFF9800),
                            fontSize = 12.sp
                        )},
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color(0xFFF0396B),
                            unfocusedIndicatorColor = Color(0xFFF0396B),
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        )
                    )
                    IconButton(
                        onClick = { updateButton = true }
                    ){
                        Icon(imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    TextField(
                        value = emailSP,
                        onValueChange = {emailSP = it},
                        readOnly = true,
                        textStyle = TextStyle(fontSize = 20.sp),
                        label = { Text(
                            text = "Email Id",
                            color = Color(0xFFFF9800),
                            fontSize = 12.sp
                        )},
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color(0xFFF0396B),
                            unfocusedIndicatorColor = Color(0xFFF0396B),
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        )
                    )
                    IconButton(// Not at all necessary
                        onClick = {}
                    ){
                        Icon(imageVector = Icons.Default.Lock,
                            contentDescription = "Lock",
                            tint = Color.DarkGray,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    TextField(
                        value = dobSP,
                        onValueChange = {dobSP = it},
                        readOnly = true,
                        textStyle = TextStyle(fontSize = 20.sp),
                        label = { Text(
                            text = "Date Of Birth", // Should be username already there
                            color = Color(0xFFFF9800),
                            fontSize = 12.sp
                        )},
                        interactionSource = dobInteractionSource,
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color(0xFFF0396B),
                            unfocusedIndicatorColor = Color(0xFFF0396B),
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                    )
                    IconButton(
                        onClick = {showCalendar = true }
                    ){
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    TextField(
                        value = passwordSP,
                        onValueChange = {passwordSP = it},
                        readOnly = true,
                        textStyle = TextStyle(fontSize = 20.sp),
                        label = { Text(
                            text = "Password", // should alert the user using email
                            color = Color(0xFFFF9800),
                            fontSize = 12.sp
                        )},
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color(0xFFF0396B),
                            unfocusedIndicatorColor = Color(0xFFF0396B),
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        visualTransformation =
                            if(passwordDisplay){
                                VisualTransformation.None
                            }else{
                                PasswordVisualTransformation()
                            },
                        trailingIcon = {
                            val passwordIcon = if(passwordDisplay)
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
                                    contentDescription = if(passwordDisplay){"Hide Password"} else {"Show Password"},
                                    tint = Color.DarkGray
                                )
                            }
                        },
                        modifier = Modifier
                            .onFocusChanged{
                                passwordFocus = it.isFocused
                            },
                    )
                    IconButton(
                        onClick = {}
                    ){
                        Icon(imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(90.dp))
            Row (
                modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                IconButton(
                    onClick = {
                        goBackToProfilePage(usernameSP, userIdSP)
                    }
                ){
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "",
                        tint = Color.White
                    )
                }

                IconButton(
                    onClick = {} // User delete completely
                ){
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = 160.dp - 100.dp / 2 - 20.dp)
        )
        {
            val currentUser = user
            //display the user selected picture instantly
            if (selectedProfileBitmap != null) {
                Image(
                    bitmap = selectedProfileBitmap!!,
                    contentDescription = "Profile picture",
                    modifier = Modifier
                        .size(190.dp)
                        .clip(CircleShape)
                        .clickable(
                            onClick = {}
                        ),
                    contentScale = ContentScale.Crop
                )
            }

            // previously saved photo, loaded from disk via its saved path
            else if(currentUser?.userPhotoUser != null){
                AsyncImage(
                    model = imageStorage.getFullPath(currentUser.userPhotoUser),
                    contentDescription = "Profile picture",
                    modifier = Modifier
                        .size(190.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
            else {
                // no photo at all, default photo
                Image(
                    painter = painterResource(Res.drawable.profile_icon),
                    contentDescription = "Default Profile picture",
                    modifier = Modifier
                        .size(190.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            IconButton(
                onClick = {
                    showProfilePhotoOptions = true
                    //image.launch()
                },
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Photo,
                    contentDescription = "Change profile picture",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
        }

        if(showProfilePhotoOptions){
            AlertDialog(
                onDismissRequest = {
                    showProfilePhotoOptions = false
                },
                containerColor = Color(0xFF1A1A1A),
                title = {
                    Text(
                        text = "Profile Picture",
                        color = Color(0xFFF0396B)
                    )
                },
                text = {
                    Column (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    )
                    {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(30.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFF1A1A1A)
                            ),
                            border = BorderStroke(1.dp, Color(0xFFF0396B)),
                            onClick = {
                                showProfilePhotoOptions = false
                                image.launch()
                            }


                        ){
                            Row (
                                modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    modifier = Modifier.padding(8.dp),
                                    imageVector = Icons.Default.Upload,
                                    contentDescription = "Upload",
                                    tint = Color.White
                                )
                                Text(text = "Upload Photo",
                                    fontSize = 13.sp,
                                    color = Color.White
                                )
                            }
                        }

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(30.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFF1A1A1A)
                            ),
                            border = BorderStroke(1.dp, Color(0xFFF0396B)),
                            onClick = {
                                showProfilePhotoOptions = false
                                pendingCamera = true
                            }
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ){
                                Icon(
                                    modifier = Modifier.padding(8.dp),
                                    imageVector = Icons.Default.PhotoCamera,
                                    contentDescription = "Camera",
                                    tint = Color.White
                                )
                                Text(
                                    text = "Take Photo",
                                    fontSize = 13.sp,
                                    color = Color.White
                                )
                            }
                        }

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(30.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFF1A1A1A)
                            ),
                            border = BorderStroke(1.dp, Color(0xFFF0396B)),
                            onClick = {
                                selectedImageBytes = null
                                selectedProfileBitmap = null

                                user?.let {currentUser ->
                                    val updatedPhoto = currentUser.copy(userPhotoUser = null )
                                    userViewModel.updateProfileEditVM((updatedPhoto)){
                                        showProfilePhotoOptions = false
                                    }
                                }

                            }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ){
                                Icon(
                                    modifier = Modifier.padding(8.dp),
                                imageVector = Icons.Default.NoPhotography, /////Should change the icon
                                contentDescription = "Default Photo",
                                tint = Color.White
                                )
                                Text(
                                    text = "No Photo",
                                    fontSize = 13.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showProfilePhotoOptions = false
                        }
                    ){
                        Text(
                            text = "Cancel",
                            color = Color(0xFFFF9800)
                        )
                    }
                }

            )
        }

        /*
        {
            //display the user selected picture instantly
            if (selectedProfileBitmap != null) {
                Image(
                    bitmap = selectedProfileBitmap!!,
                    contentDescription = "Profile picture",
                    modifier = Modifier
                        .size(190.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
            // previously saved photo, loaded from disk via its saved path
            else if(user?.userPhotoUser != null){
                AsyncImage(
                    model = user!!.userPhotoUser,
                    contentDescription = "Profile picture",
                    modifier = Modifier
                        .size(190.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
            else {
                // no photo at all, default photo
                Image(
                    painter = painterResource(Res.drawable.profile_icon),
                    contentDescription = "Default Profile picture",
                    modifier = Modifier
                        .size(190.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            IconButton(
                onClick = {
                    image.launch()
                },
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Photo,
                    contentDescription = "Change profile picture",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
        */
    }

    if(showCamera){
        Column(modifier = Modifier.fillMaxSize().background(Color.Black)) {
            Text(
                "Cancel",
                color = Color(0xFFF0396B),
                modifier = Modifier
                    .padding(start = 20.dp, top = 50.dp)
                    .clickable { showCamera = false }
            )
            CameraCapture(
                onImageCaptured = { bytes ->
                    selectedImageBytes = bytes
                    selectedProfileBitmap = bytes.decodeToImageBitmap()

                    val fileName = "profile_${userIdSP}_${kotlin.time.Clock.System.now().toEpochMilliseconds()}.jpg"
                    val imagePath = imageStorage.saveImageToFile(bytes, fileName)

                    user?.let{
                            currentUser ->
                        val updatedCameraImage = currentUser.copy(userPhotoUser = imagePath)
                        userViewModel.updateProfileEditVM(updatedCameraImage){
                        }
                    }
                    showCamera = false
                },
                onDismiss = {
                    showCamera = false
                }
            )
        }

    }

    if (showCalendar) {
        Calender(
            onDateSelected = { date ->
                val newDobString = formatDate(date)// new date

                user?.let{
                    if (newDobString.isNotEmpty()){
                        dobSP = newDobString
                        val updatedUserDob = it.copy(dateOfBirthUser = newDobString)
                        userViewModel.updateProfileEditVM(updatedUserDob){}

                        /*
                same meaning as above code
                dobSP = if (user != null) {
                    user.dateOfBirthUser
                } else {
                    dobSP // no change, keep the current value
                }*/
                    }
                    else
                        dobSP = it.dateOfBirthUser//old date
                }

            },
            onDismiss = { showCalendar = false }
        )
    }

    // you can write calendar same as username the only difference is

    //which comes first, if or the .let{}?

    //1.Username: user?.let { if (...) {...} else {...} } → .let{} is the outer wrapper,
    // if/else is inside it → it available everywhere.

    //2.DOB (buggy): if (...) { user?.let {...} } else {...} → if/else is the outer structure,
    // .let{} is only inside if branch → it only available in that one branch, not in else.


    if (updateButton) {
        AlertDialog(
            onDismissRequest = {
                updateButton = false
            },
            containerColor = Color(0xFF1A1A1A),
            title = {
                Text(
                    text = "Edit Username",
                    color = Color(0xFFF0396B)
                )
            },
            text = {
                TextField(
                    value = usernameSP,
                    onValueChange = {
                        if (usernameSP.length <= 16) {usernameSP = it}
                    },
                    textStyle = TextStyle(fontSize = 25.sp),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.DarkGray,
                        unfocusedIndicatorColor = Color.DarkGray,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        user?.let{
                            val updatedUsername = it.copy(usernameUser = usernameSP)

                            if (usernameSP.isNotEmpty())
                            {
                                userViewModel.updateProfileEditVM(updatedUsername)
                                {
                                    updateButton = false
                                }
                            }
                                else
                                    usernameSP = it.usernameUser//reset to original if left empty

                        }
                    }
                ){
                    Text("Save", color = Color(0xFFFF9800))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        updateButton = false
                    }
                ){
                    Text("Cancel", color = Color(0xFFF0396B))
                }
            }

        )
    }
}

/*
@Preview
@Composable
fun SettingsPagePreview() {
    SettingsPage()
}*/