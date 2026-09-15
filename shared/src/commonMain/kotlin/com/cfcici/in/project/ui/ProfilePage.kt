package com.cfcici.`in`.project.ui

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.zIndex
import kotlinx.coroutines.isActive
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import coil3.compose.AsyncImage
import com.cfcici.`in`.project.ImageStorage
import com.cfcici.`in`.project.data.database.UserCar
import com.cfcici.`in`.project.data.database.UserSelectedBrandCars
import com.cfcici.`in`.project.viewmodel.UserViewModel
import login.shared.generated.resources.Amarante_Regular
import login.shared.generated.resources.AutoWorldLogo
import login.shared.generated.resources.BuragoLogo
import login.shared.generated.resources.GL_4
import login.shared.generated.resources.HotWheelsLogo2
import login.shared.generated.resources.Jada_2
import login.shared.generated.resources.JohnnyLightning
import login.shared.generated.resources.KaidoHouse
import login.shared.generated.resources.M2_4
import login.shared.generated.resources.Maisto_2
import login.shared.generated.resources.Matchbox_3
import login.shared.generated.resources.Res
import login.shared.generated.resources.Solido_2
import login.shared.generated.resources.Tarmac
import login.shared.generated.resources.Tomica_3
import login.shared.generated.resources.inno64
import login.shared.generated.resources.majorette_2
import login.shared.generated.resources.miniGT_2
import login.shared.generated.resources.pr_2
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource


