package com.work.restaurant.view.home.daum_maps.presenter

import com.work.restaurant.network.model.Documents

interface MapContract {

    interface View {
        fun showKakaoData(list: List<Documents>)
    }


    interface Presenter {

        fun getKakaoData()
    }
}