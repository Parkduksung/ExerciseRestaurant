package com.work.restaurant.view.home.address_select_all

import android.content.Context
import android.os.Bundle
import android.view.View
import com.work.restaurant.R
import com.work.restaurant.base.BaseFragment
import com.work.restaurant.databinding.HomeAddressSelcetAllFragmentBinding
import com.work.restaurant.view.ExerciseRestaurantActivity.Companion.selectAll

class HomeAddressSelectAllFragment : BaseFragment<HomeAddressSelcetAllFragmentBinding>(
    HomeAddressSelcetAllFragmentBinding::bind,
    R.layout.home_address_selcet_all_fragment
),
    View.OnClickListener {


    private lateinit var addressAllDataListener: AddressAllDataListener

    interface AddressAllDataListener {
        fun sendData(data: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity as? AddressAllDataListener)?.let {
            addressAllDataListener = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddressChangeNo.setOnClickListener(this)
        binding.btnAddressChangeOk.setOnClickListener(this)
        showSelectAddress()
    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.btn_address_change_no -> {
                requireFragmentManager().beginTransaction().remove(this).commit()
            }

            R.id.btn_address_change_ok -> {

                if (::addressAllDataListener.isInitialized) {
                    arguments?.let {
                        it.getString(ADDRESS)?.let { addressAll ->
                            addressAllDataListener.sendData(addressAll)
                            fragmentManager?.popBackStack()
                            selectAll = binding.tvAddressSelect.text.toString()
                        }
                    }

                }
            }
        }
    }

    private fun showSelectAddress() {
        arguments?.let {
            binding.tvAddressSelect.text = it.getString(ADDRESS)
        }
    }


    companion object {
        private const val TAG = "HomeAddressSelectAllFragment"

        const val ADDRESS = "address"

        fun newInstance(selectAddress: String) =
            HomeAddressSelectAllFragment().apply {
                arguments = Bundle().apply {
                    putString(ADDRESS, selectAddress)
                }
            }

    }

}
