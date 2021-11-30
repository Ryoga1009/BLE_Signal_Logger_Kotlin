package com.ryoga.blelogger

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ryoga.blelogger.ui.beacon_info_list.BeaconInfoListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, BeaconInfoListFragment.newInstance())
                    .commitNow()
        }
    }
}