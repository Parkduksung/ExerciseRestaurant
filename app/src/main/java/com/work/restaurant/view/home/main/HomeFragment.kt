package com.work.restaurant.view.home.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.work.restaurant.R
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.home.address.HomeAddressActivity
import com.work.restaurant.view.home.daum_maps.MapFragment
import kotlinx.android.synthetic.main.home_fragment.*


class HomeFragment : BaseFragment(R.layout.home_fragment), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.et_home -> {
                val homeAddressActivity = Intent(this.context, HomeAddressActivity::class.java)
                startActivity(homeAddressActivity)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        et_home.setOnClickListener(this)
        startMaps()

    }

    private fun startMaps() {

        val fragmentManager = fragmentManager
        val fragmentTransaction = fragmentManager!!.beginTransaction()

        fragmentTransaction.add(
            R.id.maps_fl,
            MapFragment()
        ).commit()

    }

    companion object {

        private const val TAG = "HomeFragment"
    }

}