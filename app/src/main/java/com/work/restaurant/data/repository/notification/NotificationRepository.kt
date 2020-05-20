package com.work.restaurant.data.repository.notification

import com.work.restaurant.network.model.NotificationResponse

interface NotificationRepository {
    fun getNotificationData(
        callback: (notificationList: List<NotificationResponse>?) -> Unit
    )
}