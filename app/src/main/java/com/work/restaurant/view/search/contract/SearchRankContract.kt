package com.work.restaurant.view.search.contract

import com.work.restaurant.network.model.FitnessCenterItemModel

interface SearchRankContract {

    interface View{

        fun showSettings()

        fun showFitnessList(fitnessList: List<FitnessCenterItemModel>)


    }

    interface Presenter{

        fun settings()

        fun getFitnessList()

    }


}