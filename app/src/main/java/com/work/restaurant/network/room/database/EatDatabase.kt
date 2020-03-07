package com.work.restaurant.network.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.work.restaurant.network.room.dao.EatDao
import com.work.restaurant.network.room.entity.EatEntity

@Database(entities = [EatEntity::class], version = 7)
abstract class EatDatabase : RoomDatabase() {

    abstract fun eatDao(): EatDao

    companion object {
        private var INSTANCE: EatDatabase? = null

        fun getInstance(context: Context): EatDatabase =
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                EatDatabase::class.java,
                "eat"
            )
                .fallbackToDestructiveMigration()
                .build()
                .also {
                    INSTANCE = it
                }
    }

}