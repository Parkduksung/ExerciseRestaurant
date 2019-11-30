package com.work.restaurant.network.model

import com.google.gson.annotations.SerializedName

data class FitnessCenterItemModel(
    @SerializedName("fc_no")
    val fitnessCenterNo: Int,
    @SerializedName("fc_name")
    val fitnessCenterName: String,
    @SerializedName("fc_price")
    val fitnessCenterPrice: String,
    @SerializedName("fc_time")
    val fitnessCenterTime: String,
    @SerializedName("fc_best_part")
    val fitnessCenterBestPart: String,
    @SerializedName("fc_like_count")
    val fitnessCenterLikeCount: Int,
    @SerializedName("fc_parking")
    val fitnessCenterParking: String,
    @SerializedName("fc_calling")
    val fitnessCenterCalling: String,
    @SerializedName("fc_image")
    val fitnessCenterImage: String
)