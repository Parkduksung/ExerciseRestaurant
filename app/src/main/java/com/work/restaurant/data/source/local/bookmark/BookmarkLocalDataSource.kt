package com.work.restaurant.data.source.local.bookmark

import com.work.restaurant.network.room.entity.BookmarkEntity

interface BookmarkLocalDataSource {
    fun getAllList(
        userId: String,
        callback: (getList: List<BookmarkEntity>) -> Unit
    )

    fun addBookmark(
        bookmarkEntity: BookmarkEntity,
        callback: (isSuccess: Boolean) -> Unit
    )

    fun deleteBookmark(
        bookmarkEntity: BookmarkEntity,
        callback: (isSuccess: Boolean) -> Unit
    )
}