package com.work.restaurant.network.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "eat")
data class EatEntity(

    @PrimaryKey(autoGenerate = true)
    val eatNum: Int?,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "time")
    val time: String,
    @ColumnInfo(name = "type")
    val type: Int,
    @ColumnInfo(name = "memo")
    val memo: String

)
