package com.ryoga.blelogger.ui.beacon_scan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ryoga.blelogger.MainViewModel
import com.ryoga.blelogger.R
import com.ryoga.blelogger.databinding.BeaconScanFragmentBinding
import com.xwray.groupie.GroupieAdapter

class BeaconScanFragment : Fragment() {

    companion object {
        fun newInstance() = BeaconScanFragment()
    }

    private val mMainViewModel: MainViewModel by activityViewModels()

    private var _mBinding: BeaconScanFragmentBinding? = null
    private val mBinding get() = _mBinding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _mBinding = BeaconScanFragmentBinding.inflate(layoutInflater, container, false)

        mMainViewModel.onScanFragmentCreate()

        (activity as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            setHasOptionsMenu(true)
            title = getString(R.string.scan_beacon_title)
            show()
        }

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = GroupieAdapter()
        mBinding.beaconScanList.adapter = adapter
        mBinding.beaconScanList.layoutManager = LinearLayoutManager(requireContext())


        mBinding.buttonStart.setOnClickListener {
            mMainViewModel.onStartButtonClicked()
        }

        mBinding.buttonStop.setOnClickListener {
            mMainViewModel.onStopButtonClicked()
        }

        mMainViewModel.mBeaconList.observe(viewLifecycleOwner) {
            adapter.clear()
            it.forEach { beaconInfo ->
                adapter.add(beaconInfo)
            }
        }

        mMainViewModel.mIsBeaconRaging.observe(viewLifecycleOwner) {
            mBinding.buttonStart.isEnabled = !it
            mBinding.buttonStop.isEnabled = it
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        fragmentManager?.popBackStack()
        return super.onOptionsItemSelected(item)
    }
}