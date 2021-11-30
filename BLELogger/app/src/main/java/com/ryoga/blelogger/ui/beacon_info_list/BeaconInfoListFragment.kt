package com.ryoga.blelogger.ui.beacon_info_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ryoga.blelogger.R
import com.ryoga.blelogger.databinding.BeaconInfoListFragmentBinding
import com.ryoga.blelogger.item.BeaconInfoItem
import com.ryoga.blelogger.ui.add_beacon_info.AddBeaconInfoFragment
import com.ryoga.blelogger.ui.edit_beacon_info.EditBeaconInfoFragment
import com.xwray.groupie.GroupieAdapter


class BeaconInfoListFragment : Fragment() {

    companion object {
        fun newInstance() = BeaconInfoListFragment()
    }

    private lateinit var viewModel: BeaconInfoListViewModel

    private var _mBinding: BeaconInfoListFragmentBinding? = null
    private val mBinding get() = _mBinding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
                .create(BeaconInfoListViewModel::class.java)

        _mBinding = BeaconInfoListFragmentBinding.inflate(layoutInflater, container, false)

        (activity as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
            title = getString(R.string.beacon_info_list_title)
            show()
        }

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = GroupieAdapter()
        mBinding.beaconInfoList.adapter = adapter
        mBinding.beaconInfoList.layoutManager = LinearLayoutManager(requireContext())

        mBinding.addBeacon.setOnClickListener {
            viewModel.FabCLicked()
        }

        viewModel.mIsFabClicked.observe(viewLifecycleOwner) {
            if (it) replaceFragment(AddBeaconInfoFragment.newInstance())
        }

        viewModel.mBeaconInfoList.observe(viewLifecycleOwner) { list ->
            adapter.clear()
            list.forEach { beaconInfo ->
                adapter.add(BeaconInfoItem(beaconInfo, { position ->
                    viewModel.removeBeaconInfo(position)
                }, { beaconInfo, position ->
                    replaceFragment(EditBeaconInfoFragment(beaconInfo, position))
                }))
            }
        }

        viewModel.mIsBeaconListEmpty.observe(viewLifecycleOwner) {
            mBinding.textViewEmpty.isVisible = it

        }

        viewModel.loadBeaconInfoList()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadBeaconInfoList()
    }

    private fun replaceFragment(fragment: Fragment) {
        fragmentManager?.beginTransaction()
            ?.addToBackStack("")
            ?.replace(R.id.container, fragment)
            ?.commit()
    }

}