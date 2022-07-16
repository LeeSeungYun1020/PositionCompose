package com.leeseungyun1020.positioncompose.viewmodel

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MapsViewModel : ViewModel() {
    val location = MutableLiveData<Location>()
}