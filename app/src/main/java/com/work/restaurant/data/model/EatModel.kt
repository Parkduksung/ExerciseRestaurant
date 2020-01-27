package com.work.restaurant.data.model

import com.google.gson.annotations.SerializedName

data class EatModel(
    @SerializedName("date")
    val date: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("type")
    // 1 : 식사 , 2 : 간식
    val type: Int,
    @SerializedName("memo")
    val memo: String
) {
    fun toDiaryModel(): DiaryModel =
        DiaryModel(
            0,
            date,
            time,
            type.toString(),
            memo,
            null,
            null
        )

}
