package com.work.restaurant.view.search.rank.presenter

import com.work.restaurant.network.model.FitnessCenterItemResponse
import com.work.restaurant.network.model.kakaoSearch.KakaoSearchDocuments

interface SearchRankContract {

    interface View {

        fun showSettings()

        fun showFitnessList(fitnessList: List<FitnessCenterItemResponse>)


        fun showKakaoList(kakaoList: List<KakaoSearchDocuments>)

    }

    interface Presenter {

        fun settings()

        fun getFitnessList()

        fun getKakaoList()

    }


}