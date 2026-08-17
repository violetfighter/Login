package com.cfcici.`in`.project.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.cfcici.`in`.project.data.database.UserSelectedBrandCars
import com.cfcici.`in`.project.viewmodel.UserViewModel
import kotlinx.coroutines.launch
import login.shared.generated.resources.Amarante_Regular
import login.shared.generated.resources.Res
import org.jetbrains.compose.resources.Font



@Composable
fun ProfilePage(usernamePP: String,
                emailPP: String,
                userIdPP: Int,
                onBackToLogin: () -> Unit,
                goToUserCarCollection: (String, Int) -> Unit,
                userViewModel: UserViewModel)
{
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
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ){
        innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.Black)
        )
        {
            AnimatedVisibility(
                visible = linesVisible,
                enter = slideInVertically(
                    animationSpec = tween(durationMillis = 800),
                    initialOffsetY = { fullHeight -> fullHeight }// use negative to move upwards
                )
            ) {
                // vertical line
                Row(
                    modifier = Modifier
                        .padding(start = 15.dp)
                        .fillMaxHeight()
                        .width(4.dp)
                        .background(Color(0xFFFF9800))
                ){}
                Row(
                    modifier = Modifier
                        .padding(start = 30.dp)
                        .fillMaxHeight()
                        .width(4.dp)
                        .background(Color(0xFFF0555C))
                ){}
            }
            Column(
                modifier = Modifier.fillMaxWidth()
            )
            {
                Spacer(modifier = Modifier.height(40.dp))

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
                        Column {
                            Text(
                                text = "Hello $usernamePP",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Start,
                                color = Color.White,
                                fontSize = 30.sp,
                                fontFamily = usernameFont
                            )
                            // remove this and add email
                            Text(
                                text = "Email: $emailPP",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp),
                                textAlign = TextAlign.Start,
                                color = Color.White,
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
                        Text( // Add  total number of cars user own
                            text = "Total Collection",
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
                                            // for example in UserSelectedBrand{1, "HowWheels"}

                                            //option is a String but UserSelectedBrandCars not string, so it selected brand name and turn that to string
                                            if(option !in selectedItem.map { it.selectedBrandName }){//Take every UserSelectedBrandCars and give me only its selectedBrandName.

                                                //selectedItem = selectedItem + option  not needed. Room saves it, and then you reload the list from Room.
                                                userViewModel.insertSelectedCarBrandVM(userIdPP, option, ){
                                                    displayBrand()
                                                }
                                                //now it will store in room (UserSelectedBrandCar table) will not disappear when we navigate to next page

                                                expand = false
                                            }else{
                                                scope.launch{
                                                    snackbarHostState.showSnackbar(
                                                        message = "You already selected.",
                                                        duration = SnackbarDuration.Short
                                                    )
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
                        .padding(bottom = 90.dp)
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
                }
            }
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
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
                        tint = Color.White
                    )
                }

                IconButton(
                    modifier = Modifier
                        .padding(end = 50.dp),
                    onClick = {
                        // do something
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.PhotoCamera,
                        contentDescription = "Camera",
                        tint = Color.White
                    )
                }
            }
        }
        if(contextMenuCarBrandId != null){
            val selectedCarBrandForMenu = selectedItem.firstOrNull { it.userSelectedCarBrandId == contextMenuCarBrandId }
            if (selectedCarBrandForMenu != null) {
                AlertDialog(
                    onDismissRequest = {
                        contextMenuCarBrandId = null
                    },
                    containerColor = Color(0xFF1A1A1A),
                    title = {
                        Text(
                            selectedCarBrandForMenu.selectedBrandName,
                            color = Color.White
                        )
                    },
                    text = {
                        Text(
                            "What would you like to do with this car?",
                            color = Color.LightGray
                        )
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                contextMenuCarBrandId = null
                            }
                        ) {
                            Text(
                                "Edit",
                                color = Color(0xFFFF9800)
                            )
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                userViewModel.deleteSelectedCarBrandVM(
                                    userIdPP,
                                    selectedCarBrandForMenu.selectedBrandName,
                                ) {
                                    displayBrand()
                                }

                                contextMenuCarBrandId = null
                            }
                        ) {
                            Text(
                                "Delete",
                                color = Color(0xFFF0396B)
                            )
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun ItemBox( selectedBrandItem: UserSelectedBrandCars, brandId: Int, goToUserCarCollection: (String, Int) -> Unit , onLongPressCar: (UserSelectedBrandCars) -> Unit) // add total number of cars in each brand
{
    val usernameFont = FontFamily(Font(Res.font.Amarante_Regular, FontWeight.Normal))
    val haptics = LocalHapticFeedback.current

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 40.dp, end = 10.dp, top = 10.dp, bottom = 10.dp)
            .height(60.dp)
            .combinedClickable(
                onClick = {
                    goToUserCarCollection(selectedBrandItem.selectedBrandName, brandId)
                },
                onLongClick = {
                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                    onLongPressCar(selectedBrandItem)
                }

            ),
        shape = RoundedCornerShape(7.dp),
        color = Color.Transparent
    )
    {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.CenterStart,
            ){
            Text(
                text = selectedBrandItem.selectedBrandName,
                modifier = Modifier.padding(start = 16.dp),
                color = Color(0xFFF0396B),
                fontWeight = FontWeight.Bold,
                fontFamily = usernameFont
            )
        }
    }
}