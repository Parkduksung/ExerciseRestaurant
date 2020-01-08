package com.work.restaurant.data.repository.road

interface RoadRepository {
    fun getLocalData(
        zone: String,
        area: String,
        clickData: String,
        callback: RoadRepositoryCallback
    )
}