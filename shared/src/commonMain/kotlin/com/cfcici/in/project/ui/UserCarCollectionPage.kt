package com.cfcici.`in`.project.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.material.icons.filled.Backup
import androidx.compose.material.icons.filled.Filter
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardDoubleArrowLeft
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import login.shared.generated.resources.Amarante_Regular
import login.shared.generated.resources.Res
import login.shared.generated.resources.wp12533988
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDefaults.color
import androidx.compose.material3.Surface
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import com.cfcici.`in`.project.data.database.UserCar
import login.shared.generated.resources.nissan_skyline
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserCarCollectionPage(UserCCPBrand: String, UserCCPUserId: Int){

    val testCars = listOf(
        UserCar(
            userIdUser = 1,
            brandUser = "Hot Wheels",
            modelUser = "Nissan Skyline GT-R",
            yearUser = 2024,
            colourUser = "Blue",
            seriesUser = "J-Imports",
            typeOfSeriesUser = "Mainline",
            collectorNoUser = "123",
            photoUser = ""
        ),

        UserCar(
            userIdUser = 1,
            brandUser = "Hot Wheels",
            modelUser = "Toyota Supra",
            yearUser = 2024,
            colourUser = "Red",
            seriesUser = "J-Imports",
            typeOfSeriesUser = "Mainline",
            collectorNoUser = "124",
            photoUser = ""
        )
    )

    val usernameFont = FontFamily(Font(Res.font.Amarante_Regular))

    Scaffold(
        /*
        containerColor = Color.Black,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* navigate to add-car screen later */ },
                containerColor = Color.Transparent,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add new car",
                    modifier = Modifier.size(25.dp)
                )
            }
        }*/
    )
    {
        LazyColumn(
        modifier = Modifier.fillMaxSize()
            .background(Color.Black),//.clip(RoundedCornerShape(24.dp))
    )
        {
            item{
                Box(
                modifier = Modifier.fillMaxWidth().height(200.dp)
                ){
                    Image(
                    painter = painterResource(Res.drawable.wp12533988),
                    contentDescription = "Hotwheels",
                    modifier = Modifier.fillMaxSize(),)
                    IconButton(
                        onClick = {

                        },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp),
                        )
                    {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = null,
                        tint = Color.White
                    )
                    }
                    IconButton(
                    onClick = {},
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp),

                        ){
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.padding(15.dp))
            Text(
                text = "Hotwheels",
                color = Color.White,
                fontFamily = usernameFont,
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp,
                modifier = Modifier.padding(horizontal = 10.dp),
            )
            Text(text = "Collection",
                color = Color(0xFFF0396B),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                fontFamily = usernameFont,
                modifier = Modifier.padding(horizontal = 15.dp),
                textAlign = TextAlign.Center
            )

        }
        item {
            var searchText by remember { mutableStateOf("") }
            Row {
                SearchBar(
                    inputField = {
                        SearchBarDefaults.InputField(
                            query = searchText,
                            onQueryChange = { searchText = it },
                            onSearch = {
                                /* Search later */
                            },
                            expanded = false,
                            onExpandedChange = { },
                            placeholder = { Text("Search cars here...") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search button",
                                    tint = Color.White
                                )
                            },
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.FilterList,//Tune
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color(0xFFF0396B),
                                unfocusedIndicatorColor = Color(0xFFF0396B),
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                )
                        )
                    },
                    expanded = false,
                    onExpandedChange = { },
                    modifier = Modifier
                        .padding(10.dp),
                    //shape = RoundedCornerShape(50),
                    colors = SearchBarDefaults.colors(
                        containerColor = Color.Transparent,
                        //(0xFF1A1A1A),
                        //dividerColor = Color.White,
                    ),
                    content = { }
                )
                // Add icon for filter *************************************************************
            }
        }
            item {
                testCars.forEach {car ->
                    EachCarTab(
                        // because you use UserCar as datatype, you don't need to mention the car.model
                        selectedCar =  car
                    )
                }
            }
        }
    }
}

@Composable
//if you are using : UserCar you are passing all the parameter in the UserCar instead of only one datatype
fun EachCarTab(selectedCar: UserCar)
{
    val usernameFont = FontFamily(Font(Res.font.Amarante_Regular))

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
            Column(modifier = Modifier.padding(vertical = 5.dp, horizontal = 25.dp)){
                Image(
                    painter = painterResource(Res.drawable.nissan_skyline),
                    contentDescription = selectedCar.modelUser,
                    modifier = Modifier
                        .size(90.dp)
                        .clip(RectangleShape),
                    contentScale = ContentScale.Crop
                )
            }// need to add photo
            
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            // even gap between every child no matter how many rows you have it will automatically space it equally
            ) {
                Text(
                    text = selectedCar.modelUser,
                    color = Color.White,
                    fontFamily = usernameFont,
                    fontSize = 20.sp
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
                        fontSize = 15.sp
                    )
                }
                Row {
                    // Because Text expect string you should convert to string
                    Text(
                        text = selectedCar.collectorNoUser.toString(),
                        color = Color.White,
                        fontFamily = usernameFont,
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.width(30.dp))
                    Text(
                        text = selectedCar.seriesUser.toString(),
                        color = Color.White,
                        fontFamily = usernameFont,
                        fontSize = 15.sp
                    )
                    Spacer(Modifier.width(30.dp))

                    Text(
                        text = selectedCar.typeOfSeriesUser.toString(),
                        color = Color.White,
                        fontFamily = usernameFont,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun UserCarCollectionPagePreview() {
    UserCarCollectionPage(
        UserCCPBrand = "Hot Wheels",
        UserCCPUserId = 1
    )
}