package com.work.restaurant.view.home.address.presenter

import android.widget.TextView

interface HomeAddressContract {
    interface View {

        fun showRoadItem(address: TextView, list: List<String>)

    }

    interface Presenter {

        fun getRoadItem(address: TextView, clickData: String, area: String, zone: String)

    }
}