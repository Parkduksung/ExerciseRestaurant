package com.work.restaurant.network.model.kakaoSearch

import com.google.gson.annotations.SerializedName
import com.work.restaurant.data.model.KakaoModel

data class KakaoSearchDocuments(
    @SerializedName("address_name")
    val addressName: String,
    @SerializedName("category_group_code")
    val categoryGroupCode: String,
    @SerializedName("category_group_name")
    val categoryGroupName: String,
    @SerializedName("category_name")
    val categoryName: String,
    @SerializedName("distance")
    val distance: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("place_name")
    val placeName: String,
    @SerializedName("place_url")
    val placeUrl: String,
    @SerializedName("road_address_name")
    val roadAddressName: String,
    @SerializedName("x")
    val locationX: String,
    @SerializedName("y")
    val locationY: String
) {
    fun toKakaoModel(): KakaoModel =
        KakaoModel(
            addressName,
            distance,
            phone,
            placeName,
            placeUrl,
            locationX,
            locationY
        )
}
