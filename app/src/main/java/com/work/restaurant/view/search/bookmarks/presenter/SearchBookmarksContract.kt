package com.work.restaurant.view.search.bookmarks.presenter

import com.work.restaurant.data.model.BookmarkModel

interface SearchBookmarksContract {

    interface View {
        fun showBookmarksList(bookmarkModelList: List<BookmarkModel>)

        fun showNotLoginBookmark()

        fun showBookmarkDeleteResult(msg: Boolean)

    }

    interface Presenter {
        fun getBookmarksList(userId: String)
        fun deleteBookmark(bookmarkModel: BookmarkModel)
    }
}