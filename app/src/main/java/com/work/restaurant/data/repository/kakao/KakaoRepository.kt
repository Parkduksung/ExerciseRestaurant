package com.work.restaurant.data.repository.kakao

interface KakaoRepository {
    fun getKakaoResult(
        currentX: Double,
        currentY: Double,
        callback: KakaoRepositoryCallback
    )
}