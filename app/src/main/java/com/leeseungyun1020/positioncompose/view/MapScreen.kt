package com.leeseungyun1020.positioncompose.view

import android.location.Location
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.rememberCameraPositionState
import com.leeseungyun1020.positioncompose.mapsViewModel
import net.daum.mf.map.api.MapView

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MapScreen(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
    ) {
        var mapProperties by remember {
            mutableStateOf(
                MapProperties(maxZoomPreference = 20f, minZoomPreference = 15f)
            )
        }
        val latitude by mapsViewModel.latitude.observeAsState()
        val longitude by mapsViewModel.longitude.observeAsState()
        val now = LatLng(latitude ?: 37.503617, longitude ?: 127.044844)
        val pos = LatLng(37.503617, 127.044844)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(pos, 17f)
        }
        val origin = Location("now")
        origin.latitude = now.latitude
        origin.longitude = now.longitude
        val compare = Location("center")
        compare.latitude = 37.503617
        compare.longitude = 127.044844
        val noticeText = if (latitude == null || longitude == null) {
            "Waiting"
        } else if (origin.distanceTo(compare) <= 100) {
            "IN"
        } else {
            "OUT"
        }
//        Box(
//            modifier = Modifier.fillMaxSize(),
//
//            ) {
//            GoogleMap(
//                properties = mapProperties,
//                cameraPositionState = cameraPositionState,
//            ) {
//                Marker(
//                    position = now,
//                    title = "Now",
//                    snippet = "${now.latitude} ${now.longitude}"
//                )
//                Circle(
//                    center = pos,
//                    radius = 100.0,
//                    strokeColor = Color(27, 115, 232),
//                    strokeWidth = 5f,
//                    fillColor = Color(27, 115, 232, alpha = 26)
//                )
//            }
//            Text(
//                modifier = Modifier
//                    .align(Alignment.TopCenter)
//                    .padding(top = 64.dp)
//                    .background(Color.Black)
//                    .padding(16.dp),
//                color = Color.White,
//                text = noticeText
//            )
//        }

        val mapView = MapView(LocalContext.current)
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                mapView
            },

            )

    }
}