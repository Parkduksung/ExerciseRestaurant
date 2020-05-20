package com.work.restaurant.network.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.work.restaurant.network.room.converter.ExerciseSetConverter
import com.work.restaurant.network.room.dao.ExerciseDao
import com.work.restaurant.network.room.entity.ExerciseEntity

@Database(entities = [ExerciseEntity::class], version = 6)
@TypeConverters(ExerciseSetConverter::class)
abstract class ExerciseDatabase : RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao
}

