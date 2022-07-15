package com.leeseungyun1020.positioncompose

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.leeseungyun1020.positioncompose.ui.theme.PositionComposeTheme
import com.leeseungyun1020.positioncompose.view.LocationPermissionView
import com.leeseungyun1020.positioncompose.view.MapView
import com.leeseungyun1020.positioncompose.viewmodel.LocationPermissionViewModel

val locationPermissionViewModel = LocationPermissionViewModel()

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationPermissionViewModel.isGranted.postValue(
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
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

    }
}