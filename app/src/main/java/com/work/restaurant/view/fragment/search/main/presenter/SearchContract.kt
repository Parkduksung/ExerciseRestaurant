package com.work.restaurant.view.fragment.search.main.presenter

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