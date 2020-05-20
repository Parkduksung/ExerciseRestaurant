package com.work.restaurant.network.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.work.restaurant.network.room.dao.LoginDao
import com.work.restaurant.network.room.entity.LoginEntity


@Database(entities = [LoginEntity::class], version = 2)
abstract class LoginDatabase : RoomDatabase() {
    abstract fun loginDao(): LoginDao
}


