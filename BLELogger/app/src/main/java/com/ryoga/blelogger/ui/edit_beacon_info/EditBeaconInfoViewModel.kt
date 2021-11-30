package com.ryoga.blelogger.ui.edit_beacon_info

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ryoga.blelogger.data.model.BeaconInfo
import com.ryoga.blelogger.data.repository.BeaconInfoFileRepository

class EditBeaconInfoViewModel(application: Application) : AndroidViewModel(application) {

    private val mBeaconInfoFileRepository = BeaconInfoFileRepository(application.applicationContext)

    private val _isBeaconListUpdated = MutableLiveData<Boolean>(false)
    val isBeaconListUpdated: LiveData<Boolean> = _isBeaconListUpdated

    fun onEditButtonClicked(beaconInfo: BeaconInfo, position: Int) {
        _isBeaconListUpdated.value =
            mBeaconInfoFileRepository.updateBeaconInfo(beaconInfo, position)
    }
}