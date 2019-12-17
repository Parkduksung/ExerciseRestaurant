package com.work.restaurant.view.search.bookmarks.presenter

import com.work.restaurant.network.model.FitnessCenterItemResponse

interface SearchBookmarksContract {

    interface View {
        fun showBookmarksList(fitnessList: List<FitnessCenterItemResponse>)

    }

    interface Presenter {
        fun getBookmarksList()
    }
}