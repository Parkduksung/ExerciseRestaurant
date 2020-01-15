package com.work.restaurant.view.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment(@LayoutRes val layoutId: Int) : Fragment(), OnBackPressedListener {
    override fun onBackPressed() {
        requireFragmentManager().beginTransaction().remove(this).commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(layoutId, container, false)
        view?.setOnTouchListener { _, _ ->
            true
        }
        return view
    }
    
}


