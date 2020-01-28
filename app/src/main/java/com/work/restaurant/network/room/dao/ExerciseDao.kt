package com.work.restaurant.network.room.dao

import androidx.room.*
import com.work.restaurant.network.room.entity.ExerciseEntity

@Dao
interface ExerciseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun registerExercise(exerciseEntity: ExerciseEntity)

    @Query("SELECT COUNT(*) FROM exercise")
    fun getAllCount(): Int

    @Query("SELECT * FROM exercise")
    fun getAll(): List<ExerciseEntity>


    @Query("SELECT * FROM exercise WHERE date = (:today)")
    fun getTodayItem(today: String): List<ExerciseEntity>

    @Delete
    fun deleteExercise(exerciseEntity: ExerciseEntity)



}