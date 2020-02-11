package com.work.restaurant.network.model.kakaoSearch

import com.google.gson.annotations.SerializedName

data class SameName(
    @SerializedName("keyword")
    val keyword: String,
    @SerializedName("selected_region")
    val selectRegion: String,
    @SerializedName("region")
    val region: List<String>
)