package com.cfcici.`in`.project.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarDefaults.color
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import dev.chrisbanes.haze.blur.HazeBlurDefaults.tint
import login.shared.generated.resources.Amarante_Regular
import login.shared.generated.resources.Res
import org.jetbrains.compose.resources.Font



@Composable
fun ProfilePage(usernamePP: String, passwordPP: String)
{
    //var selectedItem by remember { mutableStateOf<String?>(null) } if we use this it will only show the one selected item
    var selectedItem by remember { mutableStateOf(listOf<String>()) }// it will allow user to select multiple box

    // on starting the bar is not visible that's why we put false otherwise till will think bar is visible and animation will not happen
    var boxVisible by remember { mutableStateOf(false) }
    var linesVisible by remember { mutableStateOf(false) }

    val usernameFont = FontFamily(Font(Res.font.Amarante_Regular, FontWeight.Normal))
    val menuItemData = listOf("HotWheels", "MatchBox", "Tomica", "Mini GT", "Kaido House", "Tarmac Works", "Pop Race",
        "Inno 64", "Auto World", "GreenLight", "Johnny Lightning", "Majorette", "M2 Machines", "Jada Toys", "Maisto", "Solido", "MINI GT", "Bburago")
    val itemColour = listOf(Color(0xFFF28FA3), Color(0xFFF7B267), Color(0xFFF49A9A), Color(0xFFF5C99B))


    var expand by remember { mutableStateOf(false) }// if dropdown is open or closed

    LaunchedEffect(Unit){
        boxVisible = true
        linesVisible = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
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
            Box(
                modifier = Modifier
                    .padding(start = 15.dp)
                    .fillMaxHeight()
                    .width(4.dp)
                    .background(Color(0xFFFF9800))
            )
            Box(
                modifier = Modifier
                    .padding(start = 30.dp)
                    .fillMaxHeight()
                    .width(4.dp)
                    .background(Color(0xFFF0555C))
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
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
                ) {
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
                            text = "PassWord: $passwordPP",
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
                    .padding(start = 40.dp, end = 10.dp, top = 20.dp, bottom = 20.dp)
                    .height(100.dp),// height of the box
                elevation = CardDefaults.cardElevation(defaultElevation = 20.dp),// useless
                colors = CardDefaults.elevatedCardColors(containerColor = Color(0xFFFF9800)),//useless
            ){
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(
                        text = "Total Collection",
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier
                            .padding(start = 16.dp, top = 30.dp, ),
                        textAlign = TextAlign.Start,
                        fontSize = 20.sp,
                        color = Color.Black,
                    )

                    // We put in Box so dropdown and icon won't move
                    Box{
                        IconButton(
                            modifier = Modifier.padding(top = 20.dp),
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
                            modifier = Modifier.height(300.dp).background(Color(0xFF1A1A1A))
                        ){
                            menuItemData.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(
                                            option,// stores what user currently selected one
                                            color = Color(0xFFFF9800)
                                        )},
                                    onClick = {
                                        if(option !in selectedItem){
                                            selectedItem = selectedItem + option
                                        }
                                         expand = false
                                    }
                                )
                                HorizontalDivider(thickness = 2.dp, color = Color(0xFFF0396B))
                            }
                        }
                    }
                }
            }
            selectedItem.forEach{
                item -> ItemBox(selectedItem = item)
            }
        }
    }
}

@Composable
fun ItemBox( selectedItem: String)
{
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 40.dp, end = 10.dp, top = 20.dp, bottom = 20.dp)
            .height(60.dp),
        shape = RoundedCornerShape(7.dp),
        color = Color(0xFFF49A9A)
    )
            {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterStart,

                ){
                    Text(
                        text = selectedItem,
                        modifier = Modifier.padding(start = 16.dp),
                        color = Color(0xFF4A2C3A),
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                    )
                }
            }
}

@Preview
@Composable
fun ProfilePagePreview(){
    ProfilePage(
        usernamePP = "Parvathi",
    passwordPP = "Pass12!@PASS")
}
