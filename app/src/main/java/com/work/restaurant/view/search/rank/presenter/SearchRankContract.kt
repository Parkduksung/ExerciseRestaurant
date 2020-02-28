package com.work.restaurant.view.search.rank.presenter

import com.work.restaurant.data.model.BookmarkModel
import com.work.restaurant.data.model.KakaoSearchModel

interface SearchRankContract {

    interface View {

        fun showKakaoList(kakaoList: List<KakaoSearchModel>)

        fun showBookmarkResult(msg: Boolean)

        fun showCurrentLocation(addressName: String)

        fun showLoad()

        fun showKakaoResult(sort: Int)

    }

    interface Presenter {

        fun addBookmarkKakaoItem(bookmarkModel: BookmarkModel)

        fun getCurrentLocation(addressName: String, itemCount: Int, sort: String)

        fun getCurrentAddress(currentX: Double, currentY: Double)


    }


}