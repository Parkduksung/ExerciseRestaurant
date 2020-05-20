package com.work.restaurant.network.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.work.restaurant.network.room.dao.BookmarkDao
import com.work.restaurant.network.room.entity.BookmarkEntity

@Database(entities = [BookmarkEntity::class], version = 4)
abstract class BookmarkDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao
}


