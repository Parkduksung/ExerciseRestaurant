package com.work.restaurant.view.home

import com.work.restaurant.data.model.DisplayBookmarkKakaoModel

interface MapInterface {

    interface CurrentLocationClickListener {
        fun clickMap(clickData: Boolean)
    }

    interface SelectMarkerListener {
        fun getMarkerData(data: DisplayBookmarkKakaoModel)
    }

    interface SearchLocationListener {
        fun findFitnessResult(sort: Int)
    }

}