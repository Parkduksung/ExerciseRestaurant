package com.work.restaurant.data.repository.road

interface RoadRepositoryDataCountCallback {
    fun onSuccess(state: Boolean)
    fun onFailure(message: String)
}