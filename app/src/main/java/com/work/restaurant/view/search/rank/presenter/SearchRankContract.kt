package com.work.restaurant.view.search.rank.presenter

import com.work.restaurant.data.model.BookmarkModel
import com.work.restaurant.data.model.KakaoSearchModel

interface SearchRankContract {

    interface View {

        fun showKakaoList(kakaoList: List<KakaoSearchModel>)
        fun showBookmarkResult(msg: String)

    }

    interface Presenter {

        fun getKakaoList()

        fun addBookmarkKakaoItem(bookmarkModel: BookmarkModel)

    }


}