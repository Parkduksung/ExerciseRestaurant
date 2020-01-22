package com.work.restaurant.data.repository.eat

interface EatRepositoryCallback {

    interface AddEatCallback {
        fun onSuccess(msg: String)
        fun onFailure(msg: String)

    }
}