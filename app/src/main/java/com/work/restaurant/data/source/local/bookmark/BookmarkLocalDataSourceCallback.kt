package com.work.restaurant.data.source.local.bookmark

import com.work.restaurant.network.room.entity.BookmarkEntity

interface BookmarkLocalDataSourceCallback {

    interface AddBookmarkCallback {
        fun onSuccess(msg: String)
        fun onFailure(msg: String)

    }

    interface DeleteBookmarkCallback {

        fun onSuccess(msg: String)
        fun onFailure(msg: String)

    }

    interface GetAllList {

        fun onSuccess(list: List<BookmarkEntity>)
        fun onFailure(msg: String)

    }


}