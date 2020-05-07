package com.work.restaurant.util

import android.app.Application
import android.content.Context
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric

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
        Fabric.with(this, Crashlytics())
    }

    fun context(): Context = applicationContext

}
