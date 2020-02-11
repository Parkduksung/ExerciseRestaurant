package com.work.restaurant.network.model.kakaoAddress

import com.google.gson.annotations.SerializedName

data class KakaoAddressMeta(
    @SerializedName("is_end")
    val isEnd: Boolean,
    @SerializedName("pageable_count")
    val pageableCount: Int,
    @SerializedName("total_count")
    val totalCount: Int
)