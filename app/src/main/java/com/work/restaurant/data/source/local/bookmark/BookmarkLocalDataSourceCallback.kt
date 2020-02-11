package com.work.restaurant.data.source.local.bookmark

import com.work.restaurant.network.room.entity.BookmarkEntity

interface BookmarkLocalDataSourceCallback {

    interface AddBookmarkCallback {
        fun onSuccess()
        fun onFailure()

    }

    interface DeleteBookmarkCallback {

        fun onSuccess()
        fun onFailure()

    }

    interface GetAllList {

        fun onSuccess(list: List<BookmarkEntity>)
        fun onFailure()

    }


}