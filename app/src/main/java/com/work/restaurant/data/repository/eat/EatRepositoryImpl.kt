package com.work.restaurant.data.repository.eat

import com.work.restaurant.data.source.local.eat.EatLocalDataSourceCallback
import com.work.restaurant.data.source.local.eat.EatLocalDataSourceImpl
import com.work.restaurant.network.room.entity.EatEntity

class EatRepositoryImpl private constructor(
    private val eatLocalDataSourceImpl: EatLocalDataSourceImpl
) : EatRepository {
    override fun updateEat(
        time: String,
        type: Int,
        memo: String,
        data: EatEntity,
        callback: EatRepositoryCallback.UpdateEatCallback
    ) {
        eatLocalDataSourceImpl.updateEat(
            time,
            type,
            memo,
            data,
            object : EatLocalDataSourceCallback.UpdateEatCallback {
                override fun onSuccess() {
                    callback.onSuccess()
                }

                override fun onFailure() {
                    callback.onFailure()
                }
            })
    }

    override fun deleteEat(data: EatEntity, callback: EatRepositoryCallback.DeleteEatCallback) {
        eatLocalDataSourceImpl.deleteEat(
            data,
            object : EatLocalDataSourceCallback.DeleteEatCallback {
                override fun onSuccess() {
                    callback.onSuccess()
                }

                override fun onFailure() {
                    callback.onFailure()
                }
            }
        )
    }

    override fun getDataOfTheDay(
        userId: String,
        today: String,
        callback: EatRepositoryCallback.GetDataOfTheDay
    ) {
        eatLocalDataSourceImpl.getDataOfTheDay(
            userId,
            today,
            object : EatLocalDataSourceCallback.GetDataOfTheDay {
                override fun onSuccess(list: List<EatEntity>) {
                    callback.onSuccess(list)
                }

                override fun onFailure() {
                    callback.onFailure()
                }
            })

    }


    override fun getList(userId: String, callback: EatRepositoryCallback.GetAllList) {
        eatLocalDataSourceImpl.getAllList(userId, object : EatLocalDataSourceCallback.GetAllList {
            override fun onSuccess(list: List<EatEntity>) {
                callback.onSuccess(list)
            }

            override fun onFailure() {
                callback.onFailure()
            }
        })

    }


    override fun addEat(
        userId: String,
        date: String,
        time: String,
        type: Int,
        memo: String,
        callback: EatRepositoryCallback.AddEatCallback
    ) {
        eatLocalDataSourceImpl.addEat(
            userId,
            date,
            time,
            type,
            memo,
            object : EatLocalDataSourceCallback.AddEatCallback {
                override fun onSuccess() {
                    callback.onSuccess()
                }

                override fun onFailure() {
                    callback.onFailure()
                }

            })
    }

    companion object {

        private var instance: EatRepositoryImpl? = null

        fun getInstance(
            eatLocalDataSourceImpl: EatLocalDataSourceImpl
        ): EatRepository =
            instance ?: EatRepositoryImpl(eatLocalDataSourceImpl)
                .also {
                    instance = it
                }

    }


}
