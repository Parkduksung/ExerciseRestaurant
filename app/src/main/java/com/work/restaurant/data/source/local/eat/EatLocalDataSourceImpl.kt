package com.work.restaurant.data.source.local.eat

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

            appExecutors.mainThread.execute {
                if (deleteEat >= 1) {
                    callback.onSuccess()
                } else {
                    callback.onSuccess()
                }

            }

        }
    }

    override fun getAllList(callback: EatLocalDataSourceCallback.GetAllList) {

        appExecutors.diskIO.execute {

            val getAllList =
                eatDatabase.eatDao().getAll()


            appExecutors.mainThread.execute {
                getAllList.takeIf { true }
                    .apply {
                        callback.onSuccess(getAllList)
                    } ?: callback.onFailure()
            }

        }


    }


    override fun getDataOfTheDay(
        date: String,
        callback: EatLocalDataSourceCallback.GetDataOfTheDay
    ) {

        appExecutors.diskIO.execute {

            val getDataOfTheDay = eatDatabase.eatDao().getTodayItem(date)

            appExecutors.mainThread.execute {
                getDataOfTheDay.takeIf { true }
                    .apply {
                        callback.onSuccess(getDataOfTheDay)
                    } ?: callback.onFailure()
            }

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


            appExecutors.mainThread.execute {
                if (registerEat >= 1) {
                    callback.onSuccess()
                } else {
                    callback.onSuccess()
                }
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

