package com.work.restaurant.util

import android.content.Context
import android.content.SharedPreferences

class MySharedPreferences(context: Context) {

    val PREFS_FILENAME = "prefs"
    val PREF_KEY_MY_EDITTEXT = "myEditText"
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)
    /* 파일 이름과 EditText를 저장할 Key 값을 만들고 prefs 인스턴스 초기화 */

    var myEditText: String
        get() = prefs.getString(PREF_KEY_MY_EDITTEXT, "")!!
        set(value) = prefs.edit().putString(PREF_KEY_MY_EDITTEXT, value).apply()
    /* get/set 함수 임의 설정. get 실행 시 저장된 값을 반환하며 default 값은 ""
     * set(value) 실행 시 value로 값을 대체한 후 저장 */


    val PREFS_CURRENT_DATE = "prefs_current_date"
    val PREF_KEY_CURRENT_DATE = "current_date"
    val prefs_current_date: SharedPreferences = context.getSharedPreferences(PREFS_CURRENT_DATE, 0)
    /* 파일 이름과 EditText를 저장할 Key 값을 만들고 prefs 인스턴스 초기화 */

    var current_date: String
        get() = prefs.getString(PREF_KEY_CURRENT_DATE, "")!!
        set(value) = prefs.edit().putString(PREF_KEY_CURRENT_DATE, value).apply()


    val PREFS_CURRENT_TIME = "prefs_current_time"
    val PREF_KEY_CURRENT_TIME = "current_time"
    val prefs_current_time: SharedPreferences = context.getSharedPreferences(PREFS_CURRENT_TIME, 0)


    var current_time: String
        get() = prefs.getString(PREF_KEY_CURRENT_TIME, "")!!
        set(value) = prefs.edit().putString(PREF_KEY_CURRENT_TIME, value).apply()

    val PREFS_CURRENT_LOCATION_LONG = "prefs_current_location_long"
    val PREF_KEY_CURRENT_LOCATION_LONG = "current_location_long"
    val prefs_current_location_long: SharedPreferences =
        context.getSharedPreferences(PREFS_CURRENT_LOCATION_LONG, 0)


    var current_location_long: String
        get() = prefs.getString(PREF_KEY_CURRENT_LOCATION_LONG, "")!!
        set(value) = prefs.edit().putString(PREF_KEY_CURRENT_LOCATION_LONG, value).apply()

    val PREFS_CURRENT_LOCATION_LAT = "prefs_current_location_lat"
    val PREF_KEY_CURRENT_LOCATION_LAT = "current_location_lat"
    val prefs_current_location_lat: SharedPreferences =
        context.getSharedPreferences(PREFS_CURRENT_LOCATION_LAT, 0)


    var current_location_lat: String
        get() = prefs.getString(PREF_KEY_CURRENT_LOCATION_LAT, "")!!
        set(value) = prefs.edit().putString(PREF_KEY_CURRENT_LOCATION_LAT, value).apply()

}