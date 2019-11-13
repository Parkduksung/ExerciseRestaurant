package com.work.restaurant

import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*


class GoogleMapFragment : OnMapReadyCallback, Fragment(),
    ActivityCompat.OnRequestPermissionsResultCallback {


    private var map: GoogleMap? = null
    private var currentMarker: Marker? = null
    var currentLocation: Location? = null
    lateinit var currentPosition: LatLng

    private lateinit var mapView: MapView

    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var locationRequest: LocationRequest? = null
    private var location: Location? = null


    private var locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult?) {
            super.onLocationResult(p0)

            val locationList = p0?.locations

            if (locationList?.size!! > 0) {
                location = locationList[locationList.size - 1]

                currentPosition = LatLng(location!!.latitude, location!!.longitude)

                val markerTitle = getCurrentAddress(currentPosition)
                val markerSnippet = ("위도:" + location!!.latitude.toString()
                        + " 경도:" + location!!.longitude.toString())

                setCurrentLocation(location, markerTitle, markerSnippet)
                currentLocation = location


            }
        }
    }


    override fun onMapReady(googleMap: GoogleMap) {

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

        map = googleMap

        setDefaultLocation()
        startLocationUpdates()

        map?.run {
//            isMyLocationEnabled = true
            uiSettings.isMyLocationButtonEnabled = true
        }

//        btn.setOnClickListener {
//            searchLocation()
//        }

    }


    override fun onAttach(context: Context) {
        Log.d(fragmentName, "onAttach")
        super.onAttach(context)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(fragmentName, "onCreate")
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

        return rootView
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(fragmentName, "onActivityCreated")
        super.onActivityCreated(savedInstanceState)


        mapView.run{
            onCreate(savedInstanceState)
            onResume()
            getMapAsync(this@GoogleMapFragment)

        }
//        if (::mapView.isInitialized)
//            mapView.onCreate(savedInstanceState)


        locationRequest = LocationRequest()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY) //정확한 위치 요청
            .setInterval(1000.toLong()) //활성 위치 업데이트 간격
            .setFastestInterval(500.toLong()) //위치 업데이트 간격

        LocationSettingsRequest.Builder().addLocationRequest(locationRequest!!)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this@GoogleMapFragment.context!!)




    }

    fun getCurrentAddress(latLng: LatLng): String {
        val geoCoder = Geocoder(this@GoogleMapFragment.context, Locale.getDefault())
        val addresses = geoCoder.getFromLocation(
            latLng.latitude,
            latLng.longitude,
            1
        )
        return addresses[0].getAddressLine(0).toString()
    }

    fun setCurrentLocation(location: Location?, markerTitle: String, markerSnippet: String) {
        currentMarker?.remove()

        val currentLatLng = LatLng(location!!.latitude, location.longitude)

        val markerOptions = MarkerOptions().apply {
            position(currentLatLng)
            title(markerTitle)
            snippet(markerSnippet)
            draggable(true)
        }

        currentMarker = map?.addMarker(markerOptions)
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))

    }

    private fun setDefaultLocation() {
        val defaultLocation = LatLng(37.56, 126.97)
        val markerTitle = "서울"
        val markerSnippet = "한국의 수도"

        currentMarker?.remove()

        val markerOptions = MarkerOptions().apply {
            position(defaultLocation)
            title(markerTitle)
            snippet(markerSnippet)
            draggable(true)
            icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        }

        currentMarker = map?.addMarker(markerOptions)
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 15f))

    }

    private fun startLocationUpdates() {
        fusedLocationClient?.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.myLooper()
        )
    }


    override fun onStart() {
        Log.d(fragmentName, "onStart")
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        Log.d(fragmentName, "onResume")
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        Log.d(fragmentName, "onPause")
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        Log.d(fragmentName, "onStop")
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroyView() {
        Log.d(fragmentName, "onDestroyView")
        super.onDestroyView()


    }

    override fun onDestroy() {
        Log.d(fragmentName, "onDestroy")
        mapView.onDestroy()
        super.onDestroy()
    }

    override fun onDetach() {
        Log.d(fragmentName, "onDetach")
        super.onDetach()
    }

    companion object {
        private const val fragmentName = "GoogleMapFragment"
    }
}