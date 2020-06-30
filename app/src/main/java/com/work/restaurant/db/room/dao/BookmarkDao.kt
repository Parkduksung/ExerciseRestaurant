package com.work.restaurant.db.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.work.restaurant.db.room.entity.BookmarkEntity

@Dao
interface BookmarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBookmark(bookmarkEntity: BookmarkEntity): Long

    @Query("SELECT * FROM bookmark WHERE bookmarkUser = (:bookmarkUser)")
    fun getAll(bookmarkUser: String): List<BookmarkEntity>

    @Query("DELETE FROM bookmark WHERE bookmarkUser = (:bookmarkUser) And bookmarkName = (:bookmarkName) AND bookmarkUrl = (:bookmarkUrl)")
    fun deleteBookmark(bookmarkUser: String, bookmarkName: String, bookmarkUrl: String): Int
}