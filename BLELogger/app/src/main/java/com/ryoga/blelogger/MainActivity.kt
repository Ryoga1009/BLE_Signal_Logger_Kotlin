package com.ryoga.blelogger

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.ryoga.blelogger.databinding.MainActivityBinding
import com.ryoga.blelogger.ui.beacon_info_list.BeaconInfoListFragment
import org.altbeacon.beacon.BeaconManager
import org.altbeacon.beacon.BeaconParser
import org.altbeacon.beacon.Region

class MainActivity : AppCompatActivity() {
    lateinit var mBeaconManager: BeaconManager
    val BEACON_FORMAT = "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"
    val region = Region("all-beacons-region", null, null, null)

    lateinit var mViewModel: MainViewModel
    private var _mBinding: MainActivityBinding? = null
    private val mBinding get() = _mBinding!!


    val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (!isGranted) {
            // TODO 位置情報がないと使えない旨ダイアログを出す
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        _mBinding = MainActivityBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        // 位置情報権限がない場合リクエスト
        val permission = ContextCompat.checkSelfPermission(
            applicationContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (permission == PackageManager.PERMISSION_DENIED) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        mBeaconManager = BeaconManager.getInstanceForApplication(application)
        mBeaconManager.beaconParsers.add(BeaconParser().setBeaconLayout(BEACON_FORMAT))

        mBeaconManager.getRegionViewModel(region).rangedBeacons.observe(this, { beaconList ->
            // 一旦トーストに検知できたビーコンの数を表示　
            // ビーコンの電池買ってきたら色々実装後に削除
            Toast.makeText(this, "Beaconの数：" + beaconList.size, Toast.LENGTH_SHORT).show()
            beaconList.forEach { beacon ->

            }
        })

        mViewModel.mIsBeaconRaging.observe(this) {
            if (it) {
                try {
                    mBeaconManager.foregroundBetweenScanPeriod = 3000
                    mBeaconManager.startRangingBeacons(region)
                } catch (e: Exception) {
                    Log.d("MYE", "onCreate: " + e.message)
                }
            } else {
                mBeaconManager.stopRangingBeacons(region)
            }
        }


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, BeaconInfoListFragment.newInstance())
                .commitNow()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mBeaconManager.stopRangingBeacons(region)
    }
}