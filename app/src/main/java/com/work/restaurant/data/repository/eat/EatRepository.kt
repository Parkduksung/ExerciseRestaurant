package com.work.restaurant.data.repository.eat

import com.work.restaurant.network.room.entity.EatEntity

interface EatRepository {

    fun addEat(
        userId: String,
        date: String,
        time: String,
        type: Int,
        memo: String,
        callback: EatRepositoryCallback.AddEatCallback
    )


    fun getList(
        userId: String,
        callback: EatRepositoryCallback.GetAllList
    )

    fun getDataOfTheDay(
        userId: String,
        today: String,
        callback: EatRepositoryCallback.GetDataOfTheDay
    )

    fun deleteEat(
        data: EatEntity,
        callback: EatRepositoryCallback.DeleteEatCallback
    )

    fun updateEat(
        time: String,
        type: Int,
        memo: String,
        data: EatEntity,
        callback: EatRepositoryCallback.UpdateEatCallback
    )

}