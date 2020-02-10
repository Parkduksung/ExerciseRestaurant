package com.work.restaurant.view.mypage.notification.presenter

import com.work.restaurant.data.repository.notification.NotificationRepository
import com.work.restaurant.data.repository.notification.NotificationRepositoryCallback
import com.work.restaurant.network.model.NotificationResponse

class MyPageNotificationPresenter(
    private val notificationView: MyPageNotificationContract.View,
    private val notificationRepository: NotificationRepository
) :
    MyPageNotificationContract.Presenter {
    override fun getNotificationList() {

        notificationRepository.getNotificationData(object : NotificationRepositoryCallback {
            override fun onSuccess(notificationList: List<NotificationResponse>) {

                val toNotificationModel = notificationList.map {
                    it.toNotificationModel()
                }
                notificationView.showNotificationList(toNotificationModel)
            }

            override fun onFailure(message: String) {

            }
        })


    }


}