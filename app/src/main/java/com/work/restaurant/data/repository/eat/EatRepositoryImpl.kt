package com.work.restaurant.data.repository.eat

import com.work.restaurant.data.source.local.eat.EatLocalDataSourceImpl
import com.work.restaurant.network.room.entity.EatEntity

class EatRepositoryImpl(
    private val eatLocalDataSourceImpl: EatLocalDataSourceImpl
) : EatRepository {
    override fun updateEat(
        time: String,
        type: Int,
        memo: String,
        data: EatEntity,
        callback: (Boolean) -> Unit
    ) {
        eatLocalDataSourceImpl.updateEat(
            time,
            type,
            memo,
            data,
            callback
        )
    }

    override fun deleteEat(data: EatEntity, callback: (Boolean) -> Unit) {
        eatLocalDataSourceImpl.deleteEat(
            data,
            callback
        )
    }

    override fun getDataOfTheDay(
        userId: String,
        today: String,
        callback: (List<EatEntity>) -> Unit
    ) {
        eatLocalDataSourceImpl.getDataOfTheDay(
            userId,
            today,
            callback
        )
    }


    override fun getList(userId: String, callback: (List<EatEntity>) -> Unit) {
        eatLocalDataSourceImpl.getAllList(userId, callback)
    }


    override fun addEat(
        userId: String,
        date: String,
        time: String,
        type: Int,
        memo: String,
        callback: (Boolean) -> Unit
    ) {
        eatLocalDataSourceImpl.addEat(
            userId,
            date,
            time,
            type,
            memo,
            callback
        )
    }

}
