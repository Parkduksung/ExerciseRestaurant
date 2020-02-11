package com.work.restaurant.data.source.remote.notification

import com.work.restaurant.network.model.NotificationResponse

interface NotificationRemoteDataSourceCallback {
    fun onSuccess(notificationList: List<NotificationResponse>)
    fun onFailure(message: String)
}