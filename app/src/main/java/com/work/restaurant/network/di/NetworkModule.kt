package com.work.restaurant.network.di

import androidx.room.Room
import com.work.restaurant.network.RetrofitInstance
import com.work.restaurant.network.api.KakaoApi
import com.work.restaurant.network.api.NotificationApi
import com.work.restaurant.network.api.QuestionApi
import com.work.restaurant.network.api.UserApi
import com.work.restaurant.network.room.database.*
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val KAKAO_URL = "https://dapi.kakao.com/"
private const val WEB_URL = "https://duksung12.cafe24.com"

val networkModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            EatDatabase::class.java,
            "eat"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
    single {
        Room.databaseBuilder(
            androidContext(),
            ExerciseDatabase::class.java,
            "exercise"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
    single {
        Room.databaseBuilder(
            androidContext(),
            BookmarkDatabase::class.java,
            "bookmark"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
    single {
        Room.databaseBuilder(
            androidContext(),
            LoginDatabase::class.java,
            "login"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
    single {
        Room.databaseBuilder(
            androidContext(),
            AddressDatabase::class.java,
            "address"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
    single<KakaoApi> {
        RetrofitInstance.getInstance(
            KAKAO_URL
        )
    }
    single<NotificationApi> {
        RetrofitInstance.getInstance(
            WEB_URL
        )
    }
    single<QuestionApi> {
        RetrofitInstance.getInstance(
            WEB_URL
        )
    }
    single<UserApi> {
        RetrofitInstance.getInstance(
            WEB_URL
        )
    }
}