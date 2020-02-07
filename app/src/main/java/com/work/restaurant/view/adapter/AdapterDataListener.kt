package com.work.restaurant.view.adapter

import com.work.restaurant.data.model.BookmarkModel
import com.work.restaurant.data.model.DiaryModel
import com.work.restaurant.data.model.KakaoSearchModel

interface AdapterDataListener {
    fun getData(data: String)

    interface GetList {
        fun getData(data: DiaryModel)
    }

    interface GetKakaoData {
        fun getKakaoData(select: Int, data: KakaoSearchModel)

    }

    interface GetBookmarkData {

        fun getBookmarkData(select: Int, data: BookmarkModel)
    }


}