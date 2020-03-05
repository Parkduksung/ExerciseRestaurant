package com.work.restaurant.network.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.work.restaurant.network.room.entity.BookmarkEntity

@Dao
interface BookmarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBookmark(bookmarkEntity: BookmarkEntity): Long

    @Query("SELECT * FROM bookmark WHERE bookmarkUser = (:bookmarkUser)")
    fun getAll(bookmarkUser: String): List<BookmarkEntity>

    @Query("DELETE FROM bookmark WHERE bookmarkName = (:bookmarkName) AND bookmarkUrl = (:bookmarkUrl)")
    fun deleteBookmark(bookmarkName: String, bookmarkUrl: String): Int
}