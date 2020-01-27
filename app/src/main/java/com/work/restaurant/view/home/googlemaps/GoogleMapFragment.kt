package com.work.restaurant.view.home.googlemaps


import android.Manifest
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
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

    //    lateinit var mService: GoogleApi
//    internal lateinit var currentPlace: PlaceResponse

    override fun onMarkerClick(p0: Marker?) = false

    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap

        if (toggleMap) {
            getLocation(selectAll)
//            nearByPlace("gym")
            placeMarkerOnMap(LatLng(lastLocation.latitude, lastLocation.longitude))
        }

        mMap.uiSettings.isZoomControlsEnabled = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.google_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        Log.d("GoogleMapFragment1", "onViewCreated")
        mapView.onCreate(savedInstanceState)
        presenter = GoogleMapPresenter(this)
        checkPer()

    }

    private fun checkPer() {
        TedPermission.with(context)
            .setPermissionListener(permissionListener)
            .setRationaleMessage("앱의 기능을 사용하기 위해서는 권한이 필요합니다.")
            .setDeniedMessage("만약 권한을 다시 얻으려면, \n\n[설정] > [권한] 에서 권한을 허용할 수 있습니다.")
            .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
//            .setPermissions(android.Manifest.permission.INTERNET,android.Manifest.permission.CALL_PHONE)
            .check()
    }

    private val permissionListener: PermissionListener = object : PermissionListener {

        override fun onPermissionGranted() {
            loadMap()
            Toast.makeText(activity, "권한이 허용되었습니다", Toast.LENGTH_SHORT).show()
        }

        override fun onPermissionDenied(deniedPermissions: ArrayList<String>?) {
            Toast.makeText(activity, "권한이 거부되었습니다.\n\n$deniedPermissions", Toast.LENGTH_SHORT)
                .show()
        }

    }

    private fun loadMap() {

        mapView.onResume()

        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(App.instance.context())

        createLocationCallBack()
        createLocationRequest()
        getCurrentLocation()
    }


//    private fun nearByPlace(typePlace: String) {
//
//        mMap.clear()
//
//        val url = getUrl(latLng.latitude, latLng.longitude, typePlace)
//
//        RetrofitInstance.getInstance<GoogleApi>("https://maps.googleapis.com/")
//            .getNearbyPlaces(url).enqueue(object : Callback<PlaceResponse> {
//                override fun onFailure(call: Call<PlaceResponse>?, t: Throwable?) {
//                    Toast.makeText(activity, "" + t!!.message, Toast.LENGTH_SHORT).show()
//                }
//
//                override fun onResponse(
//                    call: Call<PlaceResponse>?,
//                    response: Response<PlaceResponse>?
//                ) {
//
//                    Log.d("결과결과4", "${response?.body()?.results?.size}")
//
//                    if (response != null) {
//                        currentPlace = response.body()
//                        if (response.isSuccessful) {
//
//                            currentPlace = response.body()
//                            if (response.body().results?.size == 0) {
//                                Toast.makeText(App.instance.context(), "결과 없음.", Toast.LENGTH_LONG)
//                                    .show()
//                            } else {
//                                for (i in 0 until (response.body().results?.size ?: 0)) {
//
//                                    val marketOption = MarkerOptions()
//                                    val googlePlace = response.body().results!![i]
//                                    val lat = googlePlace.geometry!!.location!!.lat
//                                    val lng = googlePlace.geometry!!.location!!.lng
//                                    val placeName = googlePlace.name
//                                    val latLng = LatLng(lat, lng)
//
//                                    marketOption.position(latLng)
//                                    marketOption.title(placeName)
////                        아이콘 바꾸는거. bitmap size 생각도 해야됨.
////                        if(typePlace == "hospital"){
////                            marketOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_cooking))
////                        }
//
//                                    marketOption.icon(
//                                        BitmapDescriptorFactory.defaultMarker(
//                                            BitmapDescriptorFactory.HUE_BLUE
//                                        )
//                                    )
//
//                                    mMap.addMarker(marketOption)
//
//                                }
//                            }
//
//                        }
//                    } else {
//                        Toast.makeText(App.instance.context(), "결과 없음.", Toast.LENGTH_LONG).show()
//                    }
//                }
//
//            })
//
//    }

    private fun getUrl(latitude: Double, longitude: Double, typePlace: String): String {

        val googlePlaceUrl =
            StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json")
        googlePlaceUrl.append("?location=$latitude,$longitude")
        googlePlaceUrl.append("&radius=5000") // 1km
        googlePlaceUrl.append("&type=$typePlace")
        googlePlaceUrl.append("&key=AIzaSyBJhfUcKYnQiS3_Vx92iHv0UVFwL4IjKIE")

        Log.d("URL_DEBUG", googlePlaceUrl.toString())
        return googlePlaceUrl.toString()

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
//                nearByPlace("gym")
                placeMarkerOnMap(latLng)
                mMap.isMyLocationEnabled = true
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

    private fun getLocation(location: String) {
        mMap.clear()
        val geoCoder = Geocoder(App.instance.context(), Locale.getDefault())
        val addresses = geoCoder.getFromLocationName(
            location,
            1
        )

        if (addresses != null) {
            for (i in 0 until addresses.size) {
                latLng = LatLng(addresses[i].latitude, addresses[i].longitude)
                val markerOptions = MarkerOptions().apply {
                    position(latLng)
                    title(selectAll)
                    draggable(true)
                }

                mMap.run {
                    if (::lastLocation.isInitialized) {
                        placeMarkerOnMap(LatLng(lastLocation.latitude, lastLocation.longitude))
                    }
                    addMarker(markerOptions)
                    animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                }
            }
        }

    }

    private fun placeMarkerOnMap(location: LatLng) {
        val markerOptions = MarkerOptions()
            .position(location)
            .title("내 위치")
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

//    override fun onDestroyView() {
//        toggleMap=false
//        Log.d("GoogleMapFragment1", "onDestroyView")
//        super.onDestroyView()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        Log.d("GoogleMapFragment1", "onStop")
//    }
//
//    override fun onPause() {
//        super.onPause()
//        Log.d("GoogleMapFragment1", "onPause")
//    }
//
//    override fun onDetach() {
//        super.onDetach()
//        Log.d("GoogleMapFragment1", "onDetach")
//    }

    override fun onDestroy() {
        super.onDestroy()
        toggleMap = false
        Log.d("GoogleMapFragment1", "onDestroy")
    }

    companion object {
        private const val TAG = "GoogleMapFragment"
        var toggleMap = false

    }
}