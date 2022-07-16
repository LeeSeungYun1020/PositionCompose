package com.leeseungyun1020.positioncompose.view

import android.location.Location
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.leeseungyun1020.positioncompose.mapsViewModel

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
        val location by mapsViewModel.location.observeAsState()
        val now = LatLng(location?.latitude ?: 37.503617, location?.longitude ?: 127.044844)
        val pos = LatLng(37.503617, 127.044844)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(pos, 17f)
        }
        val compare = Location("center")
        compare.latitude = 37.503617
        compare.longitude = 127.044844
        val noticeText = if (location == null) {
            "Waiting"
        } else if (location!!.distanceTo(compare) <= 100) {
            "IN"
        } else {
            "OUT"
        }
        Box(
            modifier = Modifier.fillMaxSize(),

            ) {
            GoogleMap(
                properties = mapProperties,
                cameraPositionState = cameraPositionState,
            ) {
                Marker(
                    position = now,
                    title = "Now",
                    snippet = "${now.latitude} ${now.longitude}"
                )
                Circle(
                    center = pos,
                    radius = 100.0,
                    strokeColor = Color(27, 115, 232),
                    strokeWidth = 5f,
                    fillColor = Color(27, 115, 232, alpha = 26)
                )
            }
            Text(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 64.dp)
                    .background(Color.Black)
                    .padding(16.dp),
                color = Color.White,
                text = noticeText
            )
            if (location == null) {
                Text(text = "Loc ERR")
            } else {
                Text(text = "Loc OK")
            }
        }

    }
}