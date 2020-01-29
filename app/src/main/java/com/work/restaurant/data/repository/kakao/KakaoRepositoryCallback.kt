package com.work.restaurant.data.repository.kakao

import com.work.restaurant.network.model.kakao.Documents

interface KakaoRepositoryCallback {

    fun onSuccess(kakaoList: List<Documents>)

    fun onFailure(message: String)
}