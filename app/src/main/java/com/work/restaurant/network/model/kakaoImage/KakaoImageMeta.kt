package com.work.restaurant.network.model.kakaoImage

import com.google.gson.annotations.SerializedName

data class KakaoImageMeta(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("pageable_count")
    val pageableCount: Int,
    @SerializedName("is_end")
    val isEnd: Boolean
)