package com.work.restaurant.network.room.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.work.restaurant.network.model.ExerciseSetResponse

class ExerciseSetConverter {
    @TypeConverter
    fun listToJson(value: List<ExerciseSetResponse>?): String {

        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<ExerciseSetResponse>? {

        val objects =
            Gson().fromJson(
                value,
                Array<ExerciseSetResponse>::class.java
            ) as Array<ExerciseSetResponse>
        return objects.toList()
    }
}