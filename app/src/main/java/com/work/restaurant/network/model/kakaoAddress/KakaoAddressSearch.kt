package com.work.restaurant.network.model.kakaoAddress

data class KakaoAddressSearch(
    val documents: List<KakaoAddressDocument>,
    val meta: KakaoAddressMeta
)