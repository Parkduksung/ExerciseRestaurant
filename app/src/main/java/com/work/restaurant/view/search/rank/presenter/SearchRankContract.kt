package com.work.restaurant.view.search.rank.presenter

import com.work.restaurant.network.model.kakaoSearch.KakaoSearchDocuments

interface SearchRankContract {

    interface View {

        fun showKakaoList(kakaoList: List<KakaoSearchDocuments>)

    }

    interface Presenter {

        fun getKakaoList()

    }


}