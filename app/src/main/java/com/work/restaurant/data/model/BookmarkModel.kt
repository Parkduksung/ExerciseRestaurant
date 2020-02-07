package com.work.restaurant.data.model

import com.work.restaurant.network.room.entity.BookmarkEntity

data class BookmarkModel(
    val bookmarkName: String,
    val bookmarkUrl: String
) {
    fun toBookmarkEntity(): BookmarkEntity =
        BookmarkEntity(
            0,
            bookmarkName,
            bookmarkUrl
        )
}