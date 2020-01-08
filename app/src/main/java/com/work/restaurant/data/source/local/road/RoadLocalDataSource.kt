package com.work.restaurant.data.source.local.road

interface RoadLocalDataSource {
    fun getLocalData(
        zone: String,
        area: String,
        clickData: String,
        callback: RoadLocalDataSourceCallback
    )
}