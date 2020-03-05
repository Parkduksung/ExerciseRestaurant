package com.work.restaurant.data.model

import com.work.restaurant.network.room.entity.BookmarkEntity

data class BookmarkModel(
    val bookmarkUserId: String,
    val bookmarkName: String,
    val bookmarkUrl: String,
    val bookmarkAddress: String
) {
    fun toBookmarkEntity(): BookmarkEntity =
        BookmarkEntity(
            0,
            bookmarkUserId,
            bookmarkName,
            bookmarkUrl,
            bookmarkAddress
        )
}