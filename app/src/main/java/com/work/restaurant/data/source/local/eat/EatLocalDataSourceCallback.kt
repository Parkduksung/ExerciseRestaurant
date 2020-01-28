package com.work.restaurant.data.source.local.eat

import com.work.restaurant.network.room.entity.EatEntity

interface EatLocalDataSourceCallback {

    interface AddEatCallback {
        fun onSuccess(msg: String)
        fun onFailure(msg: String)

    }

    interface DeleteEatCallback {

        fun onSuccess(msg: String)
        fun onFailure(msg: String)

    }

    interface GetDataOfTheDay {

        fun onSuccess(list: List<EatEntity>)
        fun onFailure(msg: String)

    }

    interface GetAllList {

        fun onSuccess(list: List<EatEntity>)
        fun onFailure(msg: String)

    }


}