package com.work.restaurant.data.repository.road

import com.work.restaurant.data.source.local.road.RoadLocalDataSourceCallback
import com.work.restaurant.data.source.local.road.RoadLocalDataSourceImpl

class RoadRepositoryImpl private constructor(private val roadRemoteDataSourceImpl: RoadLocalDataSourceImpl) :
    RoadRepository {

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
            roadRemoteDataSourceImpl: RoadLocalDataSourceImpl
        ): RoadRepositoryImpl =
            instance ?: RoadRepositoryImpl(roadRemoteDataSourceImpl).also {
                instance = it
            }
    }

}