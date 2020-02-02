package com.work.restaurant.view.adapter

import com.work.restaurant.data.model.DiaryModel

interface AdapterDataListener {
    fun getData(data: String)

    interface GetList {
        fun getData(data: DiaryModel)
    }

    interface GetKakaoData {

        fun getKakaoData(select: Int, data: String)

    }

}