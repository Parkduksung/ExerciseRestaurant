package com.work.restaurant.data.repository.eat

import com.work.restaurant.data.source.local.eat.EatLocalDataSource
import com.work.restaurant.db.room.entity.EatEntity

class EatRepositoryImpl(
    private val eatLocalDataSource: EatLocalDataSource
) : EatRepository {
    override fun updateEat(
        time: String,
        type: Int,
        memo: String,
        data: EatEntity,
        callback: (isSuccess: Boolean) -> Unit
    ) {
        eatLocalDataSource.updateEat(
            time,
            type,
            memo,
            data,
            callback
        )
    }

    override fun deleteEat(data: EatEntity, callback: (isSuccess: Boolean) -> Unit) {
        eatLocalDataSource.deleteEat(
            data,
            callback
        )
    }

    override fun getDataOfTheDay(
        userId: String,
        today: String,
        callback: (getList: List<EatEntity>) -> Unit
    ) {
        eatLocalDataSource.getDataOfTheDay(
            userId,
            today,
            callback
        )
    }


    override fun getList(userId: String, callback: (getList: List<EatEntity>) -> Unit) {
        eatLocalDataSource.getAllList(userId, callback)
    }


    override fun addEat(
        userId: String,
        date: String,
        time: String,
        type: Int,
        memo: String,
        callback: (isSuccess: Boolean) -> Unit
    ) {
        eatLocalDataSource.addEat(
            userId,
            date,
            time,
            type,
            memo,
            callback
        )
    }

}
