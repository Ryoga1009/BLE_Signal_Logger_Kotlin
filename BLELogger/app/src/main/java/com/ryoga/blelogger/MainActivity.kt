package com.ryoga.blelogger

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ryoga.blelogger.databinding.MainActivityBinding
import com.ryoga.blelogger.ui.beacon_info_list.BeaconInfoListFragment
import org.altbeacon.beacon.Beacon
import org.altbeacon.beacon.BeaconManager
import org.altbeacon.beacon.BeaconParser
import org.altbeacon.beacon.Region

class MainActivity : AppCompatActivity() {

    var mBeaconManager: BeaconManager = BeaconManager.getInstanceForApplication(this)
    val BEACON_FORMAT = "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"
    val region = Region("all-beacons-region", null, null, null)

    lateinit var mViewModel: MainViewModel
    private var _mBinding: MainActivityBinding? = null
    private val mBinding get() = _mBinding!!


    val rangingObserver = Observer<Collection<Beacon>> { beaconList ->

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel =
            ViewModelProvider.AndroidViewModelFactory(application).create(MainViewModel::class.java)
        _mBinding = MainActivityBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBeaconManager.beaconParsers.add(BeaconParser().setBeaconLayout(BEACON_FORMAT))

        mBeaconManager.getRegionViewModel(region).rangedBeacons.observe(this, rangingObserver)
        mBeaconManager.startMonitoring(region)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, BeaconInfoListFragment.newInstance())
                .commitNow()
        }
    }

    override fun onResume() {
        super.onResume()

        mViewModel.mIsBeaconRagingStarted.observe(viewLifecycle)
    }
}