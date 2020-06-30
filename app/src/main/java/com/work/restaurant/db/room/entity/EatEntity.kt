package com.work.restaurant.db.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.work.restaurant.data.model.EatModel

@Entity(tableName = "eat")
data class EatEntity(

    @PrimaryKey(autoGenerate = true)
    val eatNum: Int = 0,
    @ColumnInfo(name = "userId")
    val userId: String,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "time")
    val time: String,
    @ColumnInfo(name = "type")
    val type: Int,
    @ColumnInfo(name = "memo")
    val memo: String
) {
    fun toEatModel(): EatModel =
        EatModel(
            eatNum,
            userId,
            date,
            time,
            type,
            memo
        )


}
