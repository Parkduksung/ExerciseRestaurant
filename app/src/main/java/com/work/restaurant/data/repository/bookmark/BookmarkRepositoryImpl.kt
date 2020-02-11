package com.work.restaurant.data.repository.bookmark

import com.work.restaurant.data.source.local.bookmark.BookmarkLocalDataSourceCallback
import com.work.restaurant.data.source.local.bookmark.BookmarkLocalDataSourceImpl
import com.work.restaurant.network.room.entity.BookmarkEntity

class BookmarkRepositoryImpl(
    private val bookmarkLocalDataSourceImpl: BookmarkLocalDataSourceImpl
) : BookmarkRepository {
    override fun addBookmark(
        bookmarkEntity: BookmarkEntity,
        callback: BookmarkRepositoryCallback.AddBookmarkCallback
    ) {
        bookmarkLocalDataSourceImpl.addBookmark(
            bookmarkEntity,
            object : BookmarkLocalDataSourceCallback.AddBookmarkCallback {
                override fun onSuccess() {
                    callback.onSuccess()
                }

                override fun onFailure() {
                    callback.onFailure()
                }
            }

        )
    }

    override fun getAllList(callback: BookmarkRepositoryCallback.GetAllList) {
        bookmarkLocalDataSourceImpl.getAllList(object : BookmarkLocalDataSourceCallback.GetAllList {
            override fun onSuccess(list: List<BookmarkEntity>) {
                callback.onSuccess(list)
            }

            override fun onFailure() {
                callback.onFailure()
            }
        })
    }

    override fun deleteBookmark(
        bookmarkEntity: BookmarkEntity,
        callback: BookmarkRepositoryCallback.DeleteBookmarkCallback
    ) {

        bookmarkLocalDataSourceImpl.deleteBookmark(
            bookmarkEntity,
            object : BookmarkLocalDataSourceCallback.DeleteBookmarkCallback {
                override fun onSuccess() {
                    callback.onSuccess()
                }

                override fun onFailure() {
                    callback.onFailure()
                }
            })
    }

    companion object {

        private var instance: BookmarkRepositoryImpl? = null

        fun getInstance(
            bookmarkLocalDataSourceImpl: BookmarkLocalDataSourceImpl
        ): BookmarkRepository =
            instance ?: BookmarkRepositoryImpl(bookmarkLocalDataSourceImpl)
                .also {
                    instance = it
                }

    }

}