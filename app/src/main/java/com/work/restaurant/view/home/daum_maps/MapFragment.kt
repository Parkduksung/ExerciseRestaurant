package com.work.restaurant.view.home.daum_maps

import android.Manifest
import android.app.AlertDialog
import android.content.Context.LOCATION_SERVICE
import android.content.DialogInterface
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
import com.work.restaurant.Injection
import com.work.restaurant.R
import com.work.restaurant.data.model.KakaoSearchModel
import com.work.restaurant.util.App
import com.work.restaurant.util.AppExecutors
import com.work.restaurant.view.ExerciseRestaurantActivity.Companion.selectAll
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.home.daum_maps.presenter.MapContract
import com.work.restaurant.view.home.daum_maps.presenter.MapPresenter
import com.work.restaurant.view.search.lookfor.SearchLookForActivity
import kotlinx.android.synthetic.main.map.*
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import java.util.*


class MapFragment : BaseFragment(R.layout.map), MapView.CurrentLocationEventListener,
    MapView.POIItemEventListener,
    MapContract.View, View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.btn_current_location -> {
                selectCurrentLocation()
            }
        }
    }

    override fun onCalloutBalloonOfPOIItemTouched(p0: MapView?, p1: MapPOIItem?) {


        if (currentPOIItem.itemName == p1?.itemName || selectPOIItem.itemName == p1?.itemName) {

        } else {
            val alertDialog =
                AlertDialog.Builder(
                    ContextThemeWrapper(
                        activity,
                        R.style.Theme_AppCompat_Light_Dialog
                    )
                )
            alertDialog.setTitle(p1?.itemName)
            alertDialog.setMessage("상세페이지로 이동하시겠습니까?")
            alertDialog.setPositiveButton(
                "확인",
                object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        val intent =
                            Intent(activity?.application, SearchLookForActivity()::class.java)
                        intent.putExtra(PUT_DATA, p1?.itemName)
                        intent.putExtra(PUT_TOGGLE, true)
                        startActivity(intent)
                    }
                })
            alertDialog.setNegativeButton("취소",
                object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {

                    }
                })
            alertDialog.show()

        }


    }

    override fun onCalloutBalloonOfPOIItemTouched(
        p0: MapView?,
        p1: MapPOIItem?,
        p2: MapPOIItem.CalloutBalloonButtonType?
    ) {

    }

    override fun onDraggablePOIItemMoved(p0: MapView?, p1: MapPOIItem?, p2: MapPoint?) {

    }

    override fun onPOIItemSelected(p0: MapView?, p1: MapPOIItem?) {


    }

    private lateinit var mapView: MapView

    private val selectPOIItem: MapPOIItem by lazy { MapPOIItem() }

    private val currentPOIItem: MapPOIItem by lazy { MapPOIItem() }

    private val kakaoMarkerList = mutableSetOf<MapPOIItem>()

    private lateinit var presenter: MapPresenter


    override fun showKakaoData(currentX: Double, currentY: Double, list: List<KakaoSearchModel>) {
        //currentX  =  longitude  , currentY  =  latitude

        if (!toggleMap) {
            Log.d("카카오결과", list.size.toString())
            mapView.removePOIItem(currentPOIItem)
            val currentMapPoint = MapPoint.mapPointWithGeoCoord(currentY, currentX)
            showCurrentMarker(currentPOIItem, currentMapPoint)
            mapView.currentLocationTrackingMode =
                MapView.CurrentLocationTrackingMode.TrackingModeOff
            mapView.setShowCurrentLocationMarker(false)
        } else {
            mapView.removePOIItem(selectPOIItem)
            val currentMapPoint = MapPoint.mapPointWithGeoCoord(currentY, currentX)
            showCurrentMarker(selectPOIItem, currentMapPoint)
            toggleMap = false
        }

        list.forEach {
            val mapPoint =
                MapPoint.mapPointWithGeoCoord(it.locationY.toDouble(), it.locationX.toDouble())
            showKakaoDataList(it.placeName, mapPoint)
        }

        mapView.addPOIItems(kakaoMarkerList.toTypedArray())

//        kakaoMarkerList.forEach {
//            mapView.addPOIItem(it)
//        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()

        presenter = MapPresenter(
            this,
            Injection.provideKakaoRepository()
        )

        btn_current_location.setOnClickListener(this)


    }

    private fun showKakaoDataList(name: String, mapPoint: MapPoint) {
        val mapPOIItem = MapPOIItem()
        mapPOIItem.apply {
            this.itemName = name
            this.markerType = MapPOIItem.MarkerType.BluePin
            this.mapPoint = mapPoint
            this.selectedMarkerType = MapPOIItem.MarkerType.RedPin
        }
        kakaoMarkerList.add(mapPOIItem)
    }

    private fun showCurrentMarker(mapPOIItem: MapPOIItem, mapPoint: MapPoint) {


        if (mapPOIItem == currentPOIItem) {

            val geoCoder = Geocoder(context, Locale.getDefault())

            val address = geoCoder.getFromLocation(
                mapPoint.mapPointGeoCoord.latitude,
                mapPoint.mapPointGeoCoord.longitude,
                1
            )

            if (address.isNotEmpty()) {
                Log.d("내위치결과", address[0].getAddressLine(0))
            }

            val splitAddress = address[0].getAddressLine(0).split(" ")

            App.prefs.myEditText =
                "${splitAddress[1].subSequence(0, 2)} ${splitAddress[2]} ${splitAddress[3]}"

            mapPOIItem.itemName = "내위치"
        } else {
            mapPOIItem.itemName = selectAll
        }

        mapPOIItem.apply {
            this.markerType = MapPOIItem.MarkerType.YellowPin
            this.mapPoint = mapPoint
            this.selectedMarkerType = MapPOIItem.MarkerType.RedPin
        }
        mapView.addPOIItem(mapPOIItem)
        mapView.selectPOIItem(mapPOIItem, true)
        mapView.setMapCenterPoint(mapPoint, true)


    }


    override fun onCurrentLocationUpdate(p0: MapView?, p1: MapPoint?, p2: Float) {
        p1?.let {
            presenter.getKakaoData(it.mapPointGeoCoord.longitude, it.mapPointGeoCoord.latitude)
        }
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

        AppExecutors().diskIO.execute {

            mapView.currentLocationTrackingMode =
                MapView.CurrentLocationTrackingMode.TrackingModeOff

            val geoCoder = Geocoder(context, Locale.getDefault())

            val addresses = geoCoder.getFromLocationName(location, 1)

            if (addresses.isNotEmpty()) {
                presenter.getKakaoData(addresses[0].longitude, addresses[0].latitude)
            } else {

//                Toast.makeText(this.context, "이동할 수 없습니다.", Toast.LENGTH_SHORT).show()

            }
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
        mapView.setPOIItemEventListener(this)
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
                        mapView.removePOIItem(selectPOIItem)
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

        private const val PUT_DATA = "data"
        private const val PUT_TOGGLE = "toggle"

    }
}