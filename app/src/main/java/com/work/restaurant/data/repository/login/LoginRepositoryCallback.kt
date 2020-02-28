package com.work.restaurant.data.repository.login

import com.work.restaurant.network.room.entity.LoginEntity

interface LoginRepositoryCallback {


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

}