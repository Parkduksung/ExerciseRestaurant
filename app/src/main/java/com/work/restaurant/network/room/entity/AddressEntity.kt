package com.work.restaurant.network.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "address")
data class AddressEntity(
    @PrimaryKey
    val si: String,
    @ColumnInfo(name = "gun_gu_list")
    val gunGuList: List<GunGu>
) {
    data class GunGu(
        @ColumnInfo(name = "gun_gu")
        val gunGu: String,
        @ColumnInfo(name = "dong_list")
        val dongList: List<String>
    )
}
