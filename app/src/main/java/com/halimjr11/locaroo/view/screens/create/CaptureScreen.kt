package com.halimjr11.locaroo.view.screens.create

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import java.io.File

@Composable
fun CaptureScreen(
    navController: NavController,
    padding: PaddingValues = PaddingValues()
) {
    val context = LocalContext.current

    val tempUriState = remember { mutableStateOf<Uri?>(null) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            if (granted) {
                val uri = createTempImageUri(context)
                tempUriState.value = uri
                takePictureLauncher.launch(uri)
            }
        }
    )

    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success: Boolean ->
            if (success) {
                tempUriState.value?.let { uri ->
                    // Persist path to return to CreateScreen
                    val path = uriToAbsolutePath(context, uri)
                    if (!path.isNullOrBlank()) {
                        navController.previousBackStackEntry?.savedStateHandle?.set("capturedImagePath", path)
                        navController.popBackStack()
                    }
                }
            }
        }
    )

    LaunchedEffect(Unit) {
        val granted = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        if (granted) {
            val uri = createTempImageUri(context)
            tempUriState.value = uri
            takePictureLauncher.launch(uri)
        } else {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(padding),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Camera Ready")
        Button(onClick = {
            ensureCameraPermission(context) {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }) { Text("Open Camera") }
        Button(onClick = { navController.popBackStack() }) { Text("Cancel") }
    }
}

private fun ensureCameraPermission(context: Context, onNeedRequest: () -> Unit) {
    val granted = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    if (!granted) onNeedRequest()
}

private fun createTempImageUri(context: Context): Uri {
    val imagesDir = File(context.cacheDir, "images").apply { mkdirs() }
    val file = File(imagesDir, "capture_${System.currentTimeMillis()}.jpg")
    return FileProvider.getUriForFile(
        context,
        context.packageName + ".provider",
        file
    )
}

private fun uriToAbsolutePath(context: Context, uri: Uri): String? {
    // Since we created the file ourselves in cacheDir, we can resolve back to path via lastPathSegment
    // But FileProvider returns a content uri that still references our file; we can derive the same file path
    return try {
        val segments = uri.pathSegments
        // The path after /cache_path/ is our relative path under cacheDir
        val index = segments.indexOf("cache_path")
        if (index >= 0 && index + 1 < segments.size) {
            val relative = segments.drop(index + 1).joinToString(File.separator)
            File(context.cacheDir, relative).absolutePath
        } else null
    } catch (e: Exception) { null }
}
