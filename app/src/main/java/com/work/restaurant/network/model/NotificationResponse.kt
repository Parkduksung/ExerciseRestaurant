package com.work.restaurant.network.model

import com.google.gson.annotations.SerializedName
import com.work.restaurant.data.model.NotificationModel

data class NotificationResponse(
    @SerializedName("notification_no")
    val notificationNo: Int,
    @SerializedName("notification_subject")
    val notificationSubject: String,
    @SerializedName("notification_content")
    val notificationContent: String,
    @SerializedName("notification_date")
    val notificationDate: String
) {
    fun toNotificationModel(): NotificationModel =
        NotificationModel(
            notificationNo,
            notificationSubject,
            notificationContent,
            notificationDate
        )
}
