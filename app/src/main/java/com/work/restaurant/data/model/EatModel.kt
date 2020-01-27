package com.work.restaurant.data.model

data class EatModel(
    val date: String,
    val time: String,
    val type: Int,
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
