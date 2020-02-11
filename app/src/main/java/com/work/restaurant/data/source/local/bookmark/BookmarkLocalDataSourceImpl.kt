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


            appExecutors.mainThread.execute {
                if (addBookmark >= 1) {
                    callback.onSuccess()
                } else {
                    callback.onFailure()
                }
            }

        }

    }

    override fun getAllList(callback: BookmarkLocalDataSourceCallback.GetAllList) {

        appExecutors.diskIO.execute {

            val getAllList =
                bookmarkDatabase.bookmarkDao().getAll()


            appExecutors.mainThread.execute {
                getAllList.takeIf { true }
                    .apply {
                        callback.onSuccess(getAllList)
                    } ?: callback.onFailure()
            }
        }
    }


    override fun deleteBookmark(
        bookmarkEntity: BookmarkEntity,
        callback: BookmarkLocalDataSourceCallback.DeleteBookmarkCallback
    ) {
        appExecutors.diskIO.execute {

            val deleteBookmark = bookmarkDatabase.bookmarkDao()
                .deleteBookmark(bookmarkEntity.bookmarkName, bookmarkEntity.bookmarkUrl)

            appExecutors.mainThread.execute {
                if (deleteBookmark >= 1) {
                    callback.onSuccess()

                } else {
                    callback.onFailure()
                }
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