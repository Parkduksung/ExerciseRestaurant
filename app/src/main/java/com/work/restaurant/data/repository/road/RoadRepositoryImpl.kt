package com.work.restaurant.data.repository.road

import com.work.restaurant.data.source.local.road.RoadLocalDataCountCallback
import com.work.restaurant.data.source.local.road.RoadLocalDataRegisterCallback
import com.work.restaurant.data.source.local.road.RoadLocalDataSourceCallback
import com.work.restaurant.data.source.local.road.RoadLocalDataSourceImpl
import com.work.restaurant.network.room.entity.AddressEntity

class RoadRepositoryImpl private constructor(private val roadRemoteDataSourceImpl: RoadLocalDataSourceImpl) :
    RoadRepository {
    override fun registerAddress(callback: Callback) {
        roadRemoteDataSourceImpl.registerAddress(object :
            RoadLocalDataRegisterCallback {
            override fun onSuccess(list: List<AddressEntity>) {
                callback.onSuccess(list)
            }

            override fun onFailure(message: String) {
                callback.onFailure(message)
            }
        })
    }


    override fun getAddressCount(callback: RoadRepositoryDataCountCallback) {
        roadRemoteDataSourceImpl.getAddressCount(object :
            RoadLocalDataCountCallback {
            override fun onSuccess(state: Boolean) {
                callback.onSuccess(state)
            }

            override fun onFailure(message: String) {
                callback.onFailure(message)
            }

        })
    }


    override fun getLocalData(
        zone: String,
        area: String,
        clickData: String,
        callback: RoadRepositoryCallback
    ) {
        roadRemoteDataSourceImpl.getLocalData(
            zone,
            area,
            clickData,
            object : RoadLocalDataSourceCallback {
                override fun onSuccess(list: List<String>) {
                    callback.onSuccess(list)
                }

                override fun onFailure(message: String) {
                    callback.onFailure(message)

                }
            })
    }

    companion object {

        private var instance: RoadRepositoryImpl? = null

        fun getInstance(
            roadLocalDataSourceImpl: RoadLocalDataSourceImpl
        ): RoadRepositoryImpl =
            instance ?: RoadRepositoryImpl(roadLocalDataSourceImpl)
                .also {
                    instance = it
                }
    }

}