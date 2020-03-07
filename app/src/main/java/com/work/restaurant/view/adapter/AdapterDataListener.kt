package com.work.restaurant.view.adapter

import com.work.restaurant.data.model.BookmarkModel
import com.work.restaurant.data.model.DiaryModel
import com.work.restaurant.data.model.DisplayBookmarkKakaoModel
import com.work.restaurant.data.model.NotificationModel

interface AdapterDataListener {
    fun getData(data: String)

    interface GetList {
        fun getData(data: DiaryModel)
    }



    interface GetDisplayBookmarkKakaoModel {
        fun getDisplayBookmarkKakaoData(select: Int, data: DisplayBookmarkKakaoModel)
    }


    interface GetBookmarkData {

        fun getBookmarkData(select: Int, data: BookmarkModel)
    }

    interface GetNotificationList {
        fun getData(data: NotificationModel)
    }


}