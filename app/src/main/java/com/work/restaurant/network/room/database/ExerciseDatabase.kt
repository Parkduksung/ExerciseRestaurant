package com.work.restaurant.network.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.work.restaurant.network.room.converter.ExerciseSetConverter
import com.work.restaurant.network.room.dao.ExerciseDao
import com.work.restaurant.network.room.entity.ExerciseEntity

@Database(entities = [ExerciseEntity::class], version = 5)
@TypeConverters(ExerciseSetConverter::class)
abstract class ExerciseDatabase : RoomDatabase() {

    abstract fun exerciseDao(): ExerciseDao

    companion object {
        private var INSTANCE: ExerciseDatabase? = null

        fun getInstance(context: Context): ExerciseDatabase =
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                ExerciseDatabase::class.java,
                "exercise"
            )
                .fallbackToDestructiveMigration()
                .build()
                .also {
                    INSTANCE = it
                }
    }
}

