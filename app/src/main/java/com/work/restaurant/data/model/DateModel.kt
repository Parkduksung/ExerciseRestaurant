package com.work.restaurant.data.model

import com.google.gson.annotations.SerializedName

data class DateModel(
    @SerializedName("date")
    val date: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("type")
    // 1 : 식사 , 2 : 간식
    val type: Int,
    @SerializedName("memo")
    val memo: String
)

//{
//
//    fun toDataModel(
//        date: String,
//        time: String,
//        type: Int,
//        memo: String
//    ): DataModel {
//
//        val year = date.substring(0, 4) + "년"
//        val month = date.substring(4, 6) + "월"
//        val day = date.substring(6, 8) + "일"
//
//        val totalDate = "$year $month $day"
//
//
//    }
//
//
//}