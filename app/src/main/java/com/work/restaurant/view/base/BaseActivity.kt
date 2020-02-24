package com.work.restaurant.view.base

import android.os.Bundle
import android.util.Log
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity(@LayoutRes val layoutId: Int) : AppCompatActivity() {


    override fun onBackPressed() {

        val fragmentList = supportFragmentManager.fragments

        var handled = false

        for (fragment in fragmentList) {
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
    }
}
