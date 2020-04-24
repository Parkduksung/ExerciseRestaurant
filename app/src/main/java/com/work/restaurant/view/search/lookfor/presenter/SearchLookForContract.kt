package com.work.restaurant.view.search.lookfor.presenter

import com.work.restaurant.data.model.BookmarkModel
import com.work.restaurant.data.model.DisplayBookmarkKakaoModel

interface SearchLookForContract {
    interface View {


        fun showSearchLook(searchModel: List<DisplayBookmarkKakaoModel>)

        fun showSearchNoFind()

        fun showBookmarkResult(msg: Int, selectPosition: Int)

    }

    interface Presenter {

        fun searchLook(searchItem: String)

        fun addBookmark(bookmarkModel: BookmarkModel, selectPosition: Int)

        fun deleteBookmark(bookmarkModel: BookmarkModel, selectPosition: Int)


    }
}