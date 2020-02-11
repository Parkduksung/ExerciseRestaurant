package com.work.restaurant.view.search.rank.presenter

import com.work.restaurant.data.model.BookmarkModel
import com.work.restaurant.data.model.KakaoSearchModel

interface SearchRankContract {

    interface View {

        fun showKakaoList(kakaoList: List<KakaoSearchModel>)
        fun showBookmarkResult(msg: Boolean)

    }

    interface Presenter {

        fun getKakaoList(currentX: Double, currentY: Double)

        fun addBookmarkKakaoItem(bookmarkModel: BookmarkModel)

        fun getCurrentLocation(addressName: String)
    }


}