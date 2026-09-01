package com.cfcici.`in`.project.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import login.shared.generated.resources.Amarante_Regular
import login.shared.generated.resources.Res
import login.shared.generated.resources.HotWheels
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.decodeToImageBitmap
import com.cfcici.`in`.project.ImageStorage
import com.cfcici.`in`.project.data.database.UserCar
import com.cfcici.`in`.project.viewmodel.UserViewModel
import coil3.compose.AsyncImage
import com.cfcici.`in`.project.CameraCapture
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import login.shared.generated.resources.AutoWorldLogo
import login.shared.generated.resources.BuragoLogo
import login.shared.generated.resources.GreenLight
import login.shared.generated.resources.JohnnyLightning
import login.shared.generated.resources.KaidoHouse
import login.shared.generated.resources.Logo_jada_toys
import login.shared.generated.resources.M2M
import login.shared.generated.resources.Majorette
import login.shared.generated.resources.MiniGT
import login.shared.generated.resources.PopRace
import login.shared.generated.resources.Tarmac
import login.shared.generated.resources.Tomica
import login.shared.generated.resources.black
import login.shared.generated.resources.inno64
import login.shared.generated.resources.maisto_logo_640x320
import login.shared.generated.resources.matchbox2

enum class SortOrder{
    NEWEST_FIRST, OLDEST_FIRST////???????
}
@OptIn(ExperimentalMaterial3Api::class)///****************
@Composable
fun UserCarCollectionPage( userCCPBrand: String, userCCPUserId: Int, goBackToProfile: () -> Unit, userViewModel: UserViewModel, imageStorage: ImageStorage){

    val usernameFont = FontFamily(Font(Res.font.Amarante_Regular))
    var showAddCarDialog by remember { mutableStateOf(false) }

    //From all my cars, give me only the cars that belong to the brand the user clicked.
    var searchText by remember { mutableStateOf("") }
    var getCarsFromThisBrand by remember { mutableStateOf<List<UserCar>>(emptyList()) }
    val totalCarThisBrandOwns = getCarsFromThisBrand.size

    var expand by remember { mutableStateOf(false) }


    val brandBackground = mapOf(
        "HotWheels" to Res.drawable.HotWheels,
        "MatchBox" to Res.drawable.matchbox2,
        "Tomica" to Res.drawable.Tomica,
        "Kaido House" to Res.drawable.KaidoHouse,
        "Tarmac Works" to Res.drawable.Tarmac,
        "Pop Race" to Res.drawable.PopRace,
        "Inno 64" to Res.drawable.inno64,
        "Auto World" to Res.drawable.AutoWorldLogo,
        "GreenLight" to Res.drawable.GreenLight,
        "Johnny Lightning" to Res.drawable.JohnnyLightning,
        "Majorette" to Res.drawable.Majorette,
        "M2 Machines" to Res.drawable.M2M,
        "Jada Toys" to Res.drawable.Logo_jada_toys,
        "Maisto" to Res.drawable.maisto_logo_640x320,
        "Solido" to Res.drawable.black,
        "MINI GT" to Res.drawable.MiniGT,
        "Bburago" to Res.drawable.BuragoLogo
    )
    var sortOrder by remember { mutableStateOf<SortOrder?>(null) }

    val logo = brandBackground[userCCPBrand] ?: Res.drawable.HotWheels // fallback drawable
    val filteringForCarSearch = getCarsFromThisBrand.filter { car -> searchText.isBlank()||
            car.modelUser.contains(searchText, ignoreCase = true) ||
            car.yearUser?.toString()?.contains(searchText, ignoreCase = true) == true || // because it's expect result boolean should do == true
            car.seriesUser?.contains(searchText, ignoreCase = true)  == true ||
            car.colourUser.contains(searchText, ignoreCase = true) ||
            car.typeOfSeriesUser?.contains(searchText, ignoreCase = true) == true ||
            car.collectorNoUser?.contains(searchText, ignoreCase = true) == true
    }
        .let { list ->
            when (sortOrder) {
                SortOrder.NEWEST_FIRST -> list.sortedByDescending { it.yearUser ?: 0 }
                SortOrder.OLDEST_FIRST -> list.sortedBy { it.yearUser ?: 0 }
                null -> list
            }
        }

    var contextMenuCarId by remember { mutableStateOf<Int?>(null) } // you need UserId
    var carBeingEdit by remember { mutableStateOf<UserCar?>(null) }
    var carBeingViewed by remember { mutableStateOf<UserCar?>(null) }

    fun displayCarFromThisBrand(){
        userViewModel.getUserOwnedCarsByBrandVM(userCCPUserId, userCCPBrand){
                cars -> getCarsFromThisBrand = cars
        }
    }

    LaunchedEffect(userCCPBrand) {
        displayCarFromThisBrand()
    }

    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black)
    ) {

        // 1. MAIN CONTENT (Only visible when NOT adding a car)
        if (!showAddCarDialog) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 60.dp) // reserve space so last car isn't hidden behind fixed bar
            ) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().height(200.dp)) {
                        Image(
                            painter = painterResource(logo),
                            contentDescription = "null",
                            modifier = Modifier.fillMaxSize(),
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.padding(top = 10.dp))
                    Text(
                        text = userCCPBrand,
                        color = Color.White,
                        fontFamily = usernameFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 35.sp,
                        modifier = Modifier.padding(horizontal = 10.dp),
                    )
                    Text(
                        text = "Total Owns $totalCarThisBrandOwns",
                        color = Color(0xFFF0396B),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        fontFamily = usernameFont,
                        modifier = Modifier.padding(horizontal = 15.dp),
                        textAlign = TextAlign.Center
                    )
                }

                stickyHeader {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Black)
                            .padding(bottom = 8.dp, top = 10.dp)
                    )
                    {
                        OutlinedTextField(
                            value = searchText,
                            onValueChange = { searchText = it },// this trigger very keystroke
                            placeholder = { Text("Search cars here...", color = Color.LightGray) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search button",
                                    tint = Color.White
                                )
                            },
                            trailingIcon = {
                                Box{
                                    IconButton(onClick = {expand = !expand}){
                                        Icon(
                                            imageVector = Icons.Default.Tune,
                                            contentDescription = "Filter",
                                            tint = Color.White
                                        )
                                    }
                                    DropdownMenu(
                                        expanded = expand,
                                        onDismissRequest = {expand = false},
                                        modifier = Modifier.background(Color(0xFF1A1A1A)),
                                        shape = RoundedCornerShape(16.dp)
                                    ){
                                        DropdownMenuItem(
                                            text = {
                                                Text("Newest to Oldest", color = Color.White)
                                            },
                                            onClick = {sortOrder = SortOrder.NEWEST_FIRST; expand = false}
                                        )
                                        DropdownMenuItem(
                                            text = {
                                                Text("Oldest to Newest ", color = Color.White)
                                            },
                                            onClick = {sortOrder = SortOrder.OLDEST_FIRST; expand = false}
                                        )
                                    }
                                }
                            },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Color.Black,
                                unfocusedContainerColor = Color.Black,
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color.Black,
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                cursorColor = Color.White
                            )
                        )
                    }
                }

                item {
                    if (filteringForCarSearch.isEmpty() && (totalCarThisBrandOwns > 0)) {
                        Text(
                            text = "Not Found",
                            color = Color.LightGray,
                            fontFamily = usernameFont,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth().padding(vertical = 50.dp)
                        )
                    } else {
                        filteringForCarSearch.forEach { car ->
                            EachCarTab(selectedCar = car, imageStorage = imageStorage, onLongPressCar = { contextMenuCarId = it.userCarIdUser }, onShortClick = { carBeingViewed = it })
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(Color.Black)
                    .navigationBarsPadding()
                    //.padding(vertical = 5.dp)
                ,
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                IconButton(
                    modifier = Modifier.padding(start = 50.dp),
                    onClick = { goBackToProfile() }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "go back to profile page",
                        tint = Color.White
                    )
                }
                IconButton(
                    modifier = Modifier.padding(end = 50.dp),
                    onClick = {
                        carBeingEdit = null
                        showAddCarDialog = true }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }

        if (showAddCarDialog) {
            AddNewCar(
                userCCPBrand = userCCPBrand,
                userCCPUserId = userCCPUserId,
                userViewModel = userViewModel,
                imageStorage = imageStorage,
                existingCar = carBeingEdit,
                onDismissRequest = {
                    showAddCarDialog = false
                    carBeingEdit = null
                },
                onConfirmation = {
                    showAddCarDialog = false
                    carBeingEdit = null
                    displayCarFromThisBrand()
                }
            )
        }
    }

    if (carBeingViewed != null) {
        //!! is Kotlin's "not-null assertion" — it tells the compiler "trust me, this is not null right now, treat it as a plain UserCar."
        // It'll throw a crash if you're wrong, but here you're safe because this whole block only runs inside if (carBeingViewed != null),
        // so you already know it can't be null at this point.
        val selectedCarForMenu = carBeingViewed!!
        AlertDialog(
            onDismissRequest = {carBeingViewed = null},
            containerColor = Color(0xFF1A1A1A),
            title = {
                Text(selectedCarForMenu.modelUser, color = Color(0xFFF0396B), fontSize = 30.sp)
            },
            text = {
                Column {
                    if (selectedCarForMenu.carPhotoUser.isNotEmpty()) {
                        AsyncImage(
                            model = imageStorage.getFullPath(fileName = selectedCarForMenu.carPhotoUser),
                            contentDescription = selectedCarForMenu.modelUser,
                            modifier = Modifier
                                .size(400.dp)
                                .clip(RectangleShape),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    carBeingViewed = null
                }){
                    Text("Close", color = Color(0xFFFF9800))
                }
            }
        )
    }

    if (contextMenuCarId != null) {
        val selectedCarForMenu = getCarsFromThisBrand.first { it.userCarIdUser == contextMenuCarId }

        AlertDialog(
            onDismissRequest = { contextMenuCarId = null },
            containerColor = Color(0xFF1A1A1A),
            title = {
                Text(selectedCarForMenu.modelUser, color = Color.White)
            },
            text = {
                Text("What would you like to do with this car?", color = Color.LightGray)
            },
            confirmButton = {
                TextButton(onClick = {
                    carBeingEdit = selectedCarForMenu
                    contextMenuCarId = null
                    showAddCarDialog = true
                }) {
                    Text("Edit", color = Color(0xFFFF9800))
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    userViewModel.deleteUserOwnedCarVM(selectedCarForMenu) {
                        displayCarFromThisBrand()// refresh it
                    }
                    contextMenuCarId = null
                }) {
                    Text("Delete", color = Color(0xFFF0396B))
                }
            }
        )
    }
}

