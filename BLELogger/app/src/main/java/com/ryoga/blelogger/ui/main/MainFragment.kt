package com.ryoga.blelogger.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ryoga.blelogger.R
import com.ryoga.blelogger.databinding.MainFragmentBinding
import com.ryoga.blelogger.item.BeaconRssiItem
import com.xwray.groupie.GroupieAdapter

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var mViewModel: MainViewModel

    private var _mBinding: MainFragmentBinding? = null
    private val mBinding get() = _mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mViewModel = ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
            .create(MainViewModel::class.java)
        _mBinding = MainFragmentBinding.inflate(layoutInflater, container, false)

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


        mViewModel.mBeaconInfoList.observe(viewLifecycleOwner) {
            it.forEach { beaconInfo ->
                adapter.add(BeaconRssiItem(beaconInfo))
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        fragmentManager?.popBackStack()
        return super.onOptionsItemSelected(item)
    }
}