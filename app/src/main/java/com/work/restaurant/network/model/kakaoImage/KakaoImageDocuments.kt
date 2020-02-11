package com.work.restaurant.network.model.kakaoImage

import com.google.gson.annotations.SerializedName

data class KakaoImageDocuments(
    @SerializedName("collection")
    val collection: String,
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("width")
    val width: String,
    @SerializedName("height")
    val height: String,
    @SerializedName("display_sitename")
    val displaySiteName: String,
    @SerializedName("doc_Url")
    val docUrl: String
)