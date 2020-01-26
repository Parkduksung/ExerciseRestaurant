package com.work.restaurant.view.home.daum_maps

import android.Manifest
import android.app.AlertDialog
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.Toast
import androidx.core.view.isEmpty
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.work.restaurant.R
import com.work.restaurant.util.AppExecutors
import com.work.restaurant.view.ExerciseRestaurantActivity.Companion.selectAll
import com.work.restaurant.view.base.BaseFragment
import kotlinx.android.synthetic.main.map.*
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import java.util.*


class MapFragment : BaseFragment(R.layout.map), MapView.CurrentLocationEventListener {

    private lateinit var mapView: MapView
    private lateinit var mapPOIItem: MapPOIItem
    private lateinit var currentPOIItem: MapPOIItem

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
    }

    private fun createMarker(mapPOIItem: MapPOIItem, location: String, mapPoint: MapPoint) {

        mapPOIItem.apply {
            this.itemName = location
            this.markerType = MapPOIItem.MarkerType.BluePin
            this.mapPoint = mapPoint
            this.selectedMarkerType = MapPOIItem.MarkerType.RedPin
        }
        mapView.addPOIItem(mapPOIItem)
//        mapView.selectPOIItem(mapPOIItem, true)
        mapView.setMapCenterPoint(mapPoint, true)
    }


    override fun onCurrentLocationUpdate(p0: MapView?, p1: MapPoint?, p2: Float) {


        if (::currentPOIItem.isInitialized) {
            mapView.removePOIItem(currentPOIItem)
            p1?.let {
                createMarker(currentPOIItem, "내위치", it)
            }
//            currentPOIItem.tag = 0
        } else {
            currentPOIItem = MapPOIItem()
            p1?.let {
                createMarker(currentPOIItem, "내위치", it)
            }


//            currentPOIItem.tag = 0
        }

        Log.d("결과결과123", "${p1?.mapPointGeoCoord?.latitude}")
        Log.d("결과결과123", "${p1?.mapPointGeoCoord?.longitude}")


    }

    override fun onCurrentLocationUpdateCancelled(p0: MapView?) {

    }

    override fun onCurrentLocationDeviceHeadingUpdate(p0: MapView?, p1: Float) {

    }

    override fun onCurrentLocationUpdateFailed(p0: MapView?) {

    }


    private fun selectCurrentLocation() {
        if (::mapView.isInitialized) {
            mapView.currentLocationTrackingMode =
                MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading
        }
    }


    private fun getLocation(location: String) {
        val geoCoder = Geocoder(context, Locale.getDefault())

        val runnable = Runnable {
            val addresses = geoCoder.getFromLocationName(location, 1)
            val returnedAddress = addresses[0]

            for (i in 0..returnedAddress.maxAddressLineIndex) {
                val strReturnedAddress = StringBuilder("")
                strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n")

                val mapPoint =
                    MapPoint.mapPointWithGeoCoord(addresses[i].latitude, addresses[i].longitude)

                createMarker(mapPOIItem, selectAll, mapPoint)
            }
        }
        if (::mapPOIItem.isInitialized) {
            mapView.removePOIItem(mapPOIItem)
            AppExecutors().diskIO.execute(runnable)
        } else {
            mapPOIItem = MapPOIItem()
            AppExecutors().diskIO.execute(runnable)
        }

    }


    //Gps잡히는지 확인하는것.
    private fun checkLocationServicesStatus(): Boolean {
        val locationManager = context?.getSystemService(LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }


    private fun checkPermission() {
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
            if (checkLocationServicesStatus()) {
                loadMap()
            } else {
                showDialogForLocationServiceSetting()
                Toast.makeText(context, "권한이 허용되었습니다", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onPermissionDenied(deniedPermissions: ArrayList<String>?) {
            Toast.makeText(context, "권한이 거부되었습니다.\n\n$deniedPermissions", Toast.LENGTH_SHORT)
                .show()
        }

    }

    private fun loadMap() {
        Toast.makeText(context, "GPS 활성화, 권한이 허용되었습니다", Toast.LENGTH_SHORT).show()
        mapView = MapView(this.context)
        mapView.setCurrentLocationEventListener(this)

        map_view.addView(mapView)
    }


    private fun showDialogForLocationServiceSetting() {
        val alertDialog =
            AlertDialog.Builder(
                ContextThemeWrapper(
                    activity,
                    R.style.Theme_AppCompat_Light_Dialog
                )
            )

        alertDialog.setTitle("위치 서비스 비활성화")
        alertDialog.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n" + "위치 설정을 수정하실래요?")
        alertDialog.setPositiveButton(
            "설정"
        ) { _, _ ->
            val callGPSSettingIntent =
                Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE)
        }
        alertDialog.setNegativeButton(
            "취소"
        ) { dialog, _ ->
            dialog.cancel()
        }

        alertDialog.show()


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            GPS_ENABLE_REQUEST_CODE ->
                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    mapView = MapView(this.requireContext())
                    map_view.addView(mapView)
                    Toast.makeText(context, "GPS 활성화 되었습니다", Toast.LENGTH_SHORT).show()

                    return
                }
        }
    }

    override fun onResume() {
        super.onResume()

        if (::mapView.isInitialized) {
            if (!checkLocationServicesStatus()) {
                map_view.removeView(mapView)
                Toast.makeText(context, "GPS 비활성화 되었습니다 \n 다시 활성화를 하세요.", Toast.LENGTH_SHORT).show()
            } else {
                if (map_view.isEmpty()) {
                    checkPermission()
                } else {
                    if (toggleMap) {
                        mapView.currentLocationTrackingMode =
                            MapView.CurrentLocationTrackingMode.TrackingModeOff
                        mapView.setShowCurrentLocationMarker(false)
                        getLocation(selectAll)
                    } else {
                        selectCurrentLocation()
                    }
                }
            }
        }
    }

    companion object {

        private const val GPS_ENABLE_REQUEST_CODE = 1
        var toggleMap = false

    }
}