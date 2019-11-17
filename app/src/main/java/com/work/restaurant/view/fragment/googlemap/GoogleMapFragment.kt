@file:Suppress("DEPRECATION")

package com.work.restaurant.view.fragment.googlemap

import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.work.restaurant.R
import java.io.IOException
import java.util.*


@Suppress("DEPRECATION")
class GoogleMapFragment : OnMapReadyCallback, Fragment(), GoogleMap.OnMarkerClickListener {
    override fun onMarkerClick(p0: Marker?) = false


    private lateinit var mMap: GoogleMap
    private lateinit var mapView: MapView
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location

    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap
//        val seoulPosition = LatLng(37.56, 126.97)
//        val markerOptions = MarkerOptions().apply {
//            position(seoulPosition)
//            title("서울")
//            snippet("한국의 수도")
//        }
//
//        googleMap.apply {
//            addMarker(markerOptions)
//            moveCamera(CameraUpdateFactory.newLatLng(seoulPosition))
//            animateCamera(CameraUpdateFactory.zoomTo(10f))
//        }
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setOnMarkerClickListener(this)

        setUpMap()

    }


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

        val rootView = inflater.inflate(R.layout.google_maps, container, false)

        mapView = rootView.findViewById(R.id.map)
        mapView.getMapAsync(this)
        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(this.requireActivity())

        return rootView
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated")
        super.onActivityCreated(savedInstanceState)

        if (::mapView.isInitialized) {
            mapView.onCreate(savedInstanceState)

        }


    }

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(
                this.requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this.requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }

        mMap.isMyLocationEnabled = true

        fusedLocationClient.lastLocation.addOnSuccessListener(this.requireActivity()) { location ->
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                placeMarkerOnMap(currentLatLng)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
            }
        }
    }

    private fun placeMarkerOnMap(location: LatLng) {

        val markerOptions = MarkerOptions().position(location)

        val titleStr = getAddress(location)  // add these two lines
        markerOptions.title(titleStr)

        mMap.addMarker(markerOptions)
    }

    private fun getAddress(latLng: LatLng): String {

        val geoCoder = Geocoder(this.context, Locale.getDefault())
        val addresses: List<Address>

        var array: List<String>

        try {

            addresses = geoCoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            Log.d("sssssssssssssssssssssss", addresses[0].toString())
            Log.d("sssssssssssssssssssssss", addresses[0].adminArea.substring(0, 2))
            Log.d("ttttttttttttttttttttttt", addresses[0].getAddressLine(0))

            array = addresses[0].getAddressLine(0).split(" ")

            for (i in 0 until array.size) {
                Log.e("cccccccccccccccccccccccccccccccccccccccc", array[i])
            }

            address1 = array[1].substring(0, 2)
            address2 = array[2]
            address3 = array[3]

            addressAll = "$address1 $address2 $address3"

        } catch (e: IOException) {
            Log.e("MapsActivity", e?.localizedMessage)
        }

        return addressAll
    }


    override fun onStart() {
        Log.d(TAG, "onStart")
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        Log.d(TAG, "onResume")
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        Log.d(TAG, "onPause")
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        Log.d(TAG, "onStop")
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroyView() {
        Log.d(TAG, "onDestroyView")
        super.onDestroyView()


    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        mapView.onDestroy()
        super.onDestroy()
    }

    override fun onDetach() {
        Log.d(TAG, "onDetach")
        super.onDetach()
    }

    companion object {
        private const val TAG = "GoogleMapFragment"
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1


        // 주소 변경 할때 사용용도 및 현재 주소 표현
        lateinit var address1: String
        lateinit var address2: String
        lateinit var address3: String
        lateinit var addressAll: String
    }
}