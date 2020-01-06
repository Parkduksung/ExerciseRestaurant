package com.work.restaurant.view.activity.home

class HomeAddressPresenter(private val homeAddressView: HomeAddressContract.View) :
    HomeAddressContract.Presenter {
    override fun backPage() {
        homeAddressView.showBackPage()
    }
}
