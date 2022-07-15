package com.leeseungyun1020.positioncompose.view

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private val permissions = arrayOf(
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_COARSE_LOCATION
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LocationPermissionView(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier
                    .width(256.dp)
                    .height(256.dp),
                imageVector = Icons.Filled.LocationOn,
                contentDescription = "Refresh"
            )
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = "지도 기능 사용을 위해 위치 권한이 필요합니다."
            )
            GrantedButton(Modifier.padding(top = 16.dp))
        }
    }
}

@Composable
fun GrantedButton(modifier: Modifier = Modifier) {
    val permissionRequest = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {

            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {

            }
            else -> {
                // No location access granted.
            }
        }
    }
    Button(
        modifier = modifier,
        onClick = {
            permissionRequest.launch(permissions)
        }) {
        Text(text = "확인")
    }

}