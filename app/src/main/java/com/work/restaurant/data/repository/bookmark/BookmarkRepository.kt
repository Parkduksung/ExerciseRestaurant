package com.work.restaurant.data.repository.bookmark

import com.work.restaurant.network.room.entity.BookmarkEntity

interface BookmarkRepository {
    fun getAllList(
        callback: BookmarkRepositoryCallback.GetAllList
    )

    fun addBookmark(
        bookmarkEntity: BookmarkEntity,
        callback: BookmarkRepositoryCallback.AddBookmarkCallback
    )

    fun deleteBookmark(
        bookmarkEntity: BookmarkEntity,
        callback: BookmarkRepositoryCallback.DeleteBookmarkCallback
    )
}