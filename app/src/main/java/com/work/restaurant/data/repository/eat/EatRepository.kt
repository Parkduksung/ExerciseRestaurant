package com.work.restaurant.data.repository.eat

import com.work.restaurant.db.room.entity.EatEntity

interface EatRepository {

    fun addEat(
        userId: String,
        date: String,
        time: String,
        type: Int,
        memo: String,
        callback: (isSuccess: Boolean) -> Unit
    )

    fun getList(
        userId: String,
        callback: (getList: List<EatEntity>) -> Unit
    )

    fun getDataOfTheDay(
        userId: String,
        today: String,
        callback: (getList: List<EatEntity>) -> Unit
    )

    fun deleteEat(
        data: EatEntity,
        callback: (isSuccess: Boolean) -> Unit
    )

    fun updateEat(
        time: String,
        type: Int,
        memo: String,
        data: EatEntity,
        callback: (isSuccess: Boolean) -> Unit
    )

}