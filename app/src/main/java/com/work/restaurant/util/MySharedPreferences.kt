package com.work.restaurant.util

import android.content.Context
import android.content.SharedPreferences

class MySharedPreferences(context: Context) {


    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)

    var currentLocationLong: String
        get() = prefs.getString(PREF_KEY_CURRENT_LOCATION_LONG, EMPTY_TEXT)!!
        set(value) = prefs.edit().putString(PREF_KEY_CURRENT_LOCATION_LONG, value).apply()

    var currentLocationLat: String
        get() = prefs.getString(PREF_KEY_CURRENT_LOCATION_LAT, EMPTY_TEXT)!!
        set(value) = prefs.edit().putString(PREF_KEY_CURRENT_LOCATION_LAT, value).apply()

    var loginState: Boolean
        get() = prefs.getBoolean(PREF_KEY_LOGIN_STATE, false)
        set(value) = prefs.edit().putBoolean(PREF_KEY_LOGIN_STATE, value).apply()

    var loginStateId: String
        get() = prefs.getString(PREF_KEY_LOGIN_STATE_ID, EMPTY_TEXT)!!
        set(value) = prefs.edit().putString(PREF_KEY_LOGIN_STATE_ID, value).apply()

    var loginStateNickname: String
        get() = prefs.getString(PREF_KEY_LOGIN_STATE_NICKNAME, EMPTY_TEXT)!!
        set(value) = prefs.edit().putString(PREF_KEY_LOGIN_STATE_NICKNAME, value).apply()

    companion object {

        private const val EMPTY_TEXT = ""
        private const val PREFS_FILENAME = "prefs"
        private const val PREF_KEY_CURRENT_LOCATION_LONG = "current_location_long"
        private const val PREF_KEY_CURRENT_LOCATION_LAT = "current_location_lat"
        private const val PREF_KEY_LOGIN_STATE = "login_state"
        private const val PREF_KEY_LOGIN_STATE_ID = "login_state_id"
        private const val PREF_KEY_LOGIN_STATE_NICKNAME = "login_state_nickname"

    }

}