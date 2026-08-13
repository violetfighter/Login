package com.cfcici.`in`.project.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import login.shared.generated.resources.Amarante_Regular
import login.shared.generated.resources.Res
import login.shared.generated.resources.wp12533988
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.cfcici.`in`.project.data.database.UserCar
import com.cfcici.`in`.project.viewmodel.UserViewModel
import login.shared.generated.resources.nissan_skyline
@OptIn(ExperimentalMaterial3Api::class)///****************
@Composable
fun UserCarCollectionPage( userCCPBrand: String, userCCPUserId: Int, goBackToProfile: () -> Unit, userViewModel: UserViewModel){

    val usernameFont = FontFamily(Font(Res.font.Amarante_Regular))
    var showAddCarDialog by remember { mutableStateOf(false) }

    //From all my cars, give me only the cars that belong to the brand the user clicked.
    var getCarsFromThisBrand by remember { mutableStateOf<List<UserCar>>(emptyList()) }
    var searchText by remember { mutableStateOf("") }
    val totalCarThisBrandOwns = getCarsFromThisBrand.size

    val filteringForCarSearch = getCarsFromThisBrand.filter { car -> searchText.isBlank()||
            car.modelUser.contains(searchText, ignoreCase = true) ||
            car.yearUser?.toString()?.contains(searchText, ignoreCase = true) == true || // because it's expect result boolean should do == true
            car.seriesUser?.contains(searchText, ignoreCase = true)  == true ||
            car.colourUser.contains(searchText, ignoreCase = true) ||
            car.typeOfSeriesUser?.contains(searchText, ignoreCase = true) == true ||
            car.collectorNoUser?.contains(searchText, ignoreCase = true) == true
    }

    fun displayCarFromThisBrand(){
        userViewModel.getUserOwnedCarsByBrandVM(userCCPUserId, userCCPBrand){
                cars ->
            getCarsFromThisBrand = cars
        }
    }

    LaunchedEffect(Unit) {
        displayCarFromThisBrand()
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .background(Color.Black),
    )
    {
        item{
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Image(
                    painter = painterResource(Res.drawable.wp12533988),
                    contentDescription = "Hotwheels",
                    modifier = Modifier.fillMaxSize(),
                )
                IconButton(
                    onClick = {
                        goBackToProfile () },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(start = 5.dp, top = 35.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "go back to profile page",
                        tint = Color.White)
                }
                IconButton(
                    onClick = {
                        showAddCarDialog = true
                    },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(end = 5.dp, top = 35.dp),
                ){
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
            if(showAddCarDialog){
                AddNewCar(
                    userCCPBrand = userCCPBrand,
                    userCCPUserId = userCCPUserId,
                    userViewModel = userViewModel,
                    onDismissRequest = {showAddCarDialog = false},// if user hit dismiss don't add car
                    onConfirmation = {
                        showAddCarDialog = false
                        displayCarFromThisBrand()}// user want to save the car. but close also save the car later
                )
            }
        }

        item {
            Spacer(modifier = Modifier.padding(top = 15.dp))
            Text(
                text = userCCPBrand,
                color = Color.White,
                fontFamily = usernameFont,
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp,
                modifier = Modifier.padding(horizontal = 10.dp),
            )
            Text(
                text = "$totalCarThisBrandOwns",
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
                        Icon(
                            imageVector = Icons.Default.FilterList,
                            contentDescription = null,
                            tint = Color.White
                        )
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
            if (filteringForCarSearch.isEmpty()){
                Column {  }
                Text(
                    text = "Not Found",
                    color = Color.LightGray,
                    fontFamily = usernameFont,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 50.dp)
                )
            }else{
                filteringForCarSearch.forEach { car ->
                    EachCarTab(selectedCar = car) }
            }
        }
    }
    /*Scaffold()
    {
    }*/
}

@Composable
//if you are using : UserCar you are passing all the parameter in the UserCar instead of only one datatype
fun EachCarTab(selectedCar: UserCar)
{
    val usernameFont = FontFamily(Font(Res.font.Amarante_Regular))
    val haptics = LocalHapticFeedback.current// it vibrat/buzz when you do long press

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(100.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.DarkGray
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
                    .combinedClickable(
                        onClick = {})
            )
            {
                Image(
                    painter = painterResource(Res.drawable.nissan_skyline),
                    contentDescription = selectedCar.modelUser,
                    modifier = Modifier
                        .size(90.dp)
                        .clip(RectangleShape),
                    contentScale = ContentScale.Crop////????????????
                )
            }// need to add photo
            
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
                        text = selectedCar.yearUser.toString(),
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
fun AddNewCar(userCCPBrand: String, userCCPUserId: Int, userViewModel: UserViewModel,
              onDismissRequest: () -> Unit, onConfirmation: ()-> Unit){
    val modelName = rememberTextFieldState()
    val modelColour = rememberTextFieldState()
    val seriesOfModel = rememberTextFieldState()
    val typeOfSeries = rememberTextFieldState()
    val modelCollectionNumber = rememberTextFieldState()
    var selectedYear by remember { mutableStateOf("") }// initially drop down will close
    //val modelPhoto =

    var modelNameError by remember { mutableStateOf<String?>(null) }
    var modelPhotoError by remember { mutableStateOf<String?>(null) }
    var modelColourError by remember { mutableStateOf<String?>(null) }


    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(// Dialog have its own space so even if you make it wide use this line it remove the default space in Dialog
            usePlatformDefaultWidth = false)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .height(550.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF1A1A1A)
                )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    )
                    {
                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
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
                                supportingText = {
                                    if (modelNameError != null){
                                        Text("Name of the model is required ")
                                    }
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
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
                        ) {
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
                                supportingText = {if (modelColourError != null){
                                    Text("Colour of the model is required")
                                } }
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
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
                        ) {
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
                        ) {
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
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
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
                                if (modelName.text.isEmpty()){
                                    modelNameError = ""
                                }else
                                    modelNameError = null

                                if (modelColour.text.isEmpty()){
                                    modelColourError = ""
                                }else
                                    modelColourError = null

                                if (modelName.text.isNotEmpty() || modelColour.text.isNotEmpty()){
                                    userViewModel.insertUserOwnedCarVM(
                                        userCCPUserId, userCCPBrand, modelName.text.toString(),
                                        selectedYear.toIntOrNull(), modelColour.text.toString(), seriesOfModel.text.toString(),
                                        typeOfSeries.text.toString(), modelCollectionNumber.text.toString(),
                                        "", onResult = {onConfirmation()}
                                    )
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