package com.leeseungyun1020.positioncompose.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LocationPermissionViewModel: ViewModel() {
    val isGranted: MutableLiveData<Boolean> = MutableLiveData(false)
}