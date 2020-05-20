package com.work.restaurant.view.base

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment

abstract class BaseDialogFragment(@LayoutRes val layoutId: Int) : DialogFragment() {

    override fun onResume() {
        val windowManager =
            (activity?.getSystemService(Context.WINDOW_SERVICE)) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        dialog?.window?.setLayout(size.x, size.y)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        super.onResume()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(layoutId, container, false)
        view.setOnTouchListener { _, _ ->
            true
        }
        return view

    }

}


