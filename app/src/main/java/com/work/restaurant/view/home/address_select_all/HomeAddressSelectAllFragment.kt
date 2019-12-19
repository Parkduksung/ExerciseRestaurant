package com.work.restaurant.view.home.address_select_all

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.work.restaurant.R
import kotlinx.android.synthetic.main.home_address_selcet_all_fragment.*


class HomeAddressSelectAllFragment : Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.btn_address_change_no -> {
                this.requireFragmentManager().beginTransaction().remove(this).commit()
            }

            R.id.btn_address_change_ok -> {


//                val address = addressClick
//                Log.d("mmmmmmmmmmmmmmmmmmmm", "asdfasdfasdf")
//                Log.d("mmmmmmmmmmmmmmmmmmmm", addressClick)

                val data = Intent()
//                data.putExtra("address", address)
                targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, data)
                this.requireFragmentManager().beginTransaction().remove(this).commit()


                this.activity?.finish()

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


        fun newInstance(
            selectAddress: String
        ) = HomeAddressSelectAllFragment().apply {
            arguments = Bundle().apply {
                putString("Address", selectAddress)
            }

        }

    }

}
