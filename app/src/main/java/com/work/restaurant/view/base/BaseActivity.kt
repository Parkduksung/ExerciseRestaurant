package com.work.restaurant.view.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.work.restaurant.R
import com.work.restaurant.ext.showToast


abstract class BaseActivity(@LayoutRes val layoutId: Int) :
    AppCompatActivity() {

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
                    showToast(getString(R.string.baseActivity_backPressed))
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

    }

    companion object {

        private const val INIT_TIME = 0L
        private const val LIMIT_TIME = 2000


    }

}
