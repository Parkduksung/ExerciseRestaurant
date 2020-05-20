package com.work.restaurant.data.repository.notification

import com.work.restaurant.data.source.remote.notification.NotificationRemoteDataSource
import com.work.restaurant.network.model.NotificationResponse

class NotificationRepositoryImpl(
    private val notificationRemoteDataSource: NotificationRemoteDataSource
) : NotificationRepository {
    override fun getNotificationData(callback: (notificationList: List<NotificationResponse>?) -> Unit) {
        notificationRemoteDataSource.getNotificationData(callback)
    }
}