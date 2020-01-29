package com.work.restaurant.view.home.daum_maps.presenter

import com.work.restaurant.data.model.KakaoModel

interface MapContract {

    interface View {
        fun showKakaoData(
            currentX: Double,
            currentY: Double, list: List<KakaoModel>
        )
    }


    interface Presenter {

        fun getKakaoData(
            currentX: Double,
            currentY: Double
        )
    }
}