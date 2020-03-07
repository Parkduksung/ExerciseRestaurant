package com.work.restaurant.data.source.local.eat

import com.work.restaurant.network.room.entity.EatEntity

interface EatLocalDataSource {

    fun addEat(
        userId: String,
        date: String,
        time: String,
        type: Int,
        memo: String,
        callback: EatLocalDataSourceCallback.AddEatCallback
    )

    fun deleteEat(
        data: EatEntity,
        callback: EatLocalDataSourceCallback.DeleteEatCallback
    )

    fun getDataOfTheDay(
        userId: String,
        date: String,
        callback: EatLocalDataSourceCallback.GetDataOfTheDay
    )


    fun getAllList(
        userId: String,
        callback: EatLocalDataSourceCallback.GetAllList
    )


    fun updateEat(
        time: String,
        type: Int,
        memo: String,
        data: EatEntity,
        callback: EatLocalDataSourceCallback.UpdateEatCallback
    )

}
