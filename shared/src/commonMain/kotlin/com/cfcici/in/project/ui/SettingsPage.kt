package com.cfcici.`in`.project.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import com.attafitamim.krop.core.crop.ImageCropper
import com.attafitamim.krop.core.crop.rememberImageCropper
import com.attafitamim.krop.ui.ImageCropperDialog
import com.attafitamim.krop.core.crop.CropResult
import com.attafitamim.krop.core.crop.CropError
import com.attafitamim.krop.core.images.ImageBitmapSrc
import com.attafitamim.krop.core.crop.cropperStyle
import com.attafitamim.krop.core.crop.CircleCropShape
import com.attafitamim.krop.core.crop.AspectRatio
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.decodeToImageBitmap
import androidx.compose.foundation.Canvas
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.cfcici.`in`.project.CameraCapture
import com.cfcici.`in`.project.encodeToByteArray
import com.cfcici.`in`.project.ImageStorage
import com.cfcici.`in`.project.viewmodel.UserViewModel
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import kotlin.let



data class  AvatarStyle(val name: String, val label: String)

val avatarStyles = listOf(
    AvatarStyle("avataaars", "Avataaars"),
    AvatarStyle("bottts", "Bottts"),
    AvatarStyle("pixel-art", "Pixel Art"),
    AvatarStyle("adventurer", "Adventurer"),
    AvatarStyle("fun-emoji", "Fun Emoji")
)

fun avatarUrl(style: String, seed: String): String = "https://api.dicebear.com/9.x/$style/png?seed=$seed"

fun cropCircularBitmap(
    source: ImageBitmap,
    center: Offset,   // in SOURCE image pixel coords
    radius: Float,    // in SOURCE image pixel coords
    outputSize: Int = 512
): ImageBitmap
{
    val output = ImageBitmap(outputSize, outputSize, hasAlpha = true)
    val canvas = Canvas(output)
    val drawScope = CanvasDrawScope()
    drawScope.draw(
        density = Density(1f),
        layoutDirection = LayoutDirection.Ltr,
        canvas = canvas,
        size = Size(outputSize.toFloat(), outputSize.toFloat())
    ) {
        clipPath(Path().apply {
            addOval(Rect(Offset.Zero, Size(outputSize.toFloat(), outputSize.toFloat())))
        }) {
            drawImage(
                image = source,
                srcOffset = IntOffset(
                    (center.x - radius).toInt().coerceIn(0, source.width),
                    (center.y - radius).toInt().coerceIn(0, source.height)
                ),
                srcSize = IntSize(
                    (radius * 2).toInt().coerceAtMost(source.width),
                    (radius * 2).toInt().coerceAtMost(source.height)
                ),
                dstOffset = IntOffset.Zero,
                dstSize = IntSize(outputSize, outputSize)
            )
        }
    }
    return output
}

@Composable
fun SettingsPage(userIdSP: Int, goBackToProfilePage:(String, Int) -> Unit, userViewModel: UserViewModel, imageStorage: ImageStorage,  onBackToLogin: () -> Unit)

