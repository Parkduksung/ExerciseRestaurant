package com.work.restaurant.data.repository.eat

import com.work.restaurant.network.room.entity.EatEntity

interface EatRepository {

    fun addEat(
        date: String,
        time: String,
        type: Int,
        memo: String,
        callback: EatRepositoryCallback.AddEatCallback
    )


    fun getList(
        callback: EatRepositoryCallback.GetAllList
    )

    fun getDataOfTheDay(
        today: String,
        callback: EatRepositoryCallback.GetDataOfTheDay
    )

    fun deleteEat(
        data: EatEntity,
        callback: EatRepositoryCallback.DeleteEatCallback
    )
}