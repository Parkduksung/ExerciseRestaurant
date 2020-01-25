package com.work.restaurant.view.loading

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.util.Base64
import android.util.Log
import com.work.restaurant.R
import com.work.restaurant.view.ExerciseRestaurantActivity
import kotlinx.android.synthetic.main.loading_fragment.*
import java.security.MessageDigest


class LoadingActivity : Activity(), LoadingContract.View {

    private lateinit var presenter: LoadingContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loading_fragment)

        presenter = LoadingPresenter(this)
        start()


        getHashKey()

//        start()


    }


    private fun start() {
        presenter.randomText(resources.getStringArray(R.array.load_string))
        presenter.delayTime()
        presenter.getAddressDataCount()

    }


    override fun showStartText(text: String) {
        loading_tv.text = text
    }

    override fun showDelay() {

        Handler().postDelayed({

            val nextIntent = Intent(this, ExerciseRestaurantActivity::class.java)

            startActivity(nextIntent)

            this@LoadingActivity.finish()

        }, 3000L)
    }

    private fun getHashKey(){

        val info = packageManager.getPackageInfo("com.work.restaurant", PackageManager.GET_SIGNATURES);

        for (signature in info.signatures) {

            val md = MessageDigest.getInstance("SHA")
            md.update(signature.toByteArray())

            Log.d("KeyHash :", Base64.encodeToString(md.digest(), Base64.DEFAULT))

        }

    }



    companion object {
        private const val TAG = "LoadingActivity"
    }

}