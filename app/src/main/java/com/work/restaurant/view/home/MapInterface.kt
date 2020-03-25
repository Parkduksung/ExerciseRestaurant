package com.work.restaurant.view.home

import com.work.restaurant.data.model.DisplayBookmarkKakaoModel

interface MapInterface {

    interface CurrentLocationClickListener {
        fun click(clickData: Boolean)
    }

    interface SelectMarkerListener {
        fun getMarkerData(data: DisplayBookmarkKakaoModel)
    }

    interface SearchLocationListener {
        fun finishOrNoResult(sort: Int)
    }

}