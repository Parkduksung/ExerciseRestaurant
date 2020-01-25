package com.work.restaurant

import com.work.restaurant.data.repository.eat.EatRepository
import com.work.restaurant.data.repository.eat.EatRepositoryImpl
import com.work.restaurant.data.source.local.eat.EatLocalDataSourceImpl
import com.work.restaurant.network.room.database.EatDatabase
import com.work.restaurant.util.App
import com.work.restaurant.util.AppExecutors


object Injection {
    fun provideEatRepository(): EatRepository =
        EatRepositoryImpl.getInstance(
            EatLocalDataSourceImpl(
                AppExecutors(), EatDatabase.getInstance(
                    App.instance.context()
                )
            )
        )


}