package com.work.restaurant.view.mypage.notification.presenter

import com.work.restaurant.data.repository.notification.NotificationRepository

class MyPageNotificationPresenter(
    private val notificationView: MyPageNotificationContract.View,
    private val notificationRepository: NotificationRepository
) :
    MyPageNotificationContract.Presenter {
    override fun getNotificationList() {
        notificationRepository.getNotificationData(
            callback = { notificationList ->
                if (notificationList != null) {
                    val toNotificationModel =
                        notificationList.map {
                            it.toNotificationModel()
                        }
                    notificationView.showNotificationList(toNotificationModel)
                }
            })
    }
}