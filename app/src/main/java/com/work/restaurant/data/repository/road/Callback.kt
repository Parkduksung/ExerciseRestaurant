package com.work.restaurant.data.repository.road

import com.work.restaurant.network.room.entity.AddressEntity

interface Callback {
    fun onSuccess(list: List<AddressEntity>)
    fun onFailure(message: String)
}