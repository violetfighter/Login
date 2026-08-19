package com.cfcici.`in`.project


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitViewController
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation
import platform.UIKit.UIImagePickerController
import platform.UIKit.UIImagePickerControllerDelegateProtocol
import platform.UIKit.UIImagePickerControllerOriginalImage
import platform.UIKit.UIImagePickerControllerSourceType
import platform.UIKit.UINavigationControllerDelegateProtocol
import platform.darwin.NSObject
import kotlinx.cinterop.CValue
import platform.CoreGraphics.CGRect

@OptIn(ExperimentalForeignApi::class)
private class CameraPickerDelegate(
    private val onImageCaptured: (ByteArray) -> Unit,
    private val onDismiss: () -> Unit
) : NSObject(), UIImagePickerControllerDelegateProtocol, UINavigationControllerDelegateProtocol {

    override fun imagePickerController(
        picker: UIImagePickerController,
        didFinishPickingMediaWithInfo: Map<Any?, *>
    ) {
        val image = didFinishPickingMediaWithInfo[UIImagePickerControllerOriginalImage] as? UIImage
        val data = image?.let { UIImageJPEGRepresentation(it, 0.9) }
        if (data != null) {
            onImageCaptured(data.toByteArray())
        } else {
            onDismiss()
        }
    }

    override fun imagePickerControllerDidCancel(picker: UIImagePickerController) {
        onDismiss()
    }
}

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun CameraCapture(
    onImageCaptured: (ByteArray) -> Unit,
    onDismiss: () -> Unit
) {
    val delegate = remember { CameraPickerDelegate(onImageCaptured, onDismiss) }

    UIKitViewController(
        factory = {
            UIImagePickerController().apply {
                // The Simulator has no camera hardware — Apple doesn't emulate it.
                // Fall back to the photo library there so testing doesn't crash;
                // on a real device this correctly opens the camera.
                sourceType = if (UIImagePickerController.isSourceTypeAvailable(
                        UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera
                    )
                ) {
                    UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera
                } else {
                    UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypePhotoLibrary
                }
                allowsEditing = false
                this.delegate = delegate
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

@OptIn(ExperimentalForeignApi::class)
private fun NSData.toByteArray(): ByteArray {
    val size = this.length.toInt()
    if (size == 0) return ByteArray(0)
    val bytes = ByteArray(size)
    bytes.usePinned { pinned ->
        platform.posix.memcpy(pinned.addressOf(0), this.bytes, this.length)
    }
    return bytes
}