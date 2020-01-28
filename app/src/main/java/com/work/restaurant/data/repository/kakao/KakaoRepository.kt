package com.work.restaurant.data.repository.kakao

interface KakaoRepository {
    fun getKakaoResult(callback: KakaoRepositoryCallback)
}