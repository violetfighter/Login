package com.cfcici.`in`.project

expect class ImageStorage(context: Any?) {
    fun saveImageToFile(bytes: ByteArray, fileName: String): String
    fun getFullPath(fileName: String): String   // ← new
    fun loadImageFromFile(fileName: String): ByteArray?
}