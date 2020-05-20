package com.work.restaurant.data.source.local.bookmark

import com.work.restaurant.network.room.database.BookmarkDatabase
import com.work.restaurant.network.room.entity.BookmarkEntity
import com.work.restaurant.util.AppExecutors

class BookmarkLocalDataSourceImpl(
    private val appExecutors: AppExecutors,
    private val bookmarkDatabase: BookmarkDatabase
) : BookmarkLocalDataSource {
    override fun getAllList(
        userId: String,
        callback: (getList: List<BookmarkEntity>?) -> Unit
    ) {

        appExecutors.diskIO.execute {

            val getAllList =
                bookmarkDatabase.bookmarkDao().getAll(userId)

            appExecutors.mainThread.execute {
                callback(getAllList)
            }
        }

    }
    override fun addBookmark(
        bookmarkEntity: BookmarkEntity,
        callback: (isSuccess: Boolean) -> Unit
    ) {
        appExecutors.diskIO.execute {

            val addBookmark =
                bookmarkDatabase.bookmarkDao().addBookmark(bookmarkEntity)

            appExecutors.mainThread.execute {
                if (addBookmark >= 1) {
                    callback(true)
                } else {
                    callback(false)
                }
            }
        }
    }
    override fun deleteBookmark(
        bookmarkEntity: BookmarkEntity,
        callback: (isSuccess: Boolean) -> Unit
    ) {
        appExecutors.diskIO.execute {

            val deleteBookmark =
                bookmarkDatabase.bookmarkDao().deleteBookmark(
                    bookmarkEntity.bookmarkUserId,
                    bookmarkEntity.bookmarkName,
                    bookmarkEntity.bookmarkUrl
                )

            appExecutors.mainThread.execute {
                if (deleteBookmark >= 1) {
                    callback(true)
                } else {
                    callback(false)
                }
            }

        }
    }
}