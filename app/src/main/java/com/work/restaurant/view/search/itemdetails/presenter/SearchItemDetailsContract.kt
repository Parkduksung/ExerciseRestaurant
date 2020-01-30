package com.work.restaurant.view.search.itemdetails.presenter

import com.work.restaurant.network.model.FitnessCenterItemResponse
import com.work.restaurant.network.model.kakaoImage.KakaoImageDocuments
import com.work.restaurant.network.model.kakaoSearch.KakaoSearchDocuments

interface SearchItemDetailsContract {
    interface View {

        fun showItemInfoDetail(fitnessList: FitnessCenterItemResponse)

        fun showKakaoItemInfoDetail(searchList: KakaoSearchDocuments)

        fun showKakaoImage(imageList: List<KakaoImageDocuments>)

        fun showCall(callNum: String)
    }

    interface Presenter {

        fun itemInfoDetail(searchItem: String)

        fun kakaoItemInfoDetail(searchItem: String)

        fun kakaoItemImage(searchItem: String)

        fun call(callNum: String)

    }
}