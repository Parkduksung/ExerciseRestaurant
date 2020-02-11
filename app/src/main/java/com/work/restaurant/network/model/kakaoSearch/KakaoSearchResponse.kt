package com.work.restaurant.network.model.kakaoSearch

import com.google.gson.annotations.SerializedName

data class KakaoSearchResponse(
    @SerializedName("documents")
    val documents: List<KakaoSearchDocuments>,
    @SerializedName("meta")
    val kakaoSearchMeta: KakaoSearchMeta
)

