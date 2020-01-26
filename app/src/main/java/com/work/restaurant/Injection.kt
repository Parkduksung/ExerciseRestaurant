package com.work.restaurant

import com.work.restaurant.data.repository.eat.EatRepository
import com.work.restaurant.data.repository.eat.EatRepositoryImpl
import com.work.restaurant.data.repository.exercise.ExerciseRepository
import com.work.restaurant.data.repository.exercise.ExerciseRepositoryImpl
import com.work.restaurant.data.source.local.eat.EatLocalDataSourceImpl
import com.work.restaurant.data.source.local.exercise.ExerciseLocalDataSourceImpl
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


    fun provideExerciseRepository(): ExerciseRepository =
        ExerciseRepositoryImpl.getInstance(
            ExerciseLocalDataSourceImpl(
                AppExecutors(),
                ExerciseDatabase.getInstance(
                    App.instance.context()
                )
            )

        )


}