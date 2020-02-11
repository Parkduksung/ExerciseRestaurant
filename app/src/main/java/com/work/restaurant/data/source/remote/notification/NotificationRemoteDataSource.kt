package com.work.restaurant.data.source.remote.notification

interface NotificationRemoteDataSource {

    fun getNotificationData(
        callback: NotificationRemoteDataSourceCallback
    )

}