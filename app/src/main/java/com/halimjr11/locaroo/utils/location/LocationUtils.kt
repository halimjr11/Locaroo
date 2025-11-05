package com.halimjr11.locaroo.utils.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import java.util.Locale

fun ensureLocationPermission(context: Context, onNeedRequest: (Array<String>) -> Unit) {
    val perms = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    val need = perms.any {
        ContextCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED
    }
    if (need) onNeedRequest(perms)
}

fun hasLocationPermission(context: Context): Boolean {
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

fun fetchLastKnownLocation(
    context: Context,
    onResult: (Location?, String?, String?) -> Unit
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
        onResult(null, null, null)
        return
    }

    val providers = listOf(
        LocationManager.GPS_PROVIDER,
        LocationManager.NETWORK_PROVIDER,
        LocationManager.PASSIVE_PROVIDER
    )
    var best: Location? = null
    var city: String? = null
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
        val address = try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val results = geocoder.getFromLocation(best.latitude, best.longitude, 1)
            results?.firstOrNull()?.let { adr ->
                println("Jalanan ==> address $adr")
                city = adr.subAdminArea.formatAddress()
                listOfNotNull(adr.subAdminArea.formatAddress(), adr.adminArea, adr.countryName)
                    .filter { it.isNotBlank() }
                    .joinToString(", ")
                    .ifBlank { adr.getAddressLine(0).orEmpty() }
            }
        } catch (_: Exception) {
            null
        }
        onResult(best, address, city)
    } else {
        onResult(null, null, null)
    }
}

fun String.formatAddress(): String {
    return this.replace("Kota ", "").replace("Kabupaten ", "").replace("Provinsi ", "")
}
