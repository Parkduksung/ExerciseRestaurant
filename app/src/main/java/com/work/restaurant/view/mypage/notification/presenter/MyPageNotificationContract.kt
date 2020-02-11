package com.work.restaurant.view.mypage.notification.presenter

import com.work.restaurant.data.model.NotificationModel

interface MyPageNotificationContract {


    interface View {

        fun showNotificationList(list: List<NotificationModel>)
    }

    interface Presenter {

        fun getNotificationList()
    }
}