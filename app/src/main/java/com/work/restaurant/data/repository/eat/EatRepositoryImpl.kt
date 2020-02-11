package com.work.restaurant.data.repository.eat

import com.work.restaurant.data.source.local.eat.EatLocalDataSourceCallback
import com.work.restaurant.data.source.local.eat.EatLocalDataSourceImpl
import com.work.restaurant.network.room.entity.EatEntity

class EatRepositoryImpl private constructor(
    private val eatLocalDataSourceImpl: EatLocalDataSourceImpl
) : EatRepository {
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

    override fun getDataOfTheDay(today: String, callback: EatRepositoryCallback.GetDataOfTheDay) {
        eatLocalDataSourceImpl.getDataOfTheDay(
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


    override fun getList(callback: EatRepositoryCallback.GetAllList) {
        eatLocalDataSourceImpl.getAllList(object : EatLocalDataSourceCallback.GetAllList {
            override fun onSuccess(list: List<EatEntity>) {
                callback.onSuccess(list)
            }

            override fun onFailure() {
                callback.onFailure()
            }
        })

    }


    override fun addEat(
        date: String,
        time: String,
        type: Int,
        memo: String,
        callback: EatRepositoryCallback.AddEatCallback
    ) {
        eatLocalDataSourceImpl.addEat(
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
