package com.cfcici.`in`.project.ui

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object SessionManager {
    var accessToken: String? = null

    private val _expired = MutableStateFlow(false)
    val expired = _expired.asStateFlow()

    fun login(token: String) {
        accessToken = token
        _expired.value = false
    }

    fun expire() {
        accessToken = null
        _expired.value = true
    }
}