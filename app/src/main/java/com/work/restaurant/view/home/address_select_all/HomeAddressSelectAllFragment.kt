package com.work.restaurant.view.home.address_select_all

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.work.restaurant.R
import com.work.restaurant.view.ExerciseRestaurantActivity.Companion.selectAll
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.home.daum_maps.MapFragment.Companion.dragMap
import com.work.restaurant.view.home.daum_maps.MapFragment.Companion.toggleMap
import kotlinx.android.synthetic.main.home_address_selcet_all_fragment.*


class HomeAddressSelectAllFragment : BaseFragment(R.layout.home_address_selcet_all_fragment),
    View.OnClickListener {


    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.btn_address_change_no -> {
                requireFragmentManager().beginTransaction().remove(this).commit()
            }

            R.id.btn_address_change_ok -> {
                selectAll = tv_address_select.text.toString()
                dragMap = false
                toggleMap = true
                requireFragmentManager().beginTransaction().remove(this).commit()
                activity?.finish()

            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_address_selcet_all_fragment, container, false).also {
            val address = it.findViewById<TextView>(R.id.tv_address_select)
            val bundle = arguments
            address.text = bundle?.getString("Address")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_address_change_no.setOnClickListener(this)
        btn_address_change_ok.setOnClickListener(this)
    }


    companion object {
        private const val TAG = "HomeAddressSelectAllFragment"

        private const val ADDRESS = "Address"

        fun newInstance(selectAddress: String) =
            HomeAddressSelectAllFragment().apply {
                arguments = Bundle().apply {
                    putString(ADDRESS, selectAddress)
                }

            }

    }

}
