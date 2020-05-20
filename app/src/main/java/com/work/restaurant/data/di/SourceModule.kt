package com.work.restaurant.data.di

import com.work.restaurant.data.source.local.bookmark.BookmarkLocalDataSource
import com.work.restaurant.data.source.local.bookmark.BookmarkLocalDataSourceImpl
import com.work.restaurant.data.source.local.eat.EatLocalDataSource
import com.work.restaurant.data.source.local.eat.EatLocalDataSourceImpl
import com.work.restaurant.data.source.local.exercise.ExerciseLocalDataSource
import com.work.restaurant.data.source.local.exercise.ExerciseLocalDataSourceImpl
import com.work.restaurant.data.source.local.login.LoginLocalDataSource
import com.work.restaurant.data.source.local.login.LoginLocalDataSourceImpl
import com.work.restaurant.data.source.local.road.RoadLocalDataSource
import com.work.restaurant.data.source.local.road.RoadLocalDataSourceImpl
import com.work.restaurant.data.source.remote.kakao.KakaoRemoteDataSource
import com.work.restaurant.data.source.remote.kakao.KakaoRemoteDataSourceImpl
import org.koin.dsl.module

val sourceModule =
    module {
        single<EatLocalDataSource> { EatLocalDataSourceImpl(get(), get()) }
        single<ExerciseLocalDataSource> { ExerciseLocalDataSourceImpl(get(), get()) }
        single<BookmarkLocalDataSource> { BookmarkLocalDataSourceImpl(get(), get()) }
        single<LoginLocalDataSource> { LoginLocalDataSourceImpl(get(), get()) }
        single<RoadLocalDataSource> { RoadLocalDataSourceImpl(get(), get()) }
        single<KakaoRemoteDataSource> { KakaoRemoteDataSourceImpl(get()) }
    }