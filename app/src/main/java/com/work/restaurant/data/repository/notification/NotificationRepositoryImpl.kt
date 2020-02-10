package com.work.restaurant.data.repository.notification

import com.work.restaurant.data.source.remote.notification.NotificationRemoteDataSourceCallback
import com.work.restaurant.data.source.remote.notification.NotificationRemoteDataSourceImpl
import com.work.restaurant.network.model.NotificationResponse

class NotificationRepositoryImpl(
    private val notificationRemoteDataSourceImpl: NotificationRemoteDataSourceImpl
) : NotificationRepository {
    override fun getNotificationData(callback: NotificationRepositoryCallback) {


        notificationRemoteDataSourceImpl.getNotificationData(object :
            NotificationRemoteDataSourceCallback {
            override fun onFailure(message: String) {
                callback.onFailure(message)
            }

            override fun onSuccess(notificationList: List<NotificationResponse>) {
                callback.onSuccess(notificationList)
            }
        })
    }


    companion object {

        private var instance: NotificationRepositoryImpl? = null
        fun getInstance(
            notificationRemoteDataSourceImpl: NotificationRemoteDataSourceImpl
        ): NotificationRepositoryImpl =
            instance ?: NotificationRepositoryImpl(notificationRemoteDataSourceImpl).also {
                instance = it
            }

    }

}