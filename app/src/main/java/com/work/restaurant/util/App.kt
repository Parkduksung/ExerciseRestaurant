package com.work.restaurant.util

import android.app.Application
import android.content.Context
import com.crashlytics.android.Crashlytics
import com.work.restaurant.data.repository.di.eatRepositoryModule
import com.work.restaurant.data.source.local.di.eatSourceModule
import com.work.restaurant.network.di.eatNetworkModule
import com.work.restaurant.view.presenterModule
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

        startKoin()

    }

    private fun startKoin() {
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    eatSourceModule,
                    eatRepositoryModule,
                    eatNetworkModule,
                    appExecutorsModule,
                    presenterModule
                )
            )
        }
    }

    fun context(): Context = applicationContext

}
