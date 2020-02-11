package com.work.restaurant.view.mypage.notification

import com.work.restaurant.data.model.NotificationModel

interface NotificationDataListener {
        fun getNotificationData(data: NotificationModel)
    }