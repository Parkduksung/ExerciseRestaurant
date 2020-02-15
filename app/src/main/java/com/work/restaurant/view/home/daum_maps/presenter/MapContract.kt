package com.work.restaurant.view.home.daum_maps.presenter

import com.work.restaurant.data.model.KakaoSearchModel

interface MapContract {

    interface View {
        fun showKakaoData(
            list: List<KakaoSearchModel>
        )

        fun showMarkerData(
            list: List<KakaoSearchModel>
        )
    }


    interface Presenter {

        fun getKakaoData(
            currentX: Double,
            currentY: Double
        )

        fun getMarkerData(
            markerName: String
        )
    }
}