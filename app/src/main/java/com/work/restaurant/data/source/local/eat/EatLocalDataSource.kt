package com.work.restaurant.data.source.local.eat

interface EatLocalDataSource {

    fun addEat(
        date: String,
        time: String,
        type: Int,
        memo: String,
        callback: EatLocalDataSourceCallback.AddEatCallback
    )

    fun deleteEat(
        date: String,
        callback: EatLocalDataSourceCallback.DeleteEatCallback
    )

    fun getDataOfTheDay(
        date: String,
        callback: EatLocalDataSourceCallback.GetDataOfTheDay
    )

}
