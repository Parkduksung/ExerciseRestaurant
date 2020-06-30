package com.work.restaurant.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.work.restaurant.R
import com.work.restaurant.util.App
import com.work.restaurant.view.mypage.register_ok.MyPageRegisterOkFragment


abstract class BaseFragment<T : ViewBinding>(
    private val bind: (View) -> T,
    @LayoutRes val layoutId: Int
) : androidx.fragment.app.Fragment(layoutId),
    OnBackPressedListener {

    private var _binding: T? = null

    protected val binding get() = _binding!!

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

        _binding = bind(super.onCreateView(inflater, container, savedInstanceState)!!)

        return binding.root.apply {
            setBackgroundColor(ContextCompat.getColor(App.instance.context(), R.color.colorWhite))
            setOnTouchListener { _, _ ->
                true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


