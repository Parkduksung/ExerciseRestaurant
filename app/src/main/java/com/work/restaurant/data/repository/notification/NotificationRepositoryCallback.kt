package com.work.restaurant.data.repository.notification

import com.work.restaurant.network.model.NotificationResponse

interface NotificationRepositoryCallback {
    fun onSuccess(notificationList: List<NotificationResponse>)
    fun onFailure(message: String)
}