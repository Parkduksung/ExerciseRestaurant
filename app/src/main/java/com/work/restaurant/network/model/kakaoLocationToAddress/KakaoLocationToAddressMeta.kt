package com.work.restaurant.network.model.kakaoLocationToAddress

import com.google.gson.annotations.SerializedName

data class KakaoLocationToAddressMeta(
    @SerializedName("total_count")
    val totalCount: Int
)