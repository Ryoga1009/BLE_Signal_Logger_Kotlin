package com.ryoga.blelogger.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ryoga.blelogger.data.model.BeaconInfo
import com.ryoga.blelogger.data.repository.BeaconInfoFileRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val mBeaconInfoFileRepository = BeaconInfoFileRepository(application.applicationContext)

    private val _mBeaconInfoList = MutableLiveData<List<BeaconInfo>>()
    val mBeaconInfoList: LiveData<List<BeaconInfo>> = _mBeaconInfoList

    init {
        _mBeaconInfoList.value = mBeaconInfoFileRepository.loadBeaconInfoLost()
    }
}