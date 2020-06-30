package com.work.restaurant.db.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.work.restaurant.db.room.dao.AddressDao
import com.work.restaurant.db.room.entity.AddressEntity

@Database(entities = [AddressEntity::class], version = 1)
abstract class AddressDatabase : RoomDatabase() {
    abstract fun addressDao(): AddressDao
}