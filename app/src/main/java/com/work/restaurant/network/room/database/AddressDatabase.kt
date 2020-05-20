package com.work.restaurant.network.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.work.restaurant.network.room.dao.AddressDao
import com.work.restaurant.network.room.entity.AddressEntity

@Database(entities = [AddressEntity::class], version = 1)
abstract class AddressDatabase : RoomDatabase() {
    abstract fun addressDao(): AddressDao
}