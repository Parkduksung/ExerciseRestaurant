package com.work.restaurant.data.repository.eat

import com.work.restaurant.network.room.entity.EatEntity

interface EatRepositoryCallback {

    interface AddEatCallback {
        fun onSuccess()
        fun onFailure()

    }


    interface GetAllList {
        fun onSuccess(list: List<EatEntity>)
        fun onFailure()
    }

    interface GetDataOfTheDay {
        fun onSuccess(list: List<EatEntity>)
        fun onFailure()
    }

    interface DeleteEatCallback {
        fun onSuccess()
        fun onFailure()

    }

}