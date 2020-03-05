package com.work.restaurant.network.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.work.restaurant.network.room.dao.BookmarkDao
import com.work.restaurant.network.room.entity.BookmarkEntity

@Database(entities = [BookmarkEntity::class], version = 4)
abstract class BookmarkDatabase : RoomDatabase() {

    abstract fun bookmarkDao(): BookmarkDao

    companion object {
        private var INSTANCE: BookmarkDatabase? = null

        fun getInstance(context: Context): BookmarkDatabase =
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                BookmarkDatabase::class.java,
                "bookmark"
            )
                .fallbackToDestructiveMigration()
                .build()
                .also {
                    INSTANCE = it
                }
    }

}


