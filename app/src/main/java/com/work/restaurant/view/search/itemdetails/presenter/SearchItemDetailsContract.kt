package com.work.restaurant.view.search.itemdetails.presenter

import com.work.restaurant.data.model.KakaoSearchModel

interface SearchItemDetailsContract {
    interface View {

        fun showKakaoItemInfoDetail(searchList: List<KakaoSearchModel>)

    }

    interface Presenter {

        fun kakaoItemInfoDetail(searchItem: String)

    }
}