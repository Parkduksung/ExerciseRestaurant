package com.work.restaurant.view.home.address.presenter

import android.widget.TextView
import com.work.restaurant.data.repository.road.RoadRepository

class HomeAddressPresenter(
    private val homeAddressView: HomeAddressContract.View,
    private val roadRepository: RoadRepository
) :
    HomeAddressContract.Presenter {

    override fun getRoadItem(address: TextView, clickData: String, area: String, zone: String) {

        roadRepository.getLocalData(
            zone,
            area,
            clickData,
            callback = { list ->
                if (list != null) {
                    homeAddressView.showRoadItem(address, list)
                }
            })
    }
}
