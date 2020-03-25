package com.work.restaurant.view.home.daum_maps.presenter

import com.work.restaurant.data.model.DisplayBookmarkKakaoModel
import com.work.restaurant.data.model.KakaoSearchModel

interface MapContract {

    interface View {
        fun showKakaoData(
            list: List<KakaoSearchModel>
        )

        fun showMarkerData(
            list: List<DisplayBookmarkKakaoModel>
        )

        fun showSearchData(
            list: List<KakaoSearchModel>,
            sort: Int
        )


    }


    interface Presenter {

        fun getKakaoData(
            currentX: Double,
            currentY: Double
        )

        fun getMarkerData(
            x: Double,
            y: Double,
            markerName: String
        )

        fun getThisPositionData(
            currentX: Double,
            currentY: Double,
            radius: Int,
            itemCount: Int
        )

    }
}