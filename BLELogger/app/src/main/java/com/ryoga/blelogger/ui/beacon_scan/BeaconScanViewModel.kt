package com.ryoga.blelogger.ui.beacon_scan

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ryoga.blelogger.data.model.BeaconInfo
import com.ryoga.blelogger.data.repository.BeaconInfoFileRepository

class BeaconScanViewModel(application: Application) : AndroidViewModel(application) {

    private val mBeaconInfoFileRepository = BeaconInfoFileRepository(application.applicationContext)

    private val _mBeaconInfoList = MutableLiveData<List<BeaconInfo>>()
    val mBeaconInfoList: LiveData<List<BeaconInfo>> = _mBeaconInfoList

    private val _mStartButtonEnabled = MutableLiveData<Boolean>(true)
    val mStartButtonEnabled: LiveData<Boolean> = _mStartButtonEnabled

    private val _mStopButtonEnabled = MutableLiveData<Boolean>(false)
    val mStopButtonEnabled: LiveData<Boolean> = _mStopButtonEnabled

    init {
        _mBeaconInfoList.value = mBeaconInfoFileRepository.loadBeaconInfoLost()
    }

    fun onStartButtonClicked() {
        _mStartButtonEnabled.postValue(false)
        _mStopButtonEnabled.postValue(true)
    }

    fun onStopButtonClicked() {
        _mStartButtonEnabled.postValue(true)
        _mStopButtonEnabled.postValue(false)
    }
}