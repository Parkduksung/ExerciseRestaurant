package com.work.restaurant.view.search

import com.work.restaurant.network.model.FitnessCenterItemResponse

interface SearchLookForContract {
    interface View {


        fun showSearchLook(fitnessList: MutableList<FitnessCenterItemResponse>)

        fun showSearchNoFind()

        fun showBackPage()

    }

    interface Presenter {

        fun searchLook(searchItem: String)

        fun backPage()


    }
}