@Composable
//if you are using : UserCar you are passing all the parameter in the UserCar instead of only one datatype
fun EachCarTab(selectedCar: UserCar, imageStorage: ImageStorage, onLongPressCar: (UserCar) -> Unit, onShortClick: (UserCar) -> Unit)
{
    val usernameFont = FontFamily(Font(Res.font.Amarante_Regular))
    val haptics = LocalHapticFeedback.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(100.dp)
            .combinedClickable(
                onClick = {onShortClick(selectedCar)},// show large picture
                onLongClick = {
                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                    onLongPressCar(selectedCar)
                }
            ),
        border = BorderStroke(width = 1.dp, color = Color(0xFFF0396B)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Black,
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {

        Row( modifier = Modifier.padding(5.dp))
        {
            Column(
                modifier = Modifier
                    .padding(vertical = 5.dp, horizontal = 25.dp)

            )
            {
                if (selectedCar.carPhotoUser.isNotEmpty()) {
                    AsyncImage(
                        model = imageStorage.getFullPath(fileName = selectedCar.carPhotoUser),
                        contentDescription = selectedCar.modelUser,
                        modifier = Modifier
                            .size(90.dp)
                            .clip(RectangleShape),
                        contentScale = ContentScale.Crop
                    )
                } /*
                else {
                    Image(
                        painter = painterResource(Res.drawable.nissan_skyline),
                        contentDescription = selectedCar.modelUser,
                        modifier = Modifier
                            .size(90.dp)
                            .clip(RectangleShape),
                        contentScale = ContentScale.Crop
                    )
                }*/
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
                // even gap between every child no matter how many rows you have it will automatically space it equally
            ) {
                Text(
                    text = selectedCar.modelUser,
                    color = Color.White,
                    fontFamily = usernameFont,
                    fontSize = 20.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Clip
                    // Ellipsis -> put ... at the end if word overflows
                    // Clip -> cut at the point where it only fit
                    // Visible -> overlap other element to show full word
                )
                Row {
                    // Because Text expect string you should convert to string
                    Text(
                        text = selectedCar.yearUser?.toString() ?: "",
                        color = Color.White,
                        fontFamily = usernameFont,
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.width(30.dp))
                    Text(
                        text = selectedCar.colourUser,
                        color = Color.White,
                        fontFamily = usernameFont,
                        fontSize = 15.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Clip
                    )
                }
                Row {
                    // Because Text expect string you should convert to string
                    Text(
                        text = selectedCar.collectorNoUser.toString(),
                        color = Color.White,
                        fontFamily = usernameFont,
                        fontSize = 15.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Clip
                    )
                    Spacer(modifier = Modifier.width(30.dp))
                    Text(
                        text = selectedCar.seriesUser.toString(),
                        color = Color.White,
                        fontFamily = usernameFont,
                        fontSize = 15.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Clip
                    )
                    Spacer(Modifier.width(30.dp))

                    Text(
                        text = selectedCar.typeOfSeriesUser.toString(),
                        color = Color.White,
                        fontFamily = usernameFont,
                        fontSize = 15.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Clip
                    )
                }
            }
        }
    }
}

@Composable
fun AddNewCar(userCCPBrand: String, userCCPUserId: Int, userViewModel: UserViewModel, imageStorage: ImageStorage,
              onDismissRequest: () -> Unit, onConfirmation: ()-> Unit, existingCar: UserCar? = null) // ? = -> we use AddNewCar two ways 1. add new car (empty form) 2. selected car (filled form)
{
    var oldPhotoDeleted by remember { mutableStateOf(false) }
    var showingExistingPhoto by remember { mutableStateOf(false) }
   // New car -> existingCar is null -> ""
    // Edit -> existingCar has a car -> model name appears automatically
    val modelName = rememberTextFieldState(
        initialText = existingCar?.modelUser?: ""
    )
    val modelColour = rememberTextFieldState(
        initialText = existingCar?.colourUser?: ""
    )
    val seriesOfModel = rememberTextFieldState(
        initialText = existingCar?.seriesUser?: ""
    )
    val typeOfSeries = rememberTextFieldState(
        initialText = existingCar?.typeOfSeriesUser?: ""
    )
    val modelCollectionNumber = rememberTextFieldState(
        initialText = existingCar?.collectorNoUser?: ""
    )
    var selectedYear by remember { mutableStateOf(existingCar?.yearUser?.toString()?: "") }// initially drop down will close
    var selectedImageBytes by remember { mutableStateOf<ByteArray?>(null) }// ByteArray lowest-level way to represent any file's content the photos, music, and video in byte
    var showCamera by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val launcher = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = scope,
        onResult = { byteArrays ->
            byteArrays.firstOrNull()?.let {
                selectedImageBytes = it
            }
        }
    )

    var modelNameError by remember { mutableStateOf<String?>(null) }
    var modelPhotoError by remember { mutableStateOf<String?>(null) }
    var modelColourError by remember { mutableStateOf<String?>(null) }

    var showImagePreview by remember { mutableStateOf(false) }
    var selectedImageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            //.padding(16.dp)
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        // Header Row with a Close button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick =
                {
                    if (showCamera)
                        showCamera = false
                    else onDismissRequest()
                }
            )
            {
                Text(if (showCamera)
                    "Cancel"
                else "",
                    color = Color(0xFFF0396B),
                    modifier = Modifier.padding(start = 20.dp, top = 50.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (showCamera) {
            Box(
                modifier = Modifier.fillMaxWidth()) { ///?????
                CameraCapture(
                    onImageCaptured = { bytes ->
                        selectedImageBytes = bytes
                        showCamera = false
                    },
                    onDismiss = { showCamera = false }
                )
            }
            return@Column
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Text(
                text = "Model Name:",
                modifier = Modifier.width(125.dp),
                color = Color.White
            )

            OutlinedTextField(
                state = modelName,
                modifier = Modifier.weight(1f)
                    .onFocusChanged{
                        if(it.isFocused)
                            modelNameError = null
                    },
                lineLimits = TextFieldLineLimits.SingleLine,
                textStyle = TextStyle(fontSize = 20.sp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFF0396B),
                    unfocusedBorderColor = Color(0xFFF0396B),
                    unfocusedTextColor = Color.White,
                    focusedTextColor = Color.White,
                    cursorColor = Color.White
                ),
                isError = modelNameError != null,
                supportingText = if (modelNameError != null) {
                    { Text("Name of the model is required") }
                } else null // removes the extra spacing
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Text(text = "Year: ", modifier = Modifier.width(125.dp), color = Color.White)
            YearDropDown(
                selectedYear = selectedYear,
                onYearSelected = { selectedYear = it }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Text(
                text = "Colour:",
                modifier = Modifier.width(125.dp),
                color = Color.White
            )

            OutlinedTextField(
                state = modelColour,
                modifier = Modifier.weight(1f)
                    .onFocusChanged{
                        if(it.isFocused)
                            modelColourError = null
                    },
                lineLimits = TextFieldLineLimits.SingleLine,
                textStyle = TextStyle(fontSize = 20.sp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFF0396B),
                    unfocusedBorderColor = Color(0xFFF0396B),
                    unfocusedTextColor = Color.White,
                    focusedTextColor = Color.White,
                    cursorColor = Color.White
                ),
                isError = modelColourError != null,
                supportingText = if (modelColourError != null) {
                    { Text("Name of the colour is required") }
                } else null
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Text(
                text = "Collection Number:",
                modifier = Modifier.width(125.dp),
                color = Color.White
            )

            OutlinedTextField(
                state = modelCollectionNumber,
                modifier = Modifier.weight(1f),
                lineLimits = TextFieldLineLimits.SingleLine,
                textStyle = TextStyle(fontSize = 20.sp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFF0396B),
                    unfocusedBorderColor = Color(0xFFF0396B),
                    unfocusedTextColor = Color.White,
                    focusedTextColor = Color.White,
                    cursorColor = Color.White
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Text(
                text = "Series Name:",
                modifier = Modifier.width(125.dp),
                color = Color.White
            )

            OutlinedTextField(
                state = seriesOfModel,
                modifier = Modifier.weight(1f),
                lineLimits = TextFieldLineLimits.SingleLine,
                textStyle = TextStyle(fontSize = 20.sp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFF0396B),
                    unfocusedBorderColor = Color(0xFFF0396B),
                    unfocusedTextColor = Color.White,
                    focusedTextColor = Color.White,
                    cursorColor = Color.White
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Text(
                text = "Type Of Series:",
                modifier = Modifier.width(125.dp),
                color = Color.White
            )

            OutlinedTextField(
                state = typeOfSeries,
                modifier = Modifier.weight(1f),
                lineLimits = TextFieldLineLimits.SingleLine,
                textStyle = TextStyle(fontSize = 20.sp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFF0396B),
                    unfocusedBorderColor = Color(0xFFF0396B),
                    unfocusedTextColor = Color.White,
                    focusedTextColor = Color.White,
                    cursorColor = Color.White
                )
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Row(
                modifier = Modifier.weight(1f),//  helps to make each card shared equally
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(30.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF1A1A1A)
                    ),
                    border = BorderStroke(1.dp, Color(0xFFF0396B)),
                    onClick = { launcher.launch() } // <- whole card triggers upload now
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ){
                        IconButton(onClick = { launcher.launch() }) { // Only icon triggers
                            Icon(imageVector = Icons.Default.Upload,
                                contentDescription = "Upload", tint = Color.White)
                        }
                        Text(text = "Upload Photo",
                            fontSize = 13.sp,
                            maxLines = 1,
                            color = Color.White)
                    }
                }
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(30.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF1A1A1A)
                    ),
                    border = BorderStroke(1.dp, Color(0xFFF0396B)),
                    onClick = { showCamera = true }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ){
                        IconButton(onClick = { showCamera = true }) {
                            Icon(
                                imageVector = Icons.Default.PhotoCamera,
                                contentDescription = "Camera",
                                tint = Color.White
                            )
                        }
                        Text(
                            text = "Take Photo",
                            fontSize = 13.sp,
                            maxLines = 1,
                            color = Color.White)
                    }
                }
            }
        }

        // shows the that image selected
        if (selectedImageBytes != null) {
            val bitmap = remember(selectedImageBytes) { selectedImageBytes!!.decodeToImageBitmap() }
            Box(
                modifier = Modifier.padding(top = 20.dp)
            ) {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Color(0xFFF0396B)),
                    colors = CardDefaults.cardColors(containerColor = Color.Black)
                ) {
                    Image(
                        bitmap = bitmap,
                        contentDescription = "Selected car photo",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { selectedImageBitmap = bitmap
                                showingExistingPhoto = false
                                showImagePreview = true},
                        contentScale = ContentScale.Crop
                    )
                }
                IconButton(
                    onClick = { selectedImageBytes = null },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 8.dp, y = (-8).dp)
                        .size(24.dp)
                        .background(Color(0xFFF0396B), shape = CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Remove photo",
                        tint = Color.White,
                        modifier = Modifier.size(14.dp))
                }
            }
        }else if (existingCar?.carPhotoUser?.isNotEmpty() == true && !oldPhotoDeleted){
            val bytes = imageStorage.loadImageFromFile(existingCar.carPhotoUser)

            Text(
                text = "Current Image",
                color = Color.White,
                fontSize = 15.sp,
                modifier = Modifier
                    .padding(top = 30.dp)
                    .clickable{
                        if(bytes != null){
                            selectedImageBitmap = bytes.decodeToImageBitmap()
                            showingExistingPhoto = true
                            showImagePreview = true
                        }
                    }
            )
        }

        else{
            // If editing and the old photo exists, don't show an error.
            if (modelPhotoError != null) {
                Text(text = "Photo of the model is required",
                    color = Color.Red,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(top = 30.dp))
            }
        }
        if (showImagePreview && selectedImageBitmap != null) {
            AlertDialog(
                onDismissRequest = {
                    showImagePreview = false},
                containerColor = Color(0xFF1A1A1A),
                confirmButton = {
                    Row{
                        TextButton(
                            onClick = {
                                if(showingExistingPhoto){
                                    oldPhotoDeleted = true
                                }else{
                                    selectedImageBytes = null
                                }
                                showImagePreview = false
                            }
                        ){
                            Text("Delete", color = Color(0xFFF0396B))
                        }
                        TextButton(
                            onClick = {
                                showImagePreview = false
                            }
                        ) {
                            Text("Close", color = Color(0xFFFF9800))
                        }
                    }
                },
                text = {
                    Image(
                        bitmap = selectedImageBitmap!!,
                        contentDescription = "Selected car photo",
                        modifier = Modifier.fillMaxWidth().size(400.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            )
        }
        Spacer(modifier = Modifier.padding(vertical = 20.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
                .background(Color.Black),
            horizontalArrangement = Arrangement.SpaceBetween
            ) {

            TextButton(
                onClick = {
                    onDismissRequest() }
            ) {
                Text(
                    text = "Close",
                    color = Color(0xFFF0396B)
                )
            }

            TextButton(
                onClick = {

                    // Check if the user has a photo.
                    // For a new car, they must select a photo.
                    // For an existing car, the old photo already counts.

                    // after deleting the old photo, existingCar.carPhotoUser still contains the old path because existingCar itself hasn't changed.
                    val hasPhoto = selectedImageBytes?.isNotEmpty() == true ||
                            (existingCar?.carPhotoUser?.isNotEmpty() == true && !oldPhotoDeleted)


                    if (modelName.text.isEmpty()){
                        modelNameError = ""
                    }else
                        modelNameError = null

                    if (modelColour.text.isEmpty()){
                        modelColourError = ""
                    }else
                        modelColourError = null

                    if (selectedImageBytes == null &&
                        existingCar?.carPhotoUser?.isNotEmpty() != true
                    ) {
                        modelPhotoError = ""
                    } else {
                        modelPhotoError = null
                    }
                    if (!hasPhoto) {
                        modelPhotoError = ""
                    } else {
                        modelPhotoError = null
                    }

                    if (modelName.text.isNotEmpty() && modelColour.text.isNotEmpty() && hasPhoto){
                       // var imagePath = ""// if user never picked the photo it stays empty

                        // Stores the existing photo when editing a car.
                        // If this is a new car, there is no old photo, so it starts as empty.
                        //var imagePath = existingCar?.carPhotoUser ?: ""

                        //If the user deleted the old photo, this still keeps the old path.
                        var imagePath = if(oldPhotoDeleted){""}else{existingCar?.carPhotoUser ?: ""}

                        //selectedImageBytes have the user selected photo
                        //?.let { } combo means: "if this isn't null, run the block below, and call it bytes inside."
                        // If it's null (user didn't pick a photo), this whole block is skipped entirely
                        selectedImageBytes?.let { bytes ->

                            //it is how user photo is saved using userID and timestamp in ms so it guarantees uniqueness no photo collied
                            val fileName = "car_${userCCPUserId}_${kotlin.time.Clock.System.now().toEpochMilliseconds()}.jpg"
                            imagePath = imageStorage.saveImageToFile(bytes, fileName) // convert bytes to real image (jpg file) on device's storage
                        }

                        //if user select add button + it checks
                        if(existingCar == null){//if it is empty for this
                            userViewModel.insertUserOwnedCarVM(
                                userCCPUserId, userCCPBrand, modelName.text.toString(),
                                selectedYear.toIntOrNull(), modelColour.text.toString(), seriesOfModel.text.toString(),
                                typeOfSeries.text.toString(), modelCollectionNumber.text.toString(),
                                imagePath,

                                onResult = {onConfirmation()}
                            )
                        }else{ // if it's existingCar not empty go for this
                            val updatedCar = existingCar.copy(//copy() keeps the fields you didn't change, especially the car's ID.
                                // This is an existing car, so update it.
                                // The old photo is kept unless the user selects a new photo.
                                modelUser = modelName.text.toString(),
                                yearUser = selectedYear.toIntOrNull(),
                                colourUser = modelColour.text.toString(),
                                seriesUser = seriesOfModel.text.toString(),
                                typeOfSeriesUser = typeOfSeries.text.toString(),
                                collectorNoUser = modelCollectionNumber.text.toString(),
                                carPhotoUser = imagePath
                            )
                            userViewModel.updateCarEditVM(updatedCar, onResult = onConfirmation)
                        }
                    }
                }
            ) {
                Text(
                    text = "Confirm",
                    color = Color(0xFFF0396B)
                )

            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)////***************
@Composable
fun YearDropDown(selectedYear: String, onYearSelected: (String) -> Unit) { // need to remove the null message when it's empty
    var expanded by remember { mutableStateOf(false) }
    val years = (2026 downTo 1900).map { it.toString() }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField(
            value = selectedYear,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.menuAnchor(),//????????????????
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    tint = Color.White
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFF0396B),
                unfocusedBorderColor = Color(0xFFF0396B),
                unfocusedTextColor = Color.White,
                focusedTextColor = Color.White,
                cursorColor = Color.White
            )
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.heightIn(max = 336.dp).background(Color(0xFF1A1A1A))
        ) {
            years.forEach { year ->
                DropdownMenuItem(
                    text = { Text(year,color = Color(0xFFFF9800))},
                    onClick = {
                        onYearSelected(year)
                        expanded = false
                    }
                )
            }
        }
    }
}