package com.work.restaurant.data.repository.kakao

import com.work.restaurant.network.model.Documents

interface KakaoRepositoryCallback {

    fun onSuccess(fitnessList: List<Documents>)

    fun onFailure(message: String)
}