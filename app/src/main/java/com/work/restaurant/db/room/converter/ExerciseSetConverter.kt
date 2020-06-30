package com.work.restaurant.db.room.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.work.restaurant.db.room.entity.ExerciseSetResponse

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
            )
        return objects.toList()
    }
}