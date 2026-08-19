package com.cfcici.`in`.project

import android.content.Context
import java.io.File

actual class ImageStorage actual constructor(private val context: Any?) {

    actual fun saveImageToFile(bytes: ByteArray, fileName: String): String {
        val appContext = context as Context
        val file = File(appContext.filesDir, "car_photos/$fileName")
        file.parentFile?.mkdirs()
        file.writeBytes(bytes)
        return file.absolutePath
    }
    actual fun loadImageFromFile(path: String): ByteArray? {
        return try {
            File(path).readBytes()
        } catch (e: Exception) {
            null
        }
    }
}