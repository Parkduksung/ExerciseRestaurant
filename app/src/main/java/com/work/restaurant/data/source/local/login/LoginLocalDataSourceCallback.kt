package com.work.restaurant.data.source.local.login

import com.work.restaurant.network.room.entity.LoginEntity

interface LoginLocalDataSourceCallback {

    interface LoginStateCallback {
        fun onSuccess(list: LoginEntity)
        fun onFailure()
    }


    interface RegisterCallback {
        fun onSuccess()
        fun onFailure()
    }

    interface ChangeState {
        fun onSuccess()
        fun onFailure()
    }

    interface DeleteCallback {
        fun onSuccess()
        fun onFailure()
    }

    interface FindUser {
        fun onSuccess()
        fun onFailure()
    }


}