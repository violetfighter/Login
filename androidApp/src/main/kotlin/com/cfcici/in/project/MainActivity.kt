package com.cfcici.`in`.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.cfcici.`in`.project.data.database.DBFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            val db = DBFactory(applicationContext).createDatabase()
            App(db)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    // Preview might need a mock database, for now we can't easily provide one
    // Text("App Preview")
}