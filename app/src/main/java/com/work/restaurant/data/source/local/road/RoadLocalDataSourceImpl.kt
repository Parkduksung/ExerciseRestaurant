package com.work.restaurant.data.source.local.road

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.work.restaurant.data.model.RoadModel
import com.work.restaurant.network.room.database.AddressDatabase
import com.work.restaurant.network.room.entity.AddressEntity
import com.work.restaurant.util.App
import com.work.restaurant.util.AppExecutors
import com.work.restaurant.view.home.address.HomeAddressActivity.Companion.si

class RoadLocalDataSourceImpl private constructor(
    private val addressDatabase: AddressDatabase,
    private val appExecutors: AppExecutors
) : RoadLocalDataSource {
    override fun registerAddress(roadLocalDataRegisterCallback: RoadLocalDataRegisterCallback) {

        val thread = Thread {
            val assetManager = App.instance.context().assets

            val inputStream = assetManager.open("Address.json")

            val string = inputStream.bufferedReader().use { it.readLine() }

            val gson = Gson()

            val result = gson.fromJson(string, JsonArray::class.java)

            val addressDatabase = AddressDatabase.getInstance(App.instance.context())

            if (addressDatabase?.isOpen!!) {

                result.forEach {
                    val road = gson.fromJson(it, AddressEntity::class.java)
                    addressDatabase.addressDao().registerAddress(road)
                }

                val addressList = addressDatabase.addressDao().getAll()
                if (addressList.isNotEmpty()) {
                    roadLocalDataRegisterCallback.onSuccess(addressList)
                } else {
                    roadLocalDataRegisterCallback.onFailure("error")
                }
            } else {
                roadLocalDataRegisterCallback.onFailure("error")
            }
        }
        thread.start()
    }

    override fun isContainAddress(callback: RoadLocalDataCountCallback) {

        val thread = Thread {
            val addressDatabase = AddressDatabase.getInstance(App.instance.context())

            val getAddressCount = addressDatabase?.addressDao()?.getAllCount()

            if (addressDatabase?.isOpen!!) {
                if (getAddressCount != null) {
                    callback.onSuccess(true)
                } else {
                    callback.onSuccess(false)
                }
            } else {
                callback.onFailure("error!")
            }
        }

        thread.start()

    }

    override fun getAddressCount(callback: RoadLocalDataCountCallback) {

        appExecutors.diskIO.execute {

            val getAddressCount = addressDatabase.addressDao().getAllCount()

            if (addressDatabase.isOpen) {

                appExecutors.mainThread.execute {
                    callback.onSuccess(true)
                }
            } else {
                callback.onFailure("error!")
            }
        }

    }

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

        fun getInstance(
            addressDatabase: AddressDatabase,
            appExecutors: AppExecutors
        ): RoadLocalDataSourceImpl =
            instance
                ?: RoadLocalDataSourceImpl(
                    addressDatabase,
                    appExecutors
                )
                .also {
                    instance = it
                }

    }


}