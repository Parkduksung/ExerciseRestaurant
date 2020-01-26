package com.work.restaurant.network.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.work.restaurant.data.model.EatModel
import com.work.restaurant.network.model.EatResponse

@Entity(tableName = "eat")
data class EatEntity(

    @PrimaryKey(autoGenerate = true)
    val eatNum: Int = 0,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "time")
    val time: String,
    @ColumnInfo(name = "type")
    val type: Int,
    @ColumnInfo(name = "memo")
    val memo: String

) {
    fun toEatResponse(): EatResponse {
        return EatResponse(date, time, type, memo)
    }

    fun toEatModel(): EatModel {
        return EatModel(date, time, type, memo)
    }

}
