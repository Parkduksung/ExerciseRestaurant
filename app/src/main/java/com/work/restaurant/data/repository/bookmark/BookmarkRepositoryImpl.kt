package com.work.restaurant.data.repository.bookmark

import com.work.restaurant.data.source.local.bookmark.BookmarkLocalDataSource
import com.work.restaurant.network.room.entity.BookmarkEntity

class BookmarkRepositoryImpl(
    private val bookmarkLocalDataSource: BookmarkLocalDataSource
) : BookmarkRepository {
    override fun getAllList(userId: String, callback: (getList: List<BookmarkEntity>) -> Unit) {
        bookmarkLocalDataSource.getAllList(userId, callback)
    }

    override fun addBookmark(
        bookmarkEntity: BookmarkEntity,
        callback: (isSuccess: Boolean) -> Unit
    ) {
        bookmarkLocalDataSource.addBookmark(bookmarkEntity, callback)
    }

    override fun deleteBookmark(
        bookmarkEntity: BookmarkEntity,
        callback: (isSuccess: Boolean) -> Unit
    ) {
        bookmarkLocalDataSource.deleteBookmark(bookmarkEntity, callback)
    }
}