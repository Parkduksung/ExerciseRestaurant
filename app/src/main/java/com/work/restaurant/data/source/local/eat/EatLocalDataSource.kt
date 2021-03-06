package com.work.restaurant.data.source.local.eat

import com.work.restaurant.network.room.entity.EatEntity

interface EatLocalDataSource {

    fun addEat(
        userId: String,
        date: String,
        time: String,
        type: Int,
        memo: String,
        callback: (isSuccess: Boolean) -> Unit
    )

    fun deleteEat(
        data: EatEntity,
        callback: (isSuccess: Boolean) -> Unit
    )

    fun getDataOfTheDay(
        userId: String,
        date: String,
        callback: (getList: List<EatEntity>) -> Unit
    )

    fun getAllList(
        userId: String,
        callback: (getList: List<EatEntity>) -> Unit
    )

    fun updateEat(
        time: String,
        type: Int,
        memo: String,
        data: EatEntity,
        callback: (isSuccess: Boolean) -> Unit
    )

}
