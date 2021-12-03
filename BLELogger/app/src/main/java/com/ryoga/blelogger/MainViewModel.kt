package com.ryoga.blelogger

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ryoga.blelogger.data.repository.BeaconInfoFileRepository
import com.ryoga.blelogger.item.BeaconRssiItem
import org.altbeacon.beacon.Beacon

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val mBeaconInfoFileRepository = BeaconInfoFileRepository(application.applicationContext)

    private val _mIsBeaconRanging = MutableLiveData<Boolean>(false)
    val mIsBeaconRaging: LiveData<Boolean> = _mIsBeaconRanging

    private val _mBeaconList = MutableLiveData<List<BeaconRssiItem>>()
    val mBeaconList: LiveData<List<BeaconRssiItem>> = _mBeaconList


    fun onScanFragmentCreate() {
        val list = mutableListOf<BeaconRssiItem>()
        mBeaconInfoFileRepository.loadBeaconInfoLost().forEach {
            list.add(BeaconRssiItem(it))
        }
        _mBeaconList.postValue(list)
    }

    fun onStartButtonClicked() {
        _mIsBeaconRanging.postValue(true)
    }

    fun onStopButtonClicked() {
        _mIsBeaconRanging.postValue(false)
    }

    fun onBeaconObserved(beaconList: Collection<Beacon>) {
        val list = _mBeaconList.value!!
        list.forEach {
            it.rssi = getSameBeaconRssi(it, beaconList)
        }
        _mBeaconList.postValue(list)
    }

    private fun getSameBeaconRssi(beacon: BeaconRssiItem, beaconList: Collection<Beacon>): Int {
        for (b: Beacon in beaconList) {
            if (b.id1.toString() == beacon.beaconInfo.uuid
                && b.id2.toString() == beacon.beaconInfo.major
                && b.id3.toString() == beacon.beaconInfo.minor
            ) {
                return b.rssi
            }
        }
        return 0
    }
}