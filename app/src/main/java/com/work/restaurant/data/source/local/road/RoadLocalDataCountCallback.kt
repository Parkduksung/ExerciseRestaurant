package com.work.restaurant.data.source.local.road

interface RoadLocalDataCountCallback {
    fun onSuccess(state: Boolean)
    fun onFailure(message: String)
}