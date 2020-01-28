package com.work.restaurant.network.model

import com.google.gson.annotations.SerializedName

data class KakaoResponse(
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("documents")
    val documents: Documents
)