//Reason why we use collectAsState
//Subscribes to the Flow — it starts collecting values from userViewModel.getUser(userIdSP) the moment your composable enters composition.
//Wraps each emitted value in Compose State —
//           every time the Flow emits a new value (because Room detected the row changed),
//           collectAsState() updates a State<User?> object behind the scenes, which automatically triggers recomposition of anything reading it.
{
    var showCropScreen by remember { mutableStateOf(false) }
    var imageToCrop by remember { mutableStateOf<ImageBitmap?>(null) }
    var containerSize by remember { mutableStateOf(IntSize.Zero) }

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

    val haptics = LocalHapticFeedback.current
    var showFullImage by remember { mutableStateOf(false) }
    /*val imageLongPressAnimation by animateDpAsState(
        targetValue = if (showFullImage) 300.dp else 190.dp,
        animationSpec = tween(durationMillis = 250, easing = LinearEasing),
        label = "Profile Image Size"
    )
    val scale = if (showFullImage) ContentScale.Fit else ContentScale.Crop
    */

    var zoom by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val state = rememberTransformableState { zoomChange, offsetChange , _ ->
        //zoom = (zoom * zoomChange).coerceIn(1f, 5f) // clamp so it can't shrink below original or zoom absurdly far
        //offset += offsetChange
        val newZoom = (zoom * zoomChange).coerceIn(1f, 5f)
        // how far the image is allowed to pan, scales with zoom level
        val maxOffsetX = (newZoom - 1f) * 300f// tune 300f based on image size
        val maxOffsetY = (newZoom - 1f) * 300f

        zoom = newZoom
        offset = if (zoom <= 1f){
            Offset.Zero // locked in place when not zoomed in
        }else{
            Offset(
                x = (offset.x + offsetChange.x).coerceIn(-maxOffsetX, maxOffsetX),
                y = (offset.y + offsetChange.y).coerceIn(-maxOffsetY, maxOffsetY)
                //the coerceIn(-maxOffsetX, maxOffsetX) stops the user from dragging the image so far that it disappears
            // off-screen — the further zoomed in they are, the more room they get to pan
            // (since maxOffsetX scales with (newZoom - 1f)).
            )
        }
    }

    val scope = rememberCoroutineScope()
    val imageCropper = rememberImageCropper()
    val cropState = imageCropper.cropState

    if (cropState != null) {
        ImageCropperDialog(
            state = cropState,
            style = cropperStyle(
                shapes = listOf(CircleCropShape),
                aspects = listOf(AspectRatio(1, 1)),
                guidelines = null
            )
        )
    }
    var selectedProfileBitmap by remember { mutableStateOf<ImageBitmap?>(null) }// turns byte to pixeled pics like it shows on UI and this is a local storage not saved on db
    var selectedImageBytes by remember { mutableStateOf<ByteArray?>(null) } // turns to byte
    val image = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = scope,
        onResult = { byteArrays ->
            byteArrays.firstOrNull()?.let { bytes ->

                val bitmap = bytes.decodeToImageBitmap()

                scope.launch {
                    when (val result = imageCropper.crop { ImageBitmapSrc(bitmap) }) {

                        CropResult.Cancelled -> {
                            // User cancelled cropping
                        }

                        is CropError -> {
                            println("Krop crop error: $result")
                        }

                        is CropResult.Success -> {
                            val croppedBitmap = result.bitmap

                            // Convert cropped bitmap back to bytes
                            val croppedBytes = croppedBitmap.encodeToByteArray()

                            selectedImageBytes = croppedBytes
                            selectedProfileBitmap = croppedBitmap

                            val fileName =
                                "profile_${userIdSP}_${kotlin.time.Clock.System.now().toEpochMilliseconds()}.jpg"

                            val imagePath =
                                imageStorage.saveImageToFile(
                                    croppedBytes,
                                    fileName
                                )

                            user?.let { currentUser ->

                                val updatedUser =
                                    currentUser.copy(
                                        userPhotoUser = imagePath
                                    )

                                userViewModel.updateProfileEditVM(updatedUser) {
                                    println("Cropped profile photo saved")
                                }
                                /*
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
                    */
                            }
                        }
                    }
                }
            }
        }
    )

    var deleteUserPermanently by remember  {mutableStateOf<Int?>(null)}

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
    )
    {
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
                    onClick = {deleteUserPermanently = userIdSP} // User delete completely
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
                //.combinedClickable(
                    //onClick = {}, // it is not necessary, it only because onLongClick won't stay along without onClick
                    //onLongClick = {
                        //haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                        //showFullImage = true
                    //}
                //)
        )
        {
            val currentUser = user

            Box(
                modifier = Modifier
                    .pointerInput(selectedProfileBitmap, currentUser?.userPhotoUser){
                        detectTapGestures (
                            onLongPress = {
                                haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                                showFullImage = true
                            }
                        )
                    }
            )
            {
                //display the user selected picture instantly
                //3
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
                //1
                else if(currentUser?.userPhotoUser != null){
                    val photoValue = currentUser.userPhotoUser
                    val model = if (photoValue.startsWith("http")){
                        photoValue // it's a remote DiceBear URL, use as-is
                    }else{
                        imageStorage.getFullPath(photoValue) // it's a local file, resolve full path
                    }
                    AsyncImage(
                        //model = imageStorage.getFullPath(currentUser.userPhotoUser),
                        model = model,
                        contentDescription = "Profile picture",
                        modifier = Modifier
                            .size(190.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
                else {
                    // no photo at all, default photo
                    //2
                    Image(
                        painter = painterResource(Res.drawable.profile_icon),
                        contentDescription = "Default Profile picture",
                        modifier = Modifier
                            .size(190.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
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

        if(deleteUserPermanently != null){
            AlertDialog(
                //deleteUserPermanently = null make Alert Dialog disappear
                onDismissRequest = {deleteUserPermanently = null},
                containerColor = Color(0xFF1A1A1A),
                title = {
                    Text("Delete the account", color = Color(0xFFFF9800))
                },
                text = {
                    Text("Are you sure you want to delete the account permanently?", color = Color.LightGray)
                },
                confirmButton = {
                    TextButton(
                        onClick = {deleteUserPermanently = null}
                    ){
                        Text("Cancel", color = Color(0xFFFF9800))
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        user?.let{ currentUser ->
                            userViewModel.deleteUserVM(currentUser){}
                        }
                        onBackToLogin()
                    }){
                        Text("Delete", color = Color(0xFFF0396B))
                    }
                }
            )
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
                        Avatars (
                            onAvatarSelected = {url ->
                                showProfilePhotoOptions = false
                                // had a problem with showing instantly update of profile pic, but when I added these two lines, its gone
                                // Reason -> we're writing selectedProfileBitmap  as null if it wasn't null it checks the "display the user selected picture instantly" which written like != null
                                //so that make avatars don't display instant
                                selectedProfileBitmap = null
                                selectedImageBytes = null
                                user?.let {
                                    currentUser ->
                                    val updatedAvatar = currentUser.copy(userPhotoUser = url)
                                    userViewModel.updateProfileEditVM(updatedAvatar){}
                                }
                            }
                        )
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
                        )
                        {
                            Row (
                                modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            )
                            {
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

    if (showFullImage){
        val currentUser = user

        LaunchedEffect(showFullImage){
            if(showFullImage){
                zoom = 1f
                offset = Offset.Zero
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.85f))
                .clickable(
                    indication = null,//"don't draw any ripple effect"
                    interactionSource = remember { MutableInteractionSource() }// "here's an empty tracker, since I still have to supply one"
                ){
                    showFullImage = false
                },
            contentAlignment = Alignment.Center,
        )
        {
            //display the user selected picture instantly
            //3
            //// Priority: 1) selectedProfileBitmap = instant local preview for
            //// upload/camera only (bytes, not yet in DB). 2) userPhotoUser = real
            //// source of truth from DB (local path or DiceBear URL). 3) default icon.
            //// selectedProfileBitmap must be manually reset to null whenever a
            //// different photo source is chosen (e.g. avatar), or it'll permanently
            //// shadow branch 2 since if/else-if stops at the first true match.
            if (selectedProfileBitmap != null) {
                Image(
                    bitmap = selectedProfileBitmap!!,//????
                    contentDescription = "Profile picture",
                    modifier = Modifier
                        .padding(10.dp)
                        .size(600.dp)
                        .transformable(state = state)
                        .graphicsLayer(
                            scaleX = zoom,
                            scaleY = zoom,
                            translationX = offset.x,
                            translationY = offset.y
                            ),
                    contentScale = ContentScale.Fit
                )
            }

            // previously saved photo, loaded from disk via its saved path
            //1
            else if(currentUser?.userPhotoUser != null){
                val photoValue = currentUser.userPhotoUser
                val model = if (photoValue.startsWith("http")) {
                    photoValue
                } else {
                    imageStorage.getFullPath(photoValue)
                }
                AsyncImage(
                    model = model,
                    contentDescription = "Profile picture",
                    modifier = Modifier
                        .padding(10.dp)
                        .size(600.dp)
                        .transformable(state = state)
                        .graphicsLayer(
                            scaleX = zoom,
                            scaleY = zoom,
                            translationX = offset.x,
                            translationY = offset.y
                        ),
                    contentScale = ContentScale.Fit
                )
            }
            else {
                // no photo at all, default photo
                //2
                Image(
                    painter = painterResource(Res.drawable.profile_icon),
                    contentDescription = "Default Profile picture",
                    modifier = Modifier
                        .size(300.dp)
                        .clip(CircleShape)
                        .transformable(state = state)
                        //.graphicsLayer(
                            //scaleX = zoom,
                            //scaleY = zoom,
                            //translationX = offset.x,
                            //translationY = offset.y
                       // )
                        ,
                    contentScale = ContentScale.Crop
                )
            }
        }
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
                    val bitmap = bytes.decodeToImageBitmap()
                    scope.launch {
                        when (val result = imageCropper.crop { ImageBitmapSrc(bitmap) }) {
                            CropResult.Cancelled -> {}
                            is CropError -> {
                                println("Krop crop error: $result")
                            }
                            is CropResult.Success -> {
                                val croppedBitmap = result.bitmap
                                val croppedBytes = croppedBitmap.encodeToByteArray()

                                selectedImageBytes = croppedBytes
                                selectedProfileBitmap = croppedBitmap

                                val fileName = "profile_${userIdSP}_${kotlin.time.Clock.System.now().toEpochMilliseconds()}.jpg"
                                val imagePath = imageStorage.saveImageToFile(croppedBytes, fileName)

                                user?.let { currentUser ->
                                    val updatedCameraImage = currentUser.copy(userPhotoUser = imagePath)
                                    userViewModel.updateProfileEditVM(updatedCameraImage) {}
                                }
                            }
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

///*************************************************************************************************
@Composable
fun Avatars(
    onAvatarSelected: (String) -> Unit
){
    LazyColumn(
        modifier = Modifier.height(280.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        items(avatarStyles){ style ->
            Column {
                Text(
                    text = style.label,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 6.dp)
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ){
                    items((1..8).toList()){seedNum ->
                        val url = avatarUrl(style.name, "${style.name}-$seedNum")
                        AsyncImage(
                            model = url,
                            contentDescription = "${style.label} avatar $seedNum",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(70.dp)
                                .clip(CircleShape)
                                .background(Color.DarkGray)
                                .clickable{onAvatarSelected(url)}
                        )
                    }
                }
            }
        }
    }
}


/*
@Preview
@Composable
fun SettingsPagePreview() {
    SettingsPage()
}*/