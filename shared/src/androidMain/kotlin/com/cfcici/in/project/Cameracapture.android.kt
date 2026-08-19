package com.cfcici.`in`.project

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.preat.peekaboo.ui.camera.PeekabooCamera
import com.preat.peekaboo.ui.camera.rememberPeekabooCameraState

@Composable
actual fun CameraCapture(
    onImageCaptured: (ByteArray) -> Unit,
    onDismiss: () -> Unit
) {
    val cameraState = rememberPeekabooCameraState(onCapture = { bytes ->
        if (bytes != null) {
            onImageCaptured(bytes)
        } else {
            onDismiss()
        }
    })

    Box(modifier = Modifier.fillMaxSize()) {
        PeekabooCamera(
            state = cameraState,
            modifier = Modifier.fillMaxSize(),
            permissionDeniedContent = {
                Column(
                    modifier = Modifier.background(Color.Black).fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Camera permission denied", color = Color.White)
                    TextButton(onClick = { onDismiss() }) {
                        Text("Close")
                    }
                }
            }
        )
        IconButton(
            onClick = { cameraState.capture() },
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.PhotoCamera,
                contentDescription = "Capture",
                tint = Color.White,
                modifier = Modifier.size(48.dp)
            )
        }
    }
}