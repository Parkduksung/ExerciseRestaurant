package com.work.restaurant.data.source.local.road

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.work.restaurant.data.model.RoadModel
import com.work.restaurant.util.App

class RoadLocalDataSourceImpl : RoadLocalDataSource {
    override fun getLocalData(
        zone: String,
        area: String,
        clickData: String,
        callback: RoadLocalDataSourceCallback
    ) {

        when (area) {

            "인천" -> {

                if (zone == "dong") {
                    val assetManager = App.instance.context().assets

                    val inputStream = assetManager.open("incheon.json")

                    val string = inputStream.bufferedReader().use { it.readText() }

                    val gson = Gson()

                    val result = gson.fromJson(string, JsonArray::class.java)

                    val list = mutableListOf<RoadModel>()

                    result.forEach {
                        val road = gson.fromJson(it, RoadModel::class.java)
                        if (road.gunGu == clickData) {
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

                    val inputStream = assetManager.open("incheon.json")

                    val string = inputStream.bufferedReader().use { it.readText() }

                    val gson = Gson()

                    val result = gson.fromJson(string, JsonArray::class.java)

                    val list = mutableListOf<RoadModel>()

                    result.forEach {
                        val road = gson.fromJson(it, RoadModel::class.java)
                        list.add(road)

                    }

                    if (list.isNotEmpty()) {
                        callback.onSuccess(list.map { it.gunGu }.distinct().sorted())
                    } else {
                        callback.onFailure("error")
                    }
                }


            }

            "서울" -> {


                if (zone == "dong") {
                    val assetManager = App.instance.context().assets

                    val inputStream = assetManager.open("seoul.json")

                    val string = inputStream.bufferedReader().use { it.readText() }

                    val gson = Gson()

                    val result = gson.fromJson(string, JsonArray::class.java)

                    val list = mutableListOf<RoadModel>()

                    result.forEach {
                        val road = gson.fromJson(it, RoadModel::class.java)
                        if (road.gunGu == clickData) {
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

                    val inputStream = assetManager.open("seoul.json")

                    val string = inputStream.bufferedReader().use { it.readText() }

                    val gson = Gson()

                    val result = gson.fromJson(string, JsonArray::class.java)

                    val list = mutableListOf<RoadModel>()

                    result.forEach {
                        val road = gson.fromJson(it, RoadModel::class.java)
                        list.add(road)

                    }

                    if (list.isNotEmpty()) {
                        callback.onSuccess(list.map { it.gunGu }.distinct().sorted())
                    } else {
                        callback.onFailure("error")
                    }
                }

            }


        }

//
//        val assetManager = App.instance.context().assets
//
//        val inputStream = assetManager.open("incheon.json")
//
//        val string = inputStream.bufferedReader().use { it.readText() }
//
//        val gson = Gson()
//
//        val result = gson.fromJson(string, JsonArray::class.java)
//
//        val list = mutableListOf<RoadModel>()
//
//        result.forEach {
//            val road = gson.fromJson(it, RoadModel::class.java)
//            if (road.gunGu == "계양구") {
//                list.add(road)
//            }
//        }
//
//        if (list.isNotEmpty()) {
//            callback.onSuccess(list.map { it.dong }.distinct().sorted())
//        } else {
//            callback.onFailure("error")
//        }
    }

    companion object {

        private var instance: RoadLocalDataSourceImpl? = null

        fun getInstance(): RoadLocalDataSourceImpl =
            instance ?: RoadLocalDataSourceImpl().also {
                instance = it
            }

    }


}