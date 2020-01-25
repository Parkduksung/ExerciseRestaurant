package com.work.restaurant.data.model

import com.work.restaurant.network.room.entity.AddressEntity

data class RoadModel(
    val si: String,
    val gunGu: String,
    val dong: String
) {
    fun toAddressEntity(): AddressEntity {
        return AddressEntity(
            null,
            si = si,
            gunGu = gunGu,
            dong = dong
        )
    }
}
