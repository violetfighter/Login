package com.cfcici.`in`.project.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun ProfilePage(usernamePP: String,
                passwordPP: String){
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = 40.dp)
        .background(Color.White)


    ){
        Text(
            text = "Username: $usernamePP",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start)

        Text(
            text = "PassWord: $passwordPP",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start)
    }
}

@Preview
@Composable
fun ProfilePagePreview(){
    ProfilePage(
        usernamePP = "parvathi",
    passwordPP = "PaaQQ!!!123")
}
