package com.cfcici.`in`.project.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material.icons.filled.Label
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material.icons.filled.ViewCarousel
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import login.shared.generated.resources.Amarante_Regular
import login.shared.generated.resources.Res
import login.shared.generated.resources.HotWheels
import org.jetbrains.compose.resources.Font
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.decodeToImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.zIndex
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
import login.shared.generated.resources.Matchbox_2
import kotlin.math.absoluteValue

enum class SortOrder{
    NEWEST_FIRST, OLDEST_FIRST
}

enum class AppTheme(val displayName: String){
    SUNSET_GARAGE("Sunset garage"),
    NEON_SPEEDWAY("Neon speedway"),
    RETRO_DIECAST("Retro diecast"),
    MIDNIGHT_CHROME("Midnight chrome")
}

data class SlotColors(val bg: Color, val text: Color)

data class ThemeColors(
    val headerGradient: List<Color>,
    val background: Color,
    val slots: List<SlotColors> // 3 different colors
)

fun themeColors(theme: AppTheme, isDark: Boolean): ThemeColors = when ( theme ) {

    AppTheme.NEON_SPEEDWAY -> if(!isDark) ThemeColors(
        headerGradient = listOf(Color(0xFF534AB7), Color(0xFFD4537E)),
        background = Color(0xFFF7F5FE),
        slots = listOf(
            SlotColors(Color(0xFFEEEDFE), Color(0xFF26215C)),
            SlotColors(Color(0xFFE1F5EE), Color(0xFF04342C)),
            SlotColors(Color(0xFFFBEAF0), Color(0xFF4B1528))
        )
    ) else ThemeColors(
        headerGradient = listOf(Color(0xFF3C3489), Color(0xFF72243E)),
        background = Color(0xFF140F22),
        slots = listOf(
            SlotColors(Color(0xFF3C3489), Color(0xFFAFA9EC)),
            SlotColors(Color(0xFF085041), Color(0xFF9FE1CB)),
            SlotColors(Color(0xFF72243E), Color(0xFFED93B1))
        )
    )

    AppTheme.SUNSET_GARAGE -> if(!isDark) ThemeColors( // if it is light mode
        headerGradient = listOf(Color(0xFFD85A30), Color(0xFFD4537E)),
        background = Color(0xFFFBF6F0),
        slots = listOf(
            SlotColors(Color(0xFFFAECE7), Color(0xFF4A1B0C)),
            SlotColors(Color(0xFFFBEAF0), Color(0xFF4B1528)),
            SlotColors(Color(0xFFFAEEDA), Color(0xFF412402))
        )
    )else ThemeColors( // if it is dark mode
        headerGradient = listOf(Color(0xFF712B13), Color(0xFF72243E)),
        background = Color(0xFF1A1210),
        slots = listOf(
            SlotColors(Color(0xFF712B13), Color(0xFFF0997B)),
            SlotColors(Color(0xFF72243E), Color(0xFFED93B1)),
            SlotColors(Color(0xFF633806), Color(0xFFEF9F27))
        )
    )

    AppTheme.RETRO_DIECAST -> if(!isDark) ThemeColors(
        headerGradient = listOf(Color(0xFF0F6E56), Color(0xFFD85A30)),
        background = Color(0xFFF5F7F0),
        slots = listOf(
            SlotColors(Color(0xFFE1F5EE), Color(0xFF04342C)),
            SlotColors(Color(0xFFFAECE7), Color(0xFF4A1B0C)),
            SlotColors(Color(0xFFFAEEDA), Color(0xFF412402))
        )
    ) else ThemeColors(
        headerGradient = listOf(Color(0xFF085041), Color(0xFF712B13)),
        background = Color(0xFF101410),
        slots = listOf(
            SlotColors(Color(0xFF085041), Color(0xFF9FE1CB)),
            SlotColors(Color(0xFF712B13), Color(0xFFF0997B)),
            SlotColors(Color(0xFF633806), Color(0xFFEF9F27))
        )
    )

    AppTheme.MIDNIGHT_CHROME -> if (!isDark) ThemeColors(
        headerGradient = listOf(Color(0xFF185FA5), Color(0xFF534AB7)),
        background = Color(0xFFF2F5F9),
        slots = listOf(
            SlotColors(Color(0xFFE6F1FB), Color(0xFF042C53)),
            SlotColors(Color(0xFFEEEDFE), Color(0xFF26215C)),
            SlotColors(Color(0xFFF1EFE8), Color(0xFF2C2C2A))
        )
    ) else ThemeColors(
        headerGradient = listOf(Color(0xFF0C447C), Color(0xFF3C3489)),
        background = Color(0xFF0E1116),
        slots = listOf(
            SlotColors(Color(0xFF0C447C), Color(0xFF85B7EB)),
            SlotColors(Color(0xFF3C3489), Color(0xFFAFA9EC)),
            SlotColors(Color(0xFF444441), Color(0xFFB4B2A9))
        )
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserCarCollectionPage( userCCPBrand: String, userCCPUserId: Int, goBackToProfile: () -> Unit, userViewModel: UserViewModel, imageStorage: ImageStorage)
{

    var deleteCarPermanently by remember { mutableStateOf<Int?>(null) }

    val usernameFont = FontFamily(Font(Res.font.Amarante_Regular))
    var showAddCarDialog by remember { mutableStateOf(false) }

    val user by userViewModel.getUserDetailsVM(userCCPUserId).collectAsState(initial = null)
    val currentTheme = user?.selectedTheme
        ?.let { runCatching { AppTheme.valueOf(it) }.getOrNull() }
        ?: AppTheme.SUNSET_GARAGE
    
    val isDarkMode = user?.isDarkMode ?: isSystemInDarkTheme()
    val colors = themeColors(currentTheme, isDarkMode)
    val iconColor = colors.slots[0].text

    var isPageLayoutColumns by remember { mutableStateOf(false) } //Columns and slide layout

    //From all my cars, give me only the cars that belong to the brand the user clicked.
    var searchText by remember { mutableStateOf("") }
    var getCarsFromThisBrand by remember { mutableStateOf<List<UserCar>>(emptyList()) }
    val totalCarThisBrandOwns = getCarsFromThisBrand.size

   // var expand by remember { mutableStateOf(false) }

    var isSearchActive by remember { mutableStateOf(false) }
    val searchFocusRequester = remember { FocusRequester() }

    /*val brandBackground = mapOf(
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
    )*/
    var sortOrder by remember { mutableStateOf<SortOrder?>(null) }

    //val logo = brandBackground[userCCPBrand] ?: Res.drawable.HotWheels // fallback drawable
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
    var showCardDetails by remember { mutableStateOf<UserCar?>(null) }
    val focusManager = LocalFocusManager.current
    var showLargePic by remember { mutableStateOf<UserCar?>(null) }

    fun displayCarFromThisBrand(){
        userViewModel.getUserOwnedCarsByBrandVM(userCCPUserId, userCCPBrand){
                cars -> getCarsFromThisBrand = cars
        }
    }

    LaunchedEffect(userCCPBrand) {
        displayCarFromThisBrand()
    }

    LaunchedEffect(showCardDetails) {// it should stop the search bar focus while box pop up
        if (showCardDetails != null) {
            focusManager.clearFocus()
        }
    }
/*
    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black)
    )
    {

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
*/
    Box(
        modifier = Modifier.fillMaxSize().background(colors.background)
    )
    {
        if(!showAddCarDialog){
            Column (
                modifier = Modifier.fillMaxSize()
            )
            {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Brush.horizontalGradient(colors.headerGradient))
                    )
                {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                                .statusBarsPadding(), // pushes below status bar
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        )
                        {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {

                                Text(
                                    text = userCCPBrand,
                                    color = Color.White,
                                    fontFamily = usernameFont,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 30.sp
                                )
                                Text(
                                    text = if(totalCarThisBrandOwns == 1) "$totalCarThisBrandOwns car in your collection" else "$totalCarThisBrandOwns cars in your collection",
                                    color = Color.White.copy(alpha = 0.85f),
                                    fontFamily = usernameFont,
                                    fontSize = 16.sp,
                                )
                            }

                            IconButton(
                                onClick = {
                                    userViewModel.updateDarkModeVM(userCCPUserId, !isDarkMode) {}
                                },
                                modifier = Modifier
                                    .size(48.dp)
                                    .background(
                                        color = Color.White.copy(alpha = 0.2f),
                                        shape = CircleShape
                                    )
                            ){
                                Icon(
                                    imageVector = if (isDarkMode)
                                        Icons.Default.LightMode
                                    else
                                        Icons.Default.DarkMode,
                                    contentDescription = "dark mode - light mode",
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                }

                BoxWithConstraints(// was Row(weight) + AnimatedVisibility — that combo can't animate width
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 15.dp)
                        .height(50.dp)
                )
                {
                    val collapsedWidth = 48.dp

                    val targetWidth = if (isSearchActive)
                        maxWidth
                    else
                        collapsedWidth

                    val animatedWidth by animateDpAsState( //this is the actual growing/shrinking value
                        targetValue = targetWidth,
                        animationSpec = tween(durationMillis = 300),
                        label = "searchWidth"
                    )
                    if(!isSearchActive){
                        // Icons sit underneath, fixed in place, just fade out — no animation needed on them

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .alpha(if (isSearchActive) 0f else 1f), //instant-feeling fade, not a size change
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            IconButton(
                                //modifier = Modifier.padding(start = 50.dp),
                                onClick = {
                                    isSearchActive = true
                                    isPageLayoutColumns = false
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search",
                                    tint = iconColor)
                            }
                            IconButton(onClick = { isPageLayoutColumns = !isPageLayoutColumns }) {
                                Icon(
                                    imageVector = if (isPageLayoutColumns) Icons.Default.GridView else Icons.Default.ViewCarousel,
                                    contentDescription = "Toggle layout",
                                    tint = iconColor
                                )
                            }
                            IconButton(
                                //modifier = Modifier.padding()
                                onClick = {goBackToProfile()}
                            ){
                                Icon(
                                    imageVector = Icons.Default.Home,
                                    contentDescription = "Home page",
                                    tint = iconColor
                                )
                            }
                            IconButton(
                                // modifier = Modifier.padding(end = 50.dp),
                                onClick = {
                                    carBeingEdit = null
                                    showAddCarDialog = true }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = null,
                                    tint = iconColor)
                            }
                        }

                    }
                    // Search field overlays on top, growing/shrinking left-anchored
                    if(isSearchActive){
                        OutlinedTextField(
                            value = searchText,
                            onValueChange = { searchText = it },
                            enabled = isSearchActive, //stops the invisible collapsed field from stealing taps meant for the search icon underneath
                            modifier = Modifier
                                .align(Alignment.CenterStart) //pins the left edge so all growth/shrink happens on the right side
                                .width(animatedWidth)
                                .height(56.dp)
                                .focusRequester(searchFocusRequester)
                                .alpha(if (animatedWidth > collapsedWidth + 20.dp) 1f else 0f), // hides cramped content before there's room for it
                            shape = RoundedCornerShape(28.dp),
                            placeholder = {
                                Text("Search here...",
                                    color = iconColor.copy(alpha = 0.6f),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis) },
                            leadingIcon = { Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                tint = iconColor) },
                            trailingIcon = {
                                if (isSearchActive) {
                                    IconButton(
                                        onClick = {
                                            showCardDetails = null
                                            searchText = ""
                                            isSearchActive = false }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Close search",
                                            tint = iconColor)
                                    }
                                }
                            },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = iconColor,
                                unfocusedBorderColor = iconColor.copy(alpha = 0.5f),
                                focusedTextColor = iconColor,
                                unfocusedTextColor = iconColor,
                                cursorColor = iconColor
                            )
                        )
                    }
                }
                LaunchedEffect(isSearchActive) {
                    if (isSearchActive) searchFocusRequester.requestFocus()
                }

                //the swipeable infinite Coverflow carousel, replacing the old static logo area.
                //Only shows when the brand actually has cars — falls back to nothing (search/grid below still works) when empty.

                if (getCarsFromThisBrand.isNotEmpty()){
                    if (isPageLayoutColumns) { //the grid branch
                        CarCoverflowCarousel(
                            cars = filteringForCarSearch, // was getCarsFromThisBrand — now respects search too
                            imageStorage = imageStorage,
                            colors = colors,
                            modifier = Modifier.weight(1f).fillMaxWidth(),
                            onDeleteClick = { displayCarFromThisBrand() },
                            onEditClick = { car -> carBeingEdit = car
                                          showAddCarDialog = true},
                            userViewModel = userViewModel
                        )
                    } else {
// ADDED: tracks which single car is currently expanded (only one at a time makes sense for this layout)

                        Box(
                            modifier = Modifier.weight(1f).fillMaxWidth(),
                        )
                        {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                modifier = Modifier.fillMaxWidth(),
                                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 60.dp),
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            )
                            {
                                items(items = filteringForCarSearch)
                                { car ->
                                    val slot = colors.slots[filteringForCarSearch.indexOf(car) % colors.slots.size]
                                        //val isExpanded = expandedCarId == car.userCarIdUser // ADDED

                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .combinedClickable(
                                            onClick = { showCardDetails = car },
                                                onLongClick = { showLargePic = car}
                                            ),
                                            //.animateContentSize(animationSpec = tween(durationMillis = 300)), //this is what makes the box grow/shrink smoothly instead of popping
                                        shape = RoundedCornerShape(16.dp),
                                        colors = CardDefaults.cardColors(containerColor = slot.bg)
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(16.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        )
                                        {
                                            if (car.carPhotoUser.isNotEmpty()) {
                                                AsyncImage(
                                                    model = imageStorage.getFullPath(fileName = car.carPhotoUser),
                                                    contentDescription = car.modelUser,
                                                    modifier = Modifier
                                                        .size(70.dp)
                                                        .clip(RoundedCornerShape(10.dp)),
                                                    contentScale = ContentScale.Crop
                                                )
                                            }
                                            Spacer(modifier = Modifier.width(10.dp))
                                            Text(
                                                car.modelUser,
                                                color = slot.text,
                                                fontWeight = FontWeight.Bold,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis,
                                                fontFamily = usernameFont
                                            )
                                        }
                                    }
                                }
                            }

                        }
                    }
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }else{
            AddNewCar(
                userCCPBrand = userCCPBrand,
                userCCPUserId = userCCPUserId,
                userViewModel = userViewModel,
                imageStorage = imageStorage,
                colors = colors,
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
        androidx.compose.animation.AnimatedVisibility(
            visible = showCardDetails != null,
            enter = scaleIn(animationSpec = tween(300)) + fadeIn(animationSpec = tween(300)), // added fadeIn so the scrim fades in together with the scale
            exit = scaleOut(animationSpec = tween(300)) + fadeOut(animationSpec = tween(300))
        )
        {
            val car = showCardDetails ?: return@AnimatedVisibility
            val carIndex = filteringForCarSearch.indexOf(car)
            val slot = colors.slots[carIndex % colors.slots.size]

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.6f)) // this is the scrim. Without it, the grid cards behind show through at the edges, which is exactly the glitch in your first screenshot
                    .clickable( // tapping anywhere on the dimmed background closes the popup
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }) { showCardDetails = null },
                contentAlignment = Alignment.Center
            ) {
                Card( // .background() — Card gives proper elevation/shape and stops taps on it from bubbling to the scrim behind
                    modifier = Modifier
                        .height(450.dp)
                        .fillMaxWidth(0.85f)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) { /* absorbs the click so tapping the card itself doesn't close it via the scrim */ },
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = slot.bg),
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.padding(24.dp).fillMaxSize(),
                    )
                    {
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center) {
                            Text(
                                text = car.modelUser,
                                color = slot.text,
                                fontFamily = usernameFont,
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(Modifier.height(16.dp))

                            CarDetailRow(label = "Year", value = car.yearUser?.toString() ?: "—", color = slot.text)
                            CarDetailRow(label = "Colour", value = car.colourUser, color = slot.text)
                            CarDetailRow(label = "Series", value = car.seriesUser ?: "—", color = slot.text)
                            CarDetailRow(label = "Type of series", value = car.typeOfSeriesUser ?: "—", color = slot.text)
                            CarDetailRow(label = "Collector no.", value = car.collectorNoUser ?: "—", color = slot.text)

                            Spacer(Modifier.height(16.dp))

                            Text(
                                text = "Tap outside to close",
                                color = slot.text.copy(alpha = 0.6f),
                                fontFamily = usernameFont,
                                fontSize = 12.sp
                            )
                        }

                        Spacer(Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            IconButton(
                                onClick = {
                                    carBeingEdit = car
                                    showAddCarDialog = true
                                    showCardDetails = null },
                                modifier = Modifier
                                    .background(color = slot.text.copy(alpha = 0.15f), CircleShape)
                            ) {
                                Icon(Icons.Default.Edit,
                                    contentDescription = "Edit",
                                    tint = slot.text)
                            }
                            IconButton(
                                onClick = { deleteCarPermanently = car.userCarIdUser },
                                modifier = Modifier
                                    .background(color = slot.text.copy(alpha = 0.15f), CircleShape)
                            ) {
                                Icon(imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    tint = slot.text)
                            }
                        }
                    }
                }

                if (deleteCarPermanently == car.userCarIdUser) {
                    AlertDialog(
                        onDismissRequest = { deleteCarPermanently = null },
                        containerColor = slot.bg,
                        title = { Text(car.modelUser, color = slot.text) },
                        text = { Text("Are you sure you want to delete this?", color = slot.text.copy(alpha = 0.6f)) },
                        confirmButton = {
                            TextButton(onClick = {
                                userViewModel.deleteUserOwnedCarVM(car) { displayCarFromThisBrand() }
                                showCardDetails = null // ADDED: closes the popup too, since the car it was showing no longer exists
                                deleteCarPermanently = null
                            }) { Text("Delete", color = slot.text) }
                        },
                        dismissButton = {
                            TextButton(onClick = { deleteCarPermanently = null }) { Text("Cancel", color = slot.text) }
                        }
                    )
                }
            }
        }
    }

    if (showLargePic != null){
        androidx.compose.animation.AnimatedVisibility(
            visible = showLargePic != null,
            enter = scaleIn(animationSpec = tween(300)) + fadeIn(animationSpec = tween(300)), // CHANGED: added fadeIn so the scrim fades in together with the scale
            exit = scaleOut(animationSpec = tween(300)) + fadeOut(animationSpec = tween(300))
        )
        {
            val car = showLargePic?: return@AnimatedVisibility
            val carIndex = filteringForCarSearch.indexOf(car)
            val slot = colors.slots[carIndex % colors.slots.size]

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.6f)) // ADDED — this is the scrim. Without it, the grid cards behind show through at the edges, which is exactly the glitch in your first screenshot
                    .clickable( // ADDED — tapping anywhere on the dimmed background closes the popup
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }) { showLargePic = null },
                contentAlignment = Alignment.Center
            ) {
                Card( // .background() — Card gives proper elevation/shape and stops taps on it from bubbling to the scrim behind
                    modifier = Modifier
                        .fillMaxHeight(0.60f)
                        .fillMaxWidth(0.85f) // CHANGED: was .size(600.dp) — percentage width keeps it correctly proportioned on any screen instead of a fixed pixel size that can overflow
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) { /* absorbs the click so tapping the card itself doesn't close it via the scrim */ },
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = slot.bg),
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.padding(24.dp).fillMaxSize(),
                    )
                    {
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center) {

                            if (car.carPhotoUser.isNotEmpty()) {
                                AsyncImage(
                                    model = imageStorage.getFullPath(fileName = car.carPhotoUser),
                                    contentDescription = car.modelUser,
                                    modifier = Modifier
                                        .size(350.dp)
                                        .clip(RoundedCornerShape(10.dp)),
                                    contentScale = ContentScale.Crop
                                )
                            }

                            Spacer(Modifier.height(16.dp))

                            Text(
                                text = "Tap outside to close",
                                color = slot.text.copy(alpha = 0.6f),
                                fontFamily = usernameFont,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
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
fun AddNewCar(userCCPBrand: String, userCCPUserId: Int, userViewModel: UserViewModel, imageStorage: ImageStorage, colors: ThemeColors,
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
    //val slot = colors.slots[filteringForCarSearch.indexOf(car) % colors.slots.size]

    var modelNameError by remember { mutableStateOf<String?>(null) }
    var modelPhotoError by remember { mutableStateOf<String?>(null) }
    var modelColourError by remember { mutableStateOf<String?>(null) }

    var showImagePreview by remember { mutableStateOf(false) }
    var selectedImageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    val usernameFont = FontFamily(Font(Res.font.Amarante_Regular))
    val accent = colors.headerGradient[0]
    val textColors = colors.slots[0].text

    if(showCamera){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.background))
        {
            CameraCapture(
                onImageCaptured = {
                    bytes -> selectedImageBytes = bytes
                    showCamera = false
                                  },
                onDismiss = {showCamera = false}
            )
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            //.padding(16.dp)
        ,
       // horizontalAlignment = Alignment.CenterHorizontally,
        //verticalArrangement = Arrangement.Center
    )
    {
        // Header Row with a Close button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.horizontalGradient(colors.headerGradient))
                .statusBarsPadding()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Text(
                text = if (existingCar == null) "Add a car" else "Edit car",
                color = Color.White,
                fontFamily = usernameFont,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
/*
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onDismissRequest) {
                    Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                }
                Text(
                    text = if (existingCar == null) "Add a car" else "Edit car",
                    color = Color.White,
                    fontFamily = usernameFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            */
        }
        /*
        {
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

        //Spacer(modifier = Modifier.height(16.dp))

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
*/
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp)
        )
        {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(colors.slots[2].bg.copy(alpha = 0.4f))
                    .padding(horizontal = 18.dp)
            )
            {
                AnimatedFormField(
                    label = "Model name",
                    icon = Icons.Default.DirectionsCar,
                    state = modelName,
                    colors = colors,
                    isError = modelNameError != null,
                    errorText = if (modelNameError != null) "Name of the model is required" else null,
                    onFocusChanged = { focused -> if (focused) modelNameError = null }
                )
                AnimatedYearField(
                    selectedYear = selectedYear,
                    colors = colors,
                    onYearSelected = { selectedYear = it }
                )
                AnimatedFormField(
                    label = "Colour",
                    icon = Icons.Default.Palette,
                    state = modelColour,
                    colors = colors,
                    isError = modelColourError != null,
                    errorText = if (modelColourError != null) "Name of the colour is required" else null,
                    onFocusChanged = { focused -> if (focused) modelColourError = null }
                )
                AnimatedFormField(
                    label = "Collection number",
                    icon = Icons.Default.Tag,
                    state = modelCollectionNumber,
                    colors = colors
                )
                AnimatedFormField(
                    label = "Series name",
                    icon = Icons.Default.Label,
                    state = seriesOfModel,
                    colors = colors
                )
                AnimatedFormField(
                    label = "Type of series",
                    icon = Icons.Default.Category,
                    state = typeOfSeries,
                    colors = colors,
                    showDivider = false
                )
            }

        }
            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth().padding(15.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            )
            {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .height(46.dp)
                        .clip(RoundedCornerShape(23.dp))
                        .background(colors.slots[2].bg.copy(alpha = 0.4f))
                        .clickable { launcher.launch() },
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Upload,
                        contentDescription = null,
                        tint = textColors.copy(alpha = 0.6f),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        "Upload photo",
                        color = textColors.copy(alpha = 0.6f),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .height(46.dp)
                        .clip(RoundedCornerShape(23.dp))
                        .background(colors.slots[2].bg.copy(alpha = 0.4f))
                        .clickable { showCamera = true },
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.PhotoCamera,
                        contentDescription = null,
                        tint = textColors.copy(alpha = 0.6f),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        "Take photo",
                        color = textColors.copy(alpha = 0.6f),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            if (selectedImageBytes != null) {
                val bitmap = remember(selectedImageBytes) {
                    selectedImageBytes!!.decodeToImageBitmap()
                }
                Box(
                    modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
                    contentAlignment = Alignment.Center
                )
                {
                    Box(modifier = Modifier.size(100.dp)){
                        Image(
                            bitmap = bitmap,
                            contentDescription = "Selected car photo",
                            modifier = Modifier
                                .size(100.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .clickable {
                                    selectedImageBitmap = bitmap
                                    showingExistingPhoto = false
                                    showImagePreview = true
                                },
                            contentScale = ContentScale.Crop
                        )
                        IconButton(
                            onClick = { selectedImageBytes = null },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .offset(x = 8.dp, y = (-8).dp)
                                .size(18.dp)
                                .background(
                                    color = accent, shape = CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Remove photo",
                                tint = Color.White,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }
            }
            else if (existingCar?.carPhotoUser?.isNotEmpty() == true && !oldPhotoDeleted)
            {
                val bytes = imageStorage.loadImageFromFile(existingCar.carPhotoUser)
                val existingCarBitmap = remember(bytes) { bytes?.decodeToImageBitmap() }


            if (existingCarBitmap != null) {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
                    contentAlignment = Alignment.Center
                ){
                    Box(modifier = Modifier.size(100.dp))
                    {
                        Image(
                            bitmap = existingCarBitmap,
                            contentDescription = "Selected car photo",
                            modifier = Modifier
                                .size(100.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .clickable
                                {
                                    if (bytes != null) {
                                        selectedImageBitmap = bytes.decodeToImageBitmap()
                                        showingExistingPhoto = true
                                        showImagePreview = true
                                    }
                                },
                            contentScale = ContentScale.Crop
                        )

                        IconButton(
                            onClick = {
                                selectedImageBytes = null
                                oldPhotoDeleted = true
                            },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .offset(x = 8.dp, y = (-8).dp)
                                .size(22.dp)
                                .background(
                                    accent,
                                    shape = CircleShape
                                )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Remove photo",
                                tint = Color.White,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }

            }
    } else {
        if (modelPhotoError != null) {
            Text(
                text = "Photo of the model is required",
                color = Color(0xFFE05252),
                fontSize = 13.sp,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
        if (showImagePreview && selectedImageBitmap != null) {
            AlertDialog(
                onDismissRequest = { showImagePreview = false },
                containerColor = colors.slots[2].bg,
                confirmButton = {
                    Row {
                        TextButton(
                            onClick = {
                                if (showingExistingPhoto) {
                                    oldPhotoDeleted = true
                                } else {
                                    selectedImageBytes = null
                                }
                                showImagePreview = false
                            }
                        ) {
                            Text("Delete", color = textColors)
                        }
                        TextButton(
                            onClick = {
                                showImagePreview = false
                            }
                        ) {
                            Text("Close", color = textColors)
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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(
                    text = "Close",
                    color = textColors.copy(alpha = 0.6f),
                    fontWeight = FontWeight.Medium
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


                    if (modelName.text.isEmpty()) {
                        modelNameError = ""
                    } else
                        modelNameError = null

                    if (modelColour.text.isEmpty()) {
                        modelColourError = ""
                    } else
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

                    if (modelName.text.isNotEmpty() && modelColour.text.isNotEmpty() && hasPhoto) {
                        // var imagePath = ""// if user never picked the photo it stays empty

                        // Stores the existing photo when editing a car.
                        // If this is a new car, there is no old photo, so it starts as empty.
                        //var imagePath = existingCar?.carPhotoUser ?: ""

                        //If the user deleted the old photo, this still keeps the old path.
                        var imagePath = if (oldPhotoDeleted) {
                            ""
                        } else {
                            existingCar?.carPhotoUser ?: ""
                        }

                        //selectedImageBytes have the user selected photo
                        //?.let { } combo means: "if this isn't null, run the block below, and call it bytes inside."
                        // If it's null (user didn't pick a photo), this whole block is skipped entirely
                        selectedImageBytes?.let { bytes ->

                            //it is how user photo is saved using userID and timestamp in ms so it guarantees uniqueness no photo collied
                            val fileName = "car_${userCCPUserId}_${
                                kotlin.time.Clock.System.now().toEpochMilliseconds()
                            }.jpg"
                            imagePath = imageStorage.saveImageToFile(
                                bytes,
                                fileName
                            ) // convert bytes to real image (jpg file) on device's storage
                        }

                        //if user select add button + it checks
                        if (existingCar == null) {//if it is empty for this
                            userViewModel.insertUserOwnedCarVM(
                                userCCPUserId,
                                userCCPBrand,
                                modelName.text.toString(),
                                selectedYear.toIntOrNull(),
                                modelColour.text.toString(),
                                seriesOfModel.text.toString(),
                                typeOfSeries.text.toString(),
                                modelCollectionNumber.text.toString(),
                                imagePath,

                                onResult = { onConfirmation() }
                            )
                        } else { // if it's existingCar not empty go for this
                            val updatedCar =
                                existingCar.copy(//copy() keeps the fields you didn't change, especially the car's ID.
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
                    color = textColors.copy(alpha = 0.6f),
                    fontWeight = FontWeight.Bold
                )

            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
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
            modifier = Modifier.menuAnchor(),
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

@Composable
fun CarCoverflowCarousel(
    cars: List<UserCar>,
    imageStorage: ImageStorage,
    colors: ThemeColors,
    modifier: Modifier = Modifier, //lets the caller control size instead of a fixed height baked in here
    onDeleteClick: () -> Unit,
    onEditClick: (UserCar) -> Unit,
    userViewModel: UserViewModel
)
{

    var deleteCarPermanently by remember  {mutableStateOf<Int?>(null)}
    val usernameFont = FontFamily(Font(Res.font.Amarante_Regular))
    val virtualCount = Int.MAX_VALUE
    val startPage = remember(cars.size) { virtualCount / 2 - (virtualCount / 2) % cars.size }
    val pagerState = rememberPagerState(initialPage = startPage, pageCount = { virtualCount })
    val density = LocalDensity.current

    //tracks which cars are currently flipped, keyed by car id — each card flips independently
    val flippedCars = remember { mutableStateMapOf<Int, Boolean>() }

    BoxWithConstraints(// finds available screen space
        modifier = modifier,
        contentAlignment = Alignment.TopCenter
    ) {
        val cardWidth = 350.dp

        // Calculate the padding needed to put the card exactly in the middle of the screen
        val sidePadding = (maxWidth - cardWidth) / 2

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            HorizontalPager(// horizontal page is the reason for the swipeable pages
                state = pagerState,
                pageSize = PageSize.Fixed(cardWidth),
                contentPadding = PaddingValues(horizontal = sidePadding),
                modifier = Modifier.fillMaxWidth()
                    .height(500.dp) // was fillMaxSize() — fixed height keeps the card near the top instead of stretching into (and centering within) all remaining space
            )
            {
                    page ->
                val realIndex = page % cars.size
                val car = cars[realIndex]
                val slot = colors.slots[realIndex % colors.slots.size]

                val pageOffset = (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction

                val isFlipped = flippedCars[car.userCarIdUser] == true
                val flipRotation by animateFloatAsState(
                    targetValue = if(isFlipped) 180f else 0f,
                    animationSpec = tween(durationMillis = 500),
                    label = "Card Flip"
                )

                Box(
                    modifier = Modifier
                        .graphicsLayer
                        {// changes how each page visually looks
                            cameraDistance = 12f * density.density

                            // Sharp 3D effect
                            //rotationY = pageOffset * -30f
                            rotationY = pageOffset * -30f + flipRotation

                            // Side cards significantly smaller to show overlap
                            val scale = 1f - 0.25f * pageOffset.absoluteValue.coerceIn(0f, 1f)
                            scaleX = scale
                            scaleY = scale

                            // Side cards more transparent
                            alpha = 1f - 0.5f * pageOffset.absoluteValue.coerceIn(0f, 1f)

                            // Tightly pull side cards toward the center for overlap
                            translationX = -pageOffset * 200.dp.toPx()
                        }

                        // Center card goes on top
                        .zIndex(1f - pageOffset.absoluteValue.coerceIn(0f, 1f))
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(vertical = 8.dp, horizontal = 8.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(slot.bg)
                        .clickable {
                            flippedCars[car.userCarIdUser] = !isFlipped },
                    contentAlignment = Alignment.Center
                )
                {
                    if(flipRotation <= 90f || flipRotation >= 270f)
                    {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally)
                        {
                            if (car.carPhotoUser.isNotEmpty()) {
                                AsyncImage(
                                    model = imageStorage.getFullPath(fileName = car.carPhotoUser),
                                    contentDescription = car.modelUser,
                                    modifier = Modifier
                                        .size(300.dp)
                                        .clip(RoundedCornerShape(20.dp)),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Box(
                                    modifier = Modifier
                                        .size(300.dp)
                                        .clip(CircleShape)
                                        .background(
                                            slot.text.copy(alpha = 0.15f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = null,
                                        tint = slot.text
                                    )
                                }
                            }

                            Spacer(Modifier.height(16.dp))

                            Text(
                                text = car.modelUser,
                                color = slot.text,
                                fontFamily = usernameFont,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )

                            Text(
                                text = "Tap to flip back",
                                color = slot.text.copy(alpha = 0.6f),
                                fontFamily = usernameFont,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                        }
                    }
                    else{
                        Box(modifier = Modifier.fillMaxSize())
                        {
                            IconButton(
                                onClick = { onEditClick(car)},
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .graphicsLayer{rotationY = 180f}
                                    .padding(20.dp)
                                    .background(color = slot.text.copy(alpha = 0.15f), shape = CircleShape)
                            ){
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Edit",
                                    tint = slot.text
                                )
                            }

                            Column (
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .graphicsLayer{rotationY = 180f}
                                    .padding(horizontal = 20.dp, vertical = 48.dp))
                            {
                                Text(
                                    text = car.modelUser,
                                    color = slot.text,
                                    fontFamily = usernameFont,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 22.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(Modifier.height(16.dp))
                                CarDetailRow(label = "Year", value = car.yearUser?.toString() ?: "—", color = slot.text)
                                CarDetailRow(label = "Colour", value = car.colourUser, color = slot.text)
                                CarDetailRow(label = "Series", value = car.seriesUser ?: "—", color = slot.text)
                                CarDetailRow(label = "Type of series", value = car.typeOfSeriesUser ?: "—", color = slot.text)
                                CarDetailRow(label = "Collector no.", value = car.collectorNoUser ?: "—", color = slot.text)
                                Spacer(Modifier.height(20.dp))
                                Text(
                                    text = "Tap to flip back",
                                    color = slot.text.copy(alpha = 0.6f),
                                    fontFamily = usernameFont,
                                    fontSize = 12.sp
                                )
                            }
                            IconButton(
                                onClick = {deleteCarPermanently = car.userCarIdUser},
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .graphicsLayer{rotationY = 180f}
                                    .padding(20.dp)
                                    .background(color = slot.text.copy(alpha = 0.15f), shape = CircleShape)
                            ){
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    tint = slot.text
                                )
                            }

                            if (deleteCarPermanently == car.userCarIdUser){
                                AlertDialog(
                                    onDismissRequest = { deleteCarPermanently = null },
                                    containerColor = slot.bg,
                                    title = {
                                        Text(car.modelUser, color = slot.text)
                                    },
                                    text = {
                                        Text("Are you sure you want to delete this?", color = slot.text.copy(alpha = 0.6f))
                                    },
                                    confirmButton = {
                                        TextButton(onClick = {
                                            userViewModel.deleteUserOwnedCarVM(car){
                                                onDeleteClick()
                                            }
                                        }) {
                                            Text("Delete", color = slot.text)
                                        }
                                    },
                                    dismissButton = {
                                        TextButton(onClick = {deleteCarPermanently = null}) {
                                            Text("Cancel", color = slot.text)
                                        }
                                    }
                                )
                            }
                        }
                    }
                }

            }

            Spacer(modifier = Modifier.height(16.dp))

            val currentIndex = pagerState.currentPage % cars.size

            val startIndex = maxOf(0, currentIndex - 2)
            val endIndex = minOf(cars.size - 1, currentIndex + 2)

            Row( // need to add animation
                modifier = Modifier.height(24.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (index in startIndex..endIndex) {

                    val isSelected = index == currentIndex

                    Box(
                        modifier = Modifier
                            .padding(horizontal = 3.dp)// space between the dots
                            .size(
                                if (isSelected) 16.dp else 8.dp
                            )
                            .clip(CircleShape)
                            .background(
                                if (isSelected)
                                    Color.DarkGray
                                else
                                    Color.White
                            )
                    )
                }
            }

        }
    }
}
@Composable
fun CarDetailRow(label: String, value: String, color: Color) {
    val usernameFont = FontFamily(Font(Res.font.Amarante_Regular))
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    )
    {
        Text(
            text = label,
            color = color.copy(alpha = 0.7f),
            fontSize = 14.sp,
            fontFamily = usernameFont
        )
        Text(
            text = value,
            color = color,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = usernameFont
        )
    }
}

@Composable
fun AnimatedFormField(
    label: String,
    icon: ImageVector,
    state: TextFieldState,
    colors: ThemeColors,
    showDivider: Boolean = true,
    isError: Boolean = false,
    errorText: String? = null,
    onFocusChanged: ((Boolean) -> Unit)? = null
) {
    val accent = colors.headerGradient[0]
    val textColor = colors.slots[0].text
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val iconScale by animateFloatAsState(
        targetValue = if (isFocused) 1.15f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMedium),
        label = "iconScale"
    )
    val iconBg by animateColorAsState(if (isFocused) accent else textColor.copy(alpha = 0.08f), tween(250), label = "iconBg")
    val iconTint by animateColorAsState(if (isFocused) Color.White else textColor.copy(alpha = 0.6f), tween(250), label = "iconTint")
    val labelColor by animateColorAsState(if (isFocused) accent else textColor.copy(alpha = 0.6f), tween(250), label = "labelColor")
    val underlineScale by animateFloatAsState(if (isFocused) 1f else 0f, tween(300), label = "underlineScale")

    LaunchedEffect(isFocused) { onFocusChanged?.invoke(isFocused) }

    Column {
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .graphicsLayer { scaleX = iconScale; scaleY = iconScale }
                    .clip(RoundedCornerShape(12.dp))
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(17.dp))
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = label,
                    color = if (isError) Color(0xFFE05252) else labelColor,
                    fontSize = 11.sp,
                    fontWeight = if (isFocused) FontWeight.SemiBold else FontWeight.Normal
                )
                Spacer(modifier = Modifier.height(2.dp))
                BasicTextField(
                    state = state,
                    textStyle = TextStyle(fontSize = 14.sp, color = textColor),
                    interactionSource = interactionSource,
                    lineLimits = TextFieldLineLimits.SingleLine,
                    modifier = Modifier.fillMaxWidth(),
                    cursorBrush = SolidColor(accent)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .graphicsLayer { scaleX = underlineScale; transformOrigin = TransformOrigin(0f, 0.5f) }
                        .background(if (isError) Color(0xFFE05252) else accent)
                )
                if (isError && errorText != null) {
                    Text(text = errorText,
                        color = Color(0xFFE05252),
                        fontSize = 11.sp,
                        modifier = Modifier.padding(top = 4.dp))
                }
            }
        }
        if (showDivider) {
            Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(textColor.copy(alpha = 0.08f)))
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimatedYearField(
    selectedYear: String,
    colors: ThemeColors,
    onYearSelected: (String) -> Unit,
    showDivider: Boolean = true
) {
    var expanded by remember { mutableStateOf(false) }
    val years = (2026 downTo 1900).map { it.toString() }
    val accent = colors.headerGradient[0]
    val textColor = colors.slots[0].text

    val iconScale by animateFloatAsState(
        targetValue = if (expanded)
            1.15f
        else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium),
        label = "yearIconScale"
    )
    val iconBg by animateColorAsState(
        targetValue = if (expanded) accent
        else textColor.copy(alpha = 0.08f),
        animationSpec = tween(durationMillis = 250),
        label = "yearIconBg")

    val iconTint by animateColorAsState(
        targetValue = if (expanded)
            Color.White
        else textColor.copy(alpha = 0.6f),
        animationSpec = tween(durationMillis = 250),
        label = "yearIconTint")

    val labelColor by animateColorAsState(
        targetValue = if (expanded)
            accent else textColor.copy(alpha = 0.6f),
        animationSpec = tween(durationMillis = 250),
        label = "yearLabelColor")

    val underlineScale by animateFloatAsState(
        targetValue = if (expanded) 1f
        else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "yearUnderline")

    Column {
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 14.dp)
                    .menuAnchor(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .graphicsLayer { scaleX = iconScale; scaleY = iconScale }
                        .clip(RoundedCornerShape(12.dp))
                        .background(iconBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.CalendarMonth,
                        contentDescription = null,
                        tint = iconTint,
                        modifier = Modifier.size(17.dp))
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Year",
                        color = labelColor,
                        fontSize = 11.sp,
                        fontWeight = if (expanded) FontWeight.SemiBold else FontWeight.Normal
                    )
                    Spacer(modifier = Modifier.height(2.dp))

                    Text(text = selectedYear.ifEmpty { " " },
                        fontSize = 14.sp, color = textColor)

                    Spacer(modifier = Modifier.height(6.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                            .graphicsLayer { scaleX = underlineScale; transformOrigin = TransformOrigin(0f, 0.5f) }
                            .background(accent)
                    )
                }
            }
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.heightIn(max = 336.dp).background(colors.slots[2].bg)
            ) {
                years.forEach { year ->
                    DropdownMenuItem(
                        text = { Text(year, color = colors.slots[2].text) },
                        onClick = { onYearSelected(year); expanded = false }
                    )
                }
            }
        }
        if (showDivider) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(textColor.copy(alpha = 0.08f)))
        }
    }
}
@Composable
fun ThemePickerGrid(
    currentTheme: AppTheme,
    isDark: Boolean,
    onThemeSelected: (AppTheme) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(AppTheme.entries) { theme ->
            val colors = themeColors(theme, isDark)
            val selected = theme == currentTheme

            Card(
                onClick = { onThemeSelected(theme) },
                shape = RoundedCornerShape(16.dp),
                border = if (selected)
                    BorderStroke(2.dp, colors.slots[0].text)
                else null,
                colors = CardDefaults.cardColors(containerColor = colors.background)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Brush.horizontalGradient(colors.headerGradient))
                    )
                    Spacer(Modifier.height(8.dp))

                    Text(theme.displayName,
                        color = colors.slots[0].text,
                        fontSize = 13.sp)

                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier
                            .padding(top = 6.dp)) {
                        colors.slots.forEach { slot ->
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .clip(CircleShape)
                                    .background(slot.bg)
                            )
                        }
                    }
                }
            }
        }
    }
}
