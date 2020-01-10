package com.work.restaurant.data.source.local.road

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.work.restaurant.data.model.RoadModel
import com.work.restaurant.util.App
import com.work.restaurant.view.home.address.HomeAddressActivity.Companion.si

class RoadLocalDataSourceImpl : RoadLocalDataSource {
    override fun getLocalData(
        zone: String,
        area: String,
        clickData: String,
        callback: RoadLocalDataSourceCallback
    ) {

        if (zone == "dong") {
            val assetManager = App.instance.context().assets

            val inputStream = assetManager.open("Address.json")

            val string = inputStream.bufferedReader().use { it.readText() }

            val gson = Gson()

            val result = gson.fromJson(string, JsonArray::class.java)

            val list = mutableListOf<RoadModel>()

            result.forEach {
                val road = gson.fromJson(it, RoadModel::class.java)
                if (road.si.contains(si, false) && road.gunGu == clickData) {
                    list.add(road)
                }
            }

            if (list.isNotEmpty()) {
                callback.onSuccess(list.map { it.dong }.distinct().sorted())
            } else {
                callback.onFailure("error")
            }
        }

        if (zone == "gunGu") {
            val assetManager = App.instance.context().assets

            val inputStream = assetManager.open("Address.json")

            val string = inputStream.bufferedReader().use { it.readText() }

            val gson = Gson()

            val result = gson.fromJson(string, JsonArray::class.java)

            val list = mutableListOf<RoadModel>()

            result.forEach {
                val road = gson.fromJson(it, RoadModel::class.java)
                if (road.si.contains(clickData, false)) {
                    list.add(road)
                }
            }

            if (list.isNotEmpty()) {
                callback.onSuccess(list.map { it.gunGu }.distinct().sorted())
            } else {
                callback.onFailure("error")
            }
        }

    }

    companion object {

        private var instance: RoadLocalDataSourceImpl? = null

        fun getInstance(): RoadLocalDataSourceImpl =
            instance ?: RoadLocalDataSourceImpl().also {
                instance = it
            }

    }


}