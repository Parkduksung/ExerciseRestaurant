package com.work.restaurant.data.repository.notification

interface NotificationRepository {
    fun getNotificationData(
        callback: NotificationRepositoryCallback
    )

}