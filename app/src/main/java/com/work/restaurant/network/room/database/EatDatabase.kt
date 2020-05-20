package com.work.restaurant.network.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.work.restaurant.network.room.dao.EatDao
import com.work.restaurant.network.room.entity.EatEntity

@Database(entities = [EatEntity::class], version = 7)
abstract class EatDatabase : RoomDatabase() {
    abstract fun eatDao(): EatDao
}