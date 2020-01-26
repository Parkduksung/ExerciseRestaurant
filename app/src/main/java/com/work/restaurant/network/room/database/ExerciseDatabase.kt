package com.work.restaurant.network.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.work.restaurant.network.room.dao.ExerciseDao
import com.work.restaurant.network.room.entity.ExerciseEntity

@Database(entities = [ExerciseEntity::class], version = 3)
abstract class ExerciseDatabase : RoomDatabase() {

    abstract fun exerciseDao(): ExerciseDao

    companion object {
        private var INSTANCE: ExerciseDatabase? = null

        fun getInstance(context: Context): ExerciseDatabase =
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                ExerciseDatabase::class.java,
                "eat"
            )
                .fallbackToDestructiveMigration()
                .build()
                .also {
                    INSTANCE = it
                }
    }
}

