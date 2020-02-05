package com.work.restaurant.view.home.daum_maps.presenter

import com.work.restaurant.data.model.KakaoSearchModel

interface MapContract {

    interface View {
        fun showKakaoData(
            currentX: Double,
            currentY: Double, list: List<KakaoSearchModel>
        )
    }


    interface Presenter {

        fun getKakaoData(
            currentX: Double,
            currentY: Double
        )
    }
}