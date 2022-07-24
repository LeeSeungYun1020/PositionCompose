package com.leeseungyun1020.positioncompose.view

import android.location.Location
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.maps.model.LatLng
import com.leeseungyun1020.positioncompose.mapsViewModel
import net.daum.mf.map.api.MapCircle
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MapScreen(modifier: Modifier = Modifier) {
    val mapView = MapView(LocalContext.current)
    mapView.setMapCenterPointAndZoomLevel(
        MapPoint.mapPointWithGeoCoord(37.503617, 127.044844),
        1,
        false
    )

    val centerCircle = MapCircle(
        MapPoint.mapPointWithGeoCoord(37.503617, 127.044844),
        100,
        0x8684FF,
        0x171B73E8
    )
    mapView.addCircle(centerCircle)

    val marker = MapPOIItem().apply {
        itemName = "Now"
        tag = 0
        mapPoint = MapPoint.mapPointWithGeoCoord(37.503617, 127.044844)
        markerType = MapPOIItem.MarkerType.RedPin
    }
    mapView.addPOIItem(marker)

    Surface(
        modifier = modifier
    ) {
        val latitude by mapsViewModel.latitude.observeAsState()
        val longitude by mapsViewModel.longitude.observeAsState()
        val now = LatLng(latitude ?: 37.503617, longitude ?: 127.044844)
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
        marker.mapPoint =
            MapPoint.mapPointWithGeoCoord(latitude ?: 37.503617, longitude ?: 127.044844)

        Box(
            modifier = Modifier.fillMaxSize(),

            ) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { context ->
                    mapView
                },

                )
            Text(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 64.dp)
                    .background(Color.Black)
                    .padding(16.dp),
                color = Color.White,
                text = noticeText
            )
        }


    }
}