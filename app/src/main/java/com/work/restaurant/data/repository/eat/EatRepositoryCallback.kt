package com.work.restaurant.data.repository.eat

import com.work.restaurant.network.model.EatResponse

interface EatRepositoryCallback {

    interface AddEatCallback {
        fun onSuccess(msg: String)
        fun onFailure(msg: String)

    }


    interface GetAllList{
        fun onSuccess(list: List<EatResponse>)
        fun onFailure(msg: String)
    }

}