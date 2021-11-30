package com.ryoga.blelogger.ui.add_beacon_info

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
import com.ryoga.blelogger.databinding.AddBeaconInfoFragmentBinding

class AddBeaconInfoFragment : Fragment() {

    companion object {
        fun newInstance() = AddBeaconInfoFragment()
    }

    private lateinit var viewModel: AddBeaconInfoViewModel

    private var _mBinding: AddBeaconInfoFragmentBinding? = null
    private val mBinding get() = _mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel = ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
            .create(AddBeaconInfoViewModel::class.java)
        _mBinding = AddBeaconInfoFragmentBinding.inflate(layoutInflater, container, false)

        (activity as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            setHasOptionsMenu(true)
            title = getString(R.string.add_beacon_info_title)
            show()
        }




        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.inputForm.apply {
            editTextBeaconName.isEnabled = true
            editTextUuid.isEnabled = true
            editTextMajor.isEnabled = true
            editTextMinor.isEnabled = true
        }

        mBinding.inputForm.imageViewRemove.isVisible = false
        mBinding.inputForm.viewClick.isClickable = false

        mBinding.buttonAddBeaconInfo.setOnClickListener {
            val beaconInfo = BeaconInfo(
                mBinding.inputForm.editTextBeaconName.text.toString(),
                mBinding.inputForm.editTextUuid.text.toString(),
                mBinding.inputForm.editTextMajor.text.toString(),
                mBinding.inputForm.editTextMinor.text.toString()
            )
            viewModel.addButtonClicked(beaconInfo)
        }

        viewModel.mIsBeaconAddEnd.observe(viewLifecycleOwner) {
            if (it) fragmentManager?.popBackStack()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        fragmentManager?.popBackStack()
        return super.onOptionsItemSelected(item)
    }


}