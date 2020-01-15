package com.work.restaurant.view.home.googlemaps.presenter

import android.location.Geocoder
import com.google.android.gms.maps.model.LatLng
import com.work.restaurant.util.App
import java.util.*

class GoogleMapPresenter(private val googleMapView: GoogleMapContract.View) :
    GoogleMapContract.Presenter {
    override fun getAddress(latLng: LatLng): String {

        val geocoder = Geocoder(App.instance.context(), Locale.getDefault())
        val addresses = geocoder.getFromLocation(
            latLng.latitude,
            latLng.longitude,
            1
        )

        return addresses[0].getAddressLine(0).toString()
    }
}