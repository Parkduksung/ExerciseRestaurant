package com.work.restaurant.data.repository.road

import com.work.restaurant.db.room.entity.AddressEntity

interface RoadRepository {

    fun getLocalData(
        zone: String,
        area: String,
        clickData: String,
        callback: (list: List<String>?) -> Unit
    )

    fun getAddressCount(callback: (isSuccess: Boolean) -> Unit)

    fun registerAddress(callback: (list: List<AddressEntity>?) -> Unit)
}