package com.leeseungyun1020.positioncompose.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MapView(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
    ) {
        var mapProperties by remember {
            mutableStateOf(
                MapProperties(maxZoomPreference = 20f, minZoomPreference = 15f)
            )
        }
        val pos = LatLng(37.503617, 127.044844)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(pos, 17f)
        }
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            GoogleMap(
                properties = mapProperties,
                cameraPositionState = cameraPositionState,
            ) {
                Marker(
                    position = pos,
                    title = "Now",
                    snippet = "${pos.latitude} ${pos.longitude}"
                )
                Circle(
                    center = pos,
                    radius = 100.0,
                    strokeColor = Color(27, 115, 232),
                    strokeWidth = 5f,
                    fillColor = Color(27, 115, 232, alpha = 26)
                )
            }
        }

    }
}