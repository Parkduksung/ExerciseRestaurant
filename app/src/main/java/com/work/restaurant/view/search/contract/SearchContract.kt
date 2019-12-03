package com.work.restaurant.view.search.contract

interface SearchContract {

    interface View{

        fun showInit()

        fun showSearch()

    }

    interface Presenter{

        fun init()

        fun search()

    }
}