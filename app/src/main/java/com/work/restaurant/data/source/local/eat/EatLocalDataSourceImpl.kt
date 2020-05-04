package com.work.restaurant.data.source.local.eat

import com.work.restaurant.network.room.database.EatDatabase
import com.work.restaurant.network.room.entity.EatEntity
import com.work.restaurant.util.AppExecutors

class EatLocalDataSourceImpl(
    private val appExecutors: AppExecutors,
    private val eatDatabase: EatDatabase
) : EatLocalDataSource {
    override fun updateEat(
        time: String,
        type: Int,
        memo: String,
        data: EatEntity,
        callback: (Boolean) -> Unit
    ) {

        appExecutors.diskIO.execute {

            val updateEat =
                eatDatabase.eatDao().updateEat(time, type, memo, data.userId, data.eatNum)

            appExecutors.mainThread.execute {
                if (updateEat == 1) {
                    callback(true)
                } else {
                    callback(false)
                }
            }
        }
    }

    override fun deleteEat(
        data: EatEntity,
        callback: (Boolean) -> Unit
    ) {
        appExecutors.diskIO.execute {

            val deleteEat =
                eatDatabase.eatDao().deleteEat(data)

            appExecutors.mainThread.execute {
                if (deleteEat >= 1) {
                    callback(true)
                } else {
                    callback(false)
                }
            }

        }
    }

    override fun getAllList(userId: String, callback: (List<EatEntity>) -> Unit) {

        appExecutors.diskIO.execute {

            val getAllList =
                eatDatabase.eatDao().getAll(userId)

            appExecutors.mainThread.execute {
                callback(getAllList)
            }

        }


    }


    override fun getDataOfTheDay(
        userId: String,
        date: String,
        callback: (List<EatEntity>) -> Unit
    ) {

        appExecutors.diskIO.execute {

            val getDataOfTheDay =
                eatDatabase.eatDao().getTodayItem(userId, date)

            appExecutors.mainThread.execute {
                callback(getDataOfTheDay)
            }

        }


    }

    override fun addEat(
        userId: String,
        date: String,
        time: String,
        type: Int,
        memo: String,
        callback: (Boolean) -> Unit
    ) {
        appExecutors.diskIO.execute {

            val eatEntity =
                EatEntity(userId = userId, date = date, time = time, type = type, memo = memo)

            val registerEat =
                eatDatabase.eatDao().registerEat(eatEntity)


            appExecutors.mainThread.execute {
                if (registerEat >= 1) {
                    callback(true)
                } else {
                    callback(false)
                }
            }

        }

    }
}

