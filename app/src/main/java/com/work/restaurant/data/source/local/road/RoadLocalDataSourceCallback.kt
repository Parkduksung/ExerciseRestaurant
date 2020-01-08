package com.work.restaurant.data.source.local.road

interface RoadLocalDataSourceCallback {

    fun onSuccess(list: List<String>)
    fun onFailure(message: String)

}