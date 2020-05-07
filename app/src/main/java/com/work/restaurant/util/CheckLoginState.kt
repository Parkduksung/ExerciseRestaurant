package com.work.restaurant.util

import android.text.TextUtils
import android.util.Patterns

object RelateLogin {

    private val preference = App.prefs

    fun loginState(): Boolean =
        preference.loginState && preference.loginStateId.isNotEmpty()

    fun getLoginId(): String =
        preference.loginStateId

    fun getLoginState(): Boolean =
        preference.loginState

    fun isValidEmail(charSequence: CharSequence): Boolean =
        !TextUtils.isEmpty(charSequence) && Patterns.EMAIL_ADDRESS.matcher(charSequence).matches()

}