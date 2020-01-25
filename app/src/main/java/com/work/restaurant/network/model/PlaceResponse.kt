package com.work.restaurant.network.model

import com.work.restaurant.network.model.place.Results

class PlaceResponse {
    var html_attributions: Array<String>? = null
    var status: String? = null
    var next_page_token: String? = null
    var results: Array<Results>? = null
}


