package com.example.weathernow.data.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.tasks.await

class LocationProvider(
    private val context: Context
) {

    private val fusedLocationClient =
        LocationServices.getFusedLocationProviderClient(context)

    fun hasLocationPermission(): Boolean {
        val fineLocationGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val coarseLocationGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        return fineLocationGranted || coarseLocationGranted
    }

    suspend fun getCurrentLocation(): UserLocation? {
        if (!hasLocationPermission()) {
            return null
        }

        val location = fusedLocationClient.lastLocation.await()

        return location?.let {
            UserLocation(
                latitude = it.latitude,
                longitude = it.longitude
            )
        }
    }
}

data class UserLocation(
    val latitude: Double,
    val longitude: Double
)