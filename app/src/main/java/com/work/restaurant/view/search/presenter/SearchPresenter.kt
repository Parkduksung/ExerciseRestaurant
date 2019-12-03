package com.work.restaurant.view.search.presenter

import com.work.restaurant.view.search.contract.SearchContract

class SearchPresenter(private val searchView: SearchContract.View) : SearchContract.Presenter {
    override fun init() {
        searchView.showInit()
    }

    override fun search() {
        searchView.showSearch()
    }
}