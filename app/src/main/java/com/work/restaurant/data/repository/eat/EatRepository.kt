package com.work.restaurant.data.repository.eat

interface EatRepository {

    fun addEat(
        date: String,
        time: String,
        type: Int,
        memo: String,
        callback: EatRepositoryCallback.AddEatCallback
    )
}