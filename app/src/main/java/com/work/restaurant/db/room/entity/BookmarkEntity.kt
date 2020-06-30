package com.work.restaurant.db.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.work.restaurant.data.model.BookmarkModel


@Entity(tableName = "bookmark")
class BookmarkEntity(
    @PrimaryKey(autoGenerate = true)
    val bookmarkNum: Int = 0,
    @ColumnInfo(name = "bookmarkUser")
    val bookmarkUserId: String,
    @ColumnInfo(name = "bookmarkName")
    val bookmarkName: String,
    @ColumnInfo(name = "bookmarkUrl")
    val bookmarkUrl: String,
    @ColumnInfo(name = "bookmarkAddress")
    val bookmarkAddress: String
) {
    fun toBookmarkModel(): BookmarkModel =
        BookmarkModel(
            bookmarkUserId,
            bookmarkName,
            bookmarkUrl,
            bookmarkAddress
        )
}