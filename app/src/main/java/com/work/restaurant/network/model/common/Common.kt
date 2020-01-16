package com.work.restaurant.network.model.common

import com.work.restaurant.network.api.GoogleApi
import com.work.restaurant.network.model.remote.RetrofitClient

object Common {

    private val GOOGLE_API_URL = "https://maps.googleapis.com/"

    val googleApiService: GoogleApi
        get() = RetrofitClient.getClient(GOOGLE_API_URL).create(GoogleApi::class.java)
}