package com.work.restaurant.data.source.local.road

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.work.restaurant.data.model.RoadModel
import com.work.restaurant.db.room.database.AddressDatabase
import com.work.restaurant.db.room.entity.AddressEntity
import com.work.restaurant.util.App
import com.work.restaurant.util.AppExecutors
import com.work.restaurant.view.home.address.HomeAddressActivity

class RoadLocalDataSourceImpl(
    private val addressDatabase: AddressDatabase,
    private val appExecutors: AppExecutors
) : RoadLocalDataSource {

    override fun getLocalData(
        zone: String,
        area: String,
        clickData: String,
        callback: (list: List<String>?) -> Unit
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
                if (road.si.contains(HomeAddressActivity.si, false) && road.gunGu == clickData) {
                    list.add(road)
                }
            }

            if (list.isNotEmpty()) {
                callback(list.map { it.dong }.distinct().sorted())
            } else {
                callback(null)
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
                callback(list.map { it.gunGu }.distinct().sorted())
            } else {
                callback(null)
            }
        }
    }

    override fun getAddressCount(callback: (isSuccess: Boolean) -> Unit) {

        appExecutors.diskIO.execute {

            val getAddressCount =
                addressDatabase.addressDao().getAllCount()

            appExecutors.mainThread.execute {
                if (getAddressCount != 0) {
                    callback(true)
                } else {
                    callback(false)
                }
            }
        }
    }

    override fun isContainAddress(callback: (isSuccess: Boolean) -> Unit) {
        val thread = Thread {
            val getAddressCount =
                addressDatabase.addressDao().getAllCount()
            if (getAddressCount != 0) {
                callback(true)
            } else {
                callback(false)
            }
        }
        thread.start()
    }

    override fun registerAddress(callback: (list: List<AddressEntity>?) -> Unit) {

        val thread = Thread {
            val assetManager =
                App.instance.context().assets

            val inputStream =
                assetManager.open("Address.json")

            val string =
                inputStream.bufferedReader().use { it.readLine() }

            val gson = Gson()

            val result =
                gson.fromJson(string, JsonArray::class.java)


            if (addressDatabase.isOpen) {

                result.forEach {
                    val road =
                        gson.fromJson(it, AddressEntity::class.java)
                    addressDatabase.addressDao().registerAddress(road)
                }

                val addressList =
                    addressDatabase.addressDao().getAll()
                if (addressList.isNotEmpty()) {
                    callback(addressList)
                } else {
                    callback(null)
                }
            } else {
                callback(null)
            }
        }
        thread.start()
    }

}