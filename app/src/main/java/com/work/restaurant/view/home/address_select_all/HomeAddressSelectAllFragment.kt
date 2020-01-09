package com.work.restaurant.view.home.address_select_all

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.work.restaurant.R
import com.work.restaurant.view.base.BaseFragment
import kotlinx.android.synthetic.main.home_address_selcet_all_fragment.*


class HomeAddressSelectAllFragment : BaseFragment(R.layout.home_address_selcet_all_fragment),
    View.OnClickListener {

    interface a {
        fun a(data: String)
    }


    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.btn_address_change_no -> {
                requireFragmentManager().beginTransaction().remove(this).commit()
            }

            R.id.btn_address_change_ok -> {
                val data = Intent()
                targetFragment?.onActivityResult(12345, Activity.RESULT_OK, data)
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated")
        super.onActivityCreated(savedInstanceState)

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
