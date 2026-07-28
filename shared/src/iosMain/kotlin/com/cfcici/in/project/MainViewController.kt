package com.cfcici.`in`.project

import androidx.compose.ui.window.ComposeUIViewController
import com.cfcici.`in`.project.data.database.DBFactory

fun MainViewController() = ComposeUIViewController {
    val db = DBFactory(null).createDatabase()
    App(db)
}
