package com.work.restaurant.db.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.work.restaurant.db.room.dao.LoginDao
import com.work.restaurant.db.room.entity.LoginEntity


@Database(entities = [LoginEntity::class], version = 2)
abstract class LoginDatabase : RoomDatabase() {
    abstract fun loginDao(): LoginDao
}


