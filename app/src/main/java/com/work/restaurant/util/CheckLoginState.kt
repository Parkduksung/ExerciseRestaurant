package com.work.restaurant.util

import android.text.TextUtils
import android.util.Patterns

object RelateLogin {

    private val preference = App.prefs

    fun loginState(): Boolean =
        preference.login_state && preference.login_state_id.isNotEmpty()

    fun getLoginId(): String =
        preference.login_state_id


    fun isValidEmail(charSequence: CharSequence): Boolean =
        !TextUtils.isEmpty(charSequence) && Patterns.EMAIL_ADDRESS.matcher(charSequence).matches()

}