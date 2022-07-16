package com.leeseungyun1020.positioncompose.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MapsViewModel : ViewModel() {
    val latitude = MutableLiveData(37.503617)
    val longitude = MutableLiveData(127.044844)
}