package com.work.restaurant.util

object RelateLogin {

    private val preference = App.prefs

    fun loginState(): Boolean =
        preference.login_state && preference.login_state_id.isNotEmpty()

    fun getLoginId(): String =
        preference.login_state_id

}