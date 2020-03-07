package com.work.restaurant.data.source.local.eat

import com.work.restaurant.network.room.entity.EatEntity

interface EatLocalDataSourceCallback {

    interface AddEatCallback {
        fun onSuccess()
        fun onFailure()

    }

    interface DeleteEatCallback {
        fun onSuccess()
        fun onFailure()
    }

    interface UpdateEatCallback {
        fun onSuccess()
        fun onFailure()
    }

    interface GetDataOfTheDay {
        fun onSuccess(list: List<EatEntity>)
        fun onFailure()
    }

    interface GetAllList {

        fun onSuccess(list: List<EatEntity>)
        fun onFailure()

    }


}