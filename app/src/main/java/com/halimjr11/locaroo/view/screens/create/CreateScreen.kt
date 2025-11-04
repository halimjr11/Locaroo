package com.halimjr11.locaroo.view.screens.create

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.halimjr11.locaroo.ui.navigation.NavRoute
import com.halimjr11.locaroo.view.viewmodels.create.CreateViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.Locale

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
    var latitude by rememberSaveable { mutableStateOf("") }
    var longitude by rememberSaveable { mutableStateOf("") }
    var tags by rememberSaveable { mutableStateOf("") }

    var imagePath by rememberSaveable { mutableStateOf<String?>(null) }
    var locationStatus by rememberSaveable { mutableStateOf<String?>(null) }

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

    val requestLocationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { results ->
            val granted = results[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                    results[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            if (granted) {
                fetchLastKnownLocation(context) { loc, address ->
                    if (loc != null) {
                        latitude = loc.latitude.toString()
                        longitude = loc.longitude.toString()
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

    // Ask for read permission on enter for picker use-case
    LaunchedEffect(Unit) {
        ensureMediaPermission(context) { permissions ->
            requestMediaPermissionLauncher.launch(permissions)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") })
        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            label = { Text("Location") })
        OutlinedTextField(
            value = latitude,
            onValueChange = { latitude = it },
            label = { Text("Latitude") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )
        OutlinedTextField(
            value = longitude,
            onValueChange = { longitude = it },
            label = { Text("Longitude") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )
        OutlinedTextField(
            value = tags,
            onValueChange = { tags = it },
            label = { Text("Tags (comma separated)") })

        Button(onClick = {
            if (hasLocationPermission(context)) {
                fetchLastKnownLocation(context) { loc, address ->
                    if (loc != null) {
                        latitude = loc.latitude.toString()
                        longitude = loc.longitude.toString()
                        if (!address.isNullOrBlank()) location = address
                        locationStatus = "Location updated"
                    } else {
                        locationStatus = "Unable to get location"
                    }
                }
            } else {
                ensureLocationPermission(context) { perms ->
                    requestLocationPermissionLauncher.launch(perms)
                }
            }
        }) { Text("Use Current Location") }

        locationStatus?.let { Text(it) }

        if (imagePath != null) {
            Image(
                painter = rememberAsyncImagePainter(imagePath),
                contentDescription = null,
                modifier = Modifier.height(180.dp),
                contentScale = ContentScale.Crop
            )
        } else {
            Text("No image selected")
        }

        Button(onClick = {
            ensureMediaPermission(context) { permissions ->
                requestMediaPermissionLauncher.launch(permissions)
            }
        }) { Text("Pick Image") }

        Button(onClick = { navController.navigate(NavRoute.Capture.route) }) {
            Text("Capture Image")
        }

        Spacer(Modifier.height(8.dp))

        Button(onClick = {
            val lat = latitude.toDoubleOrNull() ?: 0.0
            val lon = longitude.toDoubleOrNull() ?: 0.0
            val tagsList = tags.split(',').map { it.trim() }.filter { it.isNotEmpty() }
            val img = imagePath ?: ""
            viewModel.createPlace(
                name = name,
                description = description,
                latitude = lat,
                longitude = lon,
                location = location,
                tagsSlugs = tagsList,
                imageAbsolutePath = img
            )
        }) { Text("Submit") }

        viewModel.statusText?.let { Text(it) }
    }
}

private fun ensureMediaPermission(context: Context, onNeedRequest: (Array<String>) -> Unit) {
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
    if (need) onNeedRequest(perms)
}

private fun ensureLocationPermission(context: Context, onNeedRequest: (Array<String>) -> Unit) {
    val perms = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    val need = perms.any {
        ContextCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED
    }
    if (need) onNeedRequest(perms)
}

private fun hasLocationPermission(context: Context): Boolean {
    val fine = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
    val coarse = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
    return fine || coarse
}

private fun fetchLastKnownLocation(
    context: Context,
    onResult: (Location?, String?) -> Unit
) {
    val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val hasFine = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
    val hasCoarse = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
    if (!hasFine && !hasCoarse) {
        onResult(null, null)
        return
    }

    val providers = listOf(
        LocationManager.GPS_PROVIDER,
        LocationManager.NETWORK_PROVIDER,
        LocationManager.PASSIVE_PROVIDER
    )
    var best: Location? = null
    for (p in providers) {
        try {
            val loc = lm.getLastKnownLocation(p)
            if (loc != null && (best == null || loc.accuracy < best!!.accuracy)) {
                best = loc
            }
        } catch (_: SecurityException) {
        }
    }

    if (best != null) {
        // Try reverse geocoding best-effort
        val address = try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val results = geocoder.getFromLocation(best.latitude, best.longitude, 1)
            results?.firstOrNull()?.let { adr ->
                listOfNotNull(adr.locality, adr.adminArea, adr.countryName)
                    .filter { it.isNotBlank() }
                    .joinToString(", ")
                    .ifBlank { adr.getAddressLine(0).orEmpty() }
            }
        } catch (_: Exception) {
            null
        }
        onResult(best, address)
    } else {
        onResult(null, null)
    }
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

