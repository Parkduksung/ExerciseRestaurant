package com.work.restaurant.view.home.daum_maps

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.Toast
import androidx.core.view.isEmpty
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.work.restaurant.Injection
import com.work.restaurant.R
import com.work.restaurant.data.model.KakaoSearchModel
import com.work.restaurant.util.AppExecutors
import com.work.restaurant.view.ExerciseRestaurantActivity.Companion.selectAll
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.home.MapInterface
import com.work.restaurant.view.home.daum_maps.presenter.MapContract
import com.work.restaurant.view.home.daum_maps.presenter.MapPresenter
import kotlinx.android.synthetic.main.map.*
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import java.util.*


class MapFragment : BaseFragment(R.layout.map), MapView.CurrentLocationEventListener,
    MapView.POIItemEventListener, MapView.MapViewEventListener,
    MapContract.View, View.OnClickListener {

    private lateinit var mapView: MapView

    private val selectPOIItem: MapPOIItem by lazy { MapPOIItem() }

    private val currentPOIItem: MapPOIItem by lazy { MapPOIItem() }

    private val kakaoMarkerList = mutableSetOf<MapPOIItem>()

    private lateinit var presenter: MapPresenter

    private lateinit var mapInterface: MapInterface.CurrentLocationClickListener

    private lateinit var markerInterface: MapInterface.SelectMarkerListener

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.btn_current_location -> {
                selectCurrentLocation()
            }

        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (parentFragment is MapInterface.CurrentLocationClickListener) {
            mapInterface = parentFragment as MapInterface.CurrentLocationClickListener
        }
        if (parentFragment is MapInterface.SelectMarkerListener) {
            markerInterface = parentFragment as MapInterface.SelectMarkerListener
        }
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


    private fun loadMap() {
        Toast.makeText(context, "GPS 활성화, 권한이 허용되었습니다", Toast.LENGTH_SHORT).show()
        mapView = MapView(this.context)
        mapView.setCurrentLocationEventListener(this)
        mapView.setMapViewEventListener(this)
        mapView.setPOIItemEventListener(this)
        map_view.addView(mapView)
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


    //MapViewEventListener
    override fun showMarkerData(list: List<KakaoSearchModel>) {

        if (list.isNotEmpty()) {

            if (::markerInterface.isInitialized) {
                markerInterface.getMarkerData(list[0])
                mapInterface.click(true)
            } else {

                markerInterface = object : MapInterface.SelectMarkerListener {
                    override fun getMarkerData(data: KakaoSearchModel) {

                    }
                }
                markerInterface.getMarkerData(list[0])
                mapInterface.click(true)
            }

        }

    }

    override fun onMapViewDoubleTapped(p0: MapView?, p1: MapPoint?) {

    }

    override fun onMapViewInitialized(p0: MapView?) {

    }

    override fun onMapViewDragStarted(p0: MapView?, p1: MapPoint?) {

        if (p0 != null) {
            if (::mapInterface.isInitialized) {
                mapInterface.click(false)
            } else {
                mapInterface = object : MapInterface.CurrentLocationClickListener {
                    override fun click(clickData: Boolean) {
                    }
                }
                mapInterface.click(false)
            }
        }

    }

    override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {

    }

    override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {

    }

    override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {
        dragMap = true
        Handler().postDelayed({
            p1?.let {
                presenter.getKakaoData(it.mapPointGeoCoord.longitude, it.mapPointGeoCoord.latitude)
            }

        }, 3000L)

    }

    override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint?) {

    }

    override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {

    }

    override fun onMapViewLongPressed(p0: MapView?, p1: MapPoint?) {

    }


    //POIItemEventListener
    override fun onCalloutBalloonOfPOIItemTouched(p0: MapView?, p1: MapPOIItem?) {

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

        if (p0 != null && p1 != null) {

            if (::mapInterface.isInitialized) {

                presenter.getMarkerData(p1.itemName)
            } else {
                mapInterface = object : MapInterface.CurrentLocationClickListener {
                    override fun click(clickData: Boolean) {
                    }
                }
                presenter.getMarkerData(p1.itemName)
            }

        }


    }

    //CurrentLocationEventListener
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


    //Marker 표현하는거 관련
    override fun showKakaoData(currentX: Double, currentY: Double, list: List<KakaoSearchModel>) {
        //currentX  =  longitude  , currentY  =  latitude

        if (!dragMap) {
            if (!toggleMap) {
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
        }

        list.forEach {
            val mapPoint =
                MapPoint.mapPointWithGeoCoord(it.locationY.toDouble(), it.locationX.toDouble())
            showKakaoDataList(it.placeName, mapPoint)
        }

        mapView.addPOIItems(kakaoMarkerList.toTypedArray())

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

    //CurrentLocationEventListener 활성하 on/off
    private fun selectCurrentLocation() {
        if (::mapView.isInitialized) {
            mapView.currentLocationTrackingMode =
                MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading
        }
    }

    //주소 변경한 위치 좌표얻어오는것
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


    //Gps잡히는지 확인하는것. 등 GPS관련
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

        var dragMap = false

    }
}