package com.work.restaurant.data.source.local.road

import com.work.restaurant.network.room.entity.AddressEntity

interface RoadLocalDataRegisterCallback {
    fun onSuccess(list: List<AddressEntity>)
    fun onFailure(message: String)
}