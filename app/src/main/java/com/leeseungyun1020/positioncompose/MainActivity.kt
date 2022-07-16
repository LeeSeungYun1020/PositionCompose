package com.leeseungyun1020.positioncompose

import android.Manifest
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.leeseungyun1020.positioncompose.ui.theme.PositionComposeTheme
import com.leeseungyun1020.positioncompose.view.LocationPermissionView
import com.leeseungyun1020.positioncompose.view.MapView
import com.leeseungyun1020.positioncompose.viewmodel.LocationPermissionViewModel
import com.leeseungyun1020.positioncompose.viewmodel.MapsViewModel

val locationPermissionViewModel = LocationPermissionViewModel()
val mapsViewModel = MapsViewModel()

class MainActivity : ComponentActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionViewModel.isGranted.postValue(
                true
            )
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            updateLocation()
        } else {
            locationPermissionViewModel.isGranted.postValue(
                false
            )
        }

        setContent {
            PositionComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }

    private fun updateLocation() {
        val locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = Priority.PRIORITY_HIGH_ACCURACY
        }
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
        task.addOnSuccessListener {
            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    locationResult
                    for (location in locationResult.locations) {
                        if (location != null) {
                            mapsViewModel.latitude.postValue(location.latitude)
                            mapsViewModel.longitude.postValue(location.longitude)
                        }
                    }
                }
            }
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    exception.startResolutionForResult(
                        this@MainActivity,
                        1
                    )
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val isLocationPermissionGranted by locationPermissionViewModel.isGranted.observeAsState()
    if(isLocationPermissionGranted == true) {
        MapView()
    } else {
        LocationPermissionView()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PositionComposeTheme {
        MapView()
    }
}