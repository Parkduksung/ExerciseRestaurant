package com.work.restaurant.view.loading

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.work.restaurant.R
import com.work.restaurant.databinding.LoadingFragmentBinding
import com.work.restaurant.ext.isConnectedToGPS
import com.work.restaurant.ext.showToast
import com.work.restaurant.util.App
import com.work.restaurant.util.ShowAlertDialog
import com.work.restaurant.view.ExerciseRestaurantActivity
import com.work.restaurant.base.BaseActivity
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf
import java.util.*


class LoadingActivity : BaseActivity<LoadingFragmentBinding>(
    LoadingFragmentBinding::inflate,
    R.layout.loading_fragment
), LoadingContract.View {


    private lateinit var presenter: LoadingContract.Presenter

    private val locationManager: LocationManager by lazy { getSystemService(Context.LOCATION_SERVICE) as LocationManager }

    private val permissionListener: PermissionListener by lazy {
        object : PermissionListener {

            override fun onPermissionGranted() {

                if (isConnectedToGPS()) {
                    saveCurrentLocation()
                } else {
                    ShowAlertDialog(this@LoadingActivity).apply {
                        titleAndMessage(
                            getString(R.string.loading_gps_error_title),
                            getString(R.string.loading_gps_error_message)
                        )
                        alertDialog.setPositiveButton(
                            getString(R.string.common_change)
                        ) { _, _ ->
                            startActivity(
                                Intent(ACTION_LOCATION_SOURCE_SETTINGS)
                            )
                            finish()
                        }
                        alertDialog.setNegativeButton(
                            getString(R.string.common_no)
                        ) { _, _ ->
                            finish()
                        }
                        showDialog()
                    }
                }
            }

            override fun onPermissionDenied(deniedPermissions: ArrayList<String>?) {
                showToast(getString(R.string.common_no_denied))
                finish()
            }
        }
    }

    override fun showLoginState(result: Boolean, userId: String, userNickname: String) {
        App.prefs.apply {
            loginState = result
            loginStateId = userId
            loginStateNickname = userNickname
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = get { parametersOf(this) }
        presenter.randomText(resources.getStringArray(R.array.load_string))
        checkPermission()

    }

    @SuppressLint("MissingPermission")
    private fun saveCurrentLocation() {

        val locationListener = object : LocationListener {

            override fun onLocationChanged(location: Location) {

                if (location.longitude.isFinite() && location.latitude.isFinite()) {
                    App.prefs.currentLocationLong = location.longitude.toString()
                    App.prefs.currentLocationLat = location.latitude.toString()
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
        binding.loadingTv.text = text
    }

    override fun showDelay() {

        Handler().postDelayed({

            val nextIntent =
                Intent(this, ExerciseRestaurantActivity::class.java)

            startActivity(nextIntent)

            this@LoadingActivity.finish()

        }, DELAY_TIME)
    }

    companion object {
        private const val TAG = "LoadingActivity"

        private const val DELAY_TIME = 100L
    }

}