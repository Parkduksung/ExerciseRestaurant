package com.work.restaurant.data.source.local.bookmark

import com.work.restaurant.network.room.database.BookmarkDatabase
import com.work.restaurant.network.room.entity.BookmarkEntity
import com.work.restaurant.util.AppExecutors

class BookmarkLocalDataSourceImpl(
    private val appExecutors: AppExecutors,
    private val bookmarkDatabase: BookmarkDatabase
) : BookmarkLocalDataSource {

    override fun addBookmark(
        bookmarkEntity: BookmarkEntity,
        callback: BookmarkLocalDataSourceCallback.AddBookmarkCallback
    ) {
        appExecutors.diskIO.execute {

            val addBookmark = bookmarkDatabase.bookmarkDao().addBookmark(bookmarkEntity)
            if (addBookmark >= 1) {
                callback.onSuccess("successAdd")
            } else {
                callback.onFailure("error")
            }
        }

    }

    override fun getAllList(callback: BookmarkLocalDataSourceCallback.GetAllList) {

        appExecutors.diskIO.execute {

            val getAllList =
                bookmarkDatabase.bookmarkDao().getAll()

            callback.onSuccess(getAllList)
        }
    }


    override fun deleteBookmark(
        bookmarkEntity: BookmarkEntity,
        callback: BookmarkLocalDataSourceCallback.DeleteBookmarkCallback
    ) {
        appExecutors.diskIO.execute {

            val deleteBookmark = bookmarkDatabase.bookmarkDao()
                .deleteBookmark(bookmarkEntity.bookmarkName, bookmarkEntity.bookmarkUrl)

            if (deleteBookmark >= 1) {
                callback.onSuccess("successDelete")

            } else {
                callback.onFailure("error")
            }

        }
    }


    companion object {

        private var instance: BookmarkLocalDataSourceImpl? = null

        fun getInstance(
            appExecutors: AppExecutors,
            bookmarkDatabase: BookmarkDatabase
        ): BookmarkLocalDataSourceImpl =
            instance ?: BookmarkLocalDataSourceImpl(
                appExecutors,
                bookmarkDatabase
            ).also {
                instance = it
            }
    }
}