package com.work.restaurant.data.model

data class EatModel(
    val eatNum: Int,
    val date: String,
    val time: String,
    val type: Int,
    val memo: String
) {
    fun toDiaryModel(): DiaryModel =
        DiaryModel(
            eatNum,
            0,
            0,
            date,
            time,
            type.toString(),
            memo,
            "",
            emptyList()
        )

}
