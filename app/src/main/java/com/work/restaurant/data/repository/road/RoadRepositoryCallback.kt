package com.work.restaurant.data.repository.road

interface RoadRepositoryCallback {
    fun onSuccess(list: List<String>)
    fun onFailure(message: String)
}