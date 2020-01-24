package com.work.restaurant.data.source.local.eat

import com.work.restaurant.network.model.EatResponse
import com.work.restaurant.network.room.entity.EatEntity

interface EatLocalDataSourceCallback {

    interface AddEatCallback {
        fun onSuccess(msg: String)
        fun onFailure(msg: String)

    }

    interface DeleteEatCallback {

        fun onSuccess()
        fun onFailure(msg: String)

    }

    interface GetDataOfTheDay {

        fun onSuccess(): EatEntity
        fun onFailure(msg: String)

    }

    interface GetAllList {

        fun onSuccess(list: List<EatResponse>)
        fun onFailure(msg: String)

    }


}