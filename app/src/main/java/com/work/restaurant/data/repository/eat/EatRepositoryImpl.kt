package com.work.restaurant.data.repository.eat

import com.work.restaurant.data.source.local.eat.EatLocalDataSourceCallback
import com.work.restaurant.data.source.local.eat.EatLocalDataSourceImpl

class EatRepositoryImpl private constructor(
    private val eatLocalDataSourceImpl: EatLocalDataSourceImpl
) : EatRepository {
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
                override fun onSuccess(msg: String) {
                    callback.onSuccess(msg)
                }

                override fun onFailure(msg: String) {
                    callback.onFailure(msg)
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
