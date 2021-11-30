package com.ryoga.blelogger.item

import android.view.View
import com.ryoga.blelogger.R
import com.ryoga.blelogger.data.model.BeaconInfo
import com.ryoga.blelogger.databinding.BeaconInfoListItemBinding
import com.xwray.groupie.viewbinding.BindableItem

class BeaconInfoItem(
    private val beaconInfo: BeaconInfo,
    private val removeButtonClick: (position: Int) -> Unit,
    private val cardClick: (beaconInfo: BeaconInfo, position: Int) -> Unit
) :
    BindableItem<BeaconInfoListItemBinding>() {

    override fun bind(viewBinding: BeaconInfoListItemBinding, position: Int) {

        viewBinding.apply {
            editTextBeaconName.setText(beaconInfo.beaconName)
            editTextUuid.setText(beaconInfo.uuid)
            editTextMajor.setText(beaconInfo.major)
            editTextMinor.setText(beaconInfo.minor)
        }


        viewBinding.imageViewRemove.setOnClickListener {
            removeButtonClick(position)
        }

        viewBinding.viewClick.setOnClickListener {
            cardClick(beaconInfo, position)
        }
    }

    override fun getLayout(): Int = R.layout.beacon_info_list_item

    override fun initializeViewBinding(view: View): BeaconInfoListItemBinding {
        return BeaconInfoListItemBinding.bind(view)
    }


}