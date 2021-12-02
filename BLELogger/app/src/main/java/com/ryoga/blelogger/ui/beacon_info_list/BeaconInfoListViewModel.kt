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
        _mIsFabClicked.postValue(true)
    }

    fun loadBeaconInfoList() {
        _mBeaconInfoList.postValue(mBeaconInfoFileRepository.loadBeaconInfoLost())
        _mIsBeaconListEmpty.postValue(mBeaconInfoList.value?.isEmpty())
        _mIsButtonEnable.postValue(!(mBeaconInfoList.value.isNullOrEmpty()))

    }

    fun removeBeaconInfo(position: Int) {
        val list = mBeaconInfoFileRepository.loadBeaconInfoLost()
        list.remove(list[position])

        mBeaconInfoFileRepository.writeBeaconInfoList(list)
        _mBeaconInfoList.postValue(mBeaconInfoFileRepository.loadBeaconInfoLost())

        _mIsButtonEnable.postValue(!(mBeaconInfoList.value.isNullOrEmpty()))

        _mIsBeaconListEmpty.postValue(mBeaconInfoList.value?.isEmpty())

    }
}