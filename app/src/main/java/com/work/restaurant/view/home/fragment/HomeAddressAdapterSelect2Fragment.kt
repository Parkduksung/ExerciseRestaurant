package com.work.restaurant.view.home.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.work.restaurant.R
import com.work.restaurant.view.adapter.AdapterDataListener
import com.work.restaurant.view.adapter.AddressAdapter
import com.work.restaurant.view.home.fragment.HomeAddressFragment.Companion.addressClick
import com.work.restaurant.view.home.fragment.HomeAddressFragment.Companion.selectAddress2
import kotlinx.android.synthetic.main.home_address_select2_fragment.*

class HomeAddressAdapterSelect2Fragment : Fragment(), AdapterDataListener {
    override fun getData(data: String) {
        Log.d("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk", addressClick)

        selectAddress2 = data
        addressClick += data

        Log.d("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk1", addressClick)
    }


    private lateinit var addressAdapter: AddressAdapter


    override fun onAttach(context: Context) {
        Log.d(TAG, "onAttach")
        super.onAttach(context)
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
        return inflater.inflate(R.layout.home_address_select2_fragment, container, false).also {
            addressAdapter = AddressAdapter()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated")
        super.onActivityCreated(savedInstanceState)

        addressAdapter.setItemClickListener(this)
        startView()

    }

    private fun startView() {

        val loadingTextArrayList = resources.getStringArray(R.array.인천)

        recyclerview_address2.run {
            this.adapter = addressAdapter

            loadingTextArrayList.forEach {
                addressAdapter.addData(it)
            }
            layoutManager = GridLayoutManager(this.context, 3)
        }
    }

    override fun onStart() {
        Log.d(TAG, "onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.d(TAG, "onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.d(TAG, "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d(TAG, "onStop")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.d(TAG, "onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.d(TAG, "onDetach")
        super.onDetach()
    }

    companion object {
        private const val TAG = "HomeAddressSelect2Fragment"
    }

}
