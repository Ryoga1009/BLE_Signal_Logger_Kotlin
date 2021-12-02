package com.ryoga.blelogger.ui.add_beacon_info

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ryoga.blelogger.data.model.BeaconInfo
import com.ryoga.blelogger.data.repository.BeaconInfoFileRepository

class AddBeaconInfoViewModel(application: Application) : AndroidViewModel(application) {

    private var _mIsBeaconInfoAddEnd = MutableLiveData<Boolean>(false)
    val mIsBeaconAddEnd: LiveData<Boolean> = _mIsBeaconInfoAddEnd


    private val fileRepository =
        BeaconInfoFileRepository(getApplication<Application>().applicationContext)

    fun addButtonClicked(beaconInfo: BeaconInfo) {
        _mIsBeaconInfoAddEnd.postValue(fileRepository.writeBeaconInfo(beaconInfo))
    }


}