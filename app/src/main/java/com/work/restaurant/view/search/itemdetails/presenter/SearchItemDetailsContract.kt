package com.work.restaurant.view.search.itemdetails.presenter

import com.work.restaurant.network.model.FitnessCenterItemResponse

interface SearchItemDetailsContract {
    interface View {

        fun showItemInfoDetail(fitnessList: FitnessCenterItemResponse)

        fun showCall(callNum: String)
    }

    interface Presenter {

        fun itemInfoDetail(searchItem: String)


        fun call(callNum: String)

    }
}