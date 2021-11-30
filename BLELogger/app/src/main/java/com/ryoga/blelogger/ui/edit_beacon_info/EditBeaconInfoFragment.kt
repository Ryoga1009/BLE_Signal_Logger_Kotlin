package com.ryoga.blelogger.ui.edit_beacon_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ryoga.blelogger.R
import com.ryoga.blelogger.data.model.BeaconInfo
import com.ryoga.blelogger.databinding.EditBeaconInfoFragmentBinding

class EditBeaconInfoFragment(private var beaconInfo: BeaconInfo, private val position: Int) :
    Fragment() {

    private var _mBinding: EditBeaconInfoFragmentBinding? = null
    private val mBinding get() = _mBinding!!


    private lateinit var viewModel: EditBeaconInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
            .create(EditBeaconInfoViewModel::class.java)
        _mBinding = EditBeaconInfoFragmentBinding.inflate(layoutInflater, container, false)

        (activity as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            setHasOptionsMenu(true)
            title = getString(R.string.edit_beacon_info_title)
            show()
        }

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.inputForm.imageViewRemove.isVisible = false
        mBinding.inputForm.rootConstraint.isClickable = false

        mBinding.inputForm.apply {
            editTextBeaconName.setText(beaconInfo.beaconName)
            editTextBeaconName.isEnabled = true

            editTextUuid.setText(beaconInfo.uuid)
            editTextUuid.isEnabled = true

            editTextMajor.setText(beaconInfo.major)
            editTextMajor.isEnabled = true

            editTextMinor.setText(beaconInfo.minor)
            editTextMinor.isEnabled = true
        }

        mBinding.buttonEditBeaconInfo.setOnClickListener {
            beaconInfo = BeaconInfo(
                beaconName = mBinding.inputForm.editTextBeaconName.text.toString(),
                uuid = mBinding.inputForm.editTextUuid.text.toString(),
                major = mBinding.inputForm.editTextMajor.text.toString(),
                minor = mBinding.inputForm.editTextMinor.text.toString()
            )
            viewModel.onEditButtonClicked(beaconInfo, position)
        }

        viewModel.isBeaconListUpdated.observe(viewLifecycleOwner) {
            if (it) toBackStack()
        }
    }

    private fun toBackStack() {
        fragmentManager?.popBackStack()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        toBackStack()
        return super.onOptionsItemSelected(item)
    }
}