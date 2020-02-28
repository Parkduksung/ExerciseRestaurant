package com.work.restaurant.view.home

import com.work.restaurant.data.model.KakaoSearchModel

interface MapInterface {

    interface CurrentLocationClickListener {
        fun click(clickData: Boolean)
    }

    interface SelectMarkerListener {
        fun getMarkerData(data: KakaoSearchModel)
    }
}