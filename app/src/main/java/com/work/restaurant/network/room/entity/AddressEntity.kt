package com.work.restaurant.network.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

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
)

