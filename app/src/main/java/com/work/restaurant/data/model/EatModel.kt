package com.work.restaurant.data.model

import android.os.Parcelable
import com.work.restaurant.db.room.entity.EatEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EatModel(
    val eatNum: Int,
    val userId: String,
    val date: String,
    val time: String,
    val type: Int,
    val memo: String
) : Parcelable {
    fun toDiaryModel(): DiaryModel =
        DiaryModel(
            eatNum,
            0,
            userId,
            0,
            date,
            time,
            type.toString(),
            memo,
            "",
            emptyList()
        )

    fun toEatEntity(): EatEntity =
        EatEntity(
            eatNum, userId, date, time, type, memo
        )


}
