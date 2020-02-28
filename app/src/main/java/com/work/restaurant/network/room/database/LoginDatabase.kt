package com.work.restaurant.network.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.work.restaurant.network.room.dao.LoginDao
import com.work.restaurant.network.room.entity.LoginEntity


@Database(entities = [LoginEntity::class], version = 1)
abstract class LoginDatabase : RoomDatabase() {

    abstract fun loginDao(): LoginDao

    companion object {
        private var INSTANCE: LoginDatabase? = null

        fun getInstance(context: Context): LoginDatabase =
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                LoginDatabase::class.java,
                "login"
            )
                .fallbackToDestructiveMigration()
                .build()
                .also {
                    INSTANCE = it
                }
    }

}


