package com.cfcici.`in`.project

import androidx.compose.runtime.Composable

/**
 * Full-screen native camera capture UI.
 * Android -> Peekaboo's camera (already working, unchanged).
 * iOS -> native UIImagePickerController (Peekaboo's iOS camera is dropped due to
 *        the IrLinkageError caused by Peekaboo 0.5.2 targeting an old Compose UIKitView signature).
 *
 * onImageCaptured is called with the JPEG bytes on success.
 * onDismiss is called if the user cancels or capture fails.
 */
@Composable
expect fun CameraCapture(
    onImageCaptured: (ByteArray) -> Unit,
    onDismiss: () -> Unit
)