@file:Suppress("DEPRECATION")

package com.work.restaurant.view.home.googlemaps

import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.work.restaurant.R
import com.work.restaurant.util.App
import com.work.restaurant.view.ExerciseRestaurantActivity.Companion.selectAll
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.home.googlemaps.presenter.GoogleMapContract
import com.work.restaurant.view.home.googlemaps.presenter.GoogleMapPresenter
import kotlinx.android.synthetic.main.google_maps.*
import java.util.*


class GoogleMapFragment : GoogleMapContract.View,
    OnMapReadyCallback,
    BaseFragment(R.layout.google_maps),
    GoogleMap.OnMarkerClickListener {

    private lateinit var presenter: GoogleMapPresenter
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallBack: LocationCallback
    lateinit var lastLocation: Location
    lateinit var currentLatLng: LatLng

    override fun onMarkerClick(p0: Marker?) = false

    override fun onMapReady(mMap: GoogleMap) {

        this.mMap = mMap

//        getCurrentLocation()
        getLocation(selectAll)

//        mMap.uiSettings.isMyLocationButtonEnabled = true
//        mMap.isMyLocationEnabled = true // 현재 위치로 오는 버튼 생성됨.
//        mMap?.isMyLocationEnabled = true
//        mMap!!.uiSettings.isMyLocationButtonEnabled = true
        mMap.uiSettings.isZoomControlsEnabled = true
        this.mMap.setOnMarkerClickListener(this)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapView.onCreate(savedInstanceState)
        loadMap()
        presenter = GoogleMapPresenter(this)

    }


    private fun loadMap() {

        mapView.onResume()
        mapView.getMapAsync(this)

        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(App.instance.context())

        createLocationCallBack()
        createLocationRequest()
    }


    private fun createLocationCallBack() {
        locationCallBack = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)

                mMap.clear()
                if (locationResult != null) {
                    lastLocation = locationResult.lastLocation
                }
                currentLatLng = LatLng(lastLocation.latitude, lastLocation.longitude)
                Log.d("결과결과2", presenter.getAddress(currentLatLng))
                placeMarkerOnMap(currentLatLng)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
            }
        }
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
    }

    private fun getCurrentLocation() {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallBack,
            Looper.myLooper()
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.google_maps, container, false)
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
    }

    private fun getLocation(location: String) {
        mMap.clear()
        val geoCoder = Geocoder(App.instance.context(), Locale.getDefault())
        val addresses = geoCoder.getFromLocationName(
            location,
            1
        )

        if (addresses != null) {
            for (i in 0 until addresses.size) {
                val searchLatLng = LatLng(addresses[i].latitude, addresses[i].longitude)
                val markerOptions = MarkerOptions().apply {
                    position(searchLatLng)
                    title(selectAll)
                    draggable(true)
                }

                mMap.run {
                    addMarker(markerOptions)
                    animateCamera(CameraUpdateFactory.newLatLngZoom(searchLatLng, 15f))
                }
            }
        }
    }

    private fun placeMarkerOnMap(location: LatLng) {
        val markerOptions = MarkerOptions().position(location)
        val titleStr = presenter.getAddress(location)
        markerOptions.title(titleStr)
        mMap.addMarker(markerOptions)
    }

    override fun onResume() {
        super.onResume()
        mapView.getMapAsync(this)
    }

    companion object {
        private const val TAG = "GoogleMapFragment"
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1

    }
}