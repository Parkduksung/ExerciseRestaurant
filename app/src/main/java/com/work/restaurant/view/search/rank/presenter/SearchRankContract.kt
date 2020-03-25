package com.work.restaurant.view.search.rank.presenter

import com.work.restaurant.data.model.BookmarkModel
import com.work.restaurant.data.model.DisplayBookmarkKakaoModel

interface SearchRankContract {

    interface View {

        fun showKakaoList(kakaoList: List<DisplayBookmarkKakaoModel>)

        fun showBookmarkResult(msg: Int, selectPosition: Int)

        fun showCurrentLocation(addressName: String)

        fun showLoad()

        fun showKakaoResult(sort: Int)

    }

    interface Presenter {

        fun addBookmarkKakaoItem(bookmarkModel: BookmarkModel, selectPosition : Int)

        fun deleteBookmarkKakaoItem(bookmarkModel: BookmarkModel, selectPosition : Int)

        fun getCurrentLocation(addressName: String, itemCount: Int, sort: String)

        fun getCurrentAddress(currentX: Double, currentY: Double)


    }


}