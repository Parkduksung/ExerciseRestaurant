package com.work.restaurant.view.activity.home

import android.widget.TextView

interface HomeAddressContract {
    interface View {

        fun showBackPage()

        fun showRoadItem(address: TextView, list: List<String>)

    }

    interface Presenter {


        fun backPage()

        fun getRoadItem(address: TextView, clickData: String, area: String, zone: String)

    }
}