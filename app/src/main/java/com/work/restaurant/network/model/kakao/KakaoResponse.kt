package com.work.restaurant.network.model.kakao

import com.google.gson.annotations.SerializedName

data class KakaoResponse(
    @SerializedName("documents")
    val documents: List<Documents>,
    @SerializedName("meta")
    val meta: Meta
)

