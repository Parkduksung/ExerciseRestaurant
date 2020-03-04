package com.work.restaurant.view.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric


abstract class BaseActivity(@LayoutRes val layoutId: Int) : AppCompatActivity() {


    override fun onBackPressed() {

        var handled = false

        for (fragment in supportFragmentManager.fragments) {
            if (fragment is BaseFragment) {
                handled = fragment.onBackPressed()
                if (handled) {
                    supportFragmentManager.popBackStack()
                    break
                }
            }
        }

        if (!handled) {


            super.onBackPressed()


        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)

        Fabric.with(this, Crashlytics())
    }
}
