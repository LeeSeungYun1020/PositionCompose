package com.leeseungyun1020.positioncompose.view

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MapView(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
    ) {
        Text("Map")
    }
}