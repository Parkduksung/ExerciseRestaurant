package com.work.restaurant.data.repository.kakao

interface KakaoRepository {
    fun getKakaoResult(
        currentX: Double,
        currentY: Double,
        page: Int,
        sort: String,
        callback: KakaoRepositoryCallback
    )

    fun getKakaoItemInfo(
        placeName: String,
        callback: KakaoRepositoryCallback.KakaoItemInfoCallback
    )

    fun getKakaoAddressLocation(
        addressName: String,
        callback: KakaoRepositoryCallback.KakaoAddressCallback
    )

    fun getKakaoLocationToAddress(
        currentX: Double,
        currentY: Double,
        callback: KakaoRepositoryCallback.KakaoLocationToAddress
    )


}