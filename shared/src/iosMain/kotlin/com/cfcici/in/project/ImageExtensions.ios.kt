package com.cfcici.`in`.project

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asSkiaBitmap
import org.jetbrains.skia.EncodedImageFormat
import org.jetbrains.skia.Image

actual fun ImageBitmap.encodeToByteArray(): ByteArray {
    val skiaBitmap = this.asSkiaBitmap()
    val skiaImage = Image.makeFromBitmap(skiaBitmap)
    val data = skiaImage.encodeToData(EncodedImageFormat.JPEG, 90)
    return data?.bytes ?: byteArrayOf()
}
