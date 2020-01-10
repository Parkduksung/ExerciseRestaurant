package com.work.restaurant.network.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.work.restaurant.network.room.dao.AddressDao
import com.work.restaurant.network.room.entity.AddressEntity

@Database(entities = [AddressEntity::class], version = 1)
abstract class AddressDatabase : RoomDatabase() {

    abstract fun addressDao(): AddressDao

    companion object {
        private var INSTANCE: AddressDatabase? = null

        fun getInstance(context: Context): AddressDatabase? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AddressDatabase::class.java, "address"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE
        }
    }

}