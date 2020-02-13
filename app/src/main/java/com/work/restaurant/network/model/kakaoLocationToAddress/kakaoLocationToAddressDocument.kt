package com.work.restaurant.network.model.kakaoLocationToAddress

import com.google.gson.annotations.SerializedName
import com.work.restaurant.data.model.KakaoLocationToAddressModel

data class KakaoLocationToAddressDocument(
    @SerializedName("address_name")
    val addressName: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("region_1depth_name")
    val region1depthName: String,
    @SerializedName("region_2depth_name")
    val region2depthName: String,
    @SerializedName("region_3depth_name")
    val region3depthName: String,
    @SerializedName("region_4depth_name")
    val region4depthName: String,
    @SerializedName("region_type")
    val regionType: String,
    @SerializedName("x")
    val x: Double,
    @SerializedName("y")
    val y: Double
) {

    fun toKakaoLocationToAddressModel(): KakaoLocationToAddressModel =
        KakaoLocationToAddressModel(addressName)

}