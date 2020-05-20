package com.work.restaurant.data.source.remote.notification

import com.work.restaurant.network.api.NotificationApi
import com.work.restaurant.network.model.NotificationResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationRemoteDataSourceImpl(
    private val notificationApi: NotificationApi
) : NotificationRemoteDataSource {
    override fun getNotificationData(callback: (notificationList: List<NotificationResponse>?) -> Unit) {
        notificationApi.notificationAllItem().enqueue(object :
            Callback<List<NotificationResponse>> {
            override fun onFailure(call: Call<List<NotificationResponse>>?, t: Throwable?) {
                callback(null)
            }

            override fun onResponse(
                call: Call<List<NotificationResponse>>?,
                response: Response<List<NotificationResponse>>?
            ) {

                if (response != null) {
                    if (response.isSuccessful) {
                        val list = response.body()
                        callback(list)
                    } else {
                        callback(null)
                    }
                }
            }
        })
    }
}