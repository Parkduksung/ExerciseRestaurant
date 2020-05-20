package com.work.restaurant.data.source.remote.notification

import com.work.restaurant.network.model.NotificationResponse

interface NotificationRemoteDataSource {
    fun getNotificationData(
        callback: (notificationList: List<NotificationResponse>?) -> Unit
    )
}