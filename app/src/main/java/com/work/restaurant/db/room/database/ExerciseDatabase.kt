package com.work.restaurant.db.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.work.restaurant.db.room.converter.ExerciseSetConverter
import com.work.restaurant.db.room.dao.ExerciseDao
import com.work.restaurant.db.room.entity.ExerciseEntity

@Database(entities = [ExerciseEntity::class], version = 6)
@TypeConverters(ExerciseSetConverter::class)
abstract class ExerciseDatabase : RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao
}

