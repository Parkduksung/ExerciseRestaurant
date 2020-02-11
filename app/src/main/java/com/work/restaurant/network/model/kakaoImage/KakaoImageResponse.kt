package com.work.restaurant.network.model.kakaoImage

import com.google.gson.annotations.SerializedName

data class KakaoImageResponse(
    @SerializedName("documents")
    val kakaoImageDocuments: List<KakaoImageDocuments>,
    @SerializedName("meta")
    val kakaoImageMeta: KakaoImageMeta
)
