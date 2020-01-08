package com.work.restaurant.view.activity.home

import android.widget.TextView
import com.work.restaurant.data.repository.road.RoadRepository
import com.work.restaurant.data.repository.road.RoadRepositoryCallback

class HomeAddressPresenter(
    private val homeAddressView: HomeAddressContract.View,
    private val roadRepository: RoadRepository
) :
    HomeAddressContract.Presenter {
    override fun backPage() {
        homeAddressView.showBackPage()
    }

    override fun getRoadItem(address: TextView, clickData: String, area: String, zone: String) {

        roadRepository.getLocalData(zone, area, clickData, object : RoadRepositoryCallback {
            override fun onSuccess(list: List<String>) {
                homeAddressView.showRoadItem(address, list)
            }

            override fun onFailure(message: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }
}
