package com.work.restaurant.data.source.local.road

interface RoadLocalDataSource {
    fun getLocalData(
        zone: String,
        area: String,
        clickData: String,
        callback: RoadLocalDataSourceCallback
    )

    fun getAddressCount(callback: RoadLocalDataCountCallback)

    fun isContainAddress(callback: RoadLocalDataCountCallback)

    fun registerAddress(roadLocalDataRegisterCallback: RoadLocalDataRegisterCallback)

}