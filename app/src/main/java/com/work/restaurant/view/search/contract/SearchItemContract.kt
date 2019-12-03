package com.work.restaurant.view.search.contract

import com.work.restaurant.network.model.FitnessCenterItemModel

interface SearchItemContract {
    interface View {

        fun showItemInfoDetail(fitnessList: FitnessCenterItemModel)

    }

    interface Presenter {

        fun itemInfoDetail(searchItem: String)

    }
}