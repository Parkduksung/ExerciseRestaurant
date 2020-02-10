package com.work.restaurant.util

import android.app.Application
import android.content.Context

class App : Application() {
    companion object {
        lateinit var instance: App
            private set
        lateinit var prefs: MySharedPreferences
    }

    override fun onCreate() {
        prefs = MySharedPreferences(applicationContext)
        super.onCreate()
        instance = this

    }

    fun context(): Context = applicationContext

}
