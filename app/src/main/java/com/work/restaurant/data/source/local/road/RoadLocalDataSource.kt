package com.work.restaurant.data.source.local.road

import com.work.restaurant.network.room.entity.AddressEntity

interface RoadLocalDataSource {
    fun getLocalData(
        zone: String,
        area: String,
        clickData: String,
        callback: (list: List<String>?) -> Unit
    )

    fun getAddressCount(callback: (isSuccess: Boolean) -> Unit)

    fun isContainAddress(callback: (isSuccess: Boolean) -> Unit)

    fun registerAddress(callback: (list: List<AddressEntity>?) -> Unit)

}