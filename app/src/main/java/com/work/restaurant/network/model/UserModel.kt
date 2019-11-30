package com.work.restaurant.network.model

import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("user_no")
    val userNo: Int,
    @SerializedName("user_nickname")
    val userNickName: String,
    @SerializedName("user_email")
    val userEmail: String,
    @SerializedName("user_pass")
    val userPass: String
)