package com.work.restaurant.network.model.kakaoAddress

import com.google.gson.annotations.SerializedName

data class KakaoAddressDocument(
    @SerializedName("address")
    val address: KakaoAddress,
    @SerializedName("address_name")
    val addressName: String,
    @SerializedName("address_type")
    val addressType: String,
    @SerializedName("road_address")
    val roadAddress: Any,
    @SerializedName("x")
    val x: String,
    @SerializedName("y")
    val y: String
)