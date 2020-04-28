package com.work.restaurant.util

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

object Keyboard {

    fun hideEditText(context: Context?, editText: EditText) {
        val inputMethodManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(editText.windowToken, 0)
    }

}