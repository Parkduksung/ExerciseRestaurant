package com.work.restaurant.data.source.local.eat

import com.work.restaurant.network.room.entity.EatEntity

interface EatLocalDataSource {

    fun addEat(
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
        date: String,
        callback: EatLocalDataSourceCallback.GetDataOfTheDay
    )


    fun getAllList(

        callback: EatLocalDataSourceCallback.GetAllList
    )


}
