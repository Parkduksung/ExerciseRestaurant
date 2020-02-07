package com.work.restaurant

import com.work.restaurant.data.repository.bookmark.BookmarkRepository
import com.work.restaurant.data.repository.bookmark.BookmarkRepositoryImpl
import com.work.restaurant.data.repository.eat.EatRepository
import com.work.restaurant.data.repository.eat.EatRepositoryImpl
import com.work.restaurant.data.repository.exercise.ExerciseRepository
import com.work.restaurant.data.repository.exercise.ExerciseRepositoryImpl
import com.work.restaurant.data.repository.kakao.KakaoRepository
import com.work.restaurant.data.repository.kakao.KakaoRepositoryImpl
import com.work.restaurant.data.source.local.bookmark.BookmarkLocalDataSourceImpl
import com.work.restaurant.data.source.local.eat.EatLocalDataSourceImpl
import com.work.restaurant.data.source.local.exercise.ExerciseLocalDataSourceImpl
import com.work.restaurant.data.source.remote.kakao.KakaoRemoteDataSourceImpl
import com.work.restaurant.network.RetrofitInstance
import com.work.restaurant.network.room.database.BookmarkDatabase
import com.work.restaurant.network.room.database.EatDatabase
import com.work.restaurant.network.room.database.ExerciseDatabase
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

    fun provideBookmarkRepository(): BookmarkRepository =
        BookmarkRepositoryImpl.getInstance(
            BookmarkLocalDataSourceImpl(
                AppExecutors(), BookmarkDatabase.getInstance(
                    App.instance.context()
                )
            )
        )


    fun provideExerciseRepository(): ExerciseRepository =
        ExerciseRepositoryImpl.getInstance(
            ExerciseLocalDataSourceImpl(
                AppExecutors(),
                ExerciseDatabase.getInstance(
                    App.instance.context()
                )
            )

        )

    fun provideKakaoRepository(): KakaoRepository =
        KakaoRepositoryImpl.getInstance(
            KakaoRemoteDataSourceImpl.getInstance(
                RetrofitInstance.getInstance(
                    KAKAO_URL
                )
            )
        )


    const val KAKAO_URL = "https://dapi.kakao.com/"
}