package com.work.restaurant.data.source.local.bookmark

import com.work.restaurant.network.room.entity.BookmarkEntity

interface BookmarkLocalDataSource {


    fun getAllList(
        userId: String,
        callback: BookmarkLocalDataSourceCallback.GetAllList
    )

    fun addBookmark(
        bookmarkEntity: BookmarkEntity,
        callback: BookmarkLocalDataSourceCallback.AddBookmarkCallback
    )

    fun deleteBookmark(
        bookmarkEntity: BookmarkEntity,
        callback: BookmarkLocalDataSourceCallback.DeleteBookmarkCallback
    )

}