package com.work.restaurant.ext

import android.content.Context
import android.location.LocationManager
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.fragment.app.Fragment


fun Context.isConnectedToNetwork(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    return connectivityManager?.activeNetwork?.let { true } ?: false
}

fun Context.isConnectedToGPS(): Boolean {
    val locationManager =
        getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
        LocationManager.NETWORK_PROVIDER
    )
}

fun Fragment.isConnectedToGPS(): Boolean {
    val locationManager =
        context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
        LocationManager.NETWORK_PROVIDER
    )
}

fun Context.showToast(message: String, kind: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, kind).show()
}

fun Fragment.showToast(message: String, kind: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, message, kind).show()
}