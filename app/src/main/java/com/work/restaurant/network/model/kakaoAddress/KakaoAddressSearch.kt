package com.work.restaurant.network.model.kakaoAddress

import com.google.gson.annotations.SerializedName

data class KakaoAddressSearch(
    @SerializedName("documents")
    val documents: List<KakaoAddressDocument>,
    @SerializedName("meta")
    val meta: KakaoAddressMeta
)

