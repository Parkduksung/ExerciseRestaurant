package com.work.restaurant.view.search.bookmarks.presenter

import com.work.restaurant.data.model.BookmarkModel

interface SearchBookmarksContract {

    interface View {

        fun showBookmarksList(bookmarkModelList: List<BookmarkModel>)

        fun showLoginState(state: Boolean)

        fun showBookmarkDeleteResult(msg: Boolean)

        fun showBookmarkPresenceOrAbsence(state: Boolean)

    }

    interface Presenter {

        fun getBookmarksList(userId: String)

        fun deleteBookmark(bookmarkModel: BookmarkModel)
    }
}