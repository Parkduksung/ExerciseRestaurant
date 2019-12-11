package com.work.restaurant.view.search.contract

import com.work.restaurant.network.model.FitnessCenterItemResponse

interface SearchItemContract {
    interface View {

        fun showItemInfoDetail(fitnessList: FitnessCenterItemResponse)

        fun showCall(callNum: String)
    }

    interface Presenter {

        fun itemInfoDetail(searchItem: String)


        fun call(callNum: String)

    }
}