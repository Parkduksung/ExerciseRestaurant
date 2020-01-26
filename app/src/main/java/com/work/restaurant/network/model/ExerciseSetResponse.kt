package com.work.restaurant.network.model

import androidx.room.ColumnInfo

data class ExerciseSetResponse(
    @ColumnInfo(name = "exerciseSet_name")
    val exerciseSetName: String,
    @ColumnInfo(name = "exerciseSet_kg")
    val exerciseSetKg: String,
    @ColumnInfo(name = "exerciseSet_count")
    val exerciseSetCount: String
)