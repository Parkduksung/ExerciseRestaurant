package com.work.restaurant.network.model.place

class Results {

    var geometry: Geometry? = null
    var opening_hours: OpeningHours? = null
    var icon: String? = null
    var name: String? = null

    var photos: Array<Photos>? = null
    var id: String? = null
    var place_id: String? = null
    var reference: String? = null
    var scope: String? = null
    var types: Array<String>? = null
    var vicinity: String? = null
    var price_level: Int? = null
    var user_ratings_total: Int? = null
    var ration: Double = 0.0

}