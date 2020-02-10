package com.work.restaurant.network.model.kakaoAddress

data class KakaoAddressDocument(
    val address: KakaoAddress,
    val address_name: String,
    val address_type: String,
    val road_address: KakaoRoadAddress,
    val x: String,
    val y: String
)