package com.ryoga.blelogger

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _mIsBeaconRanging = MutableLiveData<Boolean>(false)
    val mIsBeaconRaging: LiveData<Boolean> = _mIsBeaconRanging


    fun onStartButtonClicked() {
        _mIsBeaconRanging.postValue(true)
    }

    fun onStopButtonClicked() {
        _mIsBeaconRanging.postValue(false)
    }
}