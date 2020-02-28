package com.work.restaurant.view.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.work.restaurant.R
import com.work.restaurant.util.App
import com.work.restaurant.view.mypage.register_ok.MyPageRegisterOkFragment


abstract class BaseFragment(@LayoutRes val layoutId: Int) : Fragment(), OnBackPressedListener {

    override fun onBackPressed(): Boolean {

        requireFragmentManager().fragments.forEach {
            if (it is MyPageRegisterOkFragment) {
                fragmentManager?.popBackStack()
            }
        }
        return fragmentManager?.backStackEntryCount != 0

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(layoutId, container, false)
        view.setBackgroundColor(ContextCompat.getColor(App.instance.context(), R.color.colorWhite))
        view.setOnTouchListener { _, _ ->
            true
        }
        return view
    }

}


