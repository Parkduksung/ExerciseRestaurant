package com.work.restaurant.network.model

import com.google.gson.annotations.SerializedName

data class EatResponse(
    @SerializedName("date")
    // ex) 20200101
    val date: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("type")
    // 1 : 식사 , 2 : 간식
    val type: Int,
    @SerializedName("memo")
    val memo: String
)