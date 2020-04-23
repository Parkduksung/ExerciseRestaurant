package com.work.restaurant.util

import android.app.AlertDialog
import android.content.Context
import android.view.ContextThemeWrapper
import com.work.restaurant.R

class ShowAlertDialog(context: Context) {

    val alertDialog =
        AlertDialog.Builder(
            ContextThemeWrapper(
                context,
                R.style.Theme_AppCompat_Light_Dialog
            )
        )

    fun titleAndMessage(title: String, message: String) {
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
    }

    fun cancelable(state: Boolean) {
        alertDialog.setCancelable(state)
    }

    fun showDialog() {
        alertDialog.show()
    }

}