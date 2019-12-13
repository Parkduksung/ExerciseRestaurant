package com.work.restaurant.view.search.look_for.presenter

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