package com.work.restaurant.view.home.googlemaps.presenter

import com.google.android.gms.maps.model.LatLng

interface GoogleMapContract {

    interface View {

    }


    interface Presenter {

        fun getAddress(latLng: LatLng): String

    }
}