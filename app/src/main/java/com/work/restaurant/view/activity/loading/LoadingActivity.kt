package com.work.restaurant.view.activity.loading

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.work.restaurant.R
import com.work.restaurant.view.activity.ExerciseRestaurantActivity
import kotlinx.android.synthetic.main.loading_fragment.*

class LoadingActivity : Activity(), LoadingContract.View {

    private lateinit var presenter: LoadingContract.Presenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loading_fragment)

        presenter = LoadingPresenter(this)

        start()

    }


    private fun start() {
        presenter.randomText(resources.getStringArray(R.array.load_string))
        presenter.delayTime()

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


    companion object {
        private const val TAG = "LoadingActivity"
    }

}