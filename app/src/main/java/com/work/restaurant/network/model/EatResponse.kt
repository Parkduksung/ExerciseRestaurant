package com.work.restaurant.network.model

import com.google.gson.annotations.SerializedName
import com.work.restaurant.data.model.EatModel

data class EatResponse(
    @SerializedName("date")
    val date: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("type")
    val type: Int,
    @SerializedName("memo")
    val memo: String
) {

    fun toEatModel(): EatModel =
        EatModel(
            date,
            time,
            type,
            memo
        )

}