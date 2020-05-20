package com.work.restaurant.data.repository.road

import com.work.restaurant.data.source.local.road.RoadLocalDataSource
import com.work.restaurant.network.room.entity.AddressEntity

class RoadRepositoryImpl(
    private val roadRemoteDataSource: RoadLocalDataSource
) : RoadRepository {

    override fun getLocalData(
        zone: String,
        area: String,
        clickData: String,
        callback: (list: List<String>?) -> Unit
    ) {
        roadRemoteDataSource.getLocalData(zone, area, clickData, callback)
    }

    override fun getAddressCount(callback: (isSuccess: Boolean) -> Unit) {

        roadRemoteDataSource.getAddressCount(callback)
    }

    override fun registerAddress(callback: (list: List<AddressEntity>?) -> Unit) {
        roadRemoteDataSource.registerAddress(callback)
    }
}