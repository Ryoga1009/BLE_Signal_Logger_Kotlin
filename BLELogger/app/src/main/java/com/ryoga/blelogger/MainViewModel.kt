package com.ryoga.blelogger

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _mIsBeaconRangingStarted = MutableLiveData<Boolean>(false)
    val mIsBeaconRagingStarted: LiveData<Boolean> = _mIsBeaconRangingStarted
    
}