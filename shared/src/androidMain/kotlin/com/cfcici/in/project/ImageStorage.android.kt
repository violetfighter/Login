package com.cfcici.`in`.project

import android.content.Context
import java.io.File

actual class ImageStorage actual constructor(private val context: Any?) {

    actual fun saveImageToFile(bytes: ByteArray, fileName: String): String {
        val appContext = context as Context
        val file = File(appContext.filesDir, "car_photos/$fileName")
        file.parentFile?.mkdirs()
        file.writeBytes(bytes)
        return fileName   // ← store just the filename now, not file.absolutePath
    }

    actual fun getFullPath(fileName: String): String {
        val appContext = context as Context
        return File(appContext.filesDir, "car_photos/$fileName").absolutePath
    }

    actual fun loadImageFromFile(fileName: String): ByteArray? {
        return try {
            File(getFullPath(fileName)).readBytes()
        } catch (e: Exception) {
            null
        }
    }
}