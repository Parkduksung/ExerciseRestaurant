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
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
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
    lateinit var latLng: LatLng
    private var mMarker: Marker? = null

    override fun onMarkerClick(p0: Marker?) = false

    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap

        if (::lastLocation.isInitialized) {
            getLocation(selectAll)
        }

        mMap.isMyLocationEnabled = true
        mMap.uiSettings.isZoomControlsEnabled = true
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapView.onCreate(savedInstanceState)
        presenter = GoogleMapPresenter(this)

        if (checkLocationPermission()) {
            loadMap()
        }

    }

    private fun checkLocationPermission(): Boolean {

        val checkPermission = ContextCompat.checkSelfPermission(
            this.requireContext(),
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )

        if (checkPermission != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this.requireActivity(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                ActivityCompat.requestPermissions(
                    this.requireActivity(),
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            } else {
                ActivityCompat.requestPermissions(
                    this.requireActivity(),
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            }
            return false

        } else return true

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this.requireContext(), "Permission 승인o", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(this.requireContext(), "Permission 승인x", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun loadMap() {

        mapView.onResume()
        mapView.getMapAsync(this)

        checkLocationPermission()

        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(App.instance.context())

        createLocationCallBack()
        createLocationRequest()
        getCurrentLocation()
    }


    private fun createLocationCallBack() {
        locationCallBack = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                mMap.clear()


                if (locationResult != null) {
                    lastLocation = locationResult.lastLocation
                }

                mMarker?.remove()
                latLng = LatLng(lastLocation.latitude, lastLocation.longitude)
                Log.d("결과결과2", presenter.getAddress(latLng))
                placeMarkerOnMap(latLng)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))

            }
        }
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
//            .setInterval(5000)
//            .setFastestInterval(3000)
//            .setSmallestDisplacement(10f)
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
        val markerOptions = MarkerOptions()
            .position(location)
            .title("Your position")
            .icon(
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
            )
//        val titleStr = presenter.getAddress(location)
//        markerOptions.title(titleStr)

        mMarker = mMap.addMarker(markerOptions)
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