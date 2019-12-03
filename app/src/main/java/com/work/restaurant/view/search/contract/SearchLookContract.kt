package com.work.restaurant.view.search.contract

import com.work.restaurant.network.model.FitnessCenterItemModel

interface SearchLookContract {

    interface View {


        fun showSearchLook(fitnessList: MutableList<FitnessCenterItemModel>)

        fun showSearchNoFind()

        fun showBackPage()

    }

    interface Presenter {

        fun searchLook(searchItem: String)

        fun backPage()


    }
}