package com.work.restaurant.network.room.dao

import androidx.room.*
import com.work.restaurant.network.room.entity.ExerciseEntity

@Dao
interface ExerciseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun registerExercise(exerciseEntity: ExerciseEntity): Long

    @Query("SELECT COUNT(*) FROM exercise")
    fun getAllCount(): Int

    @Query("SELECT * FROM exercise WHERE userId = (:userId)")
    fun getAll(userId: String): List<ExerciseEntity>


    @Query("SELECT * FROM exercise WHERE userId = (:userId) AND date = (:today)")
    fun getTodayItem(userId: String, today: String): List<ExerciseEntity>

    @Delete
    fun deleteExercise(exerciseEntity: ExerciseEntity): Int


}