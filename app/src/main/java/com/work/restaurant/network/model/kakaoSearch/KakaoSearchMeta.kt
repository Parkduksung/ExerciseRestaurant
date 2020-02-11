package com.work.restaurant.network.model.kakaoSearch

import com.google.gson.annotations.SerializedName

data class KakaoSearchMeta(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("pageable_count")
    val pageableCount: Int,
    @SerializedName("is_end")
    val isEnd: Boolean,
    @SerializedName("same_name")
    val sameName: SameName
)