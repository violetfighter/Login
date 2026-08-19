package com.cfcici.`in`.project

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSUserDomainMask
import platform.Foundation.dataWithBytes
import platform.Foundation.dataWithContentsOfFile
import platform.Foundation.writeToFile
import platform.posix.memcpy

actual class ImageStorage actual constructor(private val context: Any?) {

    actual fun saveImageToFile(bytes: ByteArray, fileName: String): String {
        val documentsDir = NSSearchPathForDirectoriesInDomains(
            NSDocumentDirectory, NSUserDomainMask, true
        ).first() as String
        val filePath = "$documentsDir/$fileName"
        bytes.toNSData().writeToFile(filePath, atomically = true)
        return filePath
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun ByteArray.toNSData(): NSData = this.usePinned {
        NSData.dataWithBytes(bytes = it.addressOf(0), length = this.size.toULong())
    }

    @OptIn(ExperimentalForeignApi::class)
    actual fun loadImageFromFile(path: String): ByteArray? {
        return try {
            NSData.dataWithContentsOfFile(path)?.toKotlinByteArray()
        } catch (_: Exception) {
            null
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun NSData.toKotlinByteArray(): ByteArray {
        val size = this.length.toInt()
        val result = ByteArray(size)
        if (size > 0) {
            result.usePinned {
                memcpy(it.addressOf(0), this.bytes, this.length)
            }
        }
        return result
    }
}