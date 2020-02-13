package com.work.restaurant.network.model.kakaoLocationToAddress

import com.google.gson.annotations.SerializedName


data class KakaoLocationToAddressResponse(
    @SerializedName("documents")
    val documents: List<KakaoLocationToAddressDocument>,
    @SerializedName("meta")
    val meta: KakaoLocationToAddressMeta
)