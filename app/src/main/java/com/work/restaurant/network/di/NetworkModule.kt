package com.work.restaurant.network.di

import androidx.room.Room
import com.work.restaurant.network.room.database.EatDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val eatNetworkModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            EatDatabase::class.java,
            "eat"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}