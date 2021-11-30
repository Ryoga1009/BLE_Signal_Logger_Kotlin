package com.ryoga.blelogger.ui.beacon_info_list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ryoga.blelogger.data.model.BeaconInfo
import com.ryoga.blelogger.data.repository.BeaconInfoFileRepository

class BeaconInfoListViewModel(application: Application) : AndroidViewModel(application) {

    private var _mIsFabClicked: MutableLiveData<Boolean> = MutableLiveData(false)
    var mIsFabClicked: LiveData<Boolean> = _mIsFabClicked

    private var _mBeaconInfoList = MutableLiveData<ArrayList<BeaconInfo>>()
    var mBeaconInfoList: LiveData<ArrayList<BeaconInfo>> = _mBeaconInfoList

    private var _mIsBeaconListEmpty = MutableLiveData<Boolean>(true)
    var mIsBeaconListEmpty: LiveData<Boolean> = _mIsBeaconListEmpty

    private var _mIsButtonEnable = MutableLiveData<Boolean>(false)
    var mIsButtonEnable: LiveData<Boolean> = _mIsButtonEnable

    private val mBeaconInfoFileRepository = BeaconInfoFileRepository(application.applicationContext)


    fun FabCLicked() {
        _mIsFabClicked.value = true
    }

    fun loadBeaconInfoList() {
        _mBeaconInfoList.value = mBeaconInfoFileRepository.loadBeaconInfoLost()
        _mIsBeaconListEmpty.value = mBeaconInfoList.value?.isEmpty()
        _mIsButtonEnable.value = !(mBeaconInfoList.value.isNullOrEmpty())

    }

    fun removeBeaconInfo(position: Int) {
        var list = mBeaconInfoFileRepository.loadBeaconInfoLost()
        list.remove(list.get(position))

        mBeaconInfoFileRepository.writeBeaconInfoList(list)
        _mBeaconInfoList.value = mBeaconInfoFileRepository.loadBeaconInfoLost()

        _mIsButtonEnable.value = !(mBeaconInfoList.value.isNullOrEmpty())

        _mIsBeaconListEmpty.value = mBeaconInfoList.value?.isEmpty()

    }
}