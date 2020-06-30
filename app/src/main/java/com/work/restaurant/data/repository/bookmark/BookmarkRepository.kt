package com.work.restaurant.data.repository.bookmark

import com.work.restaurant.db.room.entity.BookmarkEntity

interface BookmarkRepository {
    fun getAllList(
        userId: String,
        callback: (getList: List<BookmarkEntity>?) -> Unit
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