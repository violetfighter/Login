package com.cfcici.`in`.project

expect class ImageStorage(context: Any?) {
    fun saveImageToFile(bytes: ByteArray, fileName: String): String
    fun loadImageFromFile(path: String): ByteArray?

}