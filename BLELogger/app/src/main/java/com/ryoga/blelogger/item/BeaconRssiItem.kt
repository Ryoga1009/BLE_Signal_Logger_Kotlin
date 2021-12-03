package com.ryoga.blelogger.item

import android.view.View
import com.ryoga.blelogger.R
import com.ryoga.blelogger.data.model.BeaconInfo
import com.ryoga.blelogger.databinding.BeaconRssiListItemBinding
import com.xwray.groupie.viewbinding.BindableItem

class BeaconRssiItem(val beaconInfo: BeaconInfo) :
    BindableItem<BeaconRssiListItemBinding>() {

    var rssi: Int = 0

    override fun bind(viewBinding: BeaconRssiListItemBinding, position: Int) {
        viewBinding.textViewBeaconName.text = beaconInfo.beaconName
        viewBinding.textViewRssi.text = rssi.toString()
    }

    override fun getLayout(): Int = R.layout.beacon_rssi_list_item


    override fun initializeViewBinding(view: View): BeaconRssiListItemBinding =
        BeaconRssiListItemBinding.bind(view)


}