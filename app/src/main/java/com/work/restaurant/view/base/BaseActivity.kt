package com.work.restaurant.view.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.crashlytics.android.Crashlytics
import com.work.restaurant.R
import com.work.restaurant.util.App
import io.fabric.sdk.android.Fabric


abstract class BaseActivity(@LayoutRes val layoutId: Int) : AppCompatActivity() {

    var mBackWait: Long = INIT_TIME

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
            if (layoutId == R.layout.activity_main) {
                if (System.currentTimeMillis() - mBackWait >= LIMIT_TIME) {
                    mBackWait = System.currentTimeMillis()
                    Toast.makeText(
                        App.instance.context(),
                        getString(R.string.baseActivity_backPressed),
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    finish()
                }
            } else {
                super.onBackPressed()
            }

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)

        Fabric.with(this, Crashlytics())
    }

    companion object {

        private const val INIT_TIME = 0L
        private const val LIMIT_TIME = 2000


    }

}
