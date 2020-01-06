package com.work.restaurant.view.fragment.search.rank.presenter

import com.work.restaurant.network.model.FitnessCenterItemResponse

interface SearchRankContract {

    interface View{

        fun showSettings()

        fun showFitnessList(fitnessList: List<FitnessCenterItemResponse>)


    }

    interface Presenter{

        fun settings()

        fun getFitnessList()

    }


}