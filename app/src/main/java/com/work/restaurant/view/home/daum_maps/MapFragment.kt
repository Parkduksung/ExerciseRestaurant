package com.work.restaurant.view.home.daum_maps

import android.Manifest
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.core.view.contains
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.work.restaurant.Injection
import com.work.restaurant.R
import com.work.restaurant.data.model.DisplayBookmarkKakaoModel
import com.work.restaurant.data.model.KakaoSearchModel
import com.work.restaurant.ext.isConnectedToGPS
import com.work.restaurant.ext.showToast
import com.work.restaurant.util.App
import com.work.restaurant.util.AppExecutors
import com.work.restaurant.util.ShowAlertDialog
import com.work.restaurant.view.ExerciseRestaurantActivity.Companion.selectAll
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.home.MapInterface
import com.work.restaurant.view.home.address.HomeAddressActivity
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
    MapContract.View {


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
                if (isConnectedToGPS()) {
                    loadMap()
                } else {
                    showDialogForLocationServiceSetting()
                }
            }

            override fun onPermissionDenied(deniedPermissions: ArrayList<String>?) {
                showToast(getString(R.string.common_no_denied))
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

        presenter =
            MapPresenter(
                this,
                Injection.provideKakaoRepository(),
                Injection.provideBookmarkRepository()
            )
    }

    private fun loadMap() {
        showToast(getString(R.string.map_gps_and_authorization_on))
        mapView = MapView(context)
        mapView.setMapViewEventListener(this)
        mapView.setPOIItemEventListener(this)

        map_view.addView(mapView)

        showCurrentLocation()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            GPS_ENABLE_REQUEST_CODE ->
                if (isConnectedToGPS()) {
                    mapView =
                        MapView(requireContext())
                    map_view.addView(mapView)
                    showToast(getString(R.string.map_gps_on))
                    return
                }
        }
    }

    override fun showMarkerData(list: List<DisplayBookmarkKakaoModel>) {
        if (list.isNotEmpty()) {
            if (::markerInterface.isInitialized) {
                markerInterface.getMarkerData(list[0])
                mapInterface.clickMap(true)
            } else {
                markerInterface = object : MapInterface.SelectMarkerListener {
                    override fun getMarkerData(data: DisplayBookmarkKakaoModel) {
                    }
                }
                markerInterface.getMarkerData(list[0])
                mapInterface.clickMap(true)
            }
        }
    }

    override fun onMapViewDoubleTapped(p0: MapView?, p1: MapPoint?) {

    }

    override fun onMapViewInitialized(p0: MapView?) {

    }

    override fun onMapViewDragStarted(p0: MapView?, p1: MapPoint?) {
        p0?.let {
            if (::mapInterface.isInitialized) {
                mapInterface.clickMap(false)
            } else {
                mapInterface = object : MapInterface.CurrentLocationClickListener {
                    override fun clickMap(clickData: Boolean) {
                    }
                }
                mapInterface.clickMap(false)
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

        if (p0 != null && p1 != null) {
            if (getDistance(oldCenterPoint, p1) >= zoomLevelToRadius(p0.zoomLevel)) {
                if (::searchInterface.isInitialized) {
                    oldCenterPoint = p0.mapCenterPoint
                    searchInterface.findFitnessResult(RENEW_SEARCHABLE_STATE)
                    toggleSearchLocation = false
                }

            }
        }


    }

    override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {

    }

    override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint?) {

    }

    override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {

        if (::searchInterface.isInitialized) {
            p0?.let {
                oldCenterPoint = it.mapCenterPoint
                searchInterface.findFitnessResult(RENEW_SEARCHABLE_STATE)
                toggleSearchLocation = false
            }
        }

    }

    override fun onMapViewLongPressed(p0: MapView?, p1: MapPoint?) {

    }


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
                    override fun clickMap(clickData: Boolean) {
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

    override fun showKakaoData(
        list: List<KakaoSearchModel>
    ) {

        if (toggleSelectLocation) {
            autoZoomLevel(list[list.size - 1].distance.toInt())
            toggleSelectLocation = false
        }

        if (toggleCurrentLocation) {
            autoZoomLevel(list[list.size - 1].distance.toInt())
            toggleCurrentLocation = false
        }

        makeKakaoDataListMarker(list)

    }

    private fun autoZoomLevel(
        firstDistance: Int
    ) {

        for (i in -1..8) {
            if (i < 0) {
                if (((2.0).pow(i) * 100) > firstDistance && firstDistance >= 0) {

                    mapView.setZoomLevel(i, true)
                    break
                }
            } else if (i == 0) {
                if (((2.0).pow(i) * 100) > firstDistance && firstDistance >= ((2.0).pow(i - 1) * 100)) {

                    mapView.setZoomLevel(i, true)
                    break
                }
            } else if (i > 0) {
                if (((2.0).pow(i - 1) * 100) <= firstDistance && firstDistance < ((2.0).pow(i) * 100)) {

                    mapView.setZoomLevel(i, true)
                    break
                }
            }
        }

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
                        itemName = it.placeName
                        this.mapPoint = mapPoint
                        markerType = MapPOIItem.MarkerType.CustomImage
                        customImageResourceId = R.drawable.asdf4_4
                        selectedMarkerType = MapPOIItem.MarkerType.CustomImage
                        customSelectedImageResourceId = R.drawable.asdf7
                        isShowDisclosureButtonOnCalloutBalloon = false
                        isShowCalloutBalloonOnTouch = false
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
                        itemName = it.placeName
                        markerType = MapPOIItem.MarkerType.CustomImage
                        customImageResourceId = R.drawable.asdf4_4
                        selectedMarkerType = MapPOIItem.MarkerType.CustomImage
                        customSelectedImageResourceId = R.drawable.asdf7
                        this.mapPoint = mapPoint
                        isShowDisclosureButtonOnCalloutBalloon = false
                        isShowCalloutBalloonOnTouch = false
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

        mapPOIItem.apply {
            itemName = ""
            markerType = MapPOIItem.MarkerType.CustomImage
            customImageResourceId = R.drawable.asdf8
            this.mapPoint = mapPoint
            showAnimationType = MapPOIItem.ShowAnimationType.DropFromHeaven
            isShowDisclosureButtonOnCalloutBalloon = false
            isShowCalloutBalloonOnTouch = false
        }
        oldCenterPoint = mapPoint
        mapView.addPOIItem(mapPOIItem)

        mapView.setMapCenterPoint(mapPoint, true)
    }

    fun showCurrentLocation() {
        if (::mapView.isInitialized) {

            val currentPosition =
                MapPoint.mapPointWithGeoCoord(
                    App.prefs.current_location_lat.toDouble(),
                    App.prefs.current_location_long.toDouble()
                )

            if (::oldCenterPoint.isInitialized) {
                if (currentPosition.mapPointGeoCoord.latitude != oldCenterPoint.mapPointGeoCoord.latitude &&
                    currentPosition.mapPointGeoCoord.longitude != oldCenterPoint.mapPointGeoCoord.longitude
                ) {
                    mapView.removePOIItems(kakaoMarkerList.toTypedArray())
                    kakaoMarkerList.clear()
                }
            }

            toggleCurrentLocation = true
            mapView.removePOIItem(selectPOIItem)
            mapView.removePOIItem(currentPOIItem)

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

            val currentPosition =
                MapPoint.mapPointWithGeoCoord(latitude, longitude)
            showCurrentOrSelectMarker(selectPOIItem, currentPosition)
        }
    }

    private fun getLocation(location: String) {
        if (::mapView.isInitialized) {

            mapView.apply {
                removePOIItem(selectPOIItem)
                removePOIItem(currentPOIItem)
                removePOIItems(kakaoMarkerList.toTypedArray())
            }

            kakaoMarkerList.clear()

            AppExecutors().diskIO.execute {
                val geoCoder =
                    Geocoder(context, Locale.getDefault())
                val addresses =
                    geoCoder.getFromLocationName(location, 1)
                if (addresses.isNotEmpty()) {
                    presenter.getKakaoData(addresses[0].longitude, addresses[0].latitude)
                    showSelectLocation(addresses[0].longitude, addresses[0].latitude)
                }
            }
        }
    }


    private fun checkPermission() {
        TedPermission.with(context)
            .setPermissionListener(permissionListener)
            .setRationaleMessage(getString(R.string.tedPermission_setRationaleMessage))
            .setDeniedMessage(getString(R.string.tedPermission_setDeniedMessage))
            .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
            .check()
    }

    private fun showDialogForLocationServiceSetting() {
        ShowAlertDialog(context).apply {
            titleAndMessage(
                getString(R.string.map_no_location_denied),
                getString(R.string.map_update_location_denied)
            )
            alertDialog.setPositiveButton(
                getString(R.string.common_settings)
            ) { _, _ ->
                val callGPSSettingIntent =
                    Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE)
            }
            alertDialog.setNegativeButton(
                getString(R.string.common_no)
            ) { dialog, _ ->
                dialog.cancel()
            }
            showDialog()
        }
    }

    override fun onResume() {
        super.onResume()

        if (::mapView.isInitialized) {
            if (isConnectedToGPS()) {
                map_view.removeView(mapView)
                showToast(getString(R.string.map_gps_off))

            } else {
                if (!map_view.contains(mapView)) {
                    kakaoMarkerList.clear()
                    checkPermission()
                } else {
                    if (::mapInterface.isInitialized) {
                        mapInterface.clickMap(false)
                    }

                    if (toggleSelectLocation) {
                        mapView.removePOIItem(selectPOIItem)
                        getLocation(selectAll)
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
            MapPresenter.NO_NEW_RESULT -> {
                if (::searchInterface.isInitialized) {
                    searchInterface.findFitnessResult(NO_NEW_RESULT)
                }
            }
            MapPresenter.REMAIN_RESULT -> {
                if (::searchInterface.isInitialized) {
                    searchInterface.findFitnessResult(REMAIN_RESULT)
                    makeKakaoDataListMarker(list)
                }
            }
            MapPresenter.FINAL_RESULT -> {
                if (::searchInterface.isInitialized) {
                    searchInterface.findFitnessResult(FINAL_RESULT)
                    makeKakaoDataListMarker(list)
                }
            }
        }

    }

    fun searchByZoomLevel() {

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
                    putString(HomeAddressActivity.ADDRESS, selectAddress)
                }
            }

        private const val GPS_ENABLE_REQUEST_CODE = 1

        var toggleSearchLocation = false
        var toggleCurrentLocation = false
        var toggleSelectLocation = false

        const val NO_NEW_RESULT = 0
        const val REMAIN_RESULT = 1
        const val FINAL_RESULT = 2
        const val RENEW_SEARCHABLE_STATE = 3

    }

}
