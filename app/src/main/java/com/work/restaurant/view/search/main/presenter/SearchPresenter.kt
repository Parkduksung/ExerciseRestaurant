package com.work.restaurant.view.search.main.presenter

class SearchPresenter(private val searchView: SearchContract.View) : SearchContract.Presenter {
    override fun init() {
        searchView.showInit()
    }
}