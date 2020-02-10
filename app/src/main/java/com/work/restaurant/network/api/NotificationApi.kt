package com.work.restaurant.network.api

import com.work.restaurant.network.model.NotificationResponse
import retrofit2.Call
import retrofit2.http.GET

interface NotificationApi {
    @GET("/Notification.php")
    fun notificationAllItem(): Call<List<NotificationResponse>>
}