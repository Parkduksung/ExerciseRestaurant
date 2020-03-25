package com.work.restaurant.view.home.daum_maps

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.Toast
import androidx.core.view.contains
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.work.restaurant.Injection
import com.work.restaurant.R
import com.work.restaurant.data.model.DisplayBookmarkKakaoModel
import com.work.restaurant.data.model.KakaoSearchModel
import com.work.restaurant.util.App
import com.work.restaurant.util.AppExecutors
import com.work.restaurant.view.ExerciseRestaurantActivity.Companion.selectAll
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.home.MapInterface
import com.work.restaurant.view.home.address.HomeAddressActivity.Companion.ADDRESS
import com.work.restaurant.view.home.daum_maps.presenter.MapContract
import com.work.restaurant.view.home.daum_maps.presenter.MapPresenter
import kotlinx.android.synthetic.main.map.*
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import java.util.*
import kotlin.math.pow


class MapFragment : BaseFragment(R.layout.map),
    MapView.POIItemEventListener, MapView.MapViewEventListener,
    MapContract.View, View.OnClickListener {


    private lateinit var mapView: MapView

    private val selectPOIItem: MapPOIItem by lazy { MapPOIItem() }

    private val currentPOIItem: MapPOIItem by lazy { MapPOIItem() }

    private val kakaoMarkerList = mutableSetOf<MapPOIItem>()

    private lateinit var oldCenterPoint: MapPoint

    private lateinit var presenter: MapPresenter

    private lateinit var mapInterface: MapInterface.CurrentLocationClickListener

    private lateinit var markerInterface: MapInterface.SelectMarkerListener

    private lateinit var searchInterface: MapInterface.SearchLocationListener

    private val permissionListener: PermissionListener by lazy {
        object : PermissionListener {

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
    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.btn_current_location -> {
                showCurrentLocation()
            }

        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (parentFragment as? MapInterface.CurrentLocationClickListener)?.let {
            mapInterface = it
        }

        (parentFragment as? MapInterface.SelectMarkerListener)?.let {
            markerInterface = it
        }

        (parentFragment as? MapInterface.SearchLocationListener)?.let {
            searchInterface = it
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()

        presenter = MapPresenter(
            this,
            Injection.provideKakaoRepository(),
            Injection.provideBookmarkRepository()
        )

        btn_current_location.setOnClickListener(this)

        val bundle = arguments
        if (bundle != null) {
            val c = bundle.getString(ADDRESS)

            c?.let {
                Log.d("전달됨..", bundle.getString(ADDRESS)!!)
            }
        }


    }


    private fun loadMap() {
        Toast.makeText(context, "GPS 활성화, 권한이 허용되었습니다", Toast.LENGTH_SHORT).show()
        mapView = MapView(this.context)
//        mapView.setCurrentLocationEventListener(this)
        mapView.setMapViewEventListener(this)
        mapView.setPOIItemEventListener(this)


        map_view.addView(mapView)

        showCurrentLocation()

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
    override fun showMarkerData(list: List<DisplayBookmarkKakaoModel>) {
        if (list.isNotEmpty()) {
            if (::markerInterface.isInitialized) {
                markerInterface.getMarkerData(list[0])
                mapInterface.click(true)
            } else {
                markerInterface = object : MapInterface.SelectMarkerListener {
                    override fun getMarkerData(data: DisplayBookmarkKakaoModel) {
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


    private fun getDistance(oldCenter: MapPoint, currentCenter: MapPoint): Int {

        val locationA = Location("A")
        val locationB = Location("B")

        locationA.latitude = oldCenter.mapPointGeoCoord.latitude
        locationA.longitude = oldCenter.mapPointGeoCoord.longitude

        locationB.latitude = currentCenter.mapPointGeoCoord.latitude
        locationB.longitude = currentCenter.mapPointGeoCoord.longitude

        return locationA.distanceTo(locationB).toInt()
    }


    override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {


    }

    override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {
//        Log.d("중앙위치", p1!!.mapPointGeoCoord.latitude.toString())

        if (p0 != null && p1 != null) {
            if (getDistance(oldCenterPoint, p1) >= zoomLevelToRadius(p0.zoomLevel)) {

                if (::searchInterface.isInitialized) {
                    oldCenterPoint = p0.mapCenterPoint
                    searchInterface.finishOrNoResult(3)
                    toggleSearchLocation = false
                }

            }
        }


    }

    override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {
//        Handler().postDelayed({
//            p1?.let {
//                presenter.getKakaoData(it.mapPointGeoCoord.longitude, it.mapPointGeoCoord.latitude)
//            }
//        }, 1500L)
    }

    override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint?) {


    }

    override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {

        if (::searchInterface.isInitialized) {
            p0?.let {
                oldCenterPoint = it.mapCenterPoint
                searchInterface.finishOrNoResult(3)
                toggleSearchLocation = false
            }
        }

        Log.d("줌레벨변화는?", p1.toString())
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
                presenter.getMarkerData(
                    p1.mapPoint.mapPointGeoCoord.longitude,
                    p1.mapPoint.mapPointGeoCoord.latitude,
                    p1.itemName
                )
            } else {
                mapInterface = object : MapInterface.CurrentLocationClickListener {
                    override fun click(clickData: Boolean) {
                    }
                }
                presenter.getMarkerData(
                    p1.mapPoint.mapPointGeoCoord.longitude,
                    p1.mapPoint.mapPointGeoCoord.latitude,
                    p1.itemName
                )
            }
        }
    }

    //Marker 표현하는거 관련
    override fun showKakaoData(
        list: List<KakaoSearchModel>
    ) {

        if (toggleSelectLocation) {

            Log.d("아니왜결과왜이래", list[list.size - 1].distance)

            autoZoomLevel(list[list.size - 1].distance.toInt())
            toggleSelectLocation = false
        }

        if (toggleCurrentLocation) {

            Log.d("아니왜결과왜이래", list[list.size - 1].distance)

            autoZoomLevel(list[list.size - 1].distance.toInt())
            toggleCurrentLocation = false
        }


//        Log.d("거리는얼마나?", (list[list.size - 1].distance.toInt()).toString())

//        autoZoomLevel(list[0].distance.toInt())
        makeKakaoDataListMarker(list)
//        Log.d("현재줌레벨거리", list[0].distance.toInt().toString())
//        Log.d("현재줌레벨", mapView.zoomLevel.toString())
    }

    private fun autoZoomLevel(
        firstDistance: Int
    ) {

//        var level = -1
        for (i in -1..8) {
            if (i < 0) {
                if (((2.0).pow(i) * 100) > firstDistance && firstDistance >= 0) {
//                    level = i
                    mapView.setZoomLevel(i, true)
                    break
                }
            } else if (i == 0) {
                if (((2.0).pow(i) * 100) > firstDistance && firstDistance >= ((2.0).pow(i - 1) * 100)) {
//                    level = i
                    mapView.setZoomLevel(i, true)
                    break
                }
            } else if (i > 0) {
                if (((2.0).pow(i - 1) * 100) <= firstDistance && firstDistance < ((2.0).pow(i) * 100)) {
//                    level = i
                    mapView.setZoomLevel(i, true)
                    break
                }
            }
        }
//        if (level == 0 && firstDistance < 50) {
//            mapView.setZoomLevel(level - 1, true)
//        } else if (level == 0 && firstDistance < 100 && firstDistance >= 50) {
//            mapView.setZoomLevel(level, true)
//        } else if (level == 0 && firstDistance >= 100) {
//            mapView.setZoomLevel(level + 1, true)
//        } else {
//            mapView.setZoomLevel(level + 1, true)
//        }


//        Log.d("현재줌레벨?", level.toString())
//        Log.d("현재줌레벨?1번째 위치", firstDistance.toString())

//        mapView.setZoomLevel(level, true)
    }

    private fun makeKakaoDataListMarker(list: List<KakaoSearchModel>) {

        AppExecutors().diskIO.execute {
            if (kakaoMarkerList.size == 0) {
                list.forEach {
                    val mapPoint =
                        MapPoint.mapPointWithGeoCoord(
                            it.locationY.toDouble(),
                            it.locationX.toDouble()
                        )
                    val mapPOIItem = MapPOIItem()
                    mapPOIItem.apply {
                        this.itemName = it.placeName
                        this.markerType = MapPOIItem.MarkerType.BluePin
                        this.mapPoint = mapPoint
                        this.selectedMarkerType = MapPOIItem.MarkerType.RedPin
                    }
                    kakaoMarkerList.add(mapPOIItem)
                }

                AppExecutors().mainThread.execute {
                    mapView.addPOIItems(kakaoMarkerList.toTypedArray())
                }
            } else {

                val getNotOverlapList = mutableSetOf<MapPOIItem>()
                val _getNotOverlapList = mutableSetOf<MapPOIItem>()

                list.forEach {
                    val mapPoint =
                        MapPoint.mapPointWithGeoCoord(
                            it.locationY.toDouble(),
                            it.locationX.toDouble()
                        )

                    val mapPOIItem = MapPOIItem()
                    mapPOIItem.apply {
                        this.itemName = it.placeName
                        this.markerType = MapPOIItem.MarkerType.BluePin
                        this.mapPoint = mapPoint
                        this.selectedMarkerType = MapPOIItem.MarkerType.RedPin
                    }
                    getNotOverlapList.add(mapPOIItem)
                    _getNotOverlapList.add(mapPOIItem)
                }

                AppExecutors().mainThread.execute {

                    getNotOverlapList.forEach { getNotOverlapMarker ->
                        kakaoMarkerList.forEach { kakaoMarker ->
                            if (getNotOverlapMarker.itemName == kakaoMarker.itemName) {
                                _getNotOverlapList.remove(getNotOverlapMarker)
                            }
                        }
                    }

                    kakaoMarkerList.addAll(_getNotOverlapList)
                    mapView.addPOIItems(_getNotOverlapList.toTypedArray())
                }
            }
        }
    }

    private fun showCurrentOrSelectMarker(mapPOIItem: MapPOIItem, mapPoint: MapPoint) {
        if (mapPOIItem == currentPOIItem) {
//            mapPOIItem.itemName = "내위치"
        } else {
            mapPOIItem.itemName = selectAll
        }
        mapPOIItem.apply {
            this.markerType = MapPOIItem.MarkerType.YellowPin
            this.mapPoint = mapPoint
            this.selectedMarkerType = MapPOIItem.MarkerType.RedPin
        }
        oldCenterPoint = mapPoint
        mapView.addPOIItem(mapPOIItem)
        mapView.selectPOIItem(mapPOIItem, true)
        mapView.setMapCenterPoint(mapPoint, true)
    }


    private fun showCurrentLocation() {
        if (::mapView.isInitialized) {
            toggleCurrentLocation = true
            mapView.removePOIItem(currentPOIItem)

            val currentPosition = MapPoint.mapPointWithGeoCoord(
                App.prefs.current_location_lat.toDouble(),
                App.prefs.current_location_long.toDouble()
            )
            AppExecutors().diskIO.execute {
                presenter.getKakaoData(
                    App.prefs.current_location_long.toDouble(),
                    App.prefs.current_location_lat.toDouble()
                )
                showCurrentOrSelectMarker(currentPOIItem, currentPosition)
            }
        }
    }

    private fun showSelectLocation(longitude: Double, latitude: Double) {
        if (::mapView.isInitialized) {
            mapView.removePOIItem(selectPOIItem)
            val currentPosition = MapPoint.mapPointWithGeoCoord(latitude, longitude)
            showCurrentOrSelectMarker(selectPOIItem, currentPosition)
        }
    }

    //주소 변경한 위치 좌표얻어오는것
    private fun getLocation(location: String) {
        if (::mapView.isInitialized) {
            mapView.removePOIItem(selectPOIItem)
            AppExecutors().diskIO.execute {
                val geoCoder = Geocoder(context, Locale.getDefault())
                val addresses = geoCoder.getFromLocationName(location, 1)
                if (addresses.isNotEmpty()) {
                    presenter.getKakaoData(addresses[0].longitude, addresses[0].latitude)
                    showSelectLocation(addresses[0].longitude, addresses[0].latitude)
                }
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

                if (!map_view.contains(mapView)) {
                    kakaoMarkerList.clear()
                    checkPermission()
                } else {
                    if (::mapInterface.isInitialized) {
                        mapInterface.click(false)
                    }

                    if (toggleSelectLocation) {
                        mapView.removePOIItem(selectPOIItem)
                        getLocation(selectAll)
//                        toggleSelectLocation = false
                    }
                    //다시 page로 돌오왔을시 현재 위치로 돌아오게 하고싶으면 풀어서 사용.
//                    else {
//                        showCurrentLocation()
//                    }
                }
            }
        }
    }

    private fun zoomLevelToRadius(zoomLevel: Int): Int {
        return if (zoomLevel == -2) {
            ((2.0).pow(-1) * 100).toInt()
        } else {
            ((2.0).pow(zoomLevel) * 100).toInt()
        }
    }

    override fun showSearchData(list: List<KakaoSearchModel>, sort: Int) {

        when (sort) {

            0 -> {
                if (::searchInterface.isInitialized) {
                    searchInterface.finishOrNoResult(0)
                }
                Toast.makeText(this.context, "새롭게 검색된 결과가 없습니다.", Toast.LENGTH_SHORT).show()
            }


            1 -> {
                if (::searchInterface.isInitialized) {
                    searchInterface.finishOrNoResult(1)
                    makeKakaoDataListMarker(list)
                }

                Toast.makeText(this.context, "마지막 결과입니다.", Toast.LENGTH_SHORT).show()
//                Toast.makeText(this.context, "결과가 없습니다.", Toast.LENGTH_SHORT).show()
            }

            2 -> {
                if (::searchInterface.isInitialized) {
                    searchInterface.finishOrNoResult(2)
                    makeKakaoDataListMarker(list)
                }

                Toast.makeText(this.context, "결과가 더 남아있습니다.", Toast.LENGTH_SHORT).show()

//                Toast.makeText(this.context, "결과가 없습니다.", Toast.LENGTH_SHORT).show()
            }

        }

    }


    fun t() {

        oldCenterPoint = mapView.mapCenterPoint

        if (!toggleSearchLocation) {
            mapView.removePOIItems(kakaoMarkerList.toTypedArray())
            kakaoMarkerList.clear()
            presenter.resetData()
            toggleSearchLocation = true
        }

        presenter.getThisPositionData(
            mapView.mapCenterPoint.mapPointGeoCoord.longitude,
            mapView.mapCenterPoint.mapPointGeoCoord.latitude,
            zoomLevelToRadius(mapView.zoomLevel),
            kakaoMarkerList.size
        )

    }

    companion object {

        fun newInstance(selectAddress: String) =
            MapFragment().apply {
                arguments = Bundle().apply {
                    putString(ADDRESS, selectAddress)
                }
            }


        var toggleSearchLocation = false
        var toggleLastPage = false

        var receiveAddress = ""
        var toggleSelectLocation = false
        var toggleCurrentLocation = false
        private const val GPS_ENABLE_REQUEST_CODE = 1
    }
}