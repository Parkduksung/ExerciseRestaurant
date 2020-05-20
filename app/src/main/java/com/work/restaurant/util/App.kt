package com.work.restaurant.util

import android.app.Application
import android.content.Context
import com.crashlytics.android.Crashlytics
import com.work.restaurant.data.di.repositoryModule
import com.work.restaurant.data.di.sourceModule
import com.work.restaurant.di.presenterModule
import com.work.restaurant.network.di.networkModule
import io.fabric.sdk.android.Fabric
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

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

        startKOIN()

    }

    private fun startKOIN() {
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    sourceModule,
                    repositoryModule,
                    networkModule,
                    appExecutorsModule,
                    presenterModule
                )
            )
        }
    }

    fun context(): Context = applicationContext

}
