package com.work.restaurant.data.source.local.eat

import android.util.Log
import com.work.restaurant.network.room.database.EatDatabase
import com.work.restaurant.network.room.entity.EatEntity
import com.work.restaurant.util.AppExecutors

class EatLocalDataSourceImpl(
    private val appExecutors: AppExecutors,
    private val eatDatabase: EatDatabase
) : EatLocalDataSource {

    override fun deleteEat(
        data: EatEntity,
        callback: EatLocalDataSourceCallback.DeleteEatCallback
    ) {
        appExecutors.diskIO.execute {

            val deleteEat = eatDatabase.eatDao().deleteEat(data)

            Log.d("제거됬니?", deleteEat.toString())

            if (deleteEat >= 1) {
                callback.onSuccess("success")
            } else {
                callback.onSuccess("error")
            }

        }
    }

    override fun getAllList(callback: EatLocalDataSourceCallback.GetAllList) {

        appExecutors.diskIO.execute {

            val getAllList =
                eatDatabase.eatDao().getAll()

            getAllList.forEach {
                Log.d("결콰콰", it.date)
                Log.d("결콰콰", it.memo)
                Log.d("결콰콰", it.time)
                Log.d("결콰콰", "${it.type}")
            }


            getAllList.takeIf { true }
                .apply {
                    callback.onSuccess(getAllList)
                } ?: callback.onFailure("error")

        }


    }


    override fun getDataOfTheDay(
        date: String,
        callback: EatLocalDataSourceCallback.GetDataOfTheDay
    ) {

        appExecutors.diskIO.execute {

            val getDataOfTheDay = eatDatabase.eatDao().getTodayItem(date)

            getDataOfTheDay.takeIf { true }
                .apply {
                    callback.onSuccess(getDataOfTheDay)
                } ?: callback.onFailure("error")

        }


    }


    override fun addEat(
        date: String,
        time: String,
        type: Int,
        memo: String,
        callback: EatLocalDataSourceCallback.AddEatCallback
    ) {
        appExecutors.diskIO.execute {

            val eatEntity =
                EatEntity(date = date, time = time, type = type, memo = memo)


            val registerEat = eatDatabase.eatDao().registerEat(eatEntity)

            if (registerEat >= 1) {
                callback.onSuccess("success")
            } else {
                callback.onSuccess("error")
            }
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

