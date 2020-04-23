package com.work.restaurant.view.home.main.presenter

import com.work.restaurant.data.model.BookmarkModel

interface HomeContract {


    interface View {

        fun showBookmarkResult(result: Int)

    }

    interface Presenter {

        fun deleteBookmark(bookmarkModel: BookmarkModel)

        fun addBookmark(bookmarkModel: BookmarkModel)

    }


}