@Composable
fun ProfilePage(usernamePP: String,
                userIdPP: Int,
                onBackToLogin: () -> Unit,
                goToSetting: (Int) -> Unit,
                goToUserCarCollection: (String, Int) -> Unit,
                userViewModel: UserViewModel,
                imageStorage: ImageStorage
)
{

    val user by userViewModel.getUserDetailsVM(userIdPP).collectAsState(initial = null)
    val currentTheme = user?.selectedTheme
        ?.let { runCatching { AppTheme.valueOf(it) }.getOrNull() }
        ?: AppTheme.NEON_SPEEDWAY
    val isDarkMode = user?.isDarkMode ?: isSystemInDarkTheme()
    val colors = themeColors(currentTheme, isDarkMode)
    val textColor = colors.slots[0].text
    val backgroundColor = colors.slots[2].bg
    val accent = colors.headerGradient[0]
    val totalCarUserOwn by userViewModel.totalCarsUserOwnVM(userIdPP).collectAsState(initial = null)

    val brandLogos = mapOf(
        "HotWheels" to Res.drawable.HotWheelsLogo2,
        "MatchBox" to Res.drawable.Matchbox_3,
        "Tomica" to Res.drawable.Tomica_3,
        "Kaido House" to Res.drawable.KaidoHouse,
        "Tarmac Works" to Res.drawable.Tarmac,
        "Pop Race" to Res.drawable.pr_2,
        "Inno 64" to Res.drawable.inno64,
        "Auto World" to Res.drawable.AutoWorldLogo,
        "GreenLight" to Res.drawable.GL_4,
        "Johnny Lightning" to Res.drawable.JohnnyLightning,
        "Majorette" to Res.drawable.majorette_2,
        "M2 Machines" to Res.drawable.M2_4,
        "Jada Toys" to Res.drawable.Jada_2,
        "Maisto" to Res.drawable.Maisto_2,
        "Solido" to Res.drawable.Solido_2,
        "MINI GT" to Res.drawable.miniGT_2,
        "Bburago" to Res.drawable.BuragoLogo
    )

    //var selectedItem by remember { mutableStateOf<String?>(null) } if we use this it will only show the one selected item
    var selectedItem by remember { mutableStateOf<List<UserSelectedBrandCars>>(emptyList()) }// it will allow user to select multiple box

    // on starting the bar is not visible that's why we put false otherwise till will think bar is visible and animation will not happen
    var boxVisible by remember { mutableStateOf(false) }
    var linesVisible by remember { mutableStateOf(false) }

    val usernameFont = FontFamily(Font(Res.font.Amarante_Regular))
    val menuItemData = listOf("HotWheels", "MatchBox", "Tomica", "Kaido House", "Tarmac Works", "Pop Race", "Inno 64",
        "Auto World", "GreenLight", "Johnny Lightning", "Majorette", "M2 Machines", "Jada Toys", "Maisto", "Solido", "MINI GT", "Bburago")
    val snackbarHostState = remember { SnackbarHostState()}
    val scope = rememberCoroutineScope ()
    var expand by remember { mutableStateOf(false) }// if dropdown is open or closed
    var contextMenuCarBrandId by remember { mutableStateOf<Int?>(null) }///??????
    fun displayBrand(){
        userViewModel.getSelectedCarBrandsVM(userIdPP){ savedBrands ->
            selectedItem = savedBrands
            // when user loads the page, calls getSelectedCarBrandsVM to get the whatever brands user selected
        }
    }

    LaunchedEffect(Unit){
        boxVisible = true
        linesVisible = true
        displayBrand()
    }

    Scaffold (
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    )
    {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.background)
        )
        {

            Column(
                modifier = Modifier
                    .fillMaxSize()
            )
            {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Brush.horizontalGradient(colors.headerGradient))
                ){
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                            .statusBarsPadding(), // pushes below status bar
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically

                        ) {
                            val currentUser = user

                            if(!currentUser?.userPhotoUser.isNullOrBlank()){
                                val photoValue = currentUser.userPhotoUser
                                val model = if (photoValue.startsWith("http")){
                                    photoValue // it's a remote DiceBear URL, use as-is
                                }else{
                                    imageStorage.getFullPath(photoValue) // it's a local file, resolve full path
                                }
                                AsyncImage(
                                    //model = imageStorage.getFullPath(currentUser.userPhotoUser),
                                    model = model,
                                    contentDescription = "Profile Picture",
                                    modifier = Modifier
                                        .size(80.dp)
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                            }else
                                Box(
                                    modifier = Modifier
                                        .size(70.dp)
                                        .clip(CircleShape)
                                        .background(colors.slots[2].bg),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = "Default Profile picture",
                                        tint = textColor,
                                        modifier = Modifier.size(50.dp)
                                    )
                                }

                            Text(
                                text = " $usernamePP",
                                //modifier = Modifier.padding(start = 10.dp),
                                textAlign = TextAlign.Start,
                                color = Color.White,
                                fontSize = 28.sp,
                                fontFamily = usernameFont
                            )
                        }

                        IconButton(
                            onClick = {
                                userViewModel.updateDarkModeVM(userIdPP, !isDarkMode) {}
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

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(state = rememberScrollState())
                        .padding(horizontal = 20.dp, vertical = 20.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .clip(shape = RoundedCornerShape(12.dp))
                            .background(Brush.horizontalGradient(colors.headerGradient)),// should be opposite color go the mode
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Column (
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
                        ){
                            Text(
                                text = "$totalCarUserOwn",
                                color = Color.White,
                                fontFamily = usernameFont,
                                fontWeight = FontWeight.Bold,
                                fontSize =25.sp
                            )
                            Text(
                                text = "Total cars own",
                                color = Color.White,
                                fontFamily = usernameFont,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                        }
                    }

                    Text(
                        text = "Brands",
                        color = textColor.copy(alpha = 0.6f),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = usernameFont,
                        modifier = Modifier.padding(top = 20.dp, bottom = 10.dp)
                    )

                    menuItemData.forEachIndexed { index, brandName ->
                        // Convert each string to a UserSelectedBrandCars object (or your brand UI item)
                        val brandItem = remember(brandName) {
                            UserSelectedBrandCars(
                                userSelectedCarBrandId = index,
                                userIdFromUsers = userIdPP,
                                selectedBrandName = brandName
                            )
                        }

                        ItemBox(
                            selectedBrandItem = brandItem,
                            itemIndex = index,
                            userViewModel = userViewModel,
                            colors = colors,
                            brandId = userIdPP,
                            logo = brandLogos[brandName],
                            goToUserCarCollection = goToUserCarCollection,
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }

/*
                AnimatedVisibility(
                    visible = boxVisible,
                    enter = slideInHorizontally(
                        animationSpec = tween(durationMillis = 800),
                        initialOffsetX = { fullWidth -> -fullWidth }
                    )
                )
                {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFF0396B))
                            .padding(horizontal = 20.dp, vertical = 20.dp)
                    )
                    {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val currentUser = user

                            if(!currentUser?.userPhotoUser.isNullOrBlank()){
                                val photoValue = currentUser.userPhotoUser
                                val model = if (photoValue.startsWith("http")){
                                    photoValue // it's a remote DiceBear URL, use as-is
                                }else{
                                    imageStorage.getFullPath(photoValue) // it's a local file, resolve full path
                                }
                                AsyncImage(
                                    //model = imageStorage.getFullPath(currentUser.userPhotoUser),
                                    model = model,
                                    contentDescription = "Profile Picture",
                                    modifier = Modifier
                                        .size(80.dp)
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                            }else
                                Image(
                                    painter = painterResource(Res.drawable.profile_icon),
                                    contentDescription = "Default Profile picture",
                                    modifier = Modifier
                                        .size(80.dp)
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop
                                )

                            Text(
                                text = "Hello $usernamePP",
                                modifier = Modifier.padding(start = 10.dp),
                                textAlign = TextAlign.Start,
                                color = Color.White,
                                fontSize = 28.sp,
                                fontFamily = usernameFont
                            )
                        }
                    }
                }


                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 40.dp, end = 10.dp, top = 20.dp, bottom = 10.dp)
                        .height(100.dp),// height of the box
                    elevation = CardDefaults.cardElevation(defaultElevation = 20.dp),// useless
                    colors = CardDefaults.elevatedCardColors(containerColor = Color(0xFFFF9800)),
                )
                {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Text(
                            text = "Total Collection $totalCarUserOwn",
                            fontWeight = FontWeight.Bold,
                            fontFamily = usernameFont,
                            modifier = Modifier
                                .padding(start = 16.dp, top = 30.dp ),
                            textAlign = TextAlign.Start,
                            fontSize = 20.sp,
                            color = Color.Black,
                        )

                        // We put in Box so dropdown and icon won't move
                        Row{
                            IconButton(
                                modifier = Modifier.padding(top = 30.dp),
                                onClick = {expand = !expand}
                            ){
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Add",
                                    tint = Color.Black
                                )
                            }
                            DropdownMenu(
                                expanded = expand,
                                onDismissRequest = { expand = false },
                                modifier = Modifier
                                    .height(300.dp)// height of the dropdown
                                    .background(Color(0xFF1A1A1A))

                            )
                            {
                                menuItemData.forEach { option ->
                                    DropdownMenuItem(
                                        text =
                                            {
                                                Text(
                                                    option,// stores what user currently selected one
                                                    color = Color(0xFFFF9800)
                                                )
                                            },
                                        onClick = {
                                            // map -> map is used to take every item in a collection and transform it into something else
                                            // for example in UserSelectedBrand{1, "HotWheels"}

                                            //option is a String but UserSelectedBrandCars not string, so it selected brand name and turn that to string
                                            if(option !in selectedItem.map { it.selectedBrandName }){//Take every UserSelectedBrandCars and give me only its selectedBrandName.

                                                //selectedItem = selectedItem + option  not needed. Room saves it, and then you reload the list from Room.
                                                userViewModel.insertSelectedCarBrandVM(userIdPP, option ){
                                                    displayBrand()
                                                }
                                                //now it will store in room (UserSelectedBrandCar table) will not disappear when we navigate to next page

                                                expand = false
                                            }else{
                                                scope.launch{
                                                    snackbarHostState.showSnackbar(
                                                        message = "You already selected.",
                                                        duration = SnackbarDuration.Indefinite
                                                    )
                                                    delay(2000)
                                                }
                                                expand = false
                                            }
                                        }
                                    )
                                    HorizontalDivider(thickness = 2.dp, color = Color(0xFFF0396B))
                                }
                            }
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        //.padding(bottom = 90.dp)
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                )
                {
                    selectedItem.forEach { item ->
                        ItemBox(
                            selectedBrandItem = item,
                            brandId = userIdPP,
                            goToUserCarCollection = goToUserCarCollection,
                            onLongPressCar = { contextMenuCarBrandId = it.userSelectedCarBrandId }
                            //onClick = { goToUserCarCollection(item, userIdPP) }
                        )
                    }
                }*/
/*
                BrandOrbitCarousel(
                    brands = listOf(
                        BrandItem("HotWheels"),
                        BrandItem("MatchBox"),
                        BrandItem("Tomica"),
                        BrandItem("Kaido House"),
                        BrandItem("Tarmac Works"),
                        BrandItem("Pop Race"),
                        BrandItem("Inno 64"),
                        BrandItem("Auto World"),
                        BrandItem("GreenLight"),
                        BrandItem("Johnny Lightning"),
                        BrandItem("Majorette"),
                        BrandItem("M2 Machines"),
                        BrandItem("Jada Toys"),
                        BrandItem("Maisto"),
                        BrandItem("Solido"),
                        BrandItem("MINI GT"),
                        BrandItem("Bburago")
                    ),
                    colors = colors,
                    modifier = Modifier.fillMaxWidth().height(700.dp),
                    onBrandClick = { brand -> goToUserCarCollection(brand.name, userIdPP) }
                )*/

                Row(
                    modifier = Modifier
                        //.align(Alignment.BottomCenter)
                        .fillMaxWidth()
                    //.padding(vertical = 10.dp)
                    ,
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {
                    IconButton(
                        modifier = Modifier
                            .padding(start = 50.dp),
                        onClick = {
                            onBackToLogin()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "Logout",
                            tint = textColor
                        )
                    }

                    IconButton(
                        modifier = Modifier
                            .padding(end = 50.dp),
                        onClick = {
                            goToSetting(userIdPP)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = textColor
                        )
                    }
                }
            }

        }
    }
}

data class BrandItem(val name: String, val logo: DrawableResource? = null)

@Composable
fun BrandOrbitCarousel(
    brands: List<BrandItem>,
    colors: ThemeColors,
    modifier: Modifier = Modifier,
    idleSpeed: Float = 0.5f,
    dragSensitivity: Float = 0.003f,
    onBrandClick: (BrandItem) -> Unit
) {
    val rotation = remember { mutableFloatStateOf(0f) }
    var isDragging by remember { mutableStateOf(false) }

    // Increased horizontal spread to give cards breathing room
    val horizontalRadius = 1200.dp
    // Height of the upward arch in the center
    val curveHeight = 200.dp
    // Slight Z-rotation tilt for outer cards
    val maxTiltAngle = 17f

    val density = LocalDensity.current
    val verticalOffset = 300.dp

    LaunchedEffect(isDragging) {
        if (!isDragging) {
            var lastFrameNanos = 0L
            while (isActive) {
                withFrameNanos { frameNanos ->
                    if (lastFrameNanos != 0L) {
                        val deltaSeconds = (frameNanos - lastFrameNanos) / 1_000_000_000f
                        rotation.floatValue += idleSpeed * deltaSeconds
                    }
                    lastFrameNanos = frameNanos
                }
            }
        }
    }

    Box(
        modifier = modifier
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { isDragging = true },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        rotation.floatValue += dragAmount.x * dragSensitivity
                    },
                    onDragEnd = { isDragging = false },
                    onDragCancel = { isDragging = false }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        val n = brands.size

        if (n > 1) {
            brands.forEachIndexed { index, brand ->
                val rawPosition = (index - (n - 1) / 2f) + rotation.floatValue
                val totalPositions = n.toFloat()

                val position = ((rawPosition + totalPositions / 2f).mod(totalPositions) - totalPositions / 2f)
                val normalizedX = (position / ((n - 1) / 2f)).coerceIn(-1f, 1f)

                // Spread cards out horizontally
                val xOffsetPx = with(density) { (normalizedX * horizontalRadius.toPx()).toFloat() }

                // NEGATIVE sign curves cards DOWNWARD at the edges (arch shape)
                val yOffsetPx = with(density) {
                    (
                            verticalOffset.toPx() -
                                    curveHeight.toPx() * (1f - normalizedX * normalizedX)
                            ).toFloat()
                }

                // Tilts cards outward along the arch curve
                val rotationZAngle = normalizedX * maxTiltAngle

                val distanceFromCenter = kotlin.math.abs(normalizedX)

                // Scale center card up and edge cards down
                val scale = 1f - (distanceFromCenter * 0.2f)
                val alphaVal = (1f - (distanceFromCenter * 0.35f)).coerceIn(0.3f, 1f)

                // Ensures the center card is layered ON TOP of side cards
                val zIndexVal = 1f - distanceFromCenter

                val slot = colors.slots[index % colors.slots.size]

                Box(
                    modifier = Modifier
                        .zIndex(zIndexVal) // Layering order fix
                        .graphicsLayer {
                            translationX = xOffsetPx
                            translationY = yOffsetPx
                            rotationZ = rotationZAngle
                            scaleX = scale
                            scaleY = scale
                        }
                        .size(width = 200.dp, height = 300.dp) // Adjust card proportions
                        .alpha(alphaVal)
                        .clip(RoundedCornerShape(16.dp))
                        .background(slot.bg)
                        .clickable { onBrandClick(brand) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = brand.name,
                        color = slot.text,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center,
                        maxLines = 2,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemBox(
    selectedBrandItem: UserSelectedBrandCars,
    itemIndex: Int,
    userViewModel: UserViewModel,
    colors: ThemeColors,
    brandId: Int,
    logo: DrawableResource?,
    goToUserCarCollection: (String, Int) -> Unit,
) {
    val usernameFont = FontFamily(Font(Res.font.Amarante_Regular))
    val haptics = LocalHapticFeedback.current
    val textColor = colors.slots[0].text
    val accent = colors.headerGradient[0]
    val slot = colors.slots[itemIndex % colors.slots.size]

    var getCarsFromThisBrand by remember { mutableStateOf<List<UserCar>>(emptyList()) }
    val totalCarThisBrandOwns = getCarsFromThisBrand.size

    LaunchedEffect(selectedBrandItem.selectedBrandName) {
        userViewModel.getUserOwnedCarsByBrandVM(
            brandId,
            selectedBrandItem.selectedBrandName
        ) { cars ->
            getCarsFromThisBrand = cars
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .clip(RoundedCornerShape(16.dp)) // CLIP FIRST
            .background(accent.copy(alpha = 0.15f)) // BACKGROUND SECOND
            .clickable{ goToUserCarCollection(selectedBrandItem.selectedBrandName, brandId) }
            .padding(horizontal = 14.dp), // Inner padding for all contents
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon Box (square aspect, properly rounded)
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(slot.bg),
            contentAlignment = Alignment.Center
        ) {
            if (logo != null) {
                Image(
                    painter = painterResource(logo),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            } else {
                Icon(
                    imageVector = Icons.Default.DirectionsCar,
                    contentDescription = null,
                    tint = slot.text,
                    modifier = Modifier.size(26.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(14.dp))

        // Brand Name + Count Text
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = selectedBrandItem.selectedBrandName,
                color = textColor,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = usernameFont
            )

            Text(
                text = if (totalCarThisBrandOwns == 1) "$totalCarThisBrandOwns car" else "$totalCarThisBrandOwns cars",
                color = textColor.copy(alpha = 0.6f),
                fontSize = 13.sp,
                fontFamily = usernameFont
            )
        }

        // Right chevron arrow matching screenshot 2
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = textColor.copy(alpha = 0.4f),
            modifier = Modifier.size(20.dp)
        )
    }
}