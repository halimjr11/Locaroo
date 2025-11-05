package com.halimjr11.locaroo.view.screens.create

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.halimjr11.locaroo.ui.theme.LocarooTheme
import com.halimjr11.locaroo.utils.location.ensureLocationPermission
import com.halimjr11.locaroo.utils.location.fetchLastKnownLocation
import com.halimjr11.locaroo.utils.location.hasLocationPermission
import com.halimjr11.locaroo.view.viewmodels.create.CreateViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

@Composable
fun CreateScreen(
    navController: NavController,
    padding: PaddingValues = PaddingValues(16.dp),
    viewModel: CreateViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    var name by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var location by rememberSaveable { mutableStateOf("") }
    var latitude by rememberSaveable { mutableDoubleStateOf(0.0) }
    var longitude by rememberSaveable { mutableDoubleStateOf(0.0) }
    var tags by rememberSaveable { mutableStateOf("") }

    var imagePath by rememberSaveable { mutableStateOf<String?>(null) }
    var locationStatus by rememberSaveable { mutableStateOf<String?>(null) }

    val tempUriState = remember { mutableStateOf<Uri?>(null) }

    // Handle image selected from CaptureScreen via SavedStateHandle
    LaunchedEffect(Unit) {
        navController.currentBackStackEntry?.savedStateHandle?.getStateFlow("capturedImagePath", "")
            ?.collect { path ->
                if (path.isNotBlank()) imagePath = path
            }
    }

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                imagePath = copyUriToCache(context, it)
            }
        }
    )

    val requestMediaPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { grantedMap ->
            val granted = grantedMap.values.any { it }
            if (granted) {
                pickImageLauncher.launch("image/*")
            }
        }
    )

    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success: Boolean ->
            if (success) {
                tempUriState.value?.let { uri ->
                    val path = uriToAbsolutePath(context, uri)
                    imagePath = path
                }
            }
        }
    )

    val permissionCameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            if (granted) {
                val uri = createTempImageUri(context)
                tempUriState.value = uri
                takePictureLauncher.launch(uri)
            }
        }
    )

    val requestLocationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { results ->
            val granted = results[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                    results[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            if (granted) {
                fetchLastKnownLocation(context) { loc, address, city ->
                    if (loc != null) {
                        latitude = loc.latitude
                        longitude = loc.longitude
                        if (!address.isNullOrBlank()) location = address
                        locationStatus = "Location updated"
                    } else {
                        locationStatus = "Unable to get location"
                    }
                }
            } else {
                locationStatus = "Location permission denied"
            }
        }
    )

    CreateScreenContent(
        padding = padding,
        context = context,
        name = name,
        onNameChange = {
            name = it
        },
        description = description,
        onDescriptionChange = {
            description = it
        },
        location = location,
        onLocationChange = {
            location = it
        },
        onBack = { navController.popBackStack() },
        locationStatus = locationStatus,
        tags = tags,
        onTagsChange = {
            tags = it
        },
        imagePath = imagePath,
        onPermissionLocationRequest = {
            requestLocationPermissionLauncher.launch(it)
        },
        onPermissionMediaRequest = {
            requestMediaPermissionLauncher.launch(it)
        },
        onPickImage = {
            pickImageLauncher.launch("image/*")
        },
        onCaptureImage = {
            val uri = createTempImageUri(context)
            tempUriState.value = uri
            takePictureLauncher.launch(uri)
        },
        onPermissionCameraRequest = {
            permissionCameraLauncher.launch(Manifest.permission.CAMERA)
        },
        onSubmit = {
            val tagsList = tags.split(',').map { it.trim() }.filter { it.isNotEmpty() }
            val img = imagePath ?: ""
            viewModel.createPlace(
                name = name,
                description = description,
                latitude = latitude,
                longitude = longitude,
                location = location,
                tagsSlugs = tagsList,
                imageAbsolutePath = img
            )
        },
        statusText = viewModel.statusText
    )


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateScreenContent(
    padding: PaddingValues,
    context: Context,
    name: String,
    onNameChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    location: String,
    onLocationChange: (String) -> Unit,
    onBack: () -> Unit,
    locationStatus: String?,
    tags: String,
    onTagsChange: (String) -> Unit,
    imagePath: String?,
    onPermissionLocationRequest: (Array<String>) -> Unit,
    onPermissionMediaRequest: (Array<String>) -> Unit,
    onPermissionCameraRequest: () -> Unit,
    onPickImage: () -> Unit,
    onCaptureImage: () -> Unit,
    onSubmit: () -> Unit,
    statusText: String?
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top bar
        CenterAlignedTopAppBar(
            modifier = Modifier.background(MaterialTheme.colorScheme.primary),
            windowInsets = WindowInsets(0),
            title = { Text(text = "Create Place") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        )

        // Content
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = onNameChange,
                label = { Text("Name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                supportingText = { Text("Give your place a clear name") }
            )

            OutlinedTextField(
                value = description,
                onValueChange = onDescriptionChange,
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent
                ),
                supportingText = { Text("Tell people what makes this place special") }
            )

            OutlinedTextField(
                value = location,
                onValueChange = onLocationChange,
                label = { Text("Location") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = {
                        if (hasLocationPermission(context)) {
                            fetchLastKnownLocation(context) { loc, address, city ->
                                onLocationChange(address.orEmpty())
                            }
                        } else {
                            ensureLocationPermission(context) { perms ->
                                onPermissionLocationRequest(perms)
                            }
                        }
                    }) {
                        Icon(Icons.Default.LocationOn, contentDescription = null)
                    }
                },
                supportingText = { locationStatus?.let { Text(it) } }
            )

            OutlinedTextField(
                value = tags,
                onValueChange = onTagsChange,
                label = { Text("Tags (comma separated)") },
                modifier = Modifier.fillMaxWidth()
            )

            // Image preview card
            ElevatedCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(12.dp)
                ) {
                    if (imagePath != null) {
                        Image(
                            painter = rememberAsyncImagePainter(imagePath),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                "No image selected",
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                        }
                    }
                }
            }

            // Image actions
            Button(
                onClick = {
                    ensureMediaPermission(context) { isNeedRequest, permissions ->
                        if (isNeedRequest) {
                            onPermissionMediaRequest(permissions)
                        } else {
                            onPickImage()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Image, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Pick Image")
            }

            Button(
                onClick = {
                    ensureCameraPermission(context) { granted ->
                        if (granted) {
                            onCaptureImage()
                        } else {
                            onPermissionCameraRequest()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.AddAPhoto, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Capture Image")
            }

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = onSubmit,
                modifier = Modifier.fillMaxWidth()
            ) { Text("Submit") }

            Text(statusText.orEmpty())
        }
    }

}

private fun ensureMediaPermission(
    context: Context,
    onNeedRequest: (Boolean, Array<String>) -> Unit
) {
    val perms = if (android.os.Build.VERSION.SDK_INT >= 33) {
        arrayOf(Manifest.permission.READ_MEDIA_IMAGES)
    } else {
        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    }
    val need = perms.any {
        ContextCompat.checkSelfPermission(
            context,
            it
        ) != PackageManager.PERMISSION_GRANTED
    }
    onNeedRequest(need, perms)
}

private fun copyUriToCache(context: Context, uri: Uri): String? {
    return try {
        val fileName = "picked_${'$'}{System.currentTimeMillis()}.jpg"
        val file = File(context.cacheDir, fileName)
        context.contentResolver.openInputStream(uri).use { input: InputStream? ->
            FileOutputStream(file).use { output ->
                input?.copyTo(output)
            }
        }
        file.absolutePath
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}


private fun ensureCameraPermission(context: Context, onNeedRequest: (Boolean) -> Unit) {
    val granted = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED
    onNeedRequest(granted)
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
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewCreateLight() {
    LocarooTheme(darkTheme = false, dynamicColor = false) {
        CreateScreenContent(
            padding = PaddingValues(16.dp),
            context = LocalContext.current,
            name = "",
            onNameChange = {},
            description = "",
            onDescriptionChange = {},
            location = "",
            onLocationChange = {},
            locationStatus = null,
            tags = "",
            onTagsChange = {},
            imagePath = null,
            onPermissionLocationRequest = {},
            onPermissionMediaRequest = {},
            onPickImage = {},
            onCaptureImage = {},
            onSubmit = {},
            onBack = {},
            onPermissionCameraRequest = {},
            statusText = null
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCreateDark() {
    LocarooTheme(darkTheme = true, dynamicColor = false) {
        CreateScreenContent(
            padding = PaddingValues(16.dp),
            context = LocalContext.current,
            name = "",
            onNameChange = {},
            description = "",
            onDescriptionChange = {},
            location = "",
            onLocationChange = {},
            locationStatus = null,
            tags = "",
            onTagsChange = {},
            imagePath = null,
            onPermissionLocationRequest = {},
            onPermissionMediaRequest = {},
            onPickImage = {},
            onCaptureImage = {},
            onSubmit = {},
            onBack = {},
            onPermissionCameraRequest = {},
            statusText = null
        )
    }
}
