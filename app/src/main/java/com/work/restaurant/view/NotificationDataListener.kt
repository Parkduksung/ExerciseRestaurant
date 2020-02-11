package com.work.restaurant.view

import com.work.restaurant.data.model.NotificationModel

interface NotificationDataListener {
        fun getNotificationData(data: NotificationModel)
    }