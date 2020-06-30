package com.work.restaurant.db.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.work.restaurant.data.model.RoadModel

@Entity(tableName = "address")
data class AddressEntity(
    @PrimaryKey(autoGenerate = true)
    val addressNum: Int?,
    @ColumnInfo(name = "si")
    val si: String,
    @ColumnInfo(name = "gunGu")
    val gunGu: String,
    @ColumnInfo(name = "dong")
    val dong: String
) {
    fun toRoadModel(): RoadModel =
        RoadModel(si, gunGu, dong)
}

