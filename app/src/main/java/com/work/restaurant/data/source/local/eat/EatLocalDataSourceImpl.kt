package com.work.restaurant.data.source.local.eat

import com.work.restaurant.network.room.database.EatDatabase
import com.work.restaurant.network.room.entity.EatEntity
import com.work.restaurant.util.AppExecutors

class EatLocalDataSourceImpl(
    private val appExecutors: AppExecutors,
    private val eatDatabase: EatDatabase
) : EatLocalDataSource {


    override fun getDataOfTheDay(
        date: String,
        callback: EatLocalDataSourceCallback.GetDataOfTheDay
    ) {

    }


    override fun deleteEat(date: String, callback: EatLocalDataSourceCallback.DeleteEatCallback) {

    }


    override fun addEat(
        date: String,
        time: String,
        type: Int,
        memo: String,
        callback: EatLocalDataSourceCallback.AddEatCallback
    ) {
        appExecutors.diskIO.execute {

            val eatEntity = EatEntity(date = date, time = time, type = type, memo = memo)

            val registerEat = eatDatabase.eatDao().registerEat(eatEntity)



            registerEat.takeIf { true }
                .apply {
                    callback.onSuccess("success")
                } ?: callback.onFailure("error")

        }

    }


    companion object {

        private var instance: EatLocalDataSourceImpl? = null

        fun getInstance(
            appExecutors: AppExecutors,
            eatDatabase: EatDatabase
        ): EatLocalDataSourceImpl =
            instance ?: EatLocalDataSourceImpl(
                appExecutors,
                eatDatabase
            ).also {
                instance = it
            }
    }
}

