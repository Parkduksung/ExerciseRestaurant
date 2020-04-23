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
        super.onCreate()
        instance = this
        prefs = MySharedPreferences(applicationContext)

    }

    fun context(): Context = applicationContext

}
