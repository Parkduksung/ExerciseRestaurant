package com.work.restaurant.view.loading

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import android.widget.Toast
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.work.restaurant.Injection
import com.work.restaurant.R
import com.work.restaurant.util.App
import com.work.restaurant.view.ExerciseRestaurantActivity
import com.work.restaurant.view.base.BaseActivity
import kotlinx.android.synthetic.main.loading_fragment.*
import java.util.*


class LoadingActivity : BaseActivity(R.layout.loading_fragment), LoadingContract.View {


    private lateinit var presenter: LoadingContract.Presenter

    private val locationManager: LocationManager by lazy { getSystemService(Context.LOCATION_SERVICE) as LocationManager }

    private val permissionListener: PermissionListener by lazy {
        object : PermissionListener {

            override fun onPermissionGranted() {

                if (checkLocationServicesStatus()) {
                    saveCurrentLocation()
                } else {
                    AlertDialog.Builder(this@LoadingActivity)
                        .setTitle("GPS 오류")
                        .setMessage("GPS 상태를 활성상태로 변경후 다시 시작해 주세요.")
                        .setPositiveButton(
                            "변경하기"
                        ) { _, _ ->
                            startActivity(
                                Intent(ACTION_LOCATION_SOURCE_SETTINGS)
                            )
                            finish()
                        }
                        .setNegativeButton(
                            "취소"
                        ) { _, _ ->
                            finish()
                        }
                        .show()
                }

            }

            override fun onPermissionDenied(deniedPermissions: ArrayList<String>?) {
                Toast.makeText(
                    App.instance.context(),
                    "권한이 거부되었습니다.\n\n$deniedPermissions",
                    Toast.LENGTH_SHORT
                )
                    .show()
                finish()
            }
        }
    }

    //Gps잡히는지 확인하는것. 등 GPS관련
    private fun checkLocationServicesStatus(): Boolean {
        val locationManager =
            this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    override fun showLoginState(result: Boolean, userId: String, userNickname: String) {
        if (result) {
            App.prefs.login_state = true
            App.prefs.login_state_id = userId
            App.prefs.login_state_nickname = userNickname
        } else {
            App.prefs.login_state = false
            App.prefs.login_state_id = userId
            App.prefs.login_state_nickname = userNickname
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter =
            LoadingPresenter(
                this,
                Injection.provideLoginRepository()
            )
        presenter.randomText(resources.getStringArray(R.array.load_string))
        checkPermission()

    }

    @SuppressLint("MissingPermission")
    private fun saveCurrentLocation() {

        val locationListener = object : LocationListener {

            override fun onLocationChanged(location: Location) {

                if (location.longitude.isFinite() && location.latitude.isFinite()) {
                    App.prefs.current_location_long = location.longitude.toString()
                    App.prefs.current_location_lat = location.latitude.toString()
                    locationManager.removeUpdates(this)
                    start()
                }
            }

            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
            }

            override fun onProviderEnabled(provider: String) {
            }

            override fun onProviderDisabled(provider: String) {
            }
        }

        locationManager.requestLocationUpdates(
            LocationManager.NETWORK_PROVIDER,
            0,
            0f,
            locationListener
        )
    }

    private fun checkPermission() {
        TedPermission.with(App.instance.context())
            .setPermissionListener(permissionListener)
            .setRationaleMessage(getString(R.string.tedPermission_setRationaleMessage))
            .setDeniedMessage(getString(R.string.tedPermission_setDeniedMessage))
            .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
//            .setPermissions(android.Manifest.permission.INTERNET,android.Manifest.permission.CALL_PHONE)
            .check()
    }

    private fun start() {

        presenter.run {
            getLoginState()
            delayTime()
            getAddressDataCount()
        }

    }


    override fun showStartText(text: String) {
        loading_tv.text = text
    }

    override fun showDelay() {

        Handler().postDelayed({

            val nextIntent =
                Intent(this, ExerciseRestaurantActivity::class.java)

            startActivity(nextIntent)

            this@LoadingActivity.finish()

        }, 100L)
    }

    companion object {
        private const val TAG = "LoadingActivity"

    }

}