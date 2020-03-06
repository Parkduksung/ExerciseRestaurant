package com.work.restaurant.view.home.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.work.restaurant.R
import com.work.restaurant.data.model.KakaoSearchModel
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.home.MapInterface
import com.work.restaurant.view.home.address.HomeAddressActivity
import com.work.restaurant.view.home.address_select_all.HomeAddressSelectAllFragment
import com.work.restaurant.view.home.daum_maps.MapFragment
import com.work.restaurant.view.search.lookfor.SearchLookForActivity
import kotlinx.android.synthetic.main.home_fragment.*


class HomeFragment : BaseFragment(R.layout.home_fragment), View.OnClickListener,
    MapInterface.CurrentLocationClickListener, MapInterface.SelectMarkerListener {


    override fun getMarkerData(data: KakaoSearchModel) {
        tv_marker_place_address.text = data.addressName
        tv_marker_place_name.text = data.placeName
        getMarkerUrl = data.placeUrl
    }

    override fun click(clickData: Boolean) {

        if (clickData) {
            ll_marker_details.isVisible = true
        } else {
            ll_marker_details.isVisible = false
            tv_marker_place_address.text = ""
            tv_marker_place_name.text = ""
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADDRESS_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val getAddressData = data?.getStringExtra(HomeAddressSelectAllFragment.ADDRESS)
                getAddressData?.let {
                    requireFragmentManager().fragments.forEach { ParentFragment ->
                        if (ParentFragment is HomeFragment) {
                            ParentFragment.childFragmentManager.fragments.forEach { ChildFragment ->
                                if (ChildFragment is MapFragment) {
                                    MapFragment.toggleSelectLocation = true
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    private fun startHomeAddress() {
        val homeAddressActivity = Intent(this.context, HomeAddressActivity::class.java)
        startActivityForResult(homeAddressActivity, ADDRESS_REQUEST_CODE)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.et_search_address -> {
                startHomeAddress()
            }

            R.id.iv_search_address -> {
                startHomeAddress()
            }

            R.id.ll_search_address -> {
                startHomeAddress()
            }

            R.id.ll_marker_details -> {
                if (getMarkerUrl != "") {
                    val intent =
                        Intent(activity?.application, SearchLookForActivity()::class.java).apply {
                            putExtra(MARKER_CLICK_DATA, getMarkerUrl)
                            putExtra(MARKER_CLICK_TOGGLE, true)
                        }
                    startActivity(intent)
                } else {
                    Toast.makeText(this.context, "접속할 수 없습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        et_search_address.setOnClickListener(this)
        ll_search_address.setOnClickListener(this)
        iv_search_address.setOnClickListener(this)
        startMaps()
        ll_marker_details.setOnClickListener(this)
    }

    private fun startMaps() {

        childFragmentManager.beginTransaction().add(
            R.id.maps_fl,
            MapFragment()
        ).addToBackStack(null).commit()
    }

    companion object {

        private const val TAG = "HomeFragment"

        private var getMarkerUrl = ""

        const val MARKER_CLICK_DATA = "marker_click_data"
        const val MARKER_CLICK_TOGGLE = "marker_click_toggle"

        const val ADDRESS_REQUEST_CODE = 1
    